package cn.com.chinalife.ecdata.service.user;

import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.user.ActiveUser;

import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/1.
 */
public interface ActiveUserService {
    ActiveUser getActiveUserNum(QueryPara queryPara);

    List<ActiveUser> getActiveUserNumOfAllSources(QueryPara queryPara);

    List<String> getActiveUserDetail(QueryPara queryPara);

    int updateActive(List<ActiveUser> activeUserList);

    List<ActiveUser> getActiveUserNumOfAllSourcesFromStatResult(QueryPara queryPara);

    List<List<ActiveUser>> queryOfficialSiteActiveNum(QueryPara queryPara);

    String[][] getTableContent(List<ActiveUser> activeUsers, String[] strings);

    List<ActiveUser> getActiveUserNumOfEBaoZhang(QueryPara queryPara);

    List<String> getLatestDateOfEBaoZhang();
}
