SET foreign_key_checks = 0;

#2020-06-22;

DROP TABLE if EXISTS dw_screen, dw_screen_extra, dw_screen_preset, dw_screen_unit;

#2020-06-22;

DELETE FROM dw_rdbms_fields WHERE TABLE_NAME IN ('dw_screen', 'dw_screen_extra', 'dw_screen_preset', 'dw_screen_unit');

#2020-06-22;

DELETE FROM dw_rdbms_relations WHERE PRIMARY_TABLE_NO = 'dw_screen';

#2020-06-22;

DELETE FROM dw_rdbms_tables WHERE table_name IN ('dw_screen', 'dw_screen_extra', 'dw_screen_preset', 'dw_screen_unit');

#2020-07-13;

ALTER TABLE `dw_rdbms_relations`
	ADD COLUMN `TYPE` ENUM('Auto', 'OneToOne', 'OneToMany', 'ManyToMany') NULL DEFAULT NULL COMMENT '關聯類型' AFTER `REFERENCE_TABLE_PK`;

#2020-07-31;

ALTER TABLE `qrtz_calendars` COMMENT '日曆資訊';

#2020-07-31;

ALTER TABLE `qrtz_calendars` MODIFY COLUMN `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL COMMENT '计划名称';

#2020-07-31;

ALTER TABLE `qrtz_calendars` MODIFY COLUMN  `CALENDAR_NAME` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '触发器名称';

#2020-07-31;

ALTER TABLE `qrtz_calendars` MODIFY COLUMN  `CALENDAR` blob NOT NULL COMMENT '日曆信息';

#2020-07-31;

ALTER TABLE `qrtz_job_details` COMMENT 'job詳息信息';

#2020-07-31;

ALTER TABLE `qrtz_job_details` MODIFY COLUMN `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL COMMENT '计划名称';

#2020-07-31;

ALTER TABLE `qrtz_job_details` MODIFY COLUMN `JOB_NAME` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '作业名称';

#2020-07-31;

ALTER TABLE `qrtz_job_details` MODIFY COLUMN `JOB_GROUP` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '作业组';

#2020-07-31;

ALTER TABLE `qrtz_job_details` MODIFY COLUMN `DESCRIPTION` varchar(250) COLLATE utf8_bin DEFAULT NULL COMMENT '描述';

#2020-07-31;

ALTER TABLE `qrtz_job_details` MODIFY COLUMN `JOB_CLASS_NAME` varchar(250) COLLATE utf8_bin NOT NULL COMMENT '作业程序集名称';

#2020-07-31;

ALTER TABLE `qrtz_job_details` MODIFY COLUMN `IS_DURABLE` varchar(1) COLLATE utf8_bin NOT NULL COMMENT '是否持久';

#2020-07-31;

ALTER TABLE `qrtz_job_details` MODIFY COLUMN `IS_NONCONCURRENT` varchar(1) COLLATE utf8_bin NOT NULL COMMENT '是否并行';

#2020-07-31;

ALTER TABLE `qrtz_job_details` MODIFY COLUMN `IS_UPDATE_DATA` varchar(1) COLLATE utf8_bin NOT NULL COMMENT '是否更新';

#2020-07-31;

ALTER TABLE `qrtz_job_details` MODIFY COLUMN `REQUESTS_RECOVERY` varchar(1) COLLATE utf8_bin NOT NULL COMMENT '是否要求唤醒';

#2020-07-31;

ALTER TABLE `qrtz_job_details` MODIFY COLUMN `JOB_DATA` blob DEFAULT NULL COMMENT '作业信息';

#2020-07-31;

ALTER TABLE `qrtz_locks` COMMENT '存储程序的悲观锁的信息';

#2020-07-31;

ALTER TABLE `qrtz_locks` MODIFY COLUMN `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL COMMENT '计划名称';

#2020-07-31;

ALTER TABLE `qrtz_locks` MODIFY COLUMN `LOCK_NAME` varchar(40) COLLATE utf8_bin NOT NULL COMMENT '锁名称';

#2020-07-31;

ALTER TABLE `qrtz_paused_trigger_grps` COMMENT '存放暂停掉的触发器';

#2020-07-31;

ALTER TABLE `qrtz_paused_trigger_grps` MODIFY COLUMN `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL COMMENT '计划名称';

