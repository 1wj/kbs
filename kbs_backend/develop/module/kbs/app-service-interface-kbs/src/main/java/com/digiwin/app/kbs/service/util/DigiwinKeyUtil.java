package com.digiwin.app.kbs.service.util;
import cn.hutool.crypto.SecureUtil;

import java.util.UUID;

/**
 * @Description: digi-key生成
 * @Version: v1-知识库迭代一
 */
public class DigiwinKeyUtil {
    public static void main(String[] args) {
        String host = "{\"prod\":\"Athena\",\"ver\":\"1.0\",\"ip\":\"\",\"id\":\"BpmCloud\",\"timestamp\":\"20210506103327709\",\"acct\":\"athena\"}";
        String service = "{\"prod\":\"KSC-DDC\",\"tenant_id\":\"LI11\",\"name\":\"knowledge.cad.attribute.info.check\",\"uid\":\"KSC-DDC\"}";
        String tDigiKey = host + service;
        String key = SecureUtil.md5(tDigiKey);
        System.out.println("digi-host:" + host);
        System.out.println("digi-service:" + service);
        System.out.println("digi-key:" + key);
        System.out.println(UUID.randomUUID());
    }
}
