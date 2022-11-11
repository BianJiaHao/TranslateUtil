package com.bianjiahao.ts.service.impl;

import com.bianjiahao.ts.service.TranslateService;
import com.volcengine.service.translate.ITranslateService;
import com.volcengine.service.translate.impl.TranslateServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service("wordTranslate")
public class TranslateWordImpl implements TranslateService {
    ITranslateService translateService = TranslateServiceImpl.getInstance();

    @Autowired
    private TranslateCommonImpl translateCommon;

    @Override
    public void translateFile(File file, HttpServletResponse response,String language) throws IOException {
        InputStream inputStream = new FileInputStream(file);
        XWPFDocument document = new XWPFDocument(inputStream);
        // 读取段落
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        //List<WordFileInfo> infos = new ArrayList<>();
        for (XWPFParagraph paragraph : paragraphs) {
            String text = paragraph.getParagraphText();
            if (StringUtils.isNotBlank(text)) {
                String value = translateCommon.translateToEnglish(text, translateService,language);
                List<XWPFRun> runs = paragraph.getRuns();
                if (runs != null && runs.size() > 0) {
                    XWPFRun run = runs.get(0);
                    run.setText(value, 0);
                    int runSize = runs.size();
                    for (int j = runSize - 1; j >= 1; j--) {
                        paragraph.removeRun(j);
                    }
                }
            }
        }


        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-Type", "application/vnd.ms-word");
        response.setHeader("Content-Disposition",
                "attachment;filename=\"" +
                        new String(("translate" + ".docx").getBytes(StandardCharsets.UTF_8), "ISO8859_1") + "\"");
        ServletOutputStream out = response.getOutputStream();
        document.write(out);
        out.flush();
        out.close();
        document.close();
    }

    public String translateFile(File file,String language) throws IOException {
        InputStream inputStream = new FileInputStream(file);
        XWPFDocument document = new XWPFDocument(inputStream);
        // 读取段落
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        //List<WordFileInfo> infos = new ArrayList<>();
        for (XWPFParagraph paragraph : paragraphs) {
            String text = paragraph.getParagraphText();
            if (StringUtils.isNotBlank(text)) {
                String value = translateCommon.translateToEnglish(text, translateService,language);
                List<XWPFRun> runs = paragraph.getRuns();
                if (runs != null && runs.size() > 0) {
                    XWPFRun run = runs.get(0);
                    run.setText(value, 0);
                    int runSize = runs.size();
                    for (int j = runSize - 1; j >= 1; j--) {
                        paragraph.removeRun(j);
                    }
                }
            }
        }
        String name = file.getName();
        String[] split = name.split("\\.");


        String newPath =  "C:\\Users\\admin\\Desktop" + File.separator + split[0] + "_translated" + "." + split[1];
        FileOutputStream fileOutputStream = new FileOutputStream(newPath);
        document.write(fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
        document.close();
        return newPath;
    }

}
