package cn.com.chinalife.ecdata.service.user;

import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.user.RegisterUser;

/**
 * Created by xiexiangyu on 2018/3/2.
 */
public interface RegisterUserService {
    RegisterUser getRegisterUserNum(QueryPara queryPara);
}
