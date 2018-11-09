package cn.com.chinalife.ecdata.dao.sqlDao.fupin;

import cn.com.chinalife.ecdata.entity.fupin.PageClick;
import cn.com.chinalife.ecdata.entity.query.QueryPara;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/2.
 */
@Repository
public interface PageClickDao {
    List<PageClick> getPageClickListForTimeSpan(QueryPara queryPara);

    List<PageClick> getTopicPageList();

    List<PageClick> getNonTopicPageList();

    int updatePageClick(List<PageClick> pageClickList);

    List<PageClick> getPageClickListForTimeSpanFromStatTable(QueryPara queryPara);

    List<PageClick> getPageClickListForTimeSpanTrendFromStatTable(QueryPara queryPara);

    List<PageClick> getPageClickIPInfoList(QueryPara queryPara);

    int updatePageClickIPInfo(List<PageClick> pageClickIPInfoList);

    List<PageClick> getPageClickCompanyDistributeList(QueryPara queryPara);

    List<PageClick> getPageClickLocationDistributeList(QueryPara queryPara);
}
