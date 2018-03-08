package cn.com.chinalife.ecdata.service.impl.user;

import cn.com.chinalife.ecdata.dao.sqlDao.user.RegisterUserDao;
import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.user.RegisterUser;
import cn.com.chinalife.ecdata.service.user.RegisterUserService;
import cn.com.chinalife.ecdata.utils.CommonConstant;
import cn.com.chinalife.ecdata.utils.DataSourceContextHolder;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xiexiangyu on 2018/3/1.
 */
@Service
public class RegisterUserServiceImpl implements RegisterUserService {
    private final Logger logger = LoggerFactory.getLogger(RegisterUserServiceImpl.class);
    @Autowired
    RegisterUserDao registerUserDao;


    public RegisterUser getRegisterUserNum(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        DataSourceContextHolder.setDbType(CommonConstant.businessDataSource);
        RegisterUser registerUser = registerUserDao.getRegisterUserNum(queryPara);
        logger.info("service返回结果为 {}", JSON.toJSONString(registerUser));
        return registerUser;
    }
}
