package cn.com.chinalife.ecdata.utils;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by xiexiangyu on 2019/5/16.
 */
public class ExcelUtils {
    private final static Logger logger = LoggerFactory.getLogger(ExcelUtils.class);
    public static final int DATE_FORMAT = 1;
    public static final int DATETIME_FORMAT = 2;
    public static final int INTEGER_FORMAT = 3;
    public static final int DOUBLE_FORMAT = 4;
    public static final int STRING_FORMAT = 5;
    public static final int TIME_FORMAT = 6;
    public static final int LONG_FORMAT = 7;
    public static final int MAX_EXCEL_CELL_NUM = 1650000;
    public static final int MAX_LINE_NUM = 300000;

    // flagForDateFormat 0 表示0000-00-00 1表示0000-00-00 00:00:00
    public static Workbook getWorkBook(Map<String, String> columnFieldMap, Map<String, Integer> fieldTypeMap, List<?> dataBeanList, Map<String, Object> fakedDataMap) {
        logger.info("生成excel文件:");
        long start = System.currentTimeMillis();
        Workbook workbook = new XSSFWorkbook();
        setWorkbookStyle(workbook);
        XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
        XSSFSheet sheet = (XSSFSheet) workbook.createSheet();
        XSSFDataFormat format = (XSSFDataFormat) workbook.createDataFormat();
        XSSFRow row = sheet.createRow(0);
        generateSheetTitle(columnFieldMap.keySet(), row);
        generateSheetContent(sheet, style, format, columnFieldMap, fieldTypeMap, dataBeanList, fakedDataMap);
        long end = System.currentTimeMillis();
        logger.info("生成excel文件耗时:" + (end - start));
        return workbook;
    }


    private static void generateSheetContent(XSSFSheet sheet, XSSFCellStyle style, XSSFDataFormat format, Map<String, Object[]> columnTitleMap, Map<String, Integer> fieldTypeMap,
                                             List<?> dataBeanList) {
        if (null != dataBeanList && !dataBeanList.isEmpty()) {
            for (int i = 0; i < dataBeanList.size(); i++) {
                XSSFRow row = sheet.createRow(i + 1); // 因为已经有title了,所以要加1
                Object object = dataBeanList.get(i);
                generateRows(row, columnTitleMap, fieldTypeMap, object);
            }
        }
    }

    private static void generateRows(XSSFRow row, Map<String, Object[]> columnTitleMap, Map<String, Integer> fieldTypeMap, Object object) {
        int cellIndex = 0;
        for (String column : columnTitleMap.keySet()) {
            XSSFCell cell = row.createCell(cellIndex++);
            getAndSetCellValue(cell, column, object, fieldTypeMap);
        }
    }


    public static List<String> getTitles(Map<String, Object[]> columnTitleMap) {
        List<String> titles = new ArrayList<String>();
        for (String column : columnTitleMap.keySet()) {
            Object[] arr = columnTitleMap.get(column);
            titles.add(arr[0].toString());
        }
        return titles;
    }


    private static void setWorkbookStyle(Workbook workbook) {
        XSSFCellStyle cellStyle = (XSSFCellStyle) workbook.createCellStyle();
        XSSFFont font = (XSSFFont) workbook.createFont();
        font.setFontName("微软雅黑");
        cellStyle.setFont(font);
    }

    private static void generateSheetTitle(Set<String> columnNames, XSSFRow row) {
        int titleIndex = 0;
        for (String columnName : columnNames) {
            XSSFCell cell = row.createCell(titleIndex++);
            cell.setCellValue(columnName);
        }
    }

    private static void generateSheetTitle(List<String> columnNames, XSSFRow row) {
        int titleIndex = 0;
        for (String columnName : columnNames) {
            XSSFCell cell = row.createCell(titleIndex++);
            cell.setCellValue(columnName);
        }
    }

    private static void generateSheetContent(XSSFSheet sheet, XSSFCellStyle style, XSSFDataFormat format, Map<String, String> columnFieldMap, Map<String, Integer> fieldTypeMap,
                                             List<?> dataBeanList, Map<String, Object> fakedDataMap) {
        if (dataBeanList != null) {
            for (int i = 0; i < dataBeanList.size(); i++) {
                XSSFRow row = sheet.createRow(i + 1); // 因为已经有title了,所以要加1
                Object object = dataBeanList.get(i);
                generateRowUsingBean(row, columnFieldMap, fieldTypeMap, object);
            }
        } else {
            generateRowForFakedData(sheet, style, format, columnFieldMap, fieldTypeMap, fakedDataMap);
        }
    }

    private static void generateSheetContent(XSSFSheet sheet, List<List<Object>> rows, List<Integer> formatFlags) {
        for (int i = 0; i < rows.size(); i++) {
            XSSFRow row = sheet.createRow(i + 1); // 因为已经有title了，所以要加1
            List<Object> rowData = rows.get(i);
            generateRowUsingRowData(row, rowData, formatFlags);
        }
    }

    private static void generateRowUsingRowData(XSSFRow row, List<Object> rowData, List<Integer> formatFlags) {
        for (int i = 0; i < rowData.size(); i++) {
            XSSFCell cell = row.createCell(i);
            Object data = rowData.get(i);
            Integer formatFlag = formatFlags.get(i);
            generateCellUsingDataAndFormatFlag(cell, data, formatFlag);
        }
    }

