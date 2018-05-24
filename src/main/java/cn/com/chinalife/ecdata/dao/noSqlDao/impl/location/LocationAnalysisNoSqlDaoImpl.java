package cn.com.chinalife.ecdata.dao.noSqlDao.impl.location;

import cn.com.chinalife.ecdata.dao.noSqlDao.location.LocationAnalysisNoSqlDao;
import cn.com.chinalife.ecdata.entity.combine.AnalysisIndex;
import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.utils.CommonConstant;
import com.mongodb.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/1.
 */
@Repository
public class LocationAnalysisNoSqlDaoImpl implements LocationAnalysisNoSqlDao {

    @Autowired
    MongoTemplate mongoTemplate;

    public List<AnalysisIndex> getActiveIPAndSourceList(QueryPara queryPara) {
        DBCollection dbCollection = mongoTemplate.getCollection("UserActionCollection");
        BasicDBList condList = new BasicDBList();
        BasicDBObject cond1a = new BasicDBObject();
        cond1a.append("actionTime", new BasicDBObject("$gt", queryPara.getStartDate() + CommonConstant.beginAppendTime));
        BasicDBObject cond1b = new BasicDBObject();
        cond1b.append("actionTime", new BasicDBObject("$lte", queryPara.getEndDate() + CommonConstant.endAppendTime));
        condList.add(cond1a);
        condList.add(cond1b);
        BasicDBObject cond2 = new BasicDBObject();
        cond2.append("ip", new BasicDBObject("$ne", "null"));
        condList.add(cond2);
        if (queryPara.getUserSource() != null) {
            BasicDBObject cond3 = new BasicDBObject();
            cond3.append("clientSender", queryPara.getUserSource());
            condList.add(cond3);
        }
        BasicDBObject cond = new BasicDBObject();
        cond.put("$and", condList);
        BasicDBObject column = new BasicDBObject();
        column.put("actionTime", 1);
        column.put("clientSender", 1);
        column.put("ip", 1);
        column.put("_id", 0);
        Cursor cursor = dbCollection.find(cond, column);
        List<AnalysisIndex> analysisIndexList = new ArrayList<AnalysisIndex>();
        while (cursor.hasNext()) {
            DBObject dbObject = cursor.next();
            AnalysisIndex analysisIndex = new AnalysisIndex();
            analysisIndex.setStatDate(dbObject.get("actionTime").toString().substring(0, 10));
            analysisIndex.setIndexSource(dbObject.get("clientSender").toString());
            analysisIndex.setIp(dbObject.get("ip").toString().split(",")[0]);
            analysisIndexList.add(analysisIndex);
        }
        return analysisIndexList;
    }

    public List<AnalysisIndex> getDistinctActiveIPAndSourceList(QueryPara queryPara) {
        DBCollection dbCollection = mongoTemplate.getCollection("UserActionCollection");
        // aggregate 实现方法
        // 过滤时间和渠道
        List<AnalysisIndex> analysisIndexList = new ArrayList<AnalysisIndex>();
        List<DBObject> stageList = new ArrayList<DBObject>();
        BasicDBObject[] timeFilterArray = {new BasicDBObject("actionTime", new BasicDBObject("$gte", queryPara.getStartDate() + CommonConstant.beginAppendTime)),
                new BasicDBObject("actionTime", new BasicDBObject("$lte", queryPara.getEndDate() + CommonConstant.endAppendTime))};
        BasicDBObject timeFilterCon = new BasicDBObject();
        timeFilterCon.put("$and", timeFilterArray);
        BasicDBObject matchTime = new BasicDBObject("$match", timeFilterCon);
        BasicDBObject matchIP = new BasicDBObject("$match", new BasicDBObject("ip", new BasicDBObject("$ne", "null")));
        stageList.add(matchTime);
        stageList.add(matchIP);
        if (queryPara.getUserSource() != null) {
            BasicDBObject matchUserSource = new BasicDBObject("$match", new BasicDBObject("clientSender", queryPara.getUserSource()));
            stageList.add(matchUserSource);
        }
        BasicDBObject temp1 = new BasicDBObject("clientSender", "$clientSender").append("ip", "$ip");
        temp1.append("actionDate", new BasicDBObject(new BasicDBObject("$substr", Arrays.asList("$actionTime", 0, 10))));
        BasicDBObject groupFieldOne = new BasicDBObject("_id", temp1);
        BasicDBObject groupOne = new BasicDBObject("$group", groupFieldOne);
        stageList.add(groupOne);
        Cursor cursor = dbCollection.aggregate(stageList, AggregationOptions.builder().allowDiskUse(true).build());
        while (cursor.hasNext()) {
            DBObject dbObject = cursor.next();
            DBObject data = (DBObject) dbObject.get("_id");
            AnalysisIndex analysisIndex = new AnalysisIndex();
            analysisIndex.setStatDate(data.get("actionDate").toString());
            analysisIndex.setIndexSource(data.get("clientSender").toString());
            analysisIndex.setIp(data.get("ip").toString().split(",")[0]);
            analysisIndexList.add(analysisIndex);
        }
        return analysisIndexList;
    }

