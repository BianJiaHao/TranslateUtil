package com.bianjiahao.ts.controller;

import com.bianjiahao.ts.utils.ExcelUtils;
import com.bianjiahao.ts.utils.WordUtils;
import org.apache.commons.lang3.StringUtils;
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
        // 获取文件类型
        String fileName = file.getOriginalFilename();
        if (StringUtils.isBlank(fileName)) {
            throw new RuntimeException("file is empty");
        }
        String[] split = fileName.split("\\.");
        String fileType = split[split.length - 1];
        if ("doc".equals(fileType) || "docx".equals(fileType)) {
            WordUtils.readWord(file);
        }

        //ExcelUtils.readExcel(file);
    }
}