#2020-07-31;

ALTER TABLE `qrtz_paused_trigger_grps` MODIFY COLUMN `TRIGGER_GROUP` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '触发器组';

#2020-07-31;

ALTER TABLE `qrtz_scheduler_state` COMMENT '存储少量的有关 Scheduler 的状态信息，和别的Scheduler实例';

#2020-07-31;

ALTER TABLE `qrtz_scheduler_state` MODIFY COLUMN `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL COMMENT '计划名称';

#2020-07-31;

ALTER TABLE `qrtz_scheduler_state` MODIFY COLUMN `INSTANCE_NAME` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '实例名称';

#2020-07-31;

ALTER TABLE `qrtz_scheduler_state` MODIFY COLUMN `LAST_CHECKIN_TIME` bigint(13) NOT NULL COMMENT '最后的检查时间';

#2020-07-31;

ALTER TABLE `qrtz_scheduler_state` MODIFY COLUMN `CHECKIN_INTERVAL` bigint(13) NOT NULL COMMENT '检查间隔';

#2020-07-31;

ALTER TABLE `qrtz_triggers` COMMENT '触发器的基本信息';

#2020-07-31;

ALTER TABLE `qrtz_triggers` MODIFY COLUMN `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL COMMENT '计划名称';

#2020-07-31;

ALTER TABLE `qrtz_triggers` MODIFY COLUMN `TRIGGER_NAME` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '触发器名称';

#2020-07-31;

ALTER TABLE `qrtz_triggers` MODIFY COLUMN `TRIGGER_GROUP` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '触发器组';

#2020-07-31;

ALTER TABLE `qrtz_triggers` MODIFY COLUMN `JOB_NAME` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '作业名称';

#2020-07-31;

ALTER TABLE `qrtz_triggers` MODIFY COLUMN  `JOB_GROUP` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '作业组';

#2020-07-31;

ALTER TABLE `qrtz_triggers` MODIFY COLUMN `DESCRIPTION` varchar(250) COLLATE utf8_bin DEFAULT NULL COMMENT '描述';

#2020-07-31;

ALTER TABLE `qrtz_triggers` MODIFY COLUMN `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL COMMENT '下次执行时间';

#2020-07-31;

ALTER TABLE `qrtz_triggers` MODIFY COLUMN `PREV_FIRE_TIME` bigint(13) DEFAULT NULL COMMENT '前一次执行时间';

#2020-07-31;

ALTER TABLE `qrtz_triggers` MODIFY COLUMN `PRIORITY` int(11) DEFAULT NULL COMMENT '优先权';

#2020-07-31;

ALTER TABLE `qrtz_triggers` MODIFY COLUMN `TRIGGER_STATE` varchar(16) COLLATE utf8_bin NOT NULL COMMENT '触发器状态';

#2020-07-31;

ALTER TABLE `qrtz_triggers` MODIFY COLUMN `TRIGGER_TYPE` varchar(8) COLLATE utf8_bin NOT NULL COMMENT '触发器类型';

#2020-07-31;

ALTER TABLE `qrtz_triggers` MODIFY COLUMN `START_TIME` bigint(13) NOT NULL COMMENT '开始时间';

#2020-07-31;

ALTER TABLE `qrtz_triggers` MODIFY COLUMN `END_TIME` bigint(13) DEFAULT NULL COMMENT '结束时间';

#2020-07-31;

ALTER TABLE `qrtz_triggers` MODIFY COLUMN `CALENDAR_NAME` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '日历名称';

#2020-07-31;

ALTER TABLE `qrtz_triggers` MODIFY COLUMN `MISFIRE_INSTR` smallint(2) DEFAULT NULL COMMENT '失败次数';

#2020-07-31;

ALTER TABLE `qrtz_triggers` MODIFY COLUMN `JOB_DATA` blob DEFAULT NULL COMMENT '作业数据';

#2020-07-31;

ALTER TABLE `qrtz_cron_triggers` COMMENT '存储 Cron Trigger，包括Cron表达式和时区信息';

#2020-07-31;

ALTER TABLE `qrtz_cron_triggers` MODIFY COLUMN   `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL COMMENT '计划名称';

