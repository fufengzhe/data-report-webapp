package cn.com.chinalife.ecdata.dao.sqlDao;

import cn.com.chinalife.ecdata.entity.UpdateResult;
import cn.com.chinalife.ecdata.entity.user.UserSource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/6.
 */
@Repository
public interface InitDao {
    // 获取电商公司新渠道code及名字
    List<UserSource> getNewUserSource();

    List<UserSource> getNewUserSourceOfAll();

    List<UserSource> getOldUserSource();

    // 获取人寿所有渠道code及名字
    List<UserSource> getOldUserSourceOfAll();

    int updateDataStatus(List<UpdateResult> updateResultList);

    List<UpdateResult> getUpdateResult();

    List<UpdateResult> getFupinUpdateResult();
}
