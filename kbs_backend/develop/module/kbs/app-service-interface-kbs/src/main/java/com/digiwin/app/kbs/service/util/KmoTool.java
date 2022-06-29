package com.digiwin.app.kbs.service.util;

import com.digiwin.app.common.DWApplicationConfigUtils;

/**
 * @Author: zhupeng@digiwin.com
 * @Datetime: 2022/3/10 9:56
 * @Description: 知识中台工具类
 * @Version: 0.0.0.1
 */
public class KmoTool {

    public static String getKmoUrl(String key) {
        String kmoUrl = DWApplicationConfigUtils.getProperty("kmoUrl");
        String path = DWApplicationConfigUtils.getProperty(key);
        return kmoUrl + path;
    }
}
