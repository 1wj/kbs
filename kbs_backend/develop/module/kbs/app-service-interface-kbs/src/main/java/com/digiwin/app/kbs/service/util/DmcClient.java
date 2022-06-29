package com.digiwin.app.kbs.service.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSONObject;
import com.digiwin.app.common.DWApplicationConfigUtils;
import com.digiwin.app.kbs.service.util.doucment.GlobalConstants;
import com.digiwin.app.kbs.service.util.doucment.Login;
import com.digiwin.app.kbs.service.util.doucment.UploadUtil;
import com.digiwin.app.service.DWFile;
import com.digiwin.dmc.sdk.config.DmcUrl;
import com.digiwin.dmc.sdk.config.SDKConstants;
import com.digiwin.dmc.sdk.config.ServerSetting;
import com.digiwin.dmc.sdk.entity.FileInfo;
import com.digiwin.dmc.sdk.service.IDocumentStorageService;
import com.digiwin.dmc.sdk.service.IUserManagerService;
import com.digiwin.dmc.sdk.service.download.FileService;
import com.digiwin.dmc.sdk.service.download.IFileService;
import com.digiwin.dmc.sdk.service.impl.DocumentStorageService;
import com.digiwin.dmc.sdk.service.impl.UserManagerService;
import com.digiwin.dmc.sdk.service.upload.IGeneralDocumentUploader;
import com.digiwin.dmc.sdk.service.upload.UploadProgressEventArgs;
import com.digiwin.dmc.sdk.util.HttpRequestUtil;
import com.google.gson.internal.LinkedTreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @Description: dmc相关操作
 * @Author: zhupeng@digiwin.com
 * @Datetime: 2021/11/5 15:24
 * @Version: 0.0.0.1
 */
public class DmcClient {
    private static Logger logger = LoggerFactory.getLogger(DmcClient.class);
    private static final String EMPTY_OBJECT_ID_STR = "00000000-0000-0000-0000-000000000000";
    private static IUserManagerService userManagerService = UserManagerService.userInstance();

    static {
        // 启动dmc文档中心
        ServerSetting.setServiceUrl(DWApplicationConfigUtils.getProperty("dmcUrl"));
        ServerSetting.setIdentityName(DWApplicationConfigUtils.getProperty("dmcUserName"));
        ServerSetting.setIdentityPwd(DWApplicationConfigUtils.getProperty("dmcPwd"));
        ServerSetting.setBucketName(DWApplicationConfigUtils.getProperty("dmcBucketName"));
    }



    /**
     * 下载附件
     *
     * @param dmcId
     * @return 文件
     */
    public static byte[] downloadFile(String dmcId) {
        IFileService iFileService = FileService.fileInstance();
        byte[] bytes = iFileService.download(dmcId);
        return bytes;
    }

    /**
     * 下载附件
     *
     * @param bucketName
     * @param dmcId
     * @return 文件
     */
    public static byte[] downloadFile(String bucketName, String dmcId) {
        IFileService iFileService = FileService.fileInstance();
        byte[] bytes = iFileService.download(bucketName, dmcId);
        return bytes;
    }

    /**
     * 下载附件
     *
     * @param tenantId
     * @return 文件
     */
    public static byte[] downloadFile(String tenantId, String bucketName, String dmcId) {
        IFileService iFileService = FileService.fileInstance();
        byte[] bytes = iFileService.download(tenantId, bucketName, dmcId);
        return bytes;
    }

    /**
     * 下载附件
     *
     * @param tenantId
     * @return 文件
     */
    public static byte[] downloadFile(String tenantId, String bucketName, String fileId, String driveToken) {
        IFileService iFileService = FileService.fileInstance();
        byte[] bytes = iFileService.download(tenantId, bucketName, fileId, driveToken);
        return bytes;
    }

    /**
     * 删除dmc文件
     * /api/dmc/v2/file/{bucket}/delete/{fileId}
     *
     * @param dmcId 文档中心回传的附件id
     */
    public static String delete(String dmcId) {
        Map<String, String> headers = new HashMap();
        String userToken = userManagerService.getUserToken(ServerSetting.getUser());
        headers.put(SDKConstants.HttpHeaderUserTokenKey, userToken);
        String deleteUrl = String.format("%s/api/dmc/v2/file/%s/delete/%s", DmcUrl.getServiceUrl(), ServerSetting.getBucketName(), dmcId);
        Map<String, Object> deleteEntity = HttpRequestUtil.delete(deleteUrl, headers, Map.class);
        if ((Boolean) deleteEntity.get("success") && deleteEntity.containsKey("data")) {
            Map<String, Object> data = (Map<String, Object>) deleteEntity.get("data");
            return String.valueOf(data.get("id"));
        }
        return null;
    }

