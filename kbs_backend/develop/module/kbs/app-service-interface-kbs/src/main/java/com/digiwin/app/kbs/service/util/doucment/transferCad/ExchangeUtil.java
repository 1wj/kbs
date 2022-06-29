package com.digiwin.app.kbs.service.util.doucment.transferCad;

import com.digiwin.app.common.DWApplicationConfigUtils;

import java.io.*;

/**
 * @ClassName ExchangeTestUtil
 * @Description TODO
 * @Author HeX
 * @Date 2021/5/14 16:17
 * @Version 1.0
 **/
public class ExchangeUtil {
    /**
     * @param path  待转换文件路径
     * @param outPath 输入路径
     * @param flag 标识，0全部转换，1转轻量化，2转缩略图
     * @return
     * @throws IOException
     */
    public static int exchange(String path, String outPath, String flag) throws IOException {
        int ret = -1;
        String dirName = outPath;  // 创建文件夹
        File file = new File(dirName);
        // 两个方法的区别就是mkdir如果父目录不存在，则无法成功创建指定的目录。而mkdirs如果父目录不存在，则会同时把父目录也创建出来。
        if (!file.exists()) {
            file.mkdirs();
        }
        ProcessBuilder pb = new ProcessBuilder(DWApplicationConfigUtils.getProperty("intevuePath"), path, dirName, flag);
        pb.redirectErrorStream(true);
        Process process = pb.start();
        try {
            //可能导致进程阻塞，甚至死锁
            //获取进程的标准输入流
            final InputStream is1 = process.getInputStream();
            //获取进城的错误流
            final InputStream is2 = process.getErrorStream();
            //启动两个线程，一个线程负责读标准输出流，另一个负责读标准错误流
            new Thread() {
                public void run() {
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(is1));
                    try {
                        String line1 = null;
                        while ((line1 = br1.readLine()) != null) {
                            if (line1 != null) {
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            is1.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();

            new Thread() {
                public void run() {
                    BufferedReader br2 = new BufferedReader(new InputStreamReader(is2));
                    try {
                        String line2 = null;
                        while ((line2 = br2.readLine()) != null) {
                            if (line2 != null) {
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            is2.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();

            ret = process.waitFor();
//            System.out.println("return value:" + ret);
//            System.out.println(process.exitValue());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            process.getErrorStream().close();
//            process.getInputStream().close();
            process.getOutputStream().close();
            return ret;
        }

    }
}