#2020-07-31;

ALTER TABLE `qrtz_cron_triggers` MODIFY COLUMN `TRIGGER_NAME` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '触发器名称';

#2020-07-31;

ALTER TABLE `qrtz_cron_triggers` MODIFY COLUMN `TRIGGER_GROUP` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '触发器组';

#2020-07-31;

ALTER TABLE `qrtz_cron_triggers` MODIFY COLUMN `CRON_EXPRESSION` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '时间表达式';

#2020-07-31;

ALTER TABLE `qrtz_cron_triggers` MODIFY COLUMN `TIME_ZONE_ID` varchar(80) COLLATE utf8_bin DEFAULT NULL COMMENT '时区ID';

#2020-07-31;

ALTER TABLE `qrtz_fired_triggers` COMMENT '保存已经触发的触发器状态信息';

#2020-07-31;

ALTER TABLE `qrtz_fired_triggers` MODIFY COLUMN `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL COMMENT '计划名称';

#2020-07-31;

ALTER TABLE `qrtz_fired_triggers` MODIFY COLUMN `ENTRY_ID` varchar(95) COLLATE utf8_bin NOT NULL COMMENT '组标识';

#2020-07-31;

ALTER TABLE `qrtz_fired_triggers` MODIFY COLUMN `TRIGGER_NAME` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '触发器名称';

#2020-07-31;

ALTER TABLE `qrtz_fired_triggers` MODIFY COLUMN `TRIGGER_GROUP` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '触发器组';

#2020-07-31;

ALTER TABLE `qrtz_fired_triggers` MODIFY COLUMN  `INSTANCE_NAME` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '当前实例的名称';

#2020-07-31;

ALTER TABLE `qrtz_fired_triggers` MODIFY COLUMN `FIRED_TIME` bigint(13) NOT NULL COMMENT '当前执行时间';

#2020-07-31;

ALTER TABLE `qrtz_fired_triggers` MODIFY COLUMN `SCHED_TIME` decimal(28,0) DEFAULT NULL COMMENT '计划时间';

#2020-07-31;

ALTER TABLE `qrtz_fired_triggers` MODIFY COLUMN `PRIORITY` int(11) NOT NULL COMMENT '权重';

#2020-07-31;

ALTER TABLE `qrtz_fired_triggers` MODIFY COLUMN `STATE` varchar(16) COLLATE utf8_bin NOT NULL COMMENT '状态';

#2020-07-31;

ALTER TABLE `qrtz_fired_triggers` MODIFY COLUMN `JOB_NAME` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '作业名称';

#2020-07-31;

ALTER TABLE `qrtz_fired_triggers` MODIFY COLUMN `JOB_GROUP` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '作业组';

#2020-07-31;

ALTER TABLE `qrtz_fired_triggers` MODIFY COLUMN `IS_NONCONCURRENT` varchar(1) COLLATE utf8_bin DEFAULT NULL COMMENT '是否并行';

#2020-07-31;

ALTER TABLE `qrtz_fired_triggers` MODIFY COLUMN `REQUESTS_RECOVERY` varchar(1) COLLATE utf8_bin DEFAULT NULL COMMENT '是否要求唤醒';

#2020-07-31;

ALTER TABLE `qrtz_blob_triggers` COMMENT '自定义触发器';

#2020-07-31;

ALTER TABLE `qrtz_blob_triggers` MODIFY COLUMN `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL COMMENT '计划名称';

#2020-07-31;

ALTER TABLE `qrtz_blob_triggers` MODIFY COLUMN `TRIGGER_NAME` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '触发器名称';

#2020-07-31;

ALTER TABLE `qrtz_blob_triggers` MODIFY COLUMN `TRIGGER_GROUP` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '触发器组';

#2020-07-31;

ALTER TABLE `qrtz_blob_triggers` MODIFY COLUMN `BLOB_DATA` blob DEFAULT NULL COMMENT '保存triggers一些信息';

#2020-07-31;