    public List<AnalysisIndex> getActiveTimeDis(QueryPara queryPara) {
        DBCollection dbCollection = mongoTemplate.getCollection("UserActionCollection");
        // aggregate 实现方法
        // 过滤时间和渠道
        List<DBObject> stageList = new ArrayList<DBObject>();
        BasicDBObject[] timeFilterArray = {new BasicDBObject("actionTime", new BasicDBObject("$gte", queryPara.getStartDate() + CommonConstant.beginAppendTime)),
                new BasicDBObject("actionTime", new BasicDBObject("$lte", queryPara.getEndDate() + CommonConstant.endAppendTime))};
        BasicDBObject timeFilterCon = new BasicDBObject();
        timeFilterCon.put("$and", timeFilterArray);
        BasicDBObject matchTime = new BasicDBObject("$match", timeFilterCon);
        stageList.add(matchTime);
        if (queryPara.getUserSource() != null) {
            BasicDBObject matchUserSource = new BasicDBObject("$match", new BasicDBObject("clientSender", queryPara.getUserSource()));
            stageList.add(matchUserSource);
        }
        BasicDBObject temp1 = new BasicDBObject("clientSender", "$clientSender").append("oldUserId", "$oldUserId");
        temp1.append("actionDate", new BasicDBObject(new BasicDBObject("$substr", Arrays.asList("$actionTime", 0, 13))));
        BasicDBObject groupFieldOne = new BasicDBObject("_id", temp1);
        BasicDBObject groupOne = new BasicDBObject("$group", groupFieldOne);
        stageList.add(groupOne);
        // group stage two
        BasicDBObject temp2 = new BasicDBObject(new BasicDBObject("$concat", Arrays.asList("$_id.clientSender", "_", "$_id.actionDate")));
        BasicDBObject groupFieldTwo = new BasicDBObject("_id", temp2);
        groupFieldTwo.put("activeUserNum", new BasicDBObject("$sum", 1));
        BasicDBObject groupTwo = new BasicDBObject("$group", groupFieldTwo);
        stageList.add(groupTwo);
        Cursor cursor = dbCollection.aggregate(stageList, AggregationOptions.builder().allowDiskUse(true).build());
        List<AnalysisIndex> analysisIndexList = new ArrayList<AnalysisIndex>();
        while (cursor.hasNext()) {
            DBObject dbObject = cursor.next();
            String clientSenderAndTime = dbObject.get("_id").toString();
            String activeUserNum = dbObject.get("activeUserNum").toString();
            AnalysisIndex analysisIndex = new AnalysisIndex();
            analysisIndex.setStatDate(clientSenderAndTime.substring(13, 23));
            analysisIndex.setStatDateSpan(CommonConstant.statTimeSpanOfDate);
            analysisIndex.setIndexName(CommonConstant.distributeIndexNameOfActiveHour);
            analysisIndex.setIndexSource(clientSenderAndTime.substring(0, 12));
            analysisIndex.setDistributeType("3");
            analysisIndex.setDistributeName(clientSenderAndTime.substring(24));
            analysisIndex.setIndexValue(Integer.parseInt(activeUserNum));
            analysisIndexList.add(analysisIndex);
        }
        return analysisIndexList;
    }

