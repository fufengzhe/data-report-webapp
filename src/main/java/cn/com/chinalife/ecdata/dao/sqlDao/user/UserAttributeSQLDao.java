package cn.com.chinalife.ecdata.dao.sqlDao.user;

import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.user.UserAttribute;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/2.
 */
@Repository
public interface UserAttributeSQLDao {
    int deleteAllExistedRecord(String distributeName);

    int insertListToDB(List<UserAttribute> userAttributeList);

    List<UserAttribute> getUserAgeDisInfoList(QueryPara temp);

    List<UserAttribute> getUserRankDisInfoList(QueryPara queryPara);

    List<UserAttribute> getUserSexDisInfoList(QueryPara queryPara);

    List<UserAttribute> getUserSexList(QueryPara queryPara);

    List<UserAttribute> getUserRankList(QueryPara queryPara);

    List<UserAttribute> getUserAgeList(QueryPara queryPara);
}
