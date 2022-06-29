package com.digiwin.app.kbs.service.util.doucment.transferCad;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @ClassName FileUtil
 * @Description TODO
 * @Author HeX
 * @Date 2021/5/17 10:27
 * @Version 1.0
 **/
public class CadFileUtil {
    /**
     * 读取java文件至byte[]
     * @param filePath 文件路径
     * @return
     * @throws IOException
     */
    public static byte[] getContent(String filePath) throws IOException {
        File file = new File(filePath);
        long fileSize = file.length();
        if (fileSize > Integer.MAX_VALUE) {
            System.out.println("file too big...");
            return null;
        }
        FileInputStream fi = new FileInputStream(file);
        byte[] buffer = new byte[(int) fileSize];
        int offset = 0;
        int numRead = 0;
        while (offset < buffer.length
                && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
            offset += numRead;
        }
        // 确保所有数据均被读取
        if (offset != buffer.length) {
            throw new IOException("Could not completely read file "
                    + file.getName());
        }
        fi.close();
        return buffer;
    }
}
