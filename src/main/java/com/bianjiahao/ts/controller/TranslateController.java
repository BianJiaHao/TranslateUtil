package com.bianjiahao.ts.controller;

import com.bianjiahao.ts.utils.ExcelUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;

/**
 * @author bianjiahao
 */
@RestController
@RequestMapping("translate")
public class TranslateController {

    @PostMapping("/test")
    public void test(HttpServletResponse response, @RequestParam("file") MultipartFile file) throws Exception{
        ExcelUtils.readExcel(file);
    }
}
