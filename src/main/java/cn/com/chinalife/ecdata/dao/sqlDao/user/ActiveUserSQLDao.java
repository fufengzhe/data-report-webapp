package cn.com.chinalife.ecdata.dao.sqlDao.user;

import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.user.ActiveUser;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/2.
 */
@Repository
public interface ActiveUserSQLDao {

    int updateActive(List<ActiveUser> activeUserList);

    List<ActiveUser> getDateActiveUserNumOfAllSourcesFromStatResult(QueryPara queryPara);

    List<ActiveUser> getMonthActiveUserNumOfAllSourcesFromStatResultWithoutEBaoZhang(QueryPara queryPara);

    List<ActiveUser> getActiveUserNumOfEBaoZhang(QueryPara queryPara);

    List<String> getLatestDateOfEBaoZhang();

    List<ActiveUser> getMonthActiveUserNumOfAllSourcesFromStatResultForEBaoZhang(QueryPara queryPara);
}
