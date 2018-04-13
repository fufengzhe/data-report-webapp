package cn.com.chinalife.ecdata.dao.noSqlDao.impl.user;

import cn.com.chinalife.ecdata.dao.noSqlDao.user.ActiveUserDao;
import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.user.ActiveUser;
import cn.com.chinalife.ecdata.utils.CommonConstant;
import com.mongodb.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
        BasicDBObject matchUserSource = new BasicDBObject("$match", new BasicDBObject("clientSender", queryPara.getUserSource()));
        //group stage one
        BasicDBObject groupFieldOne = new BasicDBObject("_id", new BasicDBObject("clientSender", "$clientSender").append("oldUserId", "$oldUserId"));
        BasicDBObject groupOne = new BasicDBObject("$group", groupFieldOne);
        // group stage two
        BasicDBObject groupFieldTwo = new BasicDBObject("_id", new BasicDBObject("clientSender", "$_id.clientSender"));
        groupFieldTwo.put("activeUserNum", new BasicDBObject("$sum", 1));
        BasicDBObject groupTwo = new BasicDBObject("$group", groupFieldTwo);
        List<DBObject> stageList = new ArrayList<DBObject>();
        stageList.add(matchTime);
        stageList.add(matchUserSource);
        stageList.add(groupOne);
        stageList.add(groupTwo);
        Cursor cursor = dbCollection.aggregate(stageList, AggregationOptions.builder().allowDiskUse(true).build());
        ActiveUser activeUser = new ActiveUser();
        activeUser.setActiveUserNum(0);
        while (cursor.hasNext()) {
            activeUser.setActiveUserNum(Integer.parseInt(cursor.next().get("activeUserNum").toString()));
        }
        return activeUser;
    }

    public List<ActiveUser> getActiveUserNumOfAllSources(QueryPara queryPara) {
        DBCollection dbCollection = mongoTemplate.getCollection("UserActionCollection");
        // aggregate 实现方法
        // 过滤时间和渠道
        BasicDBObject[] timeFilterArray = {new BasicDBObject("actionTime", new BasicDBObject("$gte", queryPara.getStartDate() + CommonConstant.beginAppendTime)),
                new BasicDBObject("actionTime", new BasicDBObject("$lte", queryPara.getEndDate() + CommonConstant.endAppendTime))};
        BasicDBObject timeFilterCon = new BasicDBObject();
        timeFilterCon.put("$and", timeFilterArray);
        BasicDBObject matchTime = new BasicDBObject("$match", timeFilterCon);
        //group stage one
        BasicDBObject temp1 = new BasicDBObject("clientSender", "$clientSender").append("oldUserId", "$oldUserId");
        if ("D".equals(queryPara.getTimeSpan())) {
            temp1.append("actionDate", new BasicDBObject(new BasicDBObject("$substr", Arrays.asList("$actionTime", 0, 10))));
        }
        BasicDBObject groupFieldOne = new BasicDBObject("_id", temp1);
        BasicDBObject groupOne = new BasicDBObject("$group", groupFieldOne);
        // group stage two
        BasicDBObject temp2 = new BasicDBObject("clientSender", "$_id.clientSender");
        if ("D".equals(queryPara.getTimeSpan())) {
            temp2 = new BasicDBObject(new BasicDBObject("$concat", Arrays.asList("$_id.clientSender", "_", "$_id.actionDate")));
        }
        BasicDBObject groupFieldTwo = new BasicDBObject("_id", temp2);
        groupFieldTwo.put("activeUserNum", new BasicDBObject("$sum", 1));
        BasicDBObject groupTwo = new BasicDBObject("$group", groupFieldTwo);
        List<DBObject> stageList = new ArrayList<DBObject>();
        stageList.add(matchTime);
        stageList.add(groupOne);
        stageList.add(groupTwo);
        Cursor cursor = dbCollection.aggregate(stageList, AggregationOptions.builder().allowDiskUse(true).build());
        List<ActiveUser> activeUserList = new ArrayList<ActiveUser>();
        while (cursor.hasNext()) {
            ActiveUser activeUser = new ActiveUser();
            DBObject dbObject = cursor.next();
            if (CommonConstant.statTimeSpanOfDate.equals(queryPara.getTimeSpan())) {
                activeUser.setUserSource(dbObject.get("_id").toString().substring(0, 12));
                activeUser.setStartDate(dbObject.get("_id").toString().substring(13));
            } else {
                activeUser.setUserSource(((HashMap) dbObject.get("_id")).get("clientSender").toString());
            }
            activeUser.setActiveUserNum(Integer.parseInt(dbObject.get("activeUserNum").toString()));
            activeUserList.add(activeUser);
        }
        return activeUserList;
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

    public List<String> getActiveUserDetail(QueryPara queryPara) {
        DBCollection dbCollection = mongoTemplate.getCollection("UserActionCollection");
        BasicDBObject queryCondition = new BasicDBObject();
        queryCondition.put("actionTime", new BasicDBObject("$gte", queryPara.getQueryDate() + CommonConstant.beginAppendTime).
                append("$lte", queryPara.getQueryDate() + CommonConstant.endAppendTime));
        List<String> oldUserIds = dbCollection.distinct("oldUserId", queryCondition);
        return oldUserIds;
    }

    public List<List<ActiveUser>> queryOfficialSiteActiveNum(QueryPara queryPara) {
        List<List<ActiveUser>> activeUserList = new ArrayList<List<ActiveUser>>();
        List<ActiveUser> dayList = getActiveListForSpecificTimeLength(queryPara, 10);
        activeUserList.add(dayList);
        List<ActiveUser> hourList = getActiveListForSpecificTimeLength(queryPara, 13);
        activeUserList.add(hourList);
        return activeUserList;
    }

    private List<ActiveUser> getActiveListForSpecificTimeLength(QueryPara queryPara, int length) {
        DBCollection dbCollection = mongoTemplate.getCollection("UserActionCollection");
        // aggregate 实现方法
        // 过滤时间和渠道
        BasicDBObject[] timeFilterArray = {new BasicDBObject("actionTime", new BasicDBObject("$gte", (10 == length ? queryPara.getStartDate() : queryPara.getQueryDate()) + CommonConstant.beginAppendTime)),
                new BasicDBObject("actionTime", new BasicDBObject("$lte", (10 == length ? queryPara.getEndDate() : queryPara.getQueryDate()) + CommonConstant.endAppendTime))};
        BasicDBObject timeFilterCon = new BasicDBObject();
        timeFilterCon.put("$and", timeFilterArray);
        BasicDBObject matchTime = new BasicDBObject("$match", timeFilterCon);
        BasicDBObject matchUserSource = new BasicDBObject("$match", new BasicDBObject("clientSender", queryPara.getUserSource()));
        //group stage one
        BasicDBObject temp1 = new BasicDBObject("clientSender", "$clientSender").append("oldUserId", "$oldUserId");
        temp1.append("actionDate", new BasicDBObject(new BasicDBObject("$substr", Arrays.asList("$actionTime", 0, length))));
        BasicDBObject groupFieldOne = new BasicDBObject("_id", temp1);
        BasicDBObject groupOne = new BasicDBObject("$group", groupFieldOne);
        // group stage two
        BasicDBObject temp2 = new BasicDBObject(new BasicDBObject("$concat", Arrays.asList("$_id.clientSender", "_", "$_id.actionDate")));
        BasicDBObject groupFieldTwo = new BasicDBObject("_id", temp2);
        groupFieldTwo.put("activeUserNum", new BasicDBObject("$sum", 1));
        BasicDBObject groupTwo = new BasicDBObject("$group", groupFieldTwo);
        BasicDBObject sort = new BasicDBObject("$sort", new BasicDBObject("_id", 1));
        List<DBObject> stageList = new ArrayList<DBObject>();
        stageList.add(matchTime);
        stageList.add(matchUserSource);
        stageList.add(groupOne);
        stageList.add(groupTwo);
        stageList.add(sort);
        Cursor cursor = dbCollection.aggregate(stageList, AggregationOptions.builder().allowDiskUse(true).build());
        List<ActiveUser> activeUserList = new ArrayList<ActiveUser>();
        while (cursor.hasNext()) {
            ActiveUser activeUser = new ActiveUser();
            DBObject dbObject = cursor.next();
            if (10 == length) {
                activeUser.setStartDate(dbObject.get("_id").toString().substring(13));
            } else {
                activeUser.setStartDate(dbObject.get("_id").toString().substring(24, 26));
            }
            activeUser.setActiveUserNum(Integer.parseInt(dbObject.get("activeUserNum").toString()));
            activeUserList.add(activeUser);
        }
        return activeUserList;
    }
}
