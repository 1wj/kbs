package com.digiwin.app.kbs.service.util;

import com.digiwin.app.kbs.service.common.constant.ModuleConstant;
import com.digiwin.app.resource.DWModuleMessageResourceBundleUtils;

/**
 * @Author: zhupeng@digiwin.com
 * @Datetime: 2021/6/3 10:50
 * @Description: 多语系工具类
 * @Version: 0.0.0.1
 */
public class MultilingualismUtil {

    /**
     * 获取多语系 模组为KSC
     *
     * @param key
     * @return
     */
    public static String getLanguage(String key) {
        String message = DWModuleMessageResourceBundleUtils.getStringByModule(ModuleConstant.MODULE_NAME, key, new Object[]{""});
        return message;
    }

    /**
     * 获取多语系 模组为KSC
     *
     * @param key
     * @param patternArguments
     * @return
     */
    public static String getLanguage(String key, Object... patternArguments) {
        String message = DWModuleMessageResourceBundleUtils.getStringByModule(ModuleConstant.MODULE_NAME, key, patternArguments);
        return message;
    }

    /**
     * 获取模块下的多语系
     *
     * @param module
     * @param key
     * @return
     */
    public static String getModuleLanguage(String module, String key) {
        String message = DWModuleMessageResourceBundleUtils.getStringByModule(module, key, new Object[]{""});
        return message;
    }

    /**
     * 获取模块下的多语系
     *
     * @param module
     * @param key
     * @param patternArguments
     * @return
     */
    public static String getModuleLanguage(String module, String key, Object... patternArguments) {
        String message = DWModuleMessageResourceBundleUtils.getStringByModule(module, key, patternArguments);
        return message;
    }
}
