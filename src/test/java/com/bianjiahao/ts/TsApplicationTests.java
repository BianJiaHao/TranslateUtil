package com.bianjiahao.ts;

import org.junit.Test;
import com.alibaba.fastjson.JSON;
import com.volcengine.model.request.LangDetectRequest;
import com.volcengine.model.request.TranslateTextRequest;
import com.volcengine.model.response.LangDetectResponse;
import com.volcengine.model.response.TranslateTextResponse;
import com.volcengine.service.translate.ITranslateService;
import com.volcengine.service.translate.impl.TranslateServiceImpl;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class TsApplicationTests {

    @Test
    void contextLoads() {
    }

    public static void main(String[] args) {
        ITranslateService translateService = TranslateServiceImpl.getInstance();

        translateService.setAccessKey("AKLTNWQ4ZjVkNjFmNWYwNGQzNmFmNDM4MDU4Y2M5ZjVmZjU");
        translateService.setSecretKey("Wm1RME9XSXhOR1EyWm1Rd05EZGxZMkUyTldOak5qUXdNR1ZsTVdJMFl6UQ==");

        // lang detect
        try {
            LangDetectRequest langDetectRequest = new LangDetectRequest();
            langDetectRequest.setTextList(Arrays.asList("hello world", "how are you"));

            LangDetectResponse langDetectResponse = translateService.langDetect(langDetectRequest);
            System.out.println(JSON.toJSONString(langDetectResponse));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // translate text
        try {
            TranslateTextRequest translateTextRequest = new TranslateTextRequest();
            // translateTextRequest.setSourceLanguage("en"); // 不设置表示自动检测
            translateTextRequest.setTargetLanguage("zh");
            translateTextRequest.setTextList(Arrays.asList("hello world", "how are you"));

            TranslateTextResponse translateText = translateService.translateText(translateTextRequest);

            List<TranslateTextResponse.Translation> list = translateText.getTranslationList();
            for (TranslateTextResponse.Translation translation : list) {
                System.out.println(translation.getTranslation());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
