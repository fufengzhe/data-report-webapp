package cn.com.chinalife.ecdata.dao.noSqlDao.user;

import cn.com.chinalife.ecdata.entity.user.UserSource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/2.
 */
@Repository
public interface UserRetentionNoSQLDao {

    List<String> getActiveOldUserIdList(List<UserSource> userSourceList, String queryDate);
}
