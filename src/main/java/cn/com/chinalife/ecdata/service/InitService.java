package cn.com.chinalife.ecdata.service;

import cn.com.chinalife.ecdata.entity.user.UserSource;

import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/6.
 */
public interface InitService {
    List<UserSource> getNewUserSource();

    List<UserSource> getOldUserSource();
    
}
