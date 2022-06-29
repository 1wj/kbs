#2020-08-26;

ALTER TABLE `dw_schedule_record` CHANGE COLUMN `schedule_param` `schedule_param` TEXT NULL DEFAULT NULL COMMENT '執行參數' COLLATE 'utf8_bin' AFTER `end_time`;

#2020-09-25;

ALTER TABLE `dw_schedule` DROP COLUMN `secretKey`;

#2020-09-25;

DELETE FROM dw_rdbms_fields where TABLE_NAME = 'dw_schedule' and FIELD_NAME = 'secretKey';