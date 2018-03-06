package cn.com.chinalife.ecdata.dao.sqlDao;

import cn.com.chinalife.ecdata.entity.user.UserSource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/6.
 */
@Repository
public interface InitDao {
    List<UserSource> getNewUserSource();

    List<UserSource> getOldUserSource();
}
