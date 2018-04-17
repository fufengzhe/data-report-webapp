package cn.com.chinalife.ecdata.service.impl;

import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.trade.Premium;
import cn.com.chinalife.ecdata.entity.user.ActiveUser;
import cn.com.chinalife.ecdata.entity.user.RegisterUser;
import cn.com.chinalife.ecdata.service.ScheduleService;
import cn.com.chinalife.ecdata.service.trade.LifePremiumService;
import cn.com.chinalife.ecdata.service.trade.PropertyPremiumService;
import cn.com.chinalife.ecdata.service.user.ActiveUserService;
import cn.com.chinalife.ecdata.service.user.RegisterUserService;
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

    @Scheduled(cron = "0 50 2 * * ?")
    private void scheduledEntryForUpdate() throws InterruptedException {
        // 默认更新昨天所有渠道的数据
        QueryPara queryPara = prepareQueryPara();
        int effectedRowNum;

        logger.info("开始更新注册相关的数据");
        effectedRowNum = this.updateRegister(queryPara);
        logger.info("结束更新注册相关的数据，受影响的条数为 {}", effectedRowNum);

        logger.info("开始更新活跃相关的天维度数据");
        queryPara.setTimeSpan(CommonConstant.statTimeSpanOfDate);
        effectedRowNum = this.updateActive(queryPara);
        logger.info("结束更新活跃相关的天维度数据，受影响的条数为 {}", effectedRowNum);

        logger.info("开始更新活跃相关的月维度数据");
        queryPara.setStartDate(DateUtils.getMonthBeginDateUsingYesterday(DateUtils.getYesterday()));
        queryPara.setTimeSpan(CommonConstant.statTimeSpanOfMonth);
        effectedRowNum = this.updateActive(queryPara);
        logger.info("结束更新活跃相关的月维度数据，受影响的条数为 {}", effectedRowNum);

        logger.info("开始更新寿险保费相关数据");
        effectedRowNum = this.updateLifePremium(queryPara);
        logger.info("结束更新寿险保费相关数据，受影响的条数为 {}", effectedRowNum);

        logger.info("开始更新财险保费相关数据");
        effectedRowNum = this.updatePropertyPremium(queryPara);
        logger.info("结束更新财险保费相关数据，受影响的条数为 {}", effectedRowNum);
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

    public int updateActive(QueryPara queryPara) {
        int effectedRowNum = 0;
        int temp;
        List<ActiveUser> activeUserList;
        //电商自有渠道
        activeUserList = activeUserService.getActiveUserNumOfAllSources(queryPara);
        temp = activeUserService.updateActive(activeUserList);
        effectedRowNum += temp;
        //e宝渠道
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

        logger.info("开始插入月维度寿险保费相关数据");
        queryPara.setTimeSpan(CommonConstant.statTimeSpanOfMonth);
        premiumList = lifePremiumService.getLifePremiumDetail(queryPara);
        temp = lifePremiumService.updateLifePremium(premiumList);
        effectedRowNum += temp;
        logger.info("结束插入月维度寿险保费相关数据");

        logger.info("开始插入年维度寿险保费相关数据");
        queryPara.setTimeSpan(CommonConstant.statTimeSpanOfYear);
        premiumList = lifePremiumService.getLifePremiumDetail(queryPara);
        temp = lifePremiumService.updateLifePremium(premiumList);
        effectedRowNum += temp;
        logger.info("结束插入年维度寿险保费相关数据");
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

    //因服务器无法发送邮件，定时执行的口关闭，可通过浏览器进行调用处理，防止服务器端执行无效的操作
//    @Scheduled(cron = "0 1 0 ? * MON")
//    private void scheduledEntryForOfficialSiteQuery() throws InterruptedException, MessagingException {
//        // 周一默认查询过去一周的数据
//        QueryPara queryPara = new QueryPara();
//        queryPara.setStartDate(DateUtils.getBeforeXDay(7));
//        queryPara.setEndDate(DateUtils.getBeforeXDay(1));
//        queryPara.setQueryDate(DateUtils.getBeforeXDay(5)); //周三
//        queryPara.setUserSource("009002000000");
//        logger.info("开始查询官网过去一周天维度及周三小时维度活跃数据");
//        List<List<ActiveUser>> activeUserList = this.queryOfficialSiteActiveNum(queryPara);
//        logger.info("结束查询官网过去一周天维度及周三小时维度活跃数据,查询结果为 {}", activeUserList);
//        this.sendOfficialSiteActiveNum(queryPara, activeUserList);
//    }

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
        MailUtils.sendHtmlMail("348452440@qq.com", new String[]{"344822404@qq.com", "842949135@qq.com"}, "348452440@qq.com", "1635454312@qq.com", "官网活跃数据", html.toString());
    }

    public List<List<ActiveUser>> queryOfficialSiteActiveNum(QueryPara queryPara) {
        return activeUserService.queryOfficialSiteActiveNum(queryPara);
    }


}
