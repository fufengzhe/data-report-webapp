package cn.com.chinalife.ecdata.dao.sqlDao.userShare;

import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.userShare.UserShare;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/14.
 */
@Repository
public interface UserShareDao {

    List<UserShare> getUserShareDisInfo(QueryPara queryPara);

    int updateUserShare(List<UserShare> userShareList);

    List<UserShare> getUserSourceList();

    List<UserShare> getUserSourceDisList(QueryPara queryPara);

    List<UserShare> getHourDisList(QueryPara queryPara);

    List<UserShare> getDateTrendList(QueryPara queryPara);
}
