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
            DBObject data= (DBObject) dbObject.get("_id");
            AnalysisIndex analysisIndex = new AnalysisIndex();
            analysisIndex.setStatDate(data.get("actionDate").toString());
            analysisIndex.setIndexSource(data.get("clientSender").toString());
            analysisIndex.setIp(data.get("ip").toString().split(",")[0]);
            analysisIndexList.add(analysisIndex);
        }
        return analysisIndexList;
    }

}