    public List<AnalysisIndex> getUserCollectionInvokeDis(QueryPara queryPara) {
        DBCollection dbCollection = mongoTemplate.getCollection("USER");
        // aggregate 实现方法
        // 过滤时间和渠道
        List<DBObject> stageList1 = new ArrayList<DBObject>();
        List<DBObject> stageList2 = new ArrayList<DBObject>();
        BasicDBObject[] timeFilterArray = {new BasicDBObject("logMilliTime", new BasicDBObject("$gte", queryPara.getStartDate() + CommonConstant.beginAppendTime)),
                new BasicDBObject("logMilliTime", new BasicDBObject("$lte", queryPara.getEndDate() + CommonConstant.endAppendTime))};
        BasicDBObject timeFilterCon = new BasicDBObject();
        timeFilterCon.put("$and", timeFilterArray);
        BasicDBObject matchTime = new BasicDBObject("$match", timeFilterCon);
        stageList1.add(matchTime);
        stageList2.add(matchTime);
        if (queryPara.getUserSource() != null) {
            BasicDBObject matchUserSource = new BasicDBObject("$match", new BasicDBObject("clientSender", queryPara.getUserSource()));
            stageList1.add(matchUserSource);
            stageList2.add(matchUserSource);
        }
        BasicDBObject temp1 = new BasicDBObject("clientSender", "$clientSender").append("funName", "$funName");
        temp1.append("logDate", new BasicDBObject(new BasicDBObject("$substr", Arrays.asList("$logMilliTime", 0, 10))));
        BasicDBObject groupFieldOne = new BasicDBObject("_id", temp1);
        groupFieldOne.put("invokeTimes", new BasicDBObject("$sum", 1));
        BasicDBObject groupOne = new BasicDBObject("$group", groupFieldOne);
        stageList1.add(groupOne);
        BasicDBObject temp2 = new BasicDBObject("clientSender", "$clientSender").append("logLevel", "$logLevel");
        temp2.append("logDate", new BasicDBObject(new BasicDBObject("$substr", Arrays.asList("$logMilliTime", 0, 10))));
        BasicDBObject groupFieldTwo = new BasicDBObject("_id", temp2);
        groupFieldTwo.put("invokeTimes", new BasicDBObject("$sum", 1));
        BasicDBObject groupTwo = new BasicDBObject("$group", groupFieldTwo);
        stageList2.add(groupTwo);
        Cursor cursor = dbCollection.aggregate(stageList1, AggregationOptions.builder().allowDiskUse(true).build());
        List<AnalysisIndex> analysisIndexList = new ArrayList<AnalysisIndex>();
        while (cursor.hasNext()) {
            DBObject dbObject = cursor.next();
            AnalysisIndex analysisIndex = new AnalysisIndex();
            this.setAnalysisIndexUsingDBObject(analysisIndex, dbObject, "funName", "4");
            analysisIndexList.add(analysisIndex);
        }
        cursor = dbCollection.aggregate(stageList2, AggregationOptions.builder().allowDiskUse(true).build());
        while (cursor.hasNext()) {
            DBObject dbObject = cursor.next();
            AnalysisIndex analysisIndex = new AnalysisIndex();
            this.setAnalysisIndexUsingDBObject(analysisIndex, dbObject, "logLevel", "5");
            analysisIndexList.add(analysisIndex);
        }
        return analysisIndexList;
    }

    public List<AnalysisIndex> getMigrateDisInfo(QueryPara queryPara) {
        DBCollection dbCollection = mongoTemplate.getCollection("migrate");
        // aggregate 实现方法
        // 过滤时间和渠道
        List<DBObject> stageList1 = new ArrayList<DBObject>();
        BasicDBObject[] timeFilterArray = {new BasicDBObject("loginTime", new BasicDBObject("$gte", queryPara.getStartDate() + CommonConstant.beginAppendTime)),
                new BasicDBObject("loginTime", new BasicDBObject("$lte", queryPara.getEndDate() + CommonConstant.endAppendTime))};
        BasicDBObject timeFilterCon = new BasicDBObject();
        timeFilterCon.put("$and", timeFilterArray);
        BasicDBObject matchTime = new BasicDBObject("$match", timeFilterCon);
        stageList1.add(matchTime);
        BasicDBObject matchFromNotEqualTo = new BasicDBObject("$match", new BasicDBObject("clientSender", new BasicDBObject("$ne", "$clientSender")));
        stageList1.add(matchFromNotEqualTo);
        BasicDBObject temp1 = new BasicDBObject("fromClientSender", "$clientSender").append("toClientSender", "$clientSender").append("oldUserId", "$oldUserId");
        temp1.append("migrateDate", new BasicDBObject(new BasicDBObject("$substr", Arrays.asList("$loginTime", 0, 10))));
        BasicDBObject groupFieldOne = new BasicDBObject("_id", temp1);
        BasicDBObject groupOne = new BasicDBObject("$group", groupFieldOne);
        stageList1.add(groupOne);
        BasicDBObject temp2 = new BasicDBObject("migrateDate", "$_id.migrateDate").append("fromClientSender", "$_id.fromClientSender").
                append("toClientSender", "$_id.toClientSender");
        BasicDBObject groupFieldTwo = new BasicDBObject("_id", temp2);
        groupFieldTwo.put("migrateNum", new BasicDBObject("$sum", 1));
        BasicDBObject groupTwo = new BasicDBObject("$group", groupFieldTwo);
        stageList1.add(groupTwo);
        Cursor cursor = dbCollection.aggregate(stageList1, AggregationOptions.builder().allowDiskUse(true).build());
        List<AnalysisIndex> analysisIndexList = new ArrayList<AnalysisIndex>();
        while (cursor.hasNext()) {
            DBObject dbObject = cursor.next();
            DBObject category = (DBObject) dbObject.get("_id");
            AnalysisIndex analysisIndex = new AnalysisIndex();
            analysisIndex.setStatDate(category.get("migrateDate").toString());
            analysisIndex.setStatDateSpan(CommonConstant.statTimeSpanOfDate);
            analysisIndex.setIndexName(CommonConstant.distributeIndexNameOfMigrateCollection);
            analysisIndex.setIndexSource(category.get("fromClientSender").toString());
            analysisIndex.setDistributeType("6");
            analysisIndex.setDistributeName(category.get("toClientSender").toString());
            analysisIndex.setIndexValue(Integer.parseInt(dbObject.get("migrateNum").toString()));
            analysisIndexList.add(analysisIndex);
        }
        return analysisIndexList;
    }

