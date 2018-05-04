package cn.com.chinalife.ecdata.service.impl.salesman;

import cn.com.chinalife.ecdata.dao.sqlDao.salesman.SalesmanRelatedDao;
import cn.com.chinalife.ecdata.entity.salesman.SalesmanRelated;
import cn.com.chinalife.ecdata.service.salesman.SalesmanRelatedService;
import cn.com.chinalife.ecdata.utils.HtmlUtils;
import cn.com.chinalife.ecdata.utils.MailUtils;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiexiangyu on 2018/3/14.
 */
@Service
public class SalesmanRelatedServiceImpl implements SalesmanRelatedService {
    private final Logger logger = LoggerFactory.getLogger(SalesmanRelatedServiceImpl.class);
    @Autowired
    SalesmanRelatedDao salesmanRelatedDao;


    public File getSampleFile(String fileName) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(fileName));
        File file = new File(SalesmanRelatedServiceImpl.class.getResource("/file").getPath(), fileName);
        logger.info("service返回结果为 {}", JSON.toJSONString(file));
        return file;
    }

    public List<SalesmanRelated> getSalesmanRelatedInfo(MultipartFile file) {
        List<String> salesmanNoList = getSalesmanNoListUsingUploadedFile(file);
        if (salesmanNoList == null) {
            return null;
        } else {
            // 因驱动有in不能超过1000的限制
            int loopTime = salesmanNoList.size() / 1000 + (salesmanNoList.size() % 1000 == 0 ? 0 : 1);
            List<SalesmanRelated> salesmanRelatedList = new ArrayList<SalesmanRelated>();
            for (int i = 1; i <= loopTime; i++) {
                int innerLoopBegin = (i - 1) * 1000;
                int innerLoopEnd = i == loopTime ? salesmanNoList.size() : (i) * 1000;
                List<String> tempSalesmanNoList = new ArrayList<String>();
                for (int j = innerLoopBegin; j < innerLoopEnd; j++) {
                    tempSalesmanNoList.add(salesmanNoList.get(j));
                }
                List<String> oldUserIdAndAccountCodeList = salesmanRelatedDao.getOldUserIdListUsingSalesmanNo(tempSalesmanNoList);
                if (oldUserIdAndAccountCodeList != null && oldUserIdAndAccountCodeList.size() > 0) {
                    Map<String, String> oldUserIdAndAccountCodeMap = new HashMap<String, String>();
                    List<String> oldUserIdList = new ArrayList<String>();
                    for (String item : oldUserIdAndAccountCodeList) {
                        String[] temp = item.split(",");
                        if (temp.length > 1) {
                            oldUserIdAndAccountCodeMap.put(temp[0], temp[1]);
                            oldUserIdList.add(temp[0]);
                        }
                    }
                    List<SalesmanRelated> tempSalesmanRelatedList = salesmanRelatedDao.getSalesmanRelatedListUsingOldUserId(oldUserIdList);
                    for (SalesmanRelated salesmanRelated : tempSalesmanRelatedList) {
                        salesmanRelated.setAccountCode(oldUserIdAndAccountCodeMap.get(salesmanRelated.getOldUserId()));
                    }
                    salesmanRelatedList.addAll(tempSalesmanRelatedList);
                }
            }
            return salesmanRelatedList;
        }
    }

    public void sendResultUsingMail(List<SalesmanRelated> salesmanRelatedList, String mail) throws MessagingException {
        String[][] tableContent = this.getTableContent(salesmanRelatedList, new String[]{"业务员工号", "姓名", "身份证号", "手机号", "银行卡号", "开户行"});
        StringBuilder html = new StringBuilder("<html>");
        html.append("<table>");
        html.append("<tr><td>").append(HtmlUtils.getTable(tableContent)).append("</tr></td>");
        html.append("</table>");
        html.append("</html>");
        String[] toList = mail.split(",");
        MailUtils.sendHtmlMail("348452440@qq.com", toList, null, null, "业务员一账通绑卡信息", html.toString());
    }

    private String[][] getTableContent(List<SalesmanRelated> salesmanRelatedList, String[] title) {
        String[][] tableContent = new String[salesmanRelatedList.size() + 1][title.length];
        for (int i = 0; i < title.length; i++) {
            tableContent[0][i] = title[i];
        }
        for (int i = 0; i < salesmanRelatedList.size(); i++) {
            SalesmanRelated salesmanRelated = salesmanRelatedList.get(i);
            tableContent[i + 1][0] = salesmanRelated.getAccountCode();
            tableContent[i + 1][1] = salesmanRelated.getUserName();
            tableContent[i + 1][2] = salesmanRelated.getIdentifyNo();
            tableContent[i + 1][3] = salesmanRelated.getMobile();
            tableContent[i + 1][4] = salesmanRelated.getBankAccount();
            tableContent[i + 1][5] = salesmanRelated.getBankName();
        }
        return tableContent;
    }

    public List<String> getSalesmanNoListUsingUploadedFile(MultipartFile file) {
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        List<String> salesmanNoList = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            String line;
            salesmanNoList = new ArrayList<String>();
            while ((line = bufferedReader.readLine()) != null) {
                salesmanNoList.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return salesmanNoList;
        }
    }
}
