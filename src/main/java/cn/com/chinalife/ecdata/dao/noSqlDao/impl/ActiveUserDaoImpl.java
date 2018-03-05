package cn.com.chinalife.ecdata.dao.noSqlDao.impl;

import cn.com.chinalife.ecdata.dao.noSqlDao.user.ActiveUserDao;
import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.user.ActiveUser;
import cn.com.chinalife.ecdata.utils.CommonConstant;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/1.
 */
@Repository
public class ActiveUserDaoImpl implements ActiveUserDao {

    @Autowired
    MongoTemplate mongoTemplate;

    public ActiveUser getActiveUserNum(QueryPara queryPara) {
        DBCollection dbCollection = mongoTemplate.getCollection("UserActionCollection");
        // aggregate 实现方法
        // 过滤时间和渠道
        BasicDBObject[] timeFilterArray = {new BasicDBObject("actionTime", new BasicDBObject("$gte", queryPara.getStartDate() + CommonConstant.beginAppendTime)),
                new BasicDBObject("actionTime", new BasicDBObject("$lte", queryPara.getEndDate() + CommonConstant.endAppendTime))};
        BasicDBObject timeFilterCon = new BasicDBObject();
        timeFilterCon.put("$and", timeFilterArray);
        BasicDBObject matchTime = new BasicDBObject("$match", timeFilterCon);
        BasicDBObject matchUsersource = new BasicDBObject("$match", new BasicDBObject("clientSender", queryPara.getUserSource()));
        //group stage one
        BasicDBObject groupFieldOne = new BasicDBObject("_id", new BasicDBObject("clientSender", "$clientSender").append("oldUserId", "$oldUserId"));
        BasicDBObject groupOne = new BasicDBObject("$group", groupFieldOne);
        // group stage two
        BasicDBObject groupFieldTwo = new BasicDBObject("_id", new BasicDBObject("clientSender", "$_id.clientSender"));
        groupFieldTwo.put("activeUserNum", new BasicDBObject("$sum", 1));
        BasicDBObject groupTwo = new BasicDBObject("$group", groupFieldTwo);
        List<DBObject> stageList = new ArrayList<DBObject>();
        stageList.add(matchTime);
        stageList.add(matchUsersource);
        stageList.add(groupOne);
        stageList.add(groupTwo);
        AggregationOutput aggregate = dbCollection.aggregate(stageList);
        Iterable<DBObject> iterable = aggregate.results();
        ActiveUser activeUser = new ActiveUser();
        activeUser.setActiveUserNum(0);
        for (DBObject dbObject : iterable) {
            activeUser.setActiveUserNum(Integer.parseInt(dbObject.get("activeUserNum").toString()));
        }
        return activeUser;
    }

    /**
     * 因返回list里包含所有的oldUserId，对内存消耗较大，所以不采用
     *
     * @return
     */
    public ActiveUser getActiveUserNumUsingDistinct(QueryPara queryPara) {
        ActiveUser activeUser = new ActiveUser();
        DBCollection dbCollection = mongoTemplate.getCollection("UserActionCollection");
        BasicDBObject queryCondition = new BasicDBObject();
        queryCondition.put("clientSender", queryPara.getUserSource());
        queryCondition.put("actionTime", new BasicDBObject("$gte", queryPara.getStartDate() + CommonConstant.beginAppendTime).
                append("$lte", queryPara.getEndDate() + CommonConstant.endAppendTime));
        activeUser.setActiveUserNum(dbCollection.distinct("oldUserId", queryCondition).size());
        return activeUser;
    }
}
