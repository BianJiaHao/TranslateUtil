package com.bianjiahao.ts.controller;

import com.bianjiahao.ts.service.impl.FileServiceImpl;
import com.bianjiahao.ts.service.impl.TranslateExcelImpl;
import com.bianjiahao.ts.service.impl.TranslatePdfImpl;
import com.bianjiahao.ts.service.impl.TranslateWordImpl;
import com.bianjiahao.ts.utils.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author bianjiahao
 */
@RestController
@RequestMapping("translate")
public class TranslateController {

    @Autowired
    private TranslateWordImpl translateWord;
    @Autowired
    private TranslateExcelImpl translateExcel;
    @Autowired
    private TranslatePdfImpl translatePdf;
    @Autowired
    private FileServiceImpl fileService;

    @PostMapping("/upload")
    public R upload(@RequestParam MultipartFile file) throws IOException {
        String filePath = fileService.uploadFile(file);
        return R.ok().put("filePath",filePath);
    }

    @RequestMapping("/test/{path}")
    public void test(HttpServletResponse response, @PathVariable("path") String path) throws Exception{
        String[] split1 = path.split("-");
        String name = split1[0];
        String language = split1[1];
        String resultPath = "/root/ts/file/" + name;
        //String resultPath = "C:\\Users\\admin\\Desktop\\" + name;
        File file = new File(resultPath);


        // 获取文件类型
        String fileName = file.getName();
        if (StringUtils.isBlank(fileName)) {
            throw new RuntimeException("file is empty");
        }
        String[] split = fileName.split("\\.");
        String fileType = split[split.length - 1];
        if ("doc".equals(fileType) || "docx".equals(fileType)) {
            translateWord.translateFile(file,response,language);
        }else if ("xls".equals(fileType) || "xlsx".equals(fileType)) {
            translateExcel.translateFile(file,response,language);
        } else if ("pdf".equals(fileType)) {
            translatePdf.translateFile(file,response,language);
        }
    }
}
