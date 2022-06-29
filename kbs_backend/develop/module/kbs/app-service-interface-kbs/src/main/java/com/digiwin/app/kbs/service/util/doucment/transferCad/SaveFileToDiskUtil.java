package com.digiwin.app.kbs.service.util.doucment.transferCad;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @ClassName SaveFileToDiskUtil
 * @Description TODO
 * @Author HeX
 * @Date 2021/5/14 13:54
 * @Version 1.0
 **/
public class SaveFileToDiskUtil {
    public  static File getFileFromBytes(byte[] b, String outputFile){
        BufferedOutputStream stream = null;
        File file = null;
        try {
            file = new File(outputFile);
            File fileParent = file.getParentFile();
            if(!fileParent.exists()){
                fileParent.mkdirs();
            }
            FileOutputStream fstream = new FileOutputStream(file);
            stream = new BufferedOutputStream(fstream);
            stream.write(b);
        } catch (Exception e){
            e.printStackTrace();
        } finally{
            if (stream != null){
                try{
                    stream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return file;
    }
}
