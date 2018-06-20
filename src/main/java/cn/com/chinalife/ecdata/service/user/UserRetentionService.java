package cn.com.chinalife.ecdata.service.user;

import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.user.UserRetention;

import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/1.
 */
public interface UserRetentionService {

    List<UserRetention> getUserRetentionList(QueryPara queryPara);

    int updateUserRetention(QueryPara queryPara) throws Exception;

    List<UserRetention> getUserRetentionListForTrendChart(List<UserRetention> userRetentionList,boolean isUserSourceDimension);

    List<String> getxAxisLabel();
}
