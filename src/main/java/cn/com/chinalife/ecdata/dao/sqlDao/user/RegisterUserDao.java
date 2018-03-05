package cn.com.chinalife.ecdata.dao.sqlDao.user;

import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.user.RegisterUser;
import org.springframework.stereotype.Repository;

/**
 * Created by xiexiangyu on 2018/3/2.
 */
@Repository
public interface RegisterUserDao {
    RegisterUser getRegisterUserNum(QueryPara queryPara);
}
