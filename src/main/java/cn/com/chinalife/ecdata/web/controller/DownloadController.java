package cn.com.chinalife.ecdata.web.controller;

import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.sale.SaleOrder;
import cn.com.chinalife.ecdata.service.sale.SaleOrderService;
import com.alibaba.fastjson.JSON;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/6.
 */
@Controller
@RequestMapping("/download")
public class DownloadController {
    private final Logger logger = LoggerFactory.getLogger(DownloadController.class);
    @Autowired
    SaleOrderService saleOrderService;

    @RequestMapping("/saleOrderDetail")
    public void downloadSaleOrderDetail(QueryPara queryPara, HttpServletResponse response) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(queryPara));
        try {
            List<SaleOrder> saleOrderDetailsList = saleOrderService.getSaleOrderDetailList(queryPara);
            Workbook file = saleOrderService.getOrderDetailWorkBook(saleOrderDetailsList);
            OutputStream fileOutputStream = new ByteArrayOutputStream();
            file.write(fileOutputStream);
            String fileName = "保单详情" + queryPara.getStartDate() + "~" + queryPara.getEndDate() + ".xlsx";
            downloadExcelFile(response, fileOutputStream, fileName);
        } catch (Exception e) {
            logger.error("下载保单详情失败", e);
        }
    }

    private void downloadExcelFile(HttpServletResponse response, OutputStream fileOutputStream, String fileName) {
        OutputStream outputStream = null;
        try {
            outputStream = new BufferedOutputStream(response.getOutputStream());
            byte[] content = ((ByteArrayOutputStream) fileOutputStream).toByteArray();
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"), "ISO-8859-1"));
            response.addHeader("Content-Length", "" + content.length);
            response.setContentType("application/octet-stream");
            outputStream.write(content, 0, content.length);
            outputStream.flush();
        } catch (IOException e) {
            logger.error("下载文件失败", e);
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    logger.error("关闭输出流失败", e);
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    logger.error("关闭输出流失败", e);
                }
            }
        }
    }
}
