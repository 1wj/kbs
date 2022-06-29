package com.digiwin.app.kbs.service.util;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName Test
 * @Description TODO
 * @Author HeX
 * @Date 2022/5/6 14:01
 * @Version 1.0
 **/
public class Test {
    public static void main(String args[]) {
        SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm");
        System.out.println(sdf.format(new Date()));

        System.out.println(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm"));
    }
}
