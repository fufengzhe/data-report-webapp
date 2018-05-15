package cn.com.chinalife.ecdata.service;

import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.user.ActiveUser;

import javax.mail.MessagingException;
import java.text.ParseException;
import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/6.
 */
public interface ScheduleService {

    int updateRegister(QueryPara queryPara);

    int updateActive(QueryPara queryPara) throws ParseException;

    int updateLifePremium(QueryPara queryPara);

    int updatePropertyPremium(QueryPara queryPara);

    List<List<ActiveUser>> queryOfficialSiteActiveNum(QueryPara queryPara);

    void sendOfficialSiteActiveNum(QueryPara queryPara, List<List<ActiveUser>> activeUserList) throws MessagingException;

    int updateDistribute(QueryPara queryPara) throws Exception;
}
