package cn.com.chinalife.ecdata.dao.sqlDao.user;

import cn.com.chinalife.ecdata.entity.user.LogUser;
import org.springframework.stereotype.Repository;

/**
 * Created by xiexiangyu on 2018/3/7.
 */
@Repository
public interface LogUserDao {
    LogUser findUser(LogUser requestUser);
}
