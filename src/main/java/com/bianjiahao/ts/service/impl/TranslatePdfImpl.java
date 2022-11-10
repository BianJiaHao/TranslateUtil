package com.bianjiahao.ts.service.impl;

import com.bianjiahao.ts.service.TranslateService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class TranslatePdfImpl implements TranslateService {


    @Override
    public void translateFile(File file, HttpServletResponse response,String language) throws IOException, Exception {
//        PDDocument document;
//        String text = "";
//        try {
//            document = PDDocument.load(file.getInputStream());
//            PDPageTree pages = document.getPages();
//
//
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

    }
}