    public List<AnalysisIndex> getMigrateUserNumDisInfo(QueryPara queryPara) {
        DBCollection dbCollection = mongoTemplate.getCollection("migrate");
        // aggregate 实现方法
        // 过滤时间和渠道
        List<DBObject> stageList1 = new ArrayList<DBObject>();
        BasicDBObject matchFromNotEqualTo = new BasicDBObject("$match", new BasicDBObject("clientSender", new BasicDBObject("$ne", "$clientSender")));
        stageList1.add(matchFromNotEqualTo);
        BasicDBObject temp1 = new BasicDBObject("clientSender", "$clientSender").append("oldUserId", "$oldUserId");
        BasicDBObject groupFieldOne = new BasicDBObject("_id", temp1);
        BasicDBObject groupOne = new BasicDBObject("$group", groupFieldOne);
        stageList1.add(groupOne);
        BasicDBObject temp2 = new BasicDBObject("oldUserId", "$_id.oldUserId");
        BasicDBObject groupFieldTwo = new BasicDBObject("_id", temp2);
        groupFieldTwo.put("clientNum", new BasicDBObject("$sum", 1));
        BasicDBObject groupTwo = new BasicDBObject("$group", groupFieldTwo);
        stageList1.add(groupTwo);
        BasicDBObject temp3 = new BasicDBObject("clientNum", "$clientNum");
        BasicDBObject groupFieldThree = new BasicDBObject("_id", temp3);
        groupFieldThree.put("userNum", new BasicDBObject("$sum", 1));
        BasicDBObject groupThree = new BasicDBObject("$group", groupFieldThree);
        stageList1.add(groupThree);
        Cursor cursor = dbCollection.aggregate(stageList1, AggregationOptions.builder().allowDiskUse(true).build());
        List<AnalysisIndex> analysisIndexList = new ArrayList<AnalysisIndex>();
        while (cursor.hasNext()) {
            DBObject dbObject = cursor.next();
            DBObject category = (DBObject) dbObject.get("_id");
            AnalysisIndex analysisIndex = new AnalysisIndex();
            analysisIndex.setStatDateSpan(CommonConstant.statTimeSpanOfDate);
            analysisIndex.setIndexName(CommonConstant.distributeIndexNameOfMigrateCollectionUserNum);
            analysisIndex.setIndexSource("");
            analysisIndex.setDistributeType("7");
            analysisIndex.setDistributeName(category.get("clientNum").toString());
            analysisIndex.setIndexValue(Integer.parseInt(dbObject.get("userNum").toString()));
            analysisIndexList.add(analysisIndex);
        }
        return analysisIndexList;
    }

    private void setAnalysisIndexUsingDBObject(AnalysisIndex analysisIndex, DBObject dbObject, String distributeName, String distributeType) {
        DBObject category = (DBObject) dbObject.get("_id");
        analysisIndex.setStatDate(category.get("logDate").toString());
        analysisIndex.setStatDateSpan(CommonConstant.statTimeSpanOfDate);
        analysisIndex.setIndexName(CommonConstant.distributeIndexNameOfUserCollection);
        analysisIndex.setIndexSource(category.get("clientSender").toString());
        analysisIndex.setDistributeType(distributeType);
        analysisIndex.setDistributeName(category.get(distributeName).toString());
        analysisIndex.setIndexValue(Integer.parseInt(dbObject.get("invokeTimes").toString()));
    }

}
