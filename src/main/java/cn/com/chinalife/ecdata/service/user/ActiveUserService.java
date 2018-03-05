package cn.com.chinalife.ecdata.service.user;

import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.user.ActiveUser;

/**
 * Created by xiexiangyu on 2018/3/1.
 */
public interface ActiveUserService {
    ActiveUser getActiveUserNum(QueryPara queryPara);
}
