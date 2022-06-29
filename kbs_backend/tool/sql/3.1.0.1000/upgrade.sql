#2020-08-06;

ALTER TABLE `dw_schedule_time` CHANGE COLUMN `repeat_count` `repeat_count` INT NULL DEFAULT NULL COMMENT '執行次數' AFTER `end_time`;

#2020-11-27;

ALTER TABLE `dw_rdbms_tables`
	ADD COLUMN `delete_constraint` VARCHAR(1) NOT NULL DEFAULT 'N' COMMENT '資料表刪除約束' COLLATE 'utf8_bin' AFTER `table_description`,
	ADD COLUMN `hint_field` VARCHAR(50) NULL DEFAULT NULL COMMENT '資料表刪除提示字段' COLLATE 'utf8_bin' AFTER `delete_constraint`;