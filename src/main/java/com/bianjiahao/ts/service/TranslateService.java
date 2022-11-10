package com.bianjiahao.ts.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public interface TranslateService {

    void translateFile(File file, HttpServletResponse response,String language) throws IOException, Exception;
}
