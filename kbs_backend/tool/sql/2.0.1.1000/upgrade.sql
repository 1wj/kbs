ALTER TABLE `dw_schedule`
ADD COLUMN `program_code`  varchar(50) NULL COMMENT '作業編號' AFTER `next_execute_time`;

ALTER TABLE `dw_schedule_record`
ADD COLUMN `program_code`  varchar(50) NULL COMMENT '作業編號' AFTER `schedule_type`;

ALTER TABLE `dw_schedule_record`
ADD COLUMN `schedule_name`  varchar(50) NULL COMMENT '排程名稱' AFTER `program_code`,
ADD COLUMN `executor`  varchar(50) NULL COMMENT '執行者' AFTER `schedule_name`;
 