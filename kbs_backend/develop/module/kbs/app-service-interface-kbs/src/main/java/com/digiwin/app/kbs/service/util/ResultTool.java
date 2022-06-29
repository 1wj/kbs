package com.digiwin.app.kbs.service.util;

import com.digiwin.app.service.DWServiceResult;

/**
 * @ClassName ResultTool
 * @Description 统一返参工具类
 * @Author HeX
 * @Date 2022/2/23 10:20
 * @Version 1.0
 **/
public class ResultTool {

    /**
     * 返回成功
     * @return
     */
    public static DWServiceResult success(){
        return new DWServiceResult();
    }

    /**
     * 返回成功
     * @param data
     * @return
     */
    public static DWServiceResult success(Object data) {
        return new DWServiceResult(true,"action success",data);
    }

    /**
     * 返回失败
     * @param message
     * @return
     */
    public static DWServiceResult fail(String message){
        return new DWServiceResult(false,message,null);
    }




}