    /**
     * 判断回收站是否有这个文件
     * /api/dmc/v2/file/{bucket}/recycle/restore/{recycleId}
     *
     * @param recycleId
     * @return
     * @throws Exception
     */
    public static Boolean getByRecycleId(String recycleId) throws Exception {
        Map<String, String> headers = new HashMap();
        String userToken = userManagerService.getUserToken(ServerSetting.getUser());
        headers.put(SDKConstants.HttpHeaderUserTokenKey, userToken);
        String queryUrl = String.format("%s/api/dmc/v2/recycle/%s/search", DmcUrl.getServiceUrl(), ServerSetting.getBucketName());
        JSONObject filters = new JSONObject();
        JSONObject parameter = new JSONObject();
        filters.put("id", recycleId);
        parameter.put("filters", filters);
        parameter.put("pageNum", 1);
        parameter.put("pageSize", 1);
        parameter.put("orders", null);
        Map<String, Object> queryEntity = HttpRequestUtil.postJson(queryUrl, parameter.toJSONString(), headers, Map.class);
        Boolean exist = false;
        //判斷是否存在
        if ((Boolean) queryEntity.get("success") && queryEntity.containsKey("data")) {
            Map<String, Object> data = (Map<String, Object>) queryEntity.get("data");
            exist = String.valueOf(data.get("total")).equals("0") ? false : true;
        }
        return exist;
    }

    /**
     * 从回收站恢复这个文件
     * /api/dmc/v2/recycle/{bucket}/search
     *
     * @param recycleId
     * @return
     * @throws Exception
     */
    public static Boolean restore(String recycleId) throws Exception {
        Map<String, String> headers = new HashMap();
        String userToken = userManagerService.getUserToken(ServerSetting.getUser());
        headers.put(SDKConstants.HttpHeaderUserTokenKey, userToken);
        String recycleUrl = String.format("%s/api/dmc/v2/file/%s/recycle/restore/%s", DmcUrl.getServiceUrl(), ServerSetting.getBucketName(), recycleId);
        Map<String, Object> restoreEntity = HttpRequestUtil.postJson(recycleUrl, "", headers, Map.class);
        return (Boolean) restoreEntity.get("success");
    }

    /**
     * 判断文件是否存在
     *
     * @param dmcId
     * @return 文件空，返回true 否则false
     */
    public static boolean isEmpty(String dmcId) {
        IDocumentStorageService documentStorageService = DocumentStorageService.instance();
        FileInfo fileInfo = documentStorageService.getDocumentInfo(dmcId);
        if (StringUtils.isEmpty(fileInfo)) {
            return true;
        }
        return false;
    }

    /**
     * 下载文件（单个文件下载）
     *
     * @throws Exception
     */
    public static JSONObject download(String id,String path) throws Exception {
        String dmcId = id;
        logger.info("dmc_url:{}", DmcUrl.getServiceUrl());
        logger.info("dmc_bucket:{}", ServerSetting.getBucketName());
        String fileUrl = String.format("%s/api/dmc/v2/file/%s/preview/%s", DmcUrl.getServiceUrl(), ServerSetting.getBucketName(), dmcId);
        URL url = new URL(fileUrl);
        URLConnection conn = url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(3 * 1000);
        //得到输入流
        InputStream inputStream = conn.getInputStream();
        getFile(inputStream,path);
        File downloadFile = new File(path);
        Long size = downloadFile.length()/1024;
        logger.info(path+"文件大小为"+downloadFile.length()/1024+"KB");
        return new JSONObject().fluentPut("size:",size).fluentPut("path",path);
    }

    public static void download2(String id,String path) throws Exception {
        logger.info("dmc_url:{}", DmcUrl.getServiceUrl());
        logger.info("dmc_bucket:{}", ServerSetting.getBucketName());
        byte [] fileBytes = downloadFileForByte("KSC",id);
        File file = FileUtil.writeBytes(fileBytes, path);
        logger.info("file:{}", file.getAbsolutePath());
    }

    /**
     * 下载附件
     *
     * @param bucketName
     * @param dmcId
     * @return 文件
     */
    public static byte[] downloadFileForByte(String bucketName, String dmcId) {
        IFileService iFileService = FileService.fileInstance();
        byte[] bytes = iFileService.download(bucketName, dmcId);
        return bytes;
    }

