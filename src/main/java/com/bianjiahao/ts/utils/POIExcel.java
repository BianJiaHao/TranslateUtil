package com.bianjiahao.ts.utils;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelWriter;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import cn.hutool.poi.excel.ExcelUtil;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * excel读写工具类
 */
public class POIExcel {
    private final static String XLS = "xls";
    private final static String XLSX = "xlsx";


    public static void readExcel(HttpServletResponse response, MultipartFile file) throws IOException {
        // 检查文件
        checkFile(file);
        // 获得Workbook工作薄对象
        InputStream is = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(is);
        Workbook workbook = reader.getWorkbook();

        // 创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
        HashMap<String, List<String[]>> excelMap = new HashMap<String, List<String[]>>() {{
            put("a", null);
        }};
        if (workbook != null) {
            for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
                // --跳过隐藏表
                if (workbook.isSheetHidden(sheetNum)) {
                    continue;
                }
                List<String[]> sheetList = new ArrayList<String[]>();

                // 获得当前sheet工作表
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if (sheet == null) {
                    continue;
                }
                //--无子表名跳过
                if ("".equals(sheet.getSheetName())) {
                    continue;
                }
                // 获得当前sheet的开始行
                int firstRowNum = sheet.getFirstRowNum();
                // 获得当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();
                // // 循环除了第一行的所有行
                // for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum;
                // rowNum++) {
                // 循环所有行
                for (int rowNum = firstRowNum; rowNum <= lastRowNum; rowNum++) {
                    // 获得当前行
                    Row row = sheet.getRow(rowNum);
                    if (row == null) {
                        continue;
                    }
                    // 获得当前行的开始列
                    int firstCellNum = row.getFirstCellNum();
                    // 获得当前行的列数
                    int lastCellNum = row.getPhysicalNumberOfCells();
                    String[] cells = new String[row.getPhysicalNumberOfCells()];
                    // 循环当前行的所有列
                    for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
                        if (row == null || row.getPhysicalNumberOfCells() == 0) {
                            continue;
                        }else {
                            Cell cell = row.getCell(cellNum);
                            if (cell != null) {
                                String cellValue = getCellValue(cell);
                                String value = TranslateToEnglish.translateToEnglish(cellValue);
                                cell.setCellValue(value);
                            }
                        }




                    }

                }

            }
            FileOutputStream fos = new FileOutputStream("C:\\Users\\admin\\Desktop\\test1.xlsx");
            workbook.write(fos);
            fos.close();
            workbook.close();

        }
        excelMap.remove("a");//--删除Map初始化时的内容
    }

    public static void checkFile(MultipartFile file) throws IOException {
        // 判断文件是否存在
        if (null == file) {
            throw new FileNotFoundException("文件不存在！");
        }
        // 获得文件名
        String fileName = file.getOriginalFilename();
        // 判断文件是否是excel文件
        if (!fileName.endsWith(XLS) && !fileName.endsWith(XLSX)) {
            throw new IOException(fileName + "不是excel文件");
        }
    }

    public static Workbook getWorkBook(MultipartFile file) {
        // 获得文件名
        String fileName = file.getOriginalFilename();
        // 创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        try {
            // 获取excel文件的io流
            InputStream is = file.getInputStream();
            // 根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
//            if (fileName.endsWith(XLS)) {
//                // 2003
//                workbook = new HSSFWorkbook(is);
//
//            } else if (fileName.endsWith(XLSX)) {
//                // 2007
//                workbook = new XSSFWorkbook(is);
//            }
            ExcelReader reader = ExcelUtil.getReader(is);
            workbook = reader.getWorkbook();
        } catch (IOException e) {
        }
        return workbook;
    }

    public static String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        // 把数字当成String来读，避免出现1读成1.0的情况
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            cell.setCellType(Cell.CELL_TYPE_STRING);
        }
        // 判断数据的类型
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC: // 数字
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING: // 字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN: // Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA: // 公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BLANK: // 空值
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR: // 故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }
}