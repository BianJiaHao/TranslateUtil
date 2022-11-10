package com.bianjiahao.ts.service.impl;

import com.volcengine.model.request.TranslateTextRequest;
import com.volcengine.model.response.TranslateTextResponse;
import com.volcengine.service.translate.ITranslateService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author admin
 */
@Service("translateCommonImpl")
public class TranslateCommonImpl {


    public String translateToEnglish(String text,ITranslateService translateService,String language) {

        translateService.setAccessKey("AKLTNWQ4ZjVkNjFmNWYwNGQzNmFmNDM4MDU4Y2M5ZjVmZjU");
        translateService.setSecretKey("Wm1RME9XSXhOR1EyWm1Rd05EZGxZMkUyTldOak5qUXdNR1ZsTVdJMFl6UQ==");

        String ans = "";
        // translate text
        try {
            TranslateTextRequest translateTextRequest = new TranslateTextRequest();
            // translateTextRequest.setSourceLanguage("en"); // 不设置表示自动检测
            translateTextRequest.setTargetLanguage(language);



            translateTextRequest.setTextList(Collections.singletonList(text));
            TranslateTextResponse translateText = translateService.translateText(translateTextRequest);
            List<TranslateTextResponse.Translation> list = translateText.getTranslationList();
            ans = list.get(0).getTranslation();
        } catch (Exception e) {
            System.out.println(text);
            e.printStackTrace();
        }
        return ans;
    }
}
