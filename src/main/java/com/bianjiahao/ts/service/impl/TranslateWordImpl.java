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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service("wordTranslate")
public class TranslateWordImpl implements TranslateService {
    ITranslateService translateService = TranslateServiceImpl.getInstance();

    @Autowired
    private TranslateCommonImpl translateCommon;

    @Override
    public void translateFile(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        XWPFDocument document = new XWPFDocument(inputStream);
        // 读取段落
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        //List<WordFileInfo> infos = new ArrayList<>();
        for (XWPFParagraph paragraph : paragraphs) {
            String text = paragraph.getParagraphText();
            if (StringUtils.isNotBlank(text)) {
                String value = translateCommon.translateToEnglish(text, translateService);
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
        FileOutputStream fos = new FileOutputStream("C:\\Users\\admin\\Desktop\\test1.docx");
        document.write(fos);
        fos.close();
        document.close();
    }

}
