package cn.com.chinalife.ecdata.service.user;

import cn.com.chinalife.ecdata.entity.user.LogUser;

/**
 * Created by xiexiangyu on 2018/3/7.
 */
public interface UserLogService {
    LogUser login(LogUser requestUser) throws Exception;
}
