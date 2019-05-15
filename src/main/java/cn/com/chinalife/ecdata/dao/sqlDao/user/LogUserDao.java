package cn.com.chinalife.ecdata.dao.sqlDao.user;

import cn.com.chinalife.ecdata.entity.user.LogUser;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/7.
 */
@Repository
public interface LogUserDao {
    LogUser findUser(LogUser requestUser);

    List<LogUser> getAllValidUserInfo();

    LogUser findUserResourcesUsingName(LogUser logUser);

    LogUser findUserResourcesFromMysqlUsingName(LogUser logUser);

    LogUser findUserFromMysql(LogUser requestUser);
}
