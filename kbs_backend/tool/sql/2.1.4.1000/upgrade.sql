#2020-04-24;

ALTER TABLE `dw_schedule` ADD COLUMN `module_name` VARCHAR(50) NULL DEFAULT NULL COMMENT '模組名稱' COLLATE 'utf8_bin', ADD COLUMN `secretKey` VARCHAR(200) NULL DEFAULT NULL COMMENT 'IAM登入密鑰' COLLATE 'utf8_bin';

#2020-04-27;

ALTER TABLE `dw_schedule_time`
	ADD COLUMN `exclude_date` VARCHAR(255) NULL DEFAULT NULL COMMENT '例外日期' COLLATE 'utf8_bin' AFTER `byweeklyday`,
	ADD COLUMN `exclude_time` VARCHAR(255) NULL DEFAULT NULL COMMENT '例外時間' COLLATE 'utf8_bin' AFTER `exclude_date`;

#2020-04-27;

ALTER TABLE `dw_schedule_time`
	ADD COLUMN `retry_after_misfire_sec` INT(10) NULL DEFAULT NULL COMMENT 'misfire幾秒後重試' AFTER `exclude_time`;

#2020-04-27;

ALTER TABLE `dw_schedule_record`
	ADD COLUMN `is_misfire` VARCHAR(1) NULL DEFAULT 'N' COMMENT '是否為misfire後重試' AFTER `schedule_type`;

#2020-04-27;

ALTER TABLE `dw_schedule_time`
	ADD COLUMN `is_retry` VARCHAR(1) NOT NULL DEFAULT 'Y' COMMENT '是否在misfire後重試' AFTER `retry_after_misfire_sec`;