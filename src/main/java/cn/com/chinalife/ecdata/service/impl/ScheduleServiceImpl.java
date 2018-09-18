package cn.com.chinalife.ecdata.service.impl;

import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.trade.Premium;
import cn.com.chinalife.ecdata.entity.user.ActiveUser;
import cn.com.chinalife.ecdata.entity.user.RegisterUser;
import cn.com.chinalife.ecdata.service.InitService;
import cn.com.chinalife.ecdata.service.ScheduleService;
import cn.com.chinalife.ecdata.service.location.LocationAnalysisService;
import cn.com.chinalife.ecdata.service.trade.LifePremiumService;
import cn.com.chinalife.ecdata.service.trade.PropertyPremiumService;
import cn.com.chinalife.ecdata.service.user.ActiveUserService;
import cn.com.chinalife.ecdata.service.user.RegisterUserService;
import cn.com.chinalife.ecdata.service.user.UserAttributeService;
import cn.com.chinalife.ecdata.service.user.UserRetentionService;
import cn.com.chinalife.ecdata.service.userShare.UserShareService;
import cn.com.chinalife.ecdata.utils.CommonConstant;
import cn.com.chinalife.ecdata.utils.DateUtils;
import cn.com.chinalife.ecdata.utils.HtmlUtils;
import cn.com.chinalife.ecdata.utils.MailUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.text.ParseException;
import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/6.
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {
    private final Logger logger = LoggerFactory.getLogger(ScheduleServiceImpl.class);

    @Autowired
    RegisterUserService registerUserService;

    @Autowired
    ActiveUserService activeUserService;

    @Autowired
    LifePremiumService lifePremiumService;

    @Autowired
    PropertyPremiumService propertyPremiumService;

    @Autowired
    LocationAnalysisService locationAnalysisService;

    @Autowired
    UserShareService userShareService;


    @Autowired
    UserRetentionService userRetentionService;

    @Autowired
    UserAttributeService userAttributeService;

    @Autowired
    InitService initService;

    @Scheduled(cron = "0 0 3 * * ?")
    private void scheduledEntryForUpdate() throws InterruptedException, ParseException {
        // 默认更新昨天所有渠道的数据
        QueryPara queryPara = prepareQueryPara();
        int effectedRowNum;

        logger.info("开始更新注册相关的数据");
        effectedRowNum = this.updateRegister(queryPara);
        initService.updateDataStatus(queryPara.getStartDate(), CommonConstant.statTimeSpanOfDate, CommonConstant.statIndexNameOfRegister, "各渠道注册数据", effectedRowNum);
        logger.info("结束更新注册相关的数据，影响的条数为 {}", effectedRowNum);

        logger.info("开始更新活跃相关的天维度数据");
        queryPara.setTimeSpan(CommonConstant.statTimeSpanOfDate);
        effectedRowNum = this.updateActive(queryPara);
        initService.updateDataStatus(queryPara.getStartDate(), CommonConstant.statTimeSpanOfDate, CommonConstant.statIndexNameOfActive, "各渠道天维度活跃数据", effectedRowNum);
        logger.info("结束更新活跃相关的天维度数据，影响的条数为 {}", effectedRowNum);

        logger.info("开始更新活跃相关的月维度数据");
        queryPara.setStartDate(DateUtils.getMonthBeginDateUsingYesterday(DateUtils.getYesterday()));
        queryPara.setTimeSpan(CommonConstant.statTimeSpanOfMonth);
        effectedRowNum = this.updateActive(queryPara);
        initService.updateDataStatus(queryPara.getStartDate(), CommonConstant.statTimeSpanOfMonth, CommonConstant.statIndexNameOfActive, "各渠道月维度活跃数据", effectedRowNum);
        logger.info("结束更新活跃相关的月维度数据，影响的条数为 {}", effectedRowNum);

        logger.info("开始更新寿险保费相关数据");
        effectedRowNum = this.updateLifePremium(queryPara);
        // 截止日期
        initService.updateDataStatus(DateUtils.getYesterday(), CommonConstant.statTimeSpanOfDate, CommonConstant.statIndexNameOfLifePremium, "寿险保费数据", effectedRowNum);
        logger.info("结束更新寿险保费相关数据，影响的条数为 {}", effectedRowNum);
    }

    // 因为财险保费不是直接依赖镜像库，依赖另一个中间表（文广开发商），所以单独拎出来并把任务开始时间往后挪
    @Scheduled(cron = "0 0 4 * * ?")
    private void scheduledEntryForPropertyPremiumUpdate() throws InterruptedException, ParseException {
        // 默认更新昨天所有渠道的数据
        QueryPara queryPara = prepareQueryPara();
        int effectedRowNum;
        logger.info("开始更新财险保费相关数据");
        effectedRowNum = this.updatePropertyPremium(queryPara);
        initService.updateDataStatus(DateUtils.getYesterday(), CommonConstant.statTimeSpanOfDate, CommonConstant.statIndexNameOfPropertyPremium, "财险保费数据", effectedRowNum);
        logger.info("结束更新财险保费相关数据，影响的条数为 {}", effectedRowNum);
    }


    private QueryPara prepareQueryPara() {
        QueryPara queryPara = new QueryPara();
        String yesterday = DateUtils.getYesterday();
        queryPara.setStartDate(yesterday);
        queryPara.setEndDate(yesterday);
        return queryPara;
    }

    // 根据前端传入的日期及渠道更新相应的数据，如无渠道相关信息则更新改日期区间内所有渠道的数据
    public int updateRegister(QueryPara queryPara) {
        List<RegisterUser> registerUserList = registerUserService.getRegisterUserNumOfAllSources(queryPara);
        return registerUserService.updateRegister(registerUserList);
    }

    public int updateActive(QueryPara queryPara) throws ParseException {
        int effectedRowNum = 0;
        int temp;
        List<ActiveUser> activeUserList;
        //电商自有渠道
        activeUserList = activeUserService.getActiveUserNumOfAllSources(queryPara);
        temp = activeUserService.updateActive(activeUserList);
        effectedRowNum += temp;
        //e宝渠道,因为e宝的oracle表格不是隔天更新的，所以要拿到截止到目前指标表中最晚的e宝活跃日期，在此基础上+1
        if (CommonConstant.statTimeSpanOfDate.equals(queryPara.getTimeSpan())) {
            List<String> latestDateOfEBaoZhang = activeUserService.getLatestDateOfEBaoZhang();
            if (latestDateOfEBaoZhang.size() > 0) {
                queryPara.setStartDate(DateUtils.addXDateBasedGivenDate(latestDateOfEBaoZhang.get(0), 1));
            }
        }
        activeUserList = activeUserService.getActiveUserNumOfEBaoZhang(queryPara);
        temp = activeUserService.updateActive(activeUserList);
        effectedRowNum += temp;
        return effectedRowNum;
    }

    public int updateLifePremium(QueryPara queryPara) {
        int effectedRowNum = 0;

        logger.info("开始删除历史所有寿险保费相关数据");
        int temp = lifePremiumService.deleteAllExistedRecord(CommonConstant.statIndexNameOfLifePremium);
        logger.info("结束删除历史所有寿险保费相关数据");
        effectedRowNum += temp;

        logger.info("开始插入天维度寿险保费相关数据");
        queryPara.setTimeSpan(CommonConstant.statTimeSpanOfDate);
        List<Premium> premiumList = lifePremiumService.getLifePremiumDetail(queryPara);
        temp = lifePremiumService.updateLifePremium(premiumList);
        effectedRowNum += temp;
        logger.info("结束插入天维度寿险保费相关数据");

        return effectedRowNum;
    }

    public int updatePropertyPremium(QueryPara queryPara) {
        int effectedRowNum = 0;

        logger.info("开始删除历史所有财险保费相关数据");
        int temp = propertyPremiumService.deleteAllExistedRecord(CommonConstant.statIndexNameListOfPropertyPremium);
        logger.info("结束删除历史所有财险保费相关数据");
        effectedRowNum += temp;

        logger.info("开始插入天维度财险保费相关数据");
        queryPara.setTimeSpan(CommonConstant.statTimeSpanOfDate);
        temp = propertyPremiumService.updatePropertyPremium(queryPara);
        effectedRowNum += temp;
        logger.info("结束插入天维度财险保费相关数据");

        return effectedRowNum;
    }

    @Scheduled(cron = "0 1 0 ? * MON")
    private void scheduledEntryForOfficialSiteQuery() throws InterruptedException, MessagingException {
        // 周一默认查询过去一周的数据
        QueryPara queryPara = new QueryPara();
        queryPara.setStartDate(DateUtils.getBeforeXDay(7));
        queryPara.setEndDate(DateUtils.getBeforeXDay(1));
        queryPara.setQueryDate(DateUtils.getBeforeXDay(5)); //周三
        queryPara.setUserSource("009002000000");
        logger.info("开始查询官网过去一周天维度及周三小时维度活跃数据");
        List<List<ActiveUser>> activeUserList = this.queryOfficialSiteActiveNum(queryPara);
        logger.info("结束查询官网过去一周天维度及周三小时维度活跃数据,查询结果为 {}", activeUserList);
        this.sendOfficialSiteActiveNum(queryPara, activeUserList);
    }

    public void sendOfficialSiteActiveNum(QueryPara queryPara, List<List<ActiveUser>> activeUserList) throws MessagingException {
        String[][] tableOfDate = activeUserService.getTableContent(activeUserList.get(0), new String[]{"日期", "活跃数"});
        String[][] tableOfHour = activeUserService.getTableContent(activeUserList.get(1), new String[]{"小时", "活跃数"});
        StringBuilder html = new StringBuilder("<html>");
        html.append("<table>");

        html.append("<tr><td>").append("过去一周(").append(queryPara.getStartDate()).append("~").append(queryPara.getEndDate()).append(")活跃数据如下表所示:").append("</tr></td>");
        html.append("<tr><td>").append(HtmlUtils.getTable(tableOfDate)).append("</tr></td>");

        html.append("<tr><td>").append("上周三(").append(queryPara.getQueryDate()).append(")活跃数据如下表所示:").append("</tr></td>");
        html.append("<tr><td>").append(HtmlUtils.getTable(tableOfHour)).append("</tr></td>");
        html.append("</table>");
        html.append("</html>");
        MailUtils.sendHtmlMail("348452440@qq.com", new String[]{"344822404@qq.com","swliux@isoftstone.com"}, "348452440@qq.com", "1635454312@qq.com", "官网活跃数据", html.toString());
//        MailUtils.sendHtmlMail("348452440@qq.com", new String[]{"348452440@qq.com", "348452440@qq.com"}, "348452440@qq.com", "348452440@qq.com", "官网活跃数据", html.toString());
    }

    public List<List<ActiveUser>> queryOfficialSiteActiveNum(QueryPara queryPara) {
        return activeUserService.queryOfficialSiteActiveNum(queryPara);
    }

    @Scheduled(cron = "0 30 0 * * ?")
    private void scheduledEntryForDistribute() throws Exception {
        // 周一默认查询过去一周的数据
        QueryPara queryPara = new QueryPara();
        queryPara.setStartDate(DateUtils.getYesterday());
        queryPara.setEndDate(DateUtils.getYesterday());
        logger.info("开始更新运营商及地理位置分布信息");
        int effectedRow = this.updateDistribute(queryPara);
        logger.info("结束更新运营商及地理位置分布信息,影响的条数为 {}", effectedRow);
    }

    public int updateDistribute(QueryPara queryPara) throws Exception {
        int effectedRow = 0;
        int temp;
        if (queryPara.getWhereCondition() == null || CommonConstant.distributeIndexNameOfRegisterMobile.equals(queryPara.getWhereCondition())) {
            logger.info("开始更新注册手机号相关的运营商及地理位置分布信息");
            temp = locationAnalysisService.updateRegisterMobileDistribute(queryPara);
            effectedRow += temp;
            initService.updateDataStatus(queryPara.getStartDate(), CommonConstant.statTimeSpanOfDate, CommonConstant.distributeIndexNameOfRegisterMobile, "注册手机号运营商及地理位置分布数据", temp);
            logger.info("结束更新注册手机号相关的运营商及地理位置分布信息,影响的条数为 {}", effectedRow);
        }
        if (queryPara.getWhereCondition() == null || CommonConstant.distributeIndexNameOfActiveIP.equals(queryPara.getWhereCondition())) {
            logger.info("开始更新活跃用户IP的运营商及地理位置分布信息");
            temp = locationAnalysisService.updateActiveIPDistribute(queryPara);
            effectedRow += temp;
            initService.updateDataStatus(queryPara.getStartDate(), CommonConstant.statTimeSpanOfDate, CommonConstant.distributeIndexNameOfActiveIP, "活跃用户IP运营商及地理位置分布数据", temp);
            logger.info("结束更新活跃用户IP的运营商及地理位置分布信息,影响的条数为 {}", effectedRow);
        }
        return effectedRow;
    }

    @Scheduled(cron = "0 10 0 * * ?")
    private void scheduledEntryForActiveTimeAndUserCollectionInvokeDis() throws Exception {
        // 周一默认查询过去一周的数据
        QueryPara queryPara = new QueryPara();
        queryPara.setStartDate(DateUtils.getYesterday());
        queryPara.setEndDate(DateUtils.getYesterday());
        logger.info("开始更新活跃用户小时分布及用户中心USER集合请求情况信息");
        int effectedRow = this.updateActiveTimeAndUserCollectionInvokeDis(queryPara);
        logger.info("结束更新活跃用户小时分布及用户中心USER集合请求情况信息,影响的条数为 {}", effectedRow);
    }

    private int updateActiveTimeAndUserCollectionInvokeDis(QueryPara queryPara) throws Exception {
        int effectedRow = 0;
        int temp;
        temp = this.updateActiveTimeDis(queryPara);
        effectedRow += temp;
        temp = this.updateUserCollectionInvokeDis(queryPara);
        effectedRow += temp;
        return effectedRow;
    }

    public int updateActiveTimeDis(QueryPara queryPara) {
        int effectedRow;
        logger.info("开始更新活跃用户小时分布信息");
        effectedRow = locationAnalysisService.updateActiveTimeDis(queryPara);
        initService.updateDataStatus(queryPara.getStartDate(), CommonConstant.statTimeSpanOfDate, CommonConstant.distributeIndexNameOfActiveHour, "活跃用户小时分布数据", effectedRow);
        logger.info("开始更新活跃用户小时分布信息");
        return effectedRow;
    }

    public int updateUserCollectionInvokeDis(QueryPara queryPara) {
        int effectedRow;
        logger.info("开始更新USER集合请求情况分布信息");
        effectedRow = locationAnalysisService.updateUserCollectionInvokeDis(queryPara);
        initService.updateDataStatus(queryPara.getStartDate(), CommonConstant.statTimeSpanOfDate, CommonConstant.distributeIndexNameOfUserCollection, "USER集合请求情况分布数据", effectedRow);
        logger.info("结束更新USER集合请求情况分布信息,影响的条数为 {}", effectedRow);
        return effectedRow;
    }

    @Scheduled(cron = "0 15 0 * * ?")
    private void scheduledEntryForMigrateCollectionDis() throws Exception {
        QueryPara queryPara = new QueryPara();
        queryPara.setStartDate(DateUtils.getYesterday());
        queryPara.setEndDate(DateUtils.getYesterday());
        this.updateMigrateCollection(queryPara);
    }

    public int updateMigrateCollection(QueryPara queryPara) {
        int effectedRow = 0;
        int temp;
        logger.info("开始更新migrate集合分布信息");
        temp = locationAnalysisService.updateMigrateCollection(queryPara);
        effectedRow += temp;
        logger.info("结束更新migrate集合分布信息,影响的行数为 {}", effectedRow);
        return effectedRow;
    }

    @Scheduled(cron = "0 30 4 * * ?")
    private void scheduledEntryForUserShareUpdate() throws InterruptedException, ParseException {
        // 默认更新昨天所有渠道的数据
        QueryPara queryPara = new QueryPara();
        queryPara.setStartDate(DateUtils.getYesterday());
        queryPara.setEndDate(DateUtils.getYesterday());
        this.updateUserShare(queryPara);
    }

    public int updateUserShare(QueryPara queryPara) {
        int effectedRowNum = 0;
        logger.info("开始更新共享条款相关数据");
        effectedRowNum = userShareService.updateUserShare(queryPara);
        initService.updateDataStatus(queryPara.getStartDate(), CommonConstant.statTimeSpanOfDate, CommonConstant.statIndexNameOfUserShare, "共享条款数据", effectedRowNum);
        logger.info("结束更新共享条款相关数据，影响的条数为 {}", effectedRowNum);
        return effectedRowNum;
    }

    @Scheduled(cron = "0 40 4 * * ?")
    private void scheduledEntryForUserRetentionUpdate() throws Exception {
        QueryPara queryPara = new QueryPara();
        queryPara.setStartDate(DateUtils.getYesterday());
        queryPara.setEndDate(DateUtils.getYesterday());
        this.updateUserRetention(queryPara);
    }

    public int updateUserRetention(QueryPara queryPara) throws Exception {
        int effectedRowNum = 0;
        logger.info("开始更新用户留存相关数据");
        effectedRowNum = userRetentionService.updateUserRetention(queryPara);
        initService.updateDataStatus(queryPara.getStartDate(), CommonConstant.statTimeSpanOfDate, "userRetention", "用户留存数据", effectedRowNum);
        logger.info("结束更新用户留存相关数据，影响的条数为 {}", effectedRowNum);
        return effectedRowNum;
    }

    @Scheduled(cron = "0 01 5 * * ?")
    private void scheduledEntryForUserAttributeUpdate() throws Exception {
        QueryPara queryPara = new QueryPara();
        queryPara.setStartDate("2018-01-01");
        queryPara.setEndDate(DateUtils.getYesterday());
        this.updateUserAttribute(queryPara, true);
    }

    public int updateUserAttribute(QueryPara queryPara, boolean isScheduledRun) throws ParseException {
        int effectedRowNum = 0;
        logger.info("开始更新用户属性相关数据");
        effectedRowNum = userAttributeService.updateUserAttribute(queryPara, isScheduledRun);
        logger.info("结束更新用户属性相关数据，影响的条数为 {}", effectedRowNum);
        return effectedRowNum;
    }
}
