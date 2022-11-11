package com.bianjiahao.ts.utils;


import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileUtils {

    /**
     * 保存文件并返回路径
     */
    public static String save(MultipartFile mfile) throws IllegalStateException, IOException {
        String originalFilename = mfile.getOriginalFilename();
        String[] split = originalFilename.split("\\.");
        String fileName = System.currentTimeMillis() + "." + split[split.length - 1];
        String resultPath = "/root/ts/file/" + fileName;
        //String resultPath = "C:\\Users\\admin\\Desktop\\" + fileName;
        System.out.println(resultPath);
        File f = new File(resultPath);
        mfile.transferTo(f);
        return fileName;
    }



}
