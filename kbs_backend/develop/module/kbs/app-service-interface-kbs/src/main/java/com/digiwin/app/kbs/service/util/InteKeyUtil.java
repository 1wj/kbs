package com.digiwin.app.kbs.service.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ZipUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: zhupeng@digiwin.com
 * @Datetime: 2022/3/31 13:32
 * @Description: 天谕加解密工具类
 * @Version: 0.0.0.1
 */
public class InteKeyUtil {
    private static Logger logger = LoggerFactory.getLogger(InteKeyUtil.class);
    private static String FilePathHome = "/kbs_backend/application";

    //上传天谕key
    public static List<String> uploadKeyZip(byte[] data, String pathHome, String fileName) {
        if (!StringUtils.isEmpty(pathHome)) {
            FilePathHome = pathHome;
        }
        logger.info("上传文件");
        String filePath = FilePathHome + File.separator + fileName;
        File file = FileUtil.writeBytes(data, filePath);
        logger.info("file:{}", file.getAbsolutePath());
        File unzip = ZipUtil.unzip(filePath);
        logger.info("unzip:{}", unzip.getAbsolutePath());
        //将文件转移到最外层
        File zip_to = new File(FilePathHome + File.separator + FileUtil.mainName(fileName));
        File zip_from = new File(FilePathHome + File.separator + FileUtil.mainName(fileName) + File.separator + FileUtil.mainName(fileName));
        FileUtil.copyContent(zip_from, zip_to, true);
        logger.info("zip_to:{}", zip_to.getAbsolutePath());
        List<String> ls = ls(zip_to.getAbsolutePath());
        logger.info("文件有:{}", ls);
        //删除压缩包和解压缩的文件
        FileUtil.del(filePath);
        FileUtil.del(zip_from);
        //判断是否有文件夹decrypt\download
        FileUtil.mkdir(zip_to.getAbsolutePath() + File.separator + "decrypt");
        FileUtil.mkdir(zip_to.getAbsolutePath() + File.separator + "download");
        return ls;
    }

    //上传
    public static String upload(byte[] data, String pathHome, String fileName) {
        if (!StringUtils.isEmpty(pathHome)) {
            FilePathHome = pathHome;
        }
        logger.info("上传文件");
        String filePath = FilePathHome + File.separator + "download" + File.separator + fileName;
        File file = FileUtil.writeBytes(data, filePath);
        return file.getAbsolutePath();
    }

    /**
     * 获取文件列表
     *
     * @param path
     * @return
     */
    public static List<String> ls(String path) {
        File[] files = FileUtil.ls(path);
        return Stream.of(files).map(file -> file.getName()).collect(Collectors.toList());
    }
}
