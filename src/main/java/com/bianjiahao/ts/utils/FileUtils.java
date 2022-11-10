package com.bianjiahao.ts.utils;


import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileUtils {
    public static final String RES_PATH = new File(System.getProperty("user.dir"))+"webapps"+ File.separator + "fileserver" + File.separator + "ctq" + File.separator + "fileResource" + File.separator;
    public static final String WEB_PATH = "resources/";

    /**
     * 保存文件并返回路径
     */
    public static String save(MultipartFile mfile) throws IllegalStateException, IOException {
        String originalFilename = mfile.getOriginalFilename();
        String[] split = originalFilename.split("\\.");
        String fileName = System.currentTimeMillis() + "." + split[split.length - 1];
        String resultPath = "/root/ts/file/" + fileName;
        System.out.println(resultPath);
        File f = new File(resultPath);
        mfile.transferTo(f);
        return fileName;
    }
    /**
     * 保存文件并返回路径
     */
    public static String savePdf(MultipartFile mfile)
            throws IllegalStateException, IOException {
        String fileName = System.currentTimeMillis() + ".pdf";
        String resultPath = System.getProperty("user.dir")+ "/webapps/fileserver/ctq/fileResource/"+ fileName;
        System.out.println(resultPath);
        String dataPath =  "fileserver/ctq/fileResource/"+ fileName;
        File f = new File(resultPath);
        System.out.println(dataPath);
        if(!f.getAbsoluteFile().exists()) {
            f.getParentFile().mkdirs();
        }
        mfile.transferTo(f);
        System.out.println(f.getAbsolutePath());
        return (dataPath);
    }

    public static String saveBase64Img(String imgStr) throws IOException {// 对字节数组字符串进行Base64解码并生成图片
        imgStr = imgStr.replace("data:image/png;base64,","");
        BASE64Decoder decoder = new BASE64Decoder();
        String fileName = System.currentTimeMillis() + ".jpg";
        String resultPath = RES_PATH + fileName;
        String dataPath =  WEB_PATH + fileName;
        String path =resultPath;
        // Base64解码
        byte[] bytes = decoder.decodeBuffer(imgStr);
        for (int i = 0; i < bytes.length; ++i) {
            if (bytes[i] < 0) {// 调整异常数据
                bytes[i] += 256;
            }
        }
        // 生成jpeg图片
        OutputStream out = new FileOutputStream(path);
        out.write(bytes);
        out.flush();
        out.close();
        return dataPath;
    }

}