    private static void generateCellUsingDataAndFormatFlag(XSSFCell cell, Object data, Integer formatFlag) {
        switch (formatFlag) {
            case ExcelUtils.DATE_FORMAT:
                cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(data));
                break;
            case ExcelUtils.DATETIME_FORMAT:
                cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(data));
                break;
            case ExcelUtils.INTEGER_FORMAT:
                cell.setCellValue((Integer) data);
                break;
            case ExcelUtils.LONG_FORMAT:
                cell.setCellValue((Long) data);
                break;
            case ExcelUtils.DOUBLE_FORMAT:
                cell.setCellValue((Double) data);
                break;
            case ExcelUtils.TIME_FORMAT:
                cell.setCellValue(new SimpleDateFormat("HH:mm:ss").format(data));
                break;
            default:
                cell.setCellValue(data.toString());
                break;
        }
    }

    private static void generateRowUsingBean(XSSFRow row, Map<String, String> columnFieldMap, Map<String, Integer> fieldTypeMap, Object object) {
        int cellIndex = 0;
        for (Map.Entry<String, String> entry : columnFieldMap.entrySet()) {
            XSSFCell cell = row.createCell(cellIndex++);
            getAndSetCellValue(cell, entry.getValue(), object, fieldTypeMap); // 根据反射机制获取bean中对应属性的值
        }
    }

    private static void getAndSetCellValue(XSSFCell cell, String fieldName, Object object, Map<String, Integer> fieldTypeMap) {
        Object value = null;
        try {
            if (object instanceof Map<?, ?>) {
                Map<String, Object> map = (Map<String, Object>) object;
                value = map.get(fieldName);
            } else {
                Class beanClass = object.getClass();
                String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                Method getMethod = beanClass.getMethod(getMethodName, new Class[]{});
                value = getMethod.invoke(object, new Object[]{});
            }
            if (fieldTypeMap.containsKey(fieldName) && value != null && !value.toString().equals("")) {
                int format_flag = fieldTypeMap.get(fieldName);
                switch (format_flag) {
                    case ExcelUtils.DATE_FORMAT:
                        cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(value));
                        break;
                    case ExcelUtils.DATETIME_FORMAT:
                        cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(value));
                        break;
                    case ExcelUtils.INTEGER_FORMAT:
                        cell.setCellValue(Integer.parseInt(value.toString()));
                        break;
                    case ExcelUtils.DOUBLE_FORMAT:
                        cell.setCellValue(Double.parseDouble(value.toString()));
                        break;
                    case ExcelUtils.TIME_FORMAT:
                        cell.setCellValue(new SimpleDateFormat("HH:mm:ss").format(value));
                        break;
                    default:
                        cell.setCellValue(value.toString());
                        break;
                }
            } else {
                cell.setCellValue(value == null ? "" : value.toString());
            }

        } catch (Exception e) {
            logger.info("反射方法调用失败！", e);
        }
    }

    private static void generateRowForFakedData(XSSFSheet sheet, XSSFCellStyle style, XSSFDataFormat format, Map<String, String> columnFieldMap, Map<String, Integer> fieldTypeMap,
                                                Map<String, Object> fakedDataMap) {
        XSSFRow row = sheet.createRow(1); // 因为标题已经占据一行
        int cellIndex = 0;
        for (Map.Entry<String, String> entry : columnFieldMap.entrySet()) {
            XSSFCell cell = row.createCell(cellIndex++);
            String fieldName = entry.getValue();
            Object value = fakedDataMap.get(fieldName);
            if (fieldTypeMap.containsKey(fieldName)) {
                int format_flag = fieldTypeMap.get(fieldName);
                switch (format_flag) {
                    case ExcelUtils.DATE_FORMAT:
                        cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(value));
                        break;
                    case ExcelUtils.DATETIME_FORMAT:
                        cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(value));
                        break;
                    case ExcelUtils.INTEGER_FORMAT:
                        cell.setCellValue((Integer) value);
                        break;
                    case ExcelUtils.DOUBLE_FORMAT:
                        cell.setCellValue((Double) value);
                        break;
                    case ExcelUtils.TIME_FORMAT:
                        cell.setCellValue((Date) value);
                        style.setDataFormat(format.getFormat("hh:mm:ss"));
                        cell.setCellStyle(style);
                        break;
                    default:
                        cell.setCellValue(value.toString());
                        break;
                }
            } else {
                cell.setCellValue(value.toString());
            }
        }
    }

    public static Workbook getWorkBook(List<String> titles, List<Integer> formatFlags, List<List<Object>> rows) {
        logger.info("生成excel文件:");
        long start = System.currentTimeMillis();
        Workbook workbook = new XSSFWorkbook();
        setWorkbookStyle(workbook);
        XSSFSheet sheet = (XSSFSheet) workbook.createSheet();
        XSSFRow row = sheet.createRow(0);
        generateSheetTitle(titles, row);
        generateSheetContent(sheet, rows, formatFlags);
        long end = System.currentTimeMillis();
        logger.info("生成excel文件耗时:" + (end - start));
        return workbook;
    }
}
