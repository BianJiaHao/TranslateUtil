package com.bianjiahao.ts.utils;

import com.alibaba.fastjson.JSON;
import com.volcengine.model.request.LangDetectRequest;
import com.volcengine.model.request.TranslateTextRequest;
import com.volcengine.model.response.LangDetectResponse;
import com.volcengine.model.response.TranslateTextResponse;
import com.volcengine.service.translate.ITranslateService;
import com.volcengine.service.translate.impl.TranslateServiceImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author bianjiahao
 */
public class TranslateToEnglish {

    public static String translateToEnglish(String text) {
        ITranslateService translateService = TranslateServiceImpl.getInstance();

        translateService.setAccessKey("AKLTNWQ4ZjVkNjFmNWYwNGQzNmFmNDM4MDU4Y2M5ZjVmZjU");
        translateService.setSecretKey("Wm1RME9XSXhOR1EyWm1Rd05EZGxZMkUyTldOak5qUXdNR1ZsTVdJMFl6UQ==");

        String ans = "";
        // translate text
        try {
            TranslateTextRequest translateTextRequest = new TranslateTextRequest();
            // translateTextRequest.setSourceLanguage("en"); // 不设置表示自动检测
            translateTextRequest.setTargetLanguage("en");
            translateTextRequest.setTextList(Collections.singletonList(text));

            TranslateTextResponse translateText = translateService.translateText(translateTextRequest);

            List<TranslateTextResponse.Translation> list = translateText.getTranslationList();
            System.out.println(text);
            ans = list.get(0).getTranslation();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ans;
    }
}
