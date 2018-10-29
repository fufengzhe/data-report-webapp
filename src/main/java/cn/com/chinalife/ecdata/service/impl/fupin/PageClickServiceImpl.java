package cn.com.chinalife.ecdata.service.impl.fupin;

import cn.com.chinalife.ecdata.dao.sqlDao.fupin.PageClickDao;
import cn.com.chinalife.ecdata.entity.fupin.PageClick;
import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.service.fupin.PageClickService;
import cn.com.chinalife.ecdata.utils.*;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiexiangyu on 2018/3/1.
 */
@Service
public class PageClickServiceImpl implements PageClickService {
    private final Logger logger = LoggerFactory.getLogger(PageClickServiceImpl.class);
    @Autowired
    PageClickDao pageClickDao;

    public List<List<PageClick>> getPageClickList() {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(null));
        List<List<PageClick>> lists = new ArrayList<List<PageClick>>();
        QueryPara queryPara = new QueryPara();
        queryPara.setStartDate(DateUtils.getYesterday());
        queryPara.setEndDate(DateUtils.getYesterday());
        List<PageClick> pageClickListOfDate = this.getPageClickListForTimeSpanFromStatTable(queryPara);
        lists.add(pageClickListOfDate);

        queryPara.setStartDate(DateUtils.getBeforeXDay(7));
        queryPara.setEndDate(DateUtils.getBeforeXDay(1));
        List<PageClick> pageClickListForTrendOfDate = this.getPageClickListForTimeSpanTrendFromStatTable(queryPara);
        lists.add(pageClickListForTrendOfDate);
        logger.info("service返回结果为 {}", JSON.toJSONString(lists));
        return lists;
    }

    public List<PageClick> getPageClickListForTimeSpanFromStatTable(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        DataSourceContextHolder.setDbType(CommonConstant.businessDataSource);
        List<PageClick> pageClickList = pageClickDao.getPageClickListForTimeSpanFromStatTable(queryPara);
        logger.info("service返回结果为 {}", JSON.toJSONString(pageClickList));
        return pageClickList;
    }

    private List<PageClick> getPageClickListForTimeSpanTrendFromStatTable(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        DataSourceContextHolder.setDbType(CommonConstant.businessDataSource);
        List<PageClick> pageClickList = pageClickDao.getPageClickListForTimeSpanTrendFromStatTable(queryPara);
        for (PageClick pageClick : pageClickList) {
            pageClick.setClickPVPerPerson1(CommonUtils.divideWithXPrecision(new BigDecimal(pageClick.getClickPV1()), new BigDecimal(pageClick.getClickUV1()), 2));
            pageClick.setClickPVPerPerson2(CommonUtils.divideWithXPrecision(new BigDecimal(pageClick.getClickPV2()), new BigDecimal(pageClick.getClickUV2()), 2));
            pageClick.setClickPVPerPerson3(CommonUtils.divideWithXPrecision(new BigDecimal(pageClick.getClickPV3()), new BigDecimal(pageClick.getClickUV3()), 2));
            pageClick.setClickPVPerPerson4(CommonUtils.divideWithXPrecision(new BigDecimal(pageClick.getClickPV4()), new BigDecimal(pageClick.getClickUV4()), 2));
            pageClick.setClickPVPerPerson5(CommonUtils.divideWithXPrecision(new BigDecimal(pageClick.getClickPV5()), new BigDecimal(pageClick.getClickUV5()), 2));
            pageClick.setClickPVPerPerson6(CommonUtils.divideWithXPrecision(new BigDecimal(pageClick.getClickPV6()), new BigDecimal(pageClick.getClickUV6()), 2));
            pageClick.setClickPVPerPerson7(CommonUtils.divideWithXPrecision(new BigDecimal(pageClick.getClickPV7()), new BigDecimal(pageClick.getClickUV7()), 2));
        }
        logger.info("service返回结果为 {}", JSON.toJSONString(pageClickList));
        return pageClickList;
    }


    public List<PageClick> getPageClickListForTimeSpan(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        List<String> fupinPageUrlList = FileUtils.getStrListFromSpecifiedFile("fupinPageUrlList.txt");
        String pageUrlFilter = getPageUrlFilterUsingList(fupinPageUrlList);
        queryPara.setWhereCondition(pageUrlFilter);
        DataSourceContextHolder.setDbType(CommonConstant.fupinDataSource);
        List<PageClick> pageClickList = pageClickDao.getPageClickListForTimeSpan(queryPara);
        List<PageClick> topicPageList = pageClickDao.getTopicPageList();
        DataSourceContextHolder.setDbType(CommonConstant.businessDataSource);
        List<PageClick> nonTopicPageList = pageClickDao.getNonTopicPageList();
        this.setChnNameForPage(pageClickList, topicPageList, nonTopicPageList);
        logger.info("service返回结果为 {}", JSON.toJSONString(pageClickList));
        return pageClickList;
    }

    private String getPageUrlFilterUsingList(List<String> fupinPageUrlList) {
        StringBuilder pageUrlFilter = new StringBuilder(" (");
        if (fupinPageUrlList != null && fupinPageUrlList.size() > 0) {
            pageUrlFilter.append(" page_url LIKE '").append(fupinPageUrlList.get(0)).append("'");
            for (int i = 1; i < fupinPageUrlList.size(); i++) {
                pageUrlFilter.append(" OR page_url LIKE '").append(fupinPageUrlList.get(i)).append("'");
            }
            pageUrlFilter.append(" )");
            return pageUrlFilter.toString();
        } else {
            return null;
        }
    }

    private void setChnNameForPage(List<PageClick> pageClickList, List<PageClick> topicPageList, List<PageClick> nonTopicPageList) {
        Map<String, String> urlAndNameOfTopicPageMap = new HashMap<String, String>();
        Map<String, String> urlAndNameOfNonTopicPageMap = new HashMap<String, String>();
        for (PageClick pageClick : topicPageList) {
            urlAndNameOfTopicPageMap.put(pageClick.getPageId().toString(), pageClick.getPageName());
        }
        for (PageClick pageClick : nonTopicPageList) {
            urlAndNameOfNonTopicPageMap.put(pageClick.getPageUrl(), pageClick.getPageName());
        }
        for (PageClick pageClick : pageClickList) {
            if (0 == pageClick.getPageId()) {
                //非topic类的url
                int parameterIndex = pageClick.getPageUrl().indexOf("?");
                String key = pageClick.getPageUrl();
                if (parameterIndex > 0) {
                    key = pageClick.getPageUrl().substring(0, parameterIndex);
                }
                pageClick.setPageName(urlAndNameOfNonTopicPageMap.get(key) == null ? "未知" : urlAndNameOfNonTopicPageMap.get(key));
            } else {
                String pageId = pageClick.getPageId().toString();
                if (!pageId.matches("[0-9]+")) {
                    for (int i = 0; i < pageId.toCharArray().length; i++) {
                        if (!Character.isDigit(pageId.charAt(i))) {
                            pageId = pageId.substring(0, i);
                            break;
                        }
                    }
                }
                pageClick.setPageName(urlAndNameOfTopicPageMap.get(pageId) == null ? "未知" : urlAndNameOfTopicPageMap.get(pageId));
            }
        }
    }

    public int updatePageClick(List<PageClick> pageClickList) {
        if (pageClickList != null && pageClickList.size() > 0) {
            return pageClickDao.updatePageClick(pageClickList);
        } else {
            return 0;
        }
    }
}
