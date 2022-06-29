CREATE TABLE `dw_app_prod` (
  `app_code` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '應用產品線代碼',
  `description` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '說明資料',
  PRIMARY KEY (`app_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT '應用產品線';

CREATE TABLE `dw_app_release` (
  `app_code` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '應用產品線代碼',
  `version` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '版號',
  `file_seq` int(10) NOT NULL COMMENT '檔案序號',
  `file_id` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '放在文檔中心的檔案id',
  `published` varchar(1) COLLATE utf8_bin NOT NULL COMMENT '正式發布',
  `file_name` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '放在文檔中心的檔案名稱',
  PRIMARY KEY (`app_code`,`version`,`file_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT '應用版本發布與釋出';

CREATE TABLE `dw_app_update` (
  `app_code` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '應用產品線代碼',
  `version` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '版號',
  `allow_upd_version` varchar(2000) COLLATE utf8_bin NOT NULL COMMENT '允許版更的版本列表[分號分隔]',
  `allow_upd_expr` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '允許版更的版本表達式',
  PRIMARY KEY (`app_code`,`version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT '應用更新版本對應';

CREATE TABLE `dw_menu` (
  `id` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '編號',
  `type` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '型態',
  `parent_id` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '父層編號',
  `code` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '代碼',
  `sequence` int(20) NOT NULL DEFAULT 1 COMMENT '順序',
  `icon_class` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '圖示class',
  `default_expand` bit(1) DEFAULT b'0' COMMENT '默認展開',
  `url` varchar(2000) COLLATE utf8_bin DEFAULT NULL COMMENT 'URL',
  `open_mode` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'open 模式',
  `version` int(20) NOT NULL DEFAULT 1 COMMENT '版本',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT '菜單主表';

CREATE TABLE `dw_menu_language` (
  `menu_id` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '菜單編號',
  `language` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '語言別',
  `name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '名稱',
  PRIMARY KEY (`menu_id`,`language`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT '菜單多語言信息表';

CREATE TABLE `dw_menu_parameter` (
  `menu_id` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '菜單編號',
  `name` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '參數名稱',
  `value` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '值',
  PRIMARY KEY (`menu_id`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT '菜單參數表';

CREATE TABLE `dw_query_info` (
  `query_id` varchar(45) COLLATE utf8_bin NOT NULL COMMENT '查詢編號',
  `query_type` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '類型',
  `sql_statement` varchar(450) COLLATE utf8_bin DEFAULT NULL COMMENT 'sql',
  `publish` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '發佈者',
  `owner` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '擁有者',
  PRIMARY KEY (`query_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT '開窗信息主表';

CREATE TABLE `dw_rdbms_fields` (
  `TABLE_NAME` varchar(80) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '数据表名称',
  `SEQ` smallint(5) unsigned NOT NULL COMMENT '项次',
  `FIELD_NAME` varchar(80) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '字段编号',
  `IS_KEY` varchar(1) COLLATE utf8_bin NOT NULL DEFAULT 'N' COMMENT '主键否',
  `FIELD_TYPE` varchar(20) COLLATE utf8_bin DEFAULT '' COMMENT '字段型态，备注：1.varchar;2.number;3.date;4.datetime',
  `STATUS_CODE` varchar(1) COLLATE utf8_bin NOT NULL DEFAULT 'Y' COMMENT '状态码:Y.有效；N.无效；',
  `FIELD_DISPLAY_NAME` varchar(80) COLLATE utf8_bin DEFAULT '' COMMENT '字段名称',
  `SCALE` varchar(20) COLLATE utf8_bin DEFAULT '' COMMENT '字段长度',
  `DEFAULT_VALUE` varchar(50) COLLATE utf8_bin DEFAULT '' COMMENT '默认值',
  `NULLABLE` varchar(1) COLLATE utf8_bin NOT NULL DEFAULT 'N' COMMENT '可否為空',
  `IS_VERSION` varchar(1) COLLATE utf8_bin NOT NULL DEFAULT 'N' COMMENT '是否需要依version進行樂觀鎖',
  `SIZE` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '長度',
  `IS_AUTO_INCREMENT` varchar(1) COLLATE utf8_bin NOT NULL DEFAULT 'N' COMMENT '是否為自動產生流水號',
  PRIMARY KEY (`FIELD_NAME`,`TABLE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT '数据结构资料表';

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES
	('dw_app_update', 4, 'allow_upd_expr', 'N', 'varchar', 'Y', 'allow_upd_expr', '0', '', 'N', 'N', '20', 'N'),
	('dw_app_update', 3, 'allow_upd_version', 'N', 'varchar', 'Y', 'allow_upd_version', '0', '', 'N', 'N', '2000', 'N'),
	('dw_app_prod', 1, 'app_code', 'Y', 'varchar', 'Y', 'app_code', '0', '', 'N', 'N', '20', 'N'),
	('dw_app_release', 1, 'app_code', 'Y', 'varchar', 'Y', 'app_code', '0', '', 'N', 'N', '20', 'N'),
	('dw_app_update', 1, 'app_code', 'Y', 'varchar', 'Y', 'app_code', '0', '', 'N', 'N', '20', 'N'),
	('dw_menu', 4, 'code', 'N', 'varchar', 'Y', 'code', '0', '', 'Y', 'N', '200', 'N'),
	('dw_menu', 7, 'default_expand', 'N', 'number', 'Y', 'default_expand', '0', '0', 'Y', 'N', '1', 'N'),
	('dw_app_prod', 2, 'description', 'N', 'varchar', 'Y', 'description', '0', '', 'N', 'N', '200', 'N'),
	('dw_app_release', 4, 'file_id', 'N', 'varchar', 'Y', 'file_id', '0', '', 'N', 'N', '200', 'N'),
	('dw_app_release', 6, 'file_name', 'N', 'varchar', 'Y', 'file_name', '0', '', 'N', 'N', '200', 'N'),
	('dw_app_release', 3, 'file_seq', 'Y', 'number', 'Y', 'file_seq', '0', '', 'N', 'N', '10', 'N'),	
	('dw_menu', 6, 'icon_class', 'N', 'varchar', 'Y', 'icon_class', '0', '', 'Y', 'N', '200', 'N'),
	('dw_menu', 1, 'id', 'Y', 'varchar', 'Y', 'id', '0', '', 'N', 'N', '100', 'N'),
	('dw_menu_language', 2, 'language', 'Y', 'varchar', 'Y', 'language', '0', '', 'N', 'N', '50', 'N'),	
	('dw_menu_language', 1, 'menu_id', 'Y', 'varchar', 'Y', 'menu_id', '0', '', 'N', 'N', '100', 'N'),
	('dw_menu_parameter', 1, 'menu_id', 'Y', 'varchar', 'Y', 'menu_id', '0', '', 'N', 'N', '100', 'N'),	
	('dw_menu_language', 3, 'name', 'N', 'varchar', 'Y', 'name', '0', '', 'Y', 'N', '200', 'N'),
	('dw_menu_parameter', 2, 'name', 'Y', 'varchar', 'Y', 'name', '0', '', 'N', 'N', '200', 'N'),	
	('dw_menu', 9, 'open_mode', 'N', 'varchar', 'Y', 'open_mode', '0', '', 'Y', 'N', '50', 'N'),
	('dw_query_info', 5, 'owner', 'N', 'varchar', 'Y', 'owner', '0', '', 'Y', 'N', '45', 'N'),
	('dw_menu', 3, 'parent_id', 'N', 'varchar', 'Y', 'parent_id', '0', '', 'Y', 'N', '100', 'N'),
	('dw_query_info', 4, 'publish', 'N', 'varchar', 'Y', 'publish', '0', '', 'Y', 'N', '45', 'N'),
	('dw_app_release', 5, 'published', 'N', 'varchar', 'Y', 'published', '0', '', 'N', 'N', '1', 'N'),
	('dw_query_info', 1, 'query_id', 'Y', 'varchar', 'Y', 'query_id', '0', '', 'N', 'N', '45', 'N'),
	('dw_query_info', 2, 'query_type', 'N', 'varchar', 'Y', 'query_type', '0', '', 'N', 'N', '45', 'N'),	
	('dw_menu', 5, 'sequence', 'N', 'number', 'Y', 'sequence', '0', '', 'N', 'N', '20', 'N'),
	('dw_query_info', 3, 'sql_statement', 'N', 'varchar', 'Y', 'sql_statement', '0', '', 'N', 'N', '450', 'N'),	
	('dw_menu', 2, 'type', 'N', 'varchar', 'Y', 'type', '0', '', 'N', 'N', '50', 'N'),
	('dw_menu', 8, 'url', 'N', 'varchar', 'Y', 'url', '0', '', 'Y', 'N', '2000', 'N'),
	('dw_menu_parameter', 3, 'value', 'N', 'varchar', 'Y', 'value', '0', '', 'Y', 'N', '200', 'N'),
	('dw_app_release', 2, 'version', 'Y', 'varchar', 'Y', 'version', '0', '', 'N', 'N', '20', 'N'),
	('dw_app_update', 2, 'version', 'Y', 'varchar', 'Y', 'version', '0', '', 'N', 'N', '20', 'N'),
	('dw_menu', 10, 'version', 'N', 'number', 'Y', 'version', '0', '', 'N', 'Y', '20', 'N');

CREATE TABLE `dw_rdbms_relations` (
  `PRIMARY_TABLE_NO` varchar(80) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '来源表号',
  `REFERENCE_TABLE_NO` varchar(80) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '参照表号',
  `PRIMARY_TABLE_PK` varchar(45) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '来源表PK',
  `REFERENCE_TABLE_PK` varchar(45) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '参照表PK',
  PRIMARY KEY (`PRIMARY_TABLE_NO`,`REFERENCE_TABLE_NO`,`PRIMARY_TABLE_PK`,`REFERENCE_TABLE_PK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT '资料表关系表';

INSERT INTO `dw_rdbms_relations` (`PRIMARY_TABLE_NO`, `REFERENCE_TABLE_NO`, `PRIMARY_TABLE_PK`, `REFERENCE_TABLE_PK`) VALUES
	('dw_app_prod', 'dw_app_release', 'app_code', 'app_code'),
	('dw_app_prod', 'dw_app_update', 'app_code', 'app_code'),
	('dw_menu', 'dw_menu_language', 'id', 'menu_id'),
	('dw_menu', 'dw_menu_parameter', 'id', 'menu_id');

CREATE TABLE `dw_rdbms_tables` (
  `table_name` varchar(45) COLLATE utf8_bin NOT NULL COMMENT '资料表名',
  `table_display_name` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '资料表外显名称',
  `table_description` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '资料表描述',
  PRIMARY KEY (`table_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT '数据库资料表描述表';

INSERT INTO `dw_rdbms_tables` (`table_name`, `table_display_name`, `table_description`) VALUES
	('dw_app_prod', 'dw_app_prod', '應用產品線'),
	('dw_app_release', 'dw_app_release', '應用版本發布與釋出'),
	('dw_app_update', 'dw_app_update', '應用更新版本對應'),
	('dw_menu', 'dw_menu', '菜单'),
	('dw_menu_language', 'dw_menu_language', '菜单多语言'),
	('dw_menu_parameter', 'dw_menu_parameter', '菜单参数');

CREATE TABLE `dw_ui_metadata` (
  `operation_id` varchar(80) COLLATE utf8_bin NOT NULL COMMENT 'ui編號',
  `table_name` varchar(45) COLLATE utf8_bin NOT NULL COMMENT '表名稱',
  `field_id` varchar(45) COLLATE utf8_bin NOT NULL COMMENT '字段名稱',
  `length` int(11) DEFAULT NULL COMMENT '長度',
  `nullable` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '允許為null',
  `format` varchar(80) COLLATE utf8_bin DEFAULT NULL COMMENT '格式',
  `enum` varchar(80) COLLATE utf8_bin DEFAULT NULL COMMENT '枚舉值',
  `resource` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '來源',
  PRIMARY KEY (`operation_id`,`table_name`,`field_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT '使用者介面元數據';

CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL COMMENT '计划名称',
  `CALENDAR_NAME` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '触发器名称',
  `CALENDAR` blob NOT NULL COMMENT '日曆信息',
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT '日曆資訊';

CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL COMMENT '计划名称',
  `JOB_NAME` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '作业名称',
  `JOB_GROUP` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '作业组',
  `DESCRIPTION` varchar(250) COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `JOB_CLASS_NAME` varchar(250) COLLATE utf8_bin NOT NULL COMMENT '作业程序集名称',
  `IS_DURABLE` varchar(1) COLLATE utf8_bin NOT NULL COMMENT '是否持久',
  `IS_NONCONCURRENT` varchar(1) COLLATE utf8_bin NOT NULL COMMENT '是否并行',
  `IS_UPDATE_DATA` varchar(1) COLLATE utf8_bin NOT NULL COMMENT '是否更新',
  `REQUESTS_RECOVERY` varchar(1) COLLATE utf8_bin NOT NULL COMMENT '是否要求唤醒',
  `JOB_DATA` blob DEFAULT NULL COMMENT '作业信息',
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT 'job詳息信息';

CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL COMMENT '计划名称',
  `LOCK_NAME` varchar(40) COLLATE utf8_bin NOT NULL COMMENT '锁名称',
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT '存储程序的悲观锁的信息';

CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL COMMENT '计划名称',
  `TRIGGER_GROUP` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '触发器组',
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT '存放暂停掉的触发器';

CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL COMMENT '计划名称',
  `INSTANCE_NAME` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '实例名称',
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL COMMENT '最后的检查时间',
  `CHECKIN_INTERVAL` bigint(13) NOT NULL COMMENT '检查间隔',
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT '存储少量的有关 Scheduler 的状态信息，和别的Scheduler实例';

CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL COMMENT '计划名称',
  `TRIGGER_NAME` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '触发器名称',
  `TRIGGER_GROUP` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '触发器组',
  `JOB_NAME` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '作业名称',
  `JOB_GROUP` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '作业组',
  `DESCRIPTION` varchar(250) COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL COMMENT '下次执行时间',
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL COMMENT '前一次执行时间',
  `PRIORITY` int(11) DEFAULT NULL COMMENT '优先权',
  `TRIGGER_STATE` varchar(16) COLLATE utf8_bin NOT NULL COMMENT '触发器状态',
  `TRIGGER_TYPE` varchar(8) COLLATE utf8_bin NOT NULL COMMENT '触发器类型',
  `START_TIME` bigint(13) NOT NULL COMMENT '开始时间',
  `END_TIME` bigint(13) DEFAULT NULL COMMENT '结束时间',
  `CALENDAR_NAME` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '日历名称',
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL COMMENT '失败次数',
  `JOB_DATA` blob DEFAULT NULL COMMENT '作业数据',
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT '触发器的基本信息';

CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL COMMENT '计划名称',
  `TRIGGER_NAME` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '触发器名称',
  `TRIGGER_GROUP` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '触发器组',
  `CRON_EXPRESSION` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '时间表达式',
  `TIME_ZONE_ID` varchar(80) COLLATE utf8_bin DEFAULT NULL COMMENT '时区ID',
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT '存储 Cron Trigger，包括Cron表达式和时区信息';

CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL COMMENT '计划名称',
  `ENTRY_ID` varchar(95) COLLATE utf8_bin NOT NULL COMMENT '组标识',
  `TRIGGER_NAME` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '触发器名称',
  `TRIGGER_GROUP` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '触发器组',
  `INSTANCE_NAME` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '当前实例的名称',
  `FIRED_TIME` bigint(13) NOT NULL COMMENT '当前执行时间',
  `SCHED_TIME` decimal(28,0) DEFAULT NULL COMMENT '计划时间',
  `PRIORITY` int(11) NOT NULL COMMENT '权重',
  `STATE` varchar(16) COLLATE utf8_bin NOT NULL COMMENT '状态',
  `JOB_NAME` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '作业名称',
  `JOB_GROUP` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '作业组',
  `IS_NONCONCURRENT` varchar(1) COLLATE utf8_bin DEFAULT NULL COMMENT '是否并行',
  `REQUESTS_RECOVERY` varchar(1) COLLATE utf8_bin DEFAULT NULL COMMENT '是否要求唤醒',
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT '保存已经触发的触发器状态信息';

CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL COMMENT '计划名称',
  `TRIGGER_NAME` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '触发器名称',
  `TRIGGER_GROUP` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '触发器组',
  `BLOB_DATA` blob DEFAULT NULL COMMENT '保存triggers一些信息',
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT '自定义触发器';

CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL COMMENT '计划名称',
  `TRIGGER_NAME` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '触发器名称',
  `TRIGGER_GROUP` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '触发器组',
  `REPEAT_COUNT` bigint(7) NOT NULL COMMENT '重复次数',
  `REPEAT_INTERVAL` bigint(12) NOT NULL COMMENT '重复间隔',
  `TIMES_TRIGGERED` bigint(10) NOT NULL COMMENT '触发次数',
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT '存储简单的Trigger，包括重复次数，间隔，以及已触的次数';

CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL COMMENT '计划名称',
  `TRIGGER_NAME` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '触发器名称',
  `TRIGGER_GROUP` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '触发器组',
  `STR_PROP_1` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT 'String參數1',
  `STR_PROP_2` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT 'String參數2',
  `STR_PROP_3` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT 'String參數3',
  `INT_PROP_1` int(11) DEFAULT NULL COMMENT 'Integer參數1',
  `INT_PROP_2` int(11) DEFAULT NULL COMMENT 'Integer參數2',
  `LONG_PROP_1` bigint(20) DEFAULT NULL COMMENT 'Long參數1',
  `LONG_PROP_2` bigint(20) DEFAULT NULL COMMENT 'Long參數2',
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL COMMENT 'Decimal參數1',
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL COMMENT 'Decimal參數2',
  `BOOL_PROP_1` varchar(1) COLLATE utf8_bin DEFAULT NULL COMMENT 'Boolean參數1',
  `BOOL_PROP_2` varchar(1) COLLATE utf8_bin DEFAULT NULL COMMENT 'Boolean參數2',
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT '存储简单的Trigger屬性信息';
