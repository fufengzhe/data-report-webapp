package cn.com.chinalife.ecdata.service.user;

import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.user.RegisterUser;

import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/2.
 */
public interface RegisterUserService {
    RegisterUser getRegisterUserNum(QueryPara queryPara);

    List<RegisterUser> getRegisterNumOverview();

    List<RegisterUser> getRegisterUserNumOfAllSources(QueryPara queryPara);

    int updateRegister(List<RegisterUser> registerUserList);

    List<RegisterUser> getRegisterUserNumOfAllSourcesFromStatResult(QueryPara queryPara);

    List<List<RegisterUser>> getRegisterUserSummaryList();
}
