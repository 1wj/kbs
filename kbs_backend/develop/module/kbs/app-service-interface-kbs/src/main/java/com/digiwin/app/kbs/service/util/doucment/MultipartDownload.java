package com.digiwin.app.kbs.service.util.doucment;

import com.alibaba.fastjson.JSONArray;
import com.digiwin.app.kbs.service.util.download.MutiThreadDownLoad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

/**
 * @ClassName MultipartDownload
 * @Description TODO
 * @Author HeX
 * @Date 2022/4/20 17:03
 * @Version 1.0
 **/
public class MultipartDownload {

    private static final Logger logger = LoggerFactory.getLogger(MultipartUpload.class);

    public static void main(String[] args) {
//        download("fc6a9c20-ed8b-42af-9b12-ccf62bae2575","D:\\teataa\\dependency.zip");
    }


    public static void download(String fileId,String path,int fileTotalSize,int batchDownloadSize){
        FileOutputStream fileOutputStream = null;
        try {
            String token = Login.getUserToken();
//            String token ="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkbWMiLCJkYXRhIjoie1wiaWRcIjpcIjYwNmJlYWRmMTEwNzVlMDAxMTJhNGE3MVwiLFwidXNlcm5hbWVcIjpcIktTQ1wiLFwiaWFtXCI6ZmFsc2UsXCJhZG1pblwiOmZhbHNlLFwiYnVja2V0c1wiOltdLFwiYXV0aG9yaXRpZXNcIjpbe1wicm9sZVwiOlwiUk9MRV9CdWNrZXRDcmVhdG9yXCJ9XSxcImVuYWJsZWRcIjp0cnVlLFwiYWNjb3VudE5vbkV4cGlyZWRcIjp0cnVlLFwiY3JlZGVudGlhbHNOb25FeHBpcmVkXCI6dHJ1ZSxcImFjY291bnROb25Mb2NrZWRcIjp0cnVlfSIsImlzcyI6ImRpZ2l3aW4uZGFwLm1pZGRsZXdhcmUuZG1jLlVzZXJUb2tlbiIsImV4cCI6MTY1MDg1NzQ3Nn0.VDYBUxgwlfdJXgiRKqiGxpOBsM9CDu0g3Ao-PmIqJRI";
            fileOutputStream = new FileOutputStream(path);

//            int length = fileTotalSize*1024*1024;
            int length = fileTotalSize*1024;
            int bufferSize = batchDownloadSize*255*1024;

//            BigDecimal aBig = new BigDecimal(length);
//            BigDecimal bBig = new BigDecimal(bufferSize);

//            int totalSize =  (int)Math.ceil(aBig.divide(bBig).doubleValue());
            int totalSize = (int)(length / bufferSize);
//            int bufferSize = UploadUtil.DEFAULT_CHUNK_SIZE;
//            int bufferSize = 500*1024*1024;

            long downLoadedLen = 0;
            long from = 0;
            int i = 0;
            while (downLoadedLen < length) {
                byte[] partStream;
//                if (downLoadedLen + bufferSize > length) {
//                    bufferSize = (int) (length - downLoadedLen);
//                }
//                if (i!=0) {
//                    bufferSize = bufferSize+bufferSize;
//                }
                if (downLoadedLen > 0) {
                    from = downLoadedLen;
                }

                RestTemplate restTemplate = new RestTemplate();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.add(GlobalConstants.HTTP_HEADER_USER_TOKEN_KEY, token);
                HttpEntity request = new HttpEntity<>(headers);

//                String url = String.format("%s/api/dmc/v1/buckets/%s/files/%s/%s/%s", UploadUtil.DMC_URI, UploadUtil.BUCKET, fileId, from, bufferSize);
                String url = String.format("%s/api/dmc/v1/buckets/%s/files/%s/%s/%s",  "https://dmc-test.digiwincloud.com.cn", "KSC", fileId, from, bufferSize);
                ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, request, byte[].class);

                partStream = response.getBody();
                logger.info("第{}段下载下来的大小是{}", i+1,partStream.length);
                fileOutputStream.write(partStream);
                logger.info("总共：{}段, 第{}段下载成功：fileId={},from={},bufferSize={}.", totalSize, i+1, fileId, from, bufferSize);
                downLoadedLen += partStream.length;
                bufferSize = (int) (downLoadedLen+(batchDownloadSize*1024*1024));
                i = i+1;

            }

        } catch (Exception e) {
            logger.error("分段下载失败：{}", e.getMessage(), e);
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }

            } catch (Exception e) {
            }
        }

    }

    public static void downloadTest(String field,String path){

        int threadSize = 4;

        String fileUrl = String.format("%s/api/dmc/v2/file/%s/preview/%s","https://dmc-test.digiwincloud.com.cn", "KSC", field);

        String serverPath = fileUrl;
        String localPath = path;
        CountDownLatch latch = new CountDownLatch(threadSize);
        MutiThreadDownLoad m = new MutiThreadDownLoad(threadSize, serverPath, localPath, latch);
        long startTime = System.currentTimeMillis();
        try {
            m.executeDownLoad();
            latch.await();//等待所有的线程执行完毕
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("全部下载结束,共耗时" + (endTime - startTime) / 1000 + "s");



    }



    public static void downloadBig(String field,String path) {
        try {
//            String token = Login.getUserToken();
            String token ="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkbWMiLCJkYXRhIjoie1wiaWRcIjpcIjYwNmJlYWRmMTEwNzVlMDAxMTJhNGE3MVwiLFwidXNlcm5hbWVcIjpcIktTQ1wiLFwiaWFtXCI6ZmFsc2UsXCJhZG1pblwiOmZhbHNlLFwiYnVja2V0c1wiOltdLFwiYXV0aG9yaXRpZXNcIjpbe1wicm9sZVwiOlwiUk9MRV9CdWNrZXRDcmVhdG9yXCJ9XSxcImVuYWJsZWRcIjp0cnVlLFwiYWNjb3VudE5vbkV4cGlyZWRcIjp0cnVlLFwiY3JlZGVudGlhbHNOb25FeHBpcmVkXCI6dHJ1ZSxcImFjY291bnROb25Mb2NrZWRcIjp0cnVlfSIsImlzcyI6ImRpZ2l3aW4uZGFwLm1pZGRsZXdhcmUuZG1jLlVzZXJUb2tlbiIsImV4cCI6MTY1MDg1NzQ3Nn0.VDYBUxgwlfdJXgiRKqiGxpOBsM9CDu0g3Ao-PmIqJRI";
            RequestCallback requestCallback = request -> {
                final HttpHeaders headers = request.getHeaders();
                headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM, MediaType.ALL));
                headers.add(GlobalConstants.HTTP_HEADER_USER_TOKEN_KEY, token);
            };

//            String url = String.format("%s/api/dmc/v2/file/%s/download/%s", UploadUtil.DMC_URI, UploadUtil.BUCKET, field);
            String url = String.format("%s/api/dmc/v2/file/%s/download/%s", "https://dmc-test.digiwincloud.com.cn", "KSC", field);



//            HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
//            httpRequestFactory.setConnectionRequestTimeout(30 * 1000);
//            httpRequestFactory.setConnectTimeout(2 * 60 * 1000);
//            httpRequestFactory.setReadTimeout(600000);
            RestTemplate restTemplate = new RestTemplate();

            //对响应进行流式处理而不是将其全部加载到内存中
            restTemplate.execute(url, HttpMethod.GET, requestCallback, clientHttpResponse -> {
                Files.copy(clientHttpResponse.getBody(), Paths.get(path));
//                getFile(clientHttpResponse.getBody(),path);
//                FileUtil.writeFromStream(clientHttpResponse.getBody(),path);
                return null;
            });
        } catch (Exception e) {
            logger.error("下载大文件失败：{}", e.getMessage(), e);
        }
    }

    public static void getFile(InputStream is, String fileName) throws IOException {
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


}
