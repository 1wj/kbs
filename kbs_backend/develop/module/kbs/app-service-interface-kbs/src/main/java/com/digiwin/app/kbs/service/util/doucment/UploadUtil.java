package com.digiwin.app.kbs.service.util.doucment;

import com.digiwin.app.common.DWApplicationConfigUtils;

import java.io.*;

/**
 * 分段上船常量
 */
public class UploadUtil {

    public static final int DEFAULT_CHUNK_SIZE = 255 * 1024;
    public static final String DMC_URI = DWApplicationConfigUtils.getProperty("dmcUrl");
    public static final String BUCKET = DWApplicationConfigUtils.getProperty("dmcBucketName");

    public static File createSampleFile() throws IOException {
        File file = File.createTempFile("dmc-upload-", ".pptx");
        file.deleteOnExit();

        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
        for (int i = 0; i < 50_000; i++) {
            writer.write("01234567890123456789---abcdefghijklmnopqrstuvwxyz\n");
            writer.write("01234567890123456789---abcdefghijklmnopqrstuvwxyz\n");
        }
        writer.close();
        return file;
    }
}
