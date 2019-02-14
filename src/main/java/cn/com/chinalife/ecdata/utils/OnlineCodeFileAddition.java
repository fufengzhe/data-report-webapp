package cn.com.chinalife.ecdata.utils;

import java.io.*;

/**
 * Created by xiexiangyu on 2019/1/18.
 */
public class OnlineCodeFileAddition {
    private static int changeFileNum=0;
    public static void main(String[] args) throws Exception {
        traversalOnline("D:/online");
        System.out.println(changeFileNum);
    }

    private static void traversalOnline(String filePath) throws Exception {
        File file = new File(filePath);
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null && files.length != 0) {
                    for (File tempFile : files) {
                        if (tempFile.isDirectory()) {
                            traversalOnline(tempFile.getAbsolutePath());
                        } else {
                            readAndChangeContent(tempFile);
                        }
                    }
                }
            } else {
                readAndChangeContent(file);
            }
        }
    }

    private static void readAndChangeContent(File file) throws Exception {
        if (file.getName().endsWith("jsp")) {
            StringBuilder fileContent = new StringBuilder("");
            BufferedReader bufferedReader = null;
            FileInputStream fileInputStream = null;
            fileInputStream = new FileInputStream(file);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, "GB2312"));
            boolean hasHead = false;
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("</head>")) {
                    hasHead = true;
                    fileContent.append("<script type='text/javascript'>\n" +
                            "!function(e,t,n,g,i){e[i]=e[i]||function(){(e[i].q=e[i].q||[]).push(arguments)},n=t.createElement(\"script\"),tag=t.getElementsByTagName(\"script\")[0],n.async=1,n.src=('https:'==document.location.protocol?'https://':'http://')+g,tag.parentNode.insertBefore(n,tag)}(window,document,\"script\",\"assets.growingio.com/op/2.0/gio.js\",\"gio\");\n" +
                            "gio('init', '951b4dbc812156df', \n" +
                            "{'setImp':false,\n" +
                            "'setTrackerHost':'w1.chinalife.com.cn:443', \n" +
                            "'setTrackerScheme':'https',\n" +
                            "'setOrigin':'https://w2.chinalife.com.cn' \n" +
                            "});\n" +
                            "gio('send');\n" +
                            "</script>");
                }
                fileContent.append(line + "\n");
            }
            if (hasHead) {
                changeFileNum++;
                PrintWriter printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "GB2312")));
                printWriter.println(fileContent);
                printWriter.close();
            }
            bufferedReader.close();
            fileInputStream.close();
        }
    }
}
