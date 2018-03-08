package cn.com.chinalife.ecdata.dao.sqlDao;

import cn.com.chinalife.ecdata.entity.Auth;
import cn.com.chinalife.ecdata.entity.user.LogUser;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/8.
 */
@Repository
public interface AuthDao {
    List<Auth> getAuthUsingLogUser(LogUser logUser);

    List<Auth> getAllAuth();

    List<Auth> getAuthUsingResourceId(List<Auth> authList);
}