    public static void getFile(InputStream is,String fileName) throws IOException{
        BufferedInputStream in=null;
        BufferedOutputStream out=null;
        in=new BufferedInputStream(is);
        out=new BufferedOutputStream(new FileOutputStream(fileName));
        int len=-1;
        byte[] b=new byte[1024];
        while((len=in.read(b))!=-1){
            out.write(b,0,len);
        }
        in.close();
        out.close();
    }




    /**
     * 启动dmc参数设置
     */
    public static void startDmc() {
        ServerSetting.setServiceUrl(DWApplicationConfigUtils.getProperty("dmcUrl"));
        ServerSetting.setIdentityName(DWApplicationConfigUtils.getProperty("dmcUserName"));
        ServerSetting.setIdentityPwd(DWApplicationConfigUtils.getProperty("dmcPwd"));
        ServerSetting.setBucketName(DWApplicationConfigUtils.getProperty("dmcBucketName"));
    }


    /**
     * inputStream 输入流转换成byte[]字节数组
     *
     * @param inputStream 输入流
     * @return byte[]字节数组
     * @throws IOException 异常处理
     */
    public static byte[] toByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while (-1 != (n = inputStream.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }

    /**
     * 判断文件类型contentType
     * 使用nio 与javax.activation 结合的方法
     *
     * @param fileName 文件名（含后缀）
     * @return String
     */
    public static String getContentType(String fileName) {
        String contentType = null;
        try {
            Path path = Paths.get(fileName);
            contentType = Files.probeContentType(path);
            logger.info("NIO获取的信息是：{}", contentType);
        } catch (Exception e) {
            logger.info("NIO获取文件头类型contentType失败，原因为：{}。下面用手动方式获取", e.getMessage());
            contentType = getcontentType(FileUtil.extName(fileName));
            logger.info("手动获取的信息是：{}", contentType);
        }
        if (StringUtils.isEmpty(contentType)) {
            logger.info("contentType为空，下面用手动获取");
            contentType = getcontentType(FileUtil.extName(fileName));
            logger.info("activation获取的信息是：{}", contentType);
        }
        return contentType;
    }

    /**
     * 判断文件类型contentType
     *
     * @param filenameExtension 文件后缀
     * @return String
     */
    public static String getcontentType(String filenameExtension) {
        if (filenameExtension.equalsIgnoreCase("bmp")) {
            return "image/bmp";
        }
        if (filenameExtension.equalsIgnoreCase("gif")) {
            return "image/gif";
        }
        if (filenameExtension.equalsIgnoreCase("jpeg") ||
                filenameExtension.equalsIgnoreCase("jpg") ||
                filenameExtension.equalsIgnoreCase("png")) {
            return "image/jpg";
        }
        if (filenameExtension.equalsIgnoreCase("html") ||
                filenameExtension.equalsIgnoreCase("jsp")) {
            return "text/html";
        }
        if (filenameExtension.equalsIgnoreCase("txt")) {
            return "text/plain";
        }
        if (filenameExtension.equalsIgnoreCase("xls") ||
                filenameExtension.equalsIgnoreCase("xlsx")) {
            return "application/vnd.ms-excel";
        }
        if (filenameExtension.equalsIgnoreCase("pdf")) {
            return "application/pdf";
        }
        if (filenameExtension.equalsIgnoreCase("vsd")) {
            return "application/vnd.visio";
        }
        if (filenameExtension.equalsIgnoreCase("pptx") ||
                filenameExtension.equalsIgnoreCase("ppt")) {
            return "application/x-ppt";
        }
        if (filenameExtension.equalsIgnoreCase("docx") ||
                filenameExtension.equalsIgnoreCase("doc")) {
            return "application/msword";
        }
        if (filenameExtension.equalsIgnoreCase("xml")) {
            return "text/xml";
        }
        if (filenameExtension.equalsIgnoreCase("css")) {
            return "application/x-csi";
        }
        if (filenameExtension.equalsIgnoreCase("class")) {
            return "application/x-cit";
        }
        if (filenameExtension.equalsIgnoreCase("mp4")) {
            return "video/mpeg4";
        }
        if (filenameExtension.equalsIgnoreCase("svg")) {
            return "image/svg+xml;charset=UTF-8";
        }
        return "application/octet-stream";
    }





}
