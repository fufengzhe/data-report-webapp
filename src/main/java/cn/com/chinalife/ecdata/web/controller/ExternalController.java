package cn.com.chinalife.ecdata.web.controller;

import cn.com.chinalife.ecdata.entity.ResponseBean;
import cn.com.chinalife.ecdata.utils.CommonConstant;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by xiexiangyu on 2018/2/28.
 */
@Controller
@RequestMapping("/external")
public class ExternalController {
    private final Logger logger = LoggerFactory.getLogger(ExternalController.class);

    @RequestMapping("/userCenter")
    public String userCenter(Model model) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(null));
        ResponseBean responseBean = new ResponseBean();
        try {
        } catch (Exception e) {
            logger.error("异常信息为", e);
            responseBean.setRespCode(1);
            responseBean.setRespMsg(CommonConstant.queryFailureStr);
        } finally {
            logger.info("后端返回结果为 {}", JSON.toJSONString(responseBean));
            return "external/userCenter";
        }
    }

}
