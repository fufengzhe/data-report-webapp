package cn.com.chinalife.ecdata.dao.sqlDao.salesman;

import cn.com.chinalife.ecdata.entity.salesman.SalesmanRelated;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/14.
 */
@Repository
public interface SalesmanRelatedDao {

    List<String> getOldUserIdListUsingSalesmanNo(List<String> tempSalesmanNoList);

    List<SalesmanRelated> getSalesmanRelatedListUsingOldUserId(List<String> oldUserIdList);
}
