package com.bianjiahao.ts.utils;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class WordUtils {

    public static void readWord(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        XWPFDocument document = new XWPFDocument(inputStream);
        // 读取段落
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        //List<WordFileInfo> infos = new ArrayList<>();
        for (XWPFParagraph paragraph : paragraphs) {
            List<XWPFRun> runs = paragraph.getRuns();
            for (XWPFRun run : runs) {
                String str = run.getText(0);

                String value = TranslateToEnglish.translateToEnglish(str);
                run.setText(value,0);
            }
        }
        FileOutputStream fos = new FileOutputStream("C:\\Users\\admin\\Desktop\\test1.docx");
        document.write(fos);
        fos.close();
        document.close();
    }
}
