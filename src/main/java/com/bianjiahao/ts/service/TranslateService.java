package com.bianjiahao.ts.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface TranslateService {

    void translateFile(MultipartFile file) throws IOException, Exception;
}
