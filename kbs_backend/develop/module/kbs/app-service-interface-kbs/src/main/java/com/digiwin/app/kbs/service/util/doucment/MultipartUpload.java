package com.digiwin.app.kbs.service.util.doucment;

import com.digiwin.dap.middleware.dmc.model.FileInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import sun.nio.ch.FileChannelImpl;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Map;

/**
 * 分段上传V1
 */
public class MultipartUpload {

    private static final Logger logger = LoggerFactory.getLogger(MultipartUpload.class);

    public static String upload(File sampleFile) throws IOException {
        // Claim a upload id firstly
        String uploadId = claimUploadId(sampleFile);
//        logger.info("Claiming a new upload id {}.", uploadId);
        // Calculate how many parts to be divided
        final int partSize = 2 * UploadUtil.DEFAULT_CHUNK_SIZE;
        long fileLength = sampleFile.length();
        int partCount = (int) (fileLength / partSize);
        if (fileLength % partSize != 0) {
            partCount++;
        }
        if (partCount > 10000) {
            throw new RuntimeException("Total parts count should not exceed 10000.");
        } else {
            logger.error("Total parts count {}.", partCount);
        }
        // Upload multipart to your bucket
//        logger.info("Begin to upload multipart to DMC from a file");

        partUpload(sampleFile, uploadId, partSize, partCount, fileLength);

        logger.info("file upload done... \nfileId = {}.", uploadId);
        return uploadId;
    }

    private static void partUpload(File localFile, String fileId, int partSize, int partCount, long fileLength) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String userToken = Login.getUserToken();
        RandomAccessFile aFile = null;
        FileChannel inChannel = null;
//        FileChannel channel = new RandomAccessFile(localFile, "r").getChannel();
        try {
            aFile = new RandomAccessFile(localFile, "r");
            inChannel = aFile.getChannel();
            MappedByteBuffer map = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileLength);
            for (int i = 0; i < partCount; i++) {
                int startPos = i * partSize;
                int curPartSize = (i + 1 == partCount) ? (int) (fileLength - startPos) : partSize;

                byte[] buffer = new byte[curPartSize];
                map.get(buffer, 0, curPartSize);

                Resource resource = new ByteArrayResource(buffer);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.add(GlobalConstants.HTTP_HEADER_USER_TOKEN_KEY, userToken);
                HttpEntity request = new HttpEntity<>(resource, headers);
                String url = String.format("%s/api/dmc/v1/buckets/%s/files/%s/%s/%s/%s", UploadUtil.DMC_URI, UploadUtil.BUCKET, fileId, startPos, startPos + curPartSize - 1, fileLength);
                restTemplate.postForObject(url, request, Map.class);

                logger.info("总共：{}段, 第{}段上传成功：fileId={},startPos={},curPartSize={}.", partCount, (i + 1), fileId, startPos, curPartSize);
            }
            // 加上这几行代码,手动unmap
            Method m = FileChannelImpl.class.getDeclaredMethod("unmap", MappedByteBuffer.class);
            m.setAccessible(true);
            m.invoke(FileChannelImpl.class, map);
        } catch (Exception e) {
            logger.error("分段上传失败：fileId={},partSize={},partCount={},curPartSize={}.", fileId, partSize, partCount, fileLength, e);
        } finally {
            try {
                inChannel.close();
                aFile.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static String claimUploadId(File file) throws UnsupportedEncodingException {
        RestTemplate restTemplate = new RestTemplate();
        FileInfo fileInfo = new FileInfo();
        // todo 注意这里 文件名里面是包含了类型的
//        byte[] resu = cadFileInfoModel.getFileName().getBytes();
//        String value = new String(resu, "UTF-8");
        fileInfo.setFileName(file.getName());
//        fileInfo.setDirectoryId("6184d885-8bdd-4113-8e79-399d77b1a571");
        fileInfo.setDisplayName(file.getName() + "-分段上传");
        fileInfo.setDescription(file.getName() + "采用分段上传");
        fileInfo.setTag("分段上传;断点续传");
//        fileInfo.setExpireDate(LocalDateTime.now().plusDays(1));
        try {
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            headers.add(GlobalConstants.HTTP_HEADER_USER_TOKEN_KEY, Login.getUserToken());
            HttpEntity request = new HttpEntity<>(JsonUtils.createObjectMapper().writeValueAsString(fileInfo), headers);
            String url = String.format("%s/api/dmc/v1/buckets/%s/files/segment", UploadUtil.DMC_URI, UploadUtil.BUCKET);
            Map result = restTemplate.postForObject(url, request, Map.class);
            return result.get("id").toString();
        } catch (Exception e) {
            throw new RuntimeException("Claiming a new upload id fail.", e);
        }
    }
}
