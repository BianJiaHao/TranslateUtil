package com.bianjiahao.ts.service.impl;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.bianjiahao.ts.service.TranslateService;
import com.volcengine.service.translate.ITranslateService;
import com.volcengine.service.translate.impl.TranslateServiceImpl;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;

@Service("excelTranslate")
public class TranslateExcelImpl implements TranslateService {

    private final static String XLS = "xls";
    private final static String XLSX = "xlsx";
    ITranslateService translateService = TranslateServiceImpl.getInstance();

    @Autowired
    private TranslateCommonImpl translateCommon;

    @Override
    public void translateFile(File file, HttpServletResponse response,String language) throws Exception{
        // 检查文件
        checkFile(file);
        // 获得Workbook工作薄对象
        InputStream is = new FileInputStream(file);
        ExcelReader reader = ExcelUtil.getReader(is);
        Workbook workbook = reader.getWorkbook();

        if (workbook != null) {
            for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
                // 跳过隐藏表
                if (workbook.isSheetHidden(sheetNum)) {
                    continue;
                }
                // 获得当前sheet工作表
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if (sheet == null) {
                    continue;
                }
                // 无子表名跳过
                if ("".equals(sheet.getSheetName())) {
                    continue;
                }
                // 获得当前sheet的开始行
                int firstRowNum = sheet.getFirstRowNum();
                // 获得当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();
                for (int rowNum = firstRowNum; rowNum <= lastRowNum; rowNum++) {
                    // 获得当前行
                    Row row = sheet.getRow(rowNum);
                    if (row == null || row.getPhysicalNumberOfCells() == 0) {
                        continue;
                    }
                    // 获得当前行的开始列
                    int firstCellNum = row.getFirstCellNum();
                    // 获得当前行的列数
                    int lastCellNum = row.getPhysicalNumberOfCells();
                    // 循环当前行的所有列
                    for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
                        Cell cell = row.getCell(cellNum);
                        if (cell != null) {
                            String cellValue = getCellValue(cell);
                            String value = translateCommon.translateToEnglish(cellValue,translateService,language);
                            cell.setCellValue(value);
                        }
                    }
                }
            }

            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition",
                    "attachment;filename=\"" +
                            new String(("translate" + ".xlsx").getBytes(StandardCharsets.UTF_8), "ISO8859_1") + "\"");
            ServletOutputStream out = response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();
        }
    }

    public void checkFile(File file) throws IOException {
        // 判断文件是否存在
        if (null == file) {
            throw new FileNotFoundException("文件不存在！");
        }
        // 获得文件名
        String fileName = file.getName();
        // 判断文件是否是excel文件
        if (fileName != null && !fileName.endsWith(XLS) && !fileName.endsWith(XLSX)) {
            throw new IOException(fileName + "不是excel文件");
        }
    }

    public String getCellValue(Cell cell) {
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
            case Cell.CELL_TYPE_NUMERIC:
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING:
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA:
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BLANK:
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR:
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }
}