ALTER TABLE `qrtz_simple_triggers` COMMENT '存储简单的Trigger，包括重复次数，间隔，以及已触的次数';

#2020-07-31;

ALTER TABLE `qrtz_simple_triggers` MODIFY COLUMN  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL COMMENT '计划名称';

#2020-07-31;

ALTER TABLE `qrtz_simple_triggers` MODIFY COLUMN  `TRIGGER_NAME` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '触发器名称';

#2020-07-31;

ALTER TABLE `qrtz_simple_triggers` MODIFY COLUMN  `TRIGGER_GROUP` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '触发器组';

#2020-07-31;

ALTER TABLE `qrtz_simple_triggers` MODIFY COLUMN  `REPEAT_COUNT` bigint(7) NOT NULL COMMENT '重复次数';

#2020-07-31;

ALTER TABLE `qrtz_simple_triggers` MODIFY COLUMN  `REPEAT_INTERVAL` bigint(12) NOT NULL COMMENT '重复间隔';

#2020-07-31;

ALTER TABLE `qrtz_simple_triggers` MODIFY COLUMN  `TIMES_TRIGGERED` bigint(10) NOT NULL COMMENT '触发次数';

#2020-07-31;

ALTER TABLE `qrtz_simprop_triggers` COMMENT '存储简单的Trigger屬性信息';

#2020-07-31;

ALTER TABLE `qrtz_simprop_triggers` MODIFY COLUMN  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL COMMENT '计划名称';

#2020-07-31;

ALTER TABLE `qrtz_simprop_triggers` MODIFY COLUMN  `TRIGGER_NAME` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '触发器名称';

#2020-07-31;

ALTER TABLE `qrtz_simprop_triggers` MODIFY COLUMN  `TRIGGER_GROUP` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '触发器组';

#2020-07-31;

ALTER TABLE `qrtz_simprop_triggers` MODIFY COLUMN  `STR_PROP_1` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT 'String參數1';

#2020-07-31;

ALTER TABLE `qrtz_simprop_triggers` MODIFY COLUMN  `STR_PROP_2` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT 'String參數2';

#2020-07-31;

ALTER TABLE `qrtz_simprop_triggers` MODIFY COLUMN  `STR_PROP_3` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT 'String參數3';

#2020-07-31;

ALTER TABLE `qrtz_simprop_triggers` MODIFY COLUMN  `INT_PROP_1` int(11) DEFAULT NULL COMMENT 'Integer參數1';

#2020-07-31;

ALTER TABLE `qrtz_simprop_triggers` MODIFY COLUMN  `INT_PROP_2` int(11) DEFAULT NULL COMMENT 'Integer參數2';

#2020-07-31;

ALTER TABLE `qrtz_simprop_triggers` MODIFY COLUMN  `LONG_PROP_1` bigint(20) DEFAULT NULL COMMENT 'Long參數1';

#2020-07-31;

ALTER TABLE `qrtz_simprop_triggers` MODIFY COLUMN  `LONG_PROP_2` bigint(20) DEFAULT NULL COMMENT 'Long參數2';

#2020-07-31;

ALTER TABLE `qrtz_simprop_triggers` MODIFY COLUMN  `DEC_PROP_1` decimal(13,4) DEFAULT NULL COMMENT 'Decimal參數1';

#2020-07-31;

ALTER TABLE `qrtz_simprop_triggers` MODIFY COLUMN  `DEC_PROP_2` decimal(13,4) DEFAULT NULL COMMENT 'Decimal參數2';

#2020-07-31;

ALTER TABLE `qrtz_simprop_triggers` MODIFY COLUMN  `BOOL_PROP_1` varchar(1) COLLATE utf8_bin DEFAULT NULL COMMENT 'Boolean參數1';

#2020-07-31;

ALTER TABLE `qrtz_simprop_triggers` MODIFY COLUMN  `BOOL_PROP_2` varchar(1) COLLATE utf8_bin DEFAULT NULL COMMENT 'Boolean參數2';

#2020-07-31;

ALTER TABLE `dw_schedule` CHANGE COLUMN `schedule_param` `schedule_param` TEXT NULL DEFAULT NULL COMMENT '參數' COLLATE 'utf8_bin' AFTER `max_runtime`;

