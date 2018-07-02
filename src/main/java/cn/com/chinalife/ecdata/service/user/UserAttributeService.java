package cn.com.chinalife.ecdata.service.user;

import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.user.UserAttribute;

import java.text.ParseException;
import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/1.
 */
public interface UserAttributeService {

    int updateUserAttribute(QueryPara queryPara, boolean isScheduledRun) throws ParseException;

    int deleteAllExistedRecord(String statIndexNameOfUserAge);

    int insertListToDB(List<UserAttribute> ageDisList);

    List<UserAttribute> getUserSexList(QueryPara queryPara);

    List<UserAttribute> getUserRankList(QueryPara queryPara);

    List<UserAttribute> getUserAgeList(QueryPara queryPara);
}
