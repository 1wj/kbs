package com.digiwin.app.kbs.service.util.doucment.serializer;

import java.time.format.DateTimeFormatter;

/**
 * 日期格式话
 *
 * @Description:
 */
public interface Constants {

    DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
}
