package cn.com.chinalife.ecdata.web.controller.salesman;

import cn.com.chinalife.ecdata.entity.ResponseBean;
import cn.com.chinalife.ecdata.entity.salesman.SalesmanRelated;
import cn.com.chinalife.ecdata.service.salesman.SalesmanRelatedService;
import cn.com.chinalife.ecdata.utils.CommonConstant;
import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

/**
 * Created by xiexiangyu on 2018/2/28.
 */
@Controller
@RequestMapping("/salesman")
public class SalesmanRelatedController {
    private final Logger logger = LoggerFactory.getLogger(SalesmanRelatedController.class);
    @Autowired
    SalesmanRelatedService salesmanRelatedService;

    @RequestMapping("/bankAndMobile")
    public String bankAndMobile() {
        return "salesman/upload";
    }


    @RequestMapping("/getBankAndMobileInfo")
    public String getBankAndMobileInfo(@RequestParam("inputfile") MultipartFile file, @RequestParam("mail") String mail, HttpServletRequest request) throws MessagingException {
        logger.info("前端传入的参数为 {},收件人列表为 {}", JSON.toJSONString(file), mail);
        ResponseBean responseBean = new ResponseBean();
        try {
            List<SalesmanRelated> salesmanRelatedList = salesmanRelatedService.getSalesmanRelatedInfo(file);
            if (salesmanRelatedList == null) {
                responseBean.setRespCode(1);
                responseBean.setRespMsg("上传文件内容有误,请按照示例中文件格式上传业务员工号!");
                return JSON.toJSONString(responseBean);
            } else {
                salesmanRelatedService.sendResultUsingMail(salesmanRelatedList, mail);
                responseBean.setRespMsg("生成成功，请在邮件中查看格式化后的结果!");
                responseBean.setDetailInfo(salesmanRelatedList);
                return "salesman/success";
            }
        } catch (Exception e) {
            logger.error("异常信息为", e);
            responseBean.setRespCode(1);
            responseBean.setRespMsg(CommonConstant.queryFailureStr);
            return "salesman/fail";
        } finally {
            logger.info("后端返回结果为 {}", JSON.toJSONString(responseBean));
        }
    }

    @RequestMapping("/downSampleFile")
    public ResponseEntity<byte[]> downSampleFile(HttpServletRequest request) throws MessagingException {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(null));
        ResponseBean responseBean = new ResponseBean();
        File sampleFile = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        ResponseEntity<byte[]> responseEntity = null;
        try {
            String fileName = "salesman-sample.txt";
            sampleFile = salesmanRelatedService.getSampleFile(fileName);
            httpHeaders.setContentDispositionFormData("attachment", fileName);
            httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            responseEntity = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(sampleFile), httpHeaders, HttpStatus.CREATED);
            responseBean.setDetailInfo(responseEntity);
        } catch (Exception e) {
            logger.error("异常信息为", e);
            responseBean.setRespCode(1);
            responseBean.setRespMsg(CommonConstant.downFailureStr);
        } finally {
            logger.info("后端返回结果为 {}", JSON.toJSONString(responseBean));
            return responseEntity;
        }
    }

}
