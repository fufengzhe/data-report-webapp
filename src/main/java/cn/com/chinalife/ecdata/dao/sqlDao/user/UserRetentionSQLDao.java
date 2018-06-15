package cn.com.chinalife.ecdata.dao.sqlDao.user;

import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.user.UserRetention;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/2.
 */
@Repository
public interface UserRetentionSQLDao {
    List<UserRetention> getUserRetentionList(QueryPara queryPara);

    List<String> getOldUserIdList(QueryPara temp);

    int getExistedRetentionRowNum();

    int insertListToDB(List<UserRetention> userRetentionList);

    Integer getRegisterNumFromStatResult(QueryPara temp);
}
