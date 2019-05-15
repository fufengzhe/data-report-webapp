package cn.com.chinalife.ecdata.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiexiangyu on 2018/10/29.
 */
public class FileUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    // resources/file下的文件名
    public static List<String> getStrListFromSpecifiedFile(String fileName) {
        File file = new File(SurveyIPLocationUtils.class.getResource("/file").getPath(), fileName);
        List<String> fileContent = new ArrayList<String>();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                fileContent.add(line);
            }
        } catch (FileNotFoundException e) {
            logger.error("文件未找到，异常信息为", e);
        } catch (IOException e) {
            logger.error("文件读取异常，异常信息为", e);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    logger.error("文件关闭异常，异常信息为", e);
                }
            }
        }
        return fileContent;
    }

    public static void main(String[] args) {
//        List<String> fileList = getStrListFromSpecifiedFile("fupinPageUrlList.txt");
//        for (String line : fileList) {
//            System.out.println(line);
//        }
        System.out.println(Math.round(-1.6));
    }
}
