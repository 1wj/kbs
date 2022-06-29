package com.digiwin.app.kbs.service.util;

import com.alibaba.fastjson.JSON;
import com.digiwin.app.common.DWApplicationConfigUtils;
import com.digiwin.app.container.exceptions.DWException;
import com.digiwin.app.kbs.service.configuration.restTemplate.MyRestErrorHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @ClassName RequestClient
 * @Description (地 - 云)呼叫Client
 * @Author HeX
 * @Date 2020/3/17 11:36
 * @Version 1.0
 **/
public class RequestTool {
    /**
     * 通用请求调用
     *
     * @param requestJson
     * @return
     * @throws DWException
     */
    public static <T> T request(String url,String token,String requestJson,Class<T> clazz) throws Exception {
        // 请求头
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_UTF8.toString());
        headers.add("token", token);
        headers.add("digi-middleware-auth-app", DWApplicationConfigUtils.getProperty("iamApToken"));

        // 解决响应压缩问题
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
                HttpClientBuilder.create().build());
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
        restTemplate.setErrorHandler(new MyRestErrorHandler());
        // 封装请求头
        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        // 请求rest,注意 .net接口需要用Map接收
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(url, entity, Map.class);
        if (responseEntity.getStatusCode() == HttpStatus.NOT_FOUND || responseEntity.getStatusCode() == HttpStatus.UNAUTHORIZED || responseEntity.getStatusCode() == HttpStatus.FORBIDDEN) {
            throw new DWException(String.format("statusCode: %s", responseEntity.getStatusCode()));
        }
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new DWException(String.format("statusCode: %s", responseEntity.getStatusCode()));
        }
        Map<String, Object> body = responseEntity.getBody();
        Map<String, Object> response = (Map<String, Object>) body.get("response");
        boolean success = (boolean) response.get("success");
        if (!success) {
            throw new DWException((String) response.get("message"));
        }
        T data = JSON.parseObject(JSON.toJSONString(response.get("data")), clazz);
        return data;
    }


}
