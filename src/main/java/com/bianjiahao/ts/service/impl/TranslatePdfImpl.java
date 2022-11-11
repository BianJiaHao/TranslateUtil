package com.bianjiahao.ts.service.impl;

import com.aspose.pdf.Document;
import com.aspose.pdf.SaveFormat;
import com.bianjiahao.ts.service.TranslateService;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service("translatePdfImpl")
public class TranslatePdfImpl implements TranslateService {

    @Autowired
    private TranslateWordImpl translateWord;

    @Override
    public void translateFile(File file, HttpServletResponse response, String language) throws IOException, Exception {
        // word地址
        String wordPath = pdf2doc(file.getAbsolutePath());
        // 用word进行翻译
        String en = translateWord.translateFile(new File(wordPath), language);



        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-Type", "application/vnd.ms-pdf");
        response.setHeader("Content-Disposition",
                "attachment;filename=\"" +
                        new String(("translate" + ".pdf").getBytes(StandardCharsets.UTF_8), "ISO8859_1") + "\"");
        ServletOutputStream out = response.getOutputStream();

        FileInputStream fileInputStream = new FileInputStream(en);
        XWPFDocument xwpfDocument = new XWPFDocument(fileInputStream);
        PdfOptions pdfOptions = PdfOptions.create();

        PdfConverter.getInstance().convert(xwpfDocument,out,pdfOptions);
        fileInputStream.close();


        out.flush();
        out.close();





    }

    public  String pdf2doc(String pdfPath) {
        long old = System.currentTimeMillis();
        String wordPath = null;
        try {
            //新建一个word文档
            wordPath=pdfPath.substring(0,pdfPath.lastIndexOf("."))+".docx";
            FileOutputStream os = new FileOutputStream(wordPath);
            //doc是将要被转化的word文档
            Document doc = new Document(pdfPath);
            //全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            doc.save(os, SaveFormat.DocX);
            os.close();
            //转化用时
            long now = System.currentTimeMillis();
            System.out.println("Pdf 转 Word 共耗时：" + ((now - old) / 1000.0) + "秒");
        } catch (Exception e) {
            System.out.println("Pdf 转 Word 失败...");
            e.printStackTrace();
        }

        return wordPath;
    }


}
