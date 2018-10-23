package cn.com.chinalife.ecdata.service.fupin;

import cn.com.chinalife.ecdata.entity.fupin.PageClick;
import cn.com.chinalife.ecdata.entity.query.QueryPara;

import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/2.
 */
public interface PageClickService {

    List<List<PageClick>> getPageClickList();

    List<PageClick> getPageClickListForTimeSpan(QueryPara queryPara);

    List<PageClick> getPageClickListForTimeSpanFromStatTable(QueryPara queryPara);

    int updatePageClick(List<PageClick> pageClickList);
}
