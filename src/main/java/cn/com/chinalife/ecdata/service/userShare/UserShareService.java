package cn.com.chinalife.ecdata.service.userShare;

import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.userShare.UserShare;

import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/14.
 */
public interface UserShareService {
    int updateUserShare(QueryPara queryPara);

    List<UserShare> getUserSourceList();

    List<UserShare> getUserSourceDisList(QueryPara queryPara);

    List<UserShare> getHourDisList(QueryPara queryPara);

    List<UserShare> getDateTrend(QueryPara queryPara);
}
