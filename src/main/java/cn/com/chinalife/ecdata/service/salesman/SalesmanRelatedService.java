package cn.com.chinalife.ecdata.service.salesman;

import cn.com.chinalife.ecdata.entity.salesman.SalesmanRelated;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.File;
import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/14.
 */
public interface SalesmanRelatedService {


    File getSampleFile(String s);

    List<SalesmanRelated> getSalesmanRelatedInfo(MultipartFile file);

    void sendResultUsingMail(List<SalesmanRelated> salesmanRelatedList, String mail) throws MessagingException;
}
