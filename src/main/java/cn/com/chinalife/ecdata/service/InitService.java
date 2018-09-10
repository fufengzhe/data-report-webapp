package cn.com.chinalife.ecdata.service;

import cn.com.chinalife.ecdata.entity.UpdateResult;
import cn.com.chinalife.ecdata.entity.user.UserSource;

import java.util.List;
import java.util.Map;

/**
 * Created by xiexiangyu on 2018/3/6.
 */
public interface InitService {
    List<UserSource> getNewUserSource();

    List<UserSource> getALLNewUserSource();

    List<UserSource> getOldUserSource();

    Map<String, String> getAllNewUserSourceCodeAndName();

    Map<String, String> getAllOldUserSourceCodeAndName();

    Map<String, String> getNewUserSourceCodeAndName();

    int updateDataStatus(List<UpdateResult> updateResultList);

    int updateDataStatus(UpdateResult updateResult);

    int updateDataStatus(String statTime, String statTimeSpan, String indexName, String indexDesc, Integer effectedRowNum);

    List<UpdateResult> getUpdateResult();
}
