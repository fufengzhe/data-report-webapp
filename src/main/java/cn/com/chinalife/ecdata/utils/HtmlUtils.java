package cn.com.chinalife.ecdata.utils;

/**
 * Created by xiexiangyu on 2018/4/12.
 */
public class HtmlUtils {
    private static final String attributeOfTr = "style=\"background-color: #CDE6F9; COLOR: #000000; font-weight: bold;\"";
    private static final String attributeOfTable = "align=\"left\" border=\"1\" cellpadding=\"1\" cellspacing=\"0\" style=\"text-align: center; font-size: 14px; FONT-FAMILY: 微软雅黑\"";

    public static String getTable(String[][] tableContent) {
        StringBuilder tableStr = new StringBuilder("<table").append(" ").append(attributeOfTable).append(">");
        tableStr.append("<tr").append(" ").append(attributeOfTr).append(">");
        String[] titles = tableContent[0];
        for (String title : titles) {
            tableStr.append("<td>").append(title).append("</td>");
        }
        tableStr.append("</tr>");
        for (int i = 1; i < tableContent.length; i++) {
            tableStr.append("<tr>");
            for (int j = 0; j < tableContent[i].length; j++) {
                tableStr.append("<td>").append(tableContent[i][j]).append("</td>");
            }
            tableStr.append("</tr>");
        }
        tableStr.append("</table>");
        return tableStr.toString();
    }
}
