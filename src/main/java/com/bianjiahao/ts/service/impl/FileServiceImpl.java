package com.bianjiahao.ts.service.impl;

import com.bianjiahao.ts.service.FileService;
import com.bianjiahao.ts.utils.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service("fileService")
public class FileServiceImpl implements FileService {
    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        return FileUtils.save(file);
    }
}
