package cn.com.chinalife.ecdata.dao.noSqlDao.impl.user;

import cn.com.chinalife.ecdata.dao.noSqlDao.user.UserRetentionNoSQLDao;
import cn.com.chinalife.ecdata.entity.user.UserSource;
import cn.com.chinalife.ecdata.utils.CommonConstant;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/1.
 */
@Repository
public class UserRetentionDaoImpl implements UserRetentionNoSQLDao {

    @Autowired
    MongoTemplate mongoTemplate;

    public List<String> getActiveOldUserIdList(List<UserSource> userSourceList, String queryDate) {
        DBCollection dbCollection = mongoTemplate.getCollection("UserActionCollection");
        BasicDBObject queryCondition = new BasicDBObject();
        queryCondition.put("actionTime", new BasicDBObject("$gte", queryDate + CommonConstant.beginAppendTime).
                append("$lte", queryDate + CommonConstant.endAppendTime));
        queryCondition.put("clientSender", new BasicDBObject("$in", Arrays.asList(getUserSourceArrayUsingList(userSourceList))));
        List<String> oldUserIds = dbCollection.distinct("oldUserId", queryCondition);
        return oldUserIds;
    }

    private String[] getUserSourceArrayUsingList(List<UserSource> userSourceList) {
        String[] userSourceArray = new String[userSourceList.size()];
        int index = 0;
        for (UserSource userSource : userSourceList) {
            userSourceArray[index++] = userSource.getUserSource();
        }
        return userSourceArray;
    }
}
