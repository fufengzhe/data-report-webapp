package cn.com.chinalife.ecdata.dao.noSqlDao.user;

import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.user.ActiveUser;

import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/1.
 */
public interface ActiveUserDao {
    ActiveUser getActiveUserNum(QueryPara queryPara);

    List<ActiveUser> getActiveUserNumOfAllSources(QueryPara queryPara);

    List<String> getActiveUserDetail(QueryPara queryPara);

}