#2020-07-31;

ALTER TABLE `dw_schedule_record` CHANGE COLUMN `execute_id` `execute_id` VARCHAR(128) NOT NULL COMMENT '執行編號' COLLATE 'utf8_bin' FIRST, CHANGE COLUMN `message` `message` TEXT NULL COMMENT '訊息id' COLLATE 'utf8_bin' AFTER `execute_status`;

#2020-07-31;

UPDATE `dw_rdbms_fields` SET `FIELD_TYPE`='TEXT', `SIZE`=NULL WHERE  `FIELD_NAME`='schedule_param' AND `TABLE_NAME`='dw_schedule';

#2020-07-31;

UPDATE `dw_rdbms_fields` SET `SIZE`='128' WHERE  `FIELD_NAME`='execute_id' AND `TABLE_NAME`='dw_schedule_record';

#2020-07-31;

UPDATE `dw_rdbms_fields` SET `FIELD_TYPE`='TEXT', `SIZE`=NULL WHERE  `FIELD_NAME`='message' AND `TABLE_NAME`='dw_schedule_record';

#2020-07-31;

UPDATE `dw_rdbms_fields` SET `FIELD_TYPE`='TEXT', `SIZE`=NULL WHERE  `FIELD_NAME`='schedule_param' AND `TABLE_NAME`='dw_schedule_record';

#2020-07-31;

ALTER TABLE `dw_schedule` MODIFY COLUMN  `effective_time` TIMESTAMP NULL DEFAULT NULL  COMMENT '排程生效時間';

#2020-07-31;

ALTER TABLE `dw_schedule` MODIFY COLUMN  `expire_time` TIMESTAMP NULL DEFAULT NULL  COMMENT '排程失效時間';

#2020-07-31;

ALTER TABLE `dw_schedule` MODIFY COLUMN  `last_success_time` TIMESTAMP NULL DEFAULT NULL  COMMENT '上次成功時間';

#2020-07-31;

ALTER TABLE `dw_schedule` MODIFY COLUMN  `next_execute_time` TIMESTAMP NULL DEFAULT NULL  COMMENT '下次執行時間';

#2020-07-31;

ALTER TABLE `dw_schedule_time` MODIFY COLUMN `start_time` TIMESTAMP NULL DEFAULT NULL COMMENT '開始日期';

#2020-07-31;

ALTER TABLE `dw_schedule_time` MODIFY COLUMN `end_time` TIMESTAMP NULL DEFAULT NULL COMMENT '結束日期';

#2020-07-31;

ALTER TABLE `dw_rdbms_fields` MODIFY COLUMN `NULLABLE` varchar(1) COLLATE utf8_bin NOT NULL DEFAULT 'N' COMMENT '可否為空';

#2020-07-31;

ALTER TABLE `dw_rdbms_fields` MODIFY COLUMN `IS_VERSION` varchar(1) COLLATE utf8_bin NOT NULL DEFAULT 'N' COMMENT '是否需要依version進行樂觀鎖';

#2020-07-31;

ALTER TABLE `dw_rdbms_fields` MODIFY COLUMN `SIZE` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '長度';

#2020-07-31;

ALTER TABLE `dw_rdbms_fields` MODIFY COLUMN `IS_AUTO_INCREMENT` varchar(1) COLLATE utf8_bin NOT NULL DEFAULT 'N' COMMENT '是否為自動產生流水號';

#2020-07-31;

ALTER TABLE `dw_schedule_time` MODIFY COLUMN `repeat_count` decimal(65,0) DEFAULT NULL COMMENT '重覆次數';

#2020-07-31;

ALTER TABLE `dw_schedule_time` MODIFY COLUMN `frequency` INT(10) NULL DEFAULT NULL COMMENT '週期頻率' COLLATE 'utf8_bin';

#2020-07-31;

ALTER TABLE `dw_schedule_time` MODIFY COLUMN `repeat_type` INT(10) NULL DEFAULT NULL COMMENT '區間類型  1.每幾分 2.每幾小時 3.每幾天' COLLATE 'utf8_bin';

SET foreign_key_checks = 1;
