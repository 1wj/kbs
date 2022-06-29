CREATE TABLE `dw_log_operation` (
	`log_id` VARCHAR(50) NOT NULL COMMENT '主键' COLLATE 'utf8_bin',
	`operate_no` VARCHAR(50) NULL DEFAULT NULL COMMENT '操作流水號' COLLATE 'utf8_bin',
	`operate_type` VARCHAR(20) NULL DEFAULT NULL COMMENT '操作類型(1:pc 2：pda)' COLLATE 'utf8_bin',
	`operate_desc` VARCHAR(50) NULL DEFAULT NULL COMMENT '操作名稱' COLLATE 'utf8_bin',
	`menu` VARCHAR(50) NULL DEFAULT NULL COMMENT '選單菜單' COLLATE 'utf8_bin',
	`module` VARCHAR(50) NULL DEFAULT NULL COMMENT '模組' COLLATE 'utf8_bin',
	`url` VARCHAR(256) NULL DEFAULT NULL COMMENT '接口路徑' COLLATE 'utf8_bin',
	`input_value` TEXT NULL DEFAULT NULL COMMENT '操作接口入参' COLLATE 'utf8_bin',
	`return_value` TEXT NULL DEFAULT NULL COMMENT '操作接口回參' COLLATE 'utf8_bin',
	`client_ip` VARCHAR(50) NULL DEFAULT NULL COMMENT '客户端ip' COLLATE 'utf8_bin',
	`time_consume` VARCHAR(100) NULL DEFAULT NULL COMMENT '本次操作耗時(ms)' COLLATE 'utf8_bin',
	`status` TINYINT(4) NULL DEFAULT NULL COMMENT '操作狀態',
	`remark` TEXT NULL DEFAULT NULL COMMENT '備註' COLLATE 'utf8_bin',
	`create_name` VARCHAR(50) NULL DEFAULT NULL COMMENT '創建人' COLLATE 'utf8_bin',
	`create_time` DATETIME NULL DEFAULT current_timestamp() COMMENT '創建時間',
	`update_name` VARCHAR(50) NULL DEFAULT NULL COMMENT '更新人' COLLATE 'utf8_bin',
	`update_time` DATETIME NULL DEFAULT NULL ON UPDATE current_timestamp() COMMENT '更新時間',
	`traceid` VARCHAR(50) NULL DEFAULT NULL COMMENT '追蹤鍵' COLLATE 'utf8_bin',
	PRIMARY KEY (`log_id`) USING BTREE
)
COMMENT='操作日誌'
COLLATE='utf8_bin'
ENGINE=InnoDB
ROW_FORMAT=COMPACT
;

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES ('dw_log_operation', 1, 'log_id', 'Y', 'varchar', 'N', 'id', '0', '', 'N', 'N', '50', 'N');

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES ('dw_log_operation', 2, 'operate_no', 'N', 'varchar', 'N', 'operate_no', '0', '', 'N', 'N', '50', 'N');

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES ('dw_log_operation', 3, 'operate_type', 'N', 'varchar', 'N', 'operate_type', '0', '', 'N', 'N', '20', 'N');

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES ('dw_log_operation', 4, 'operate_desc', 'N', 'varchar', 'N', 'operate_desc', '0', '', 'N', 'N', '50', 'N');

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES ('dw_log_operation', 5, 'menu', 'N', 'varchar', 'N', 'menu', '0', '', 'N', 'N', '50', 'N');

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES ('dw_log_operation', 6, 'module', 'N', 'varchar', 'N', 'module', '0', '', 'N', 'N', '50', 'N');

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES ('dw_log_operation', 7, 'client_ip', 'N', 'varchar', 'N', 'client_ip', '0', '', 'N', 'N', '50', 'N');

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES ('dw_log_operation', 8, 'url', 'N', 'varchar', 'N', 'url', '0', '', 'N', 'N', '1000', 'N');

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES ('dw_log_operation', 9, 'input_value', 'N', 'text', 'N', 'input_value', '0', '', 'N', 'N', '4000', 'N');

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES ('dw_log_operation', 10, 'return_value', 'N', 'text', 'N', 'return_value', '0', '', 'N', 'N', '4000', 'N');

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES ('dw_log_operation', 11, 'time_consume', 'N', 'varchar', 'N', 'time_consume', '0', '', 'N', 'N', '50', 'N');

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES ('dw_log_operation', 12, 'status', 'N', 'INT', 'Y', 'status', '0', '', 'N', 'N', '4', 'N');

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES ('dw_log_operation', 13, 'create_name', 'N', 'varchar', 'N', 'create_name', '0', '', 'N', 'N', '50', 'N');

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES ('dw_log_operation', 14, 'remark', 'N', 'varchar', 'N', 'remark', '0', '', 'N', 'N', '1000', 'N');

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES ('dw_log_operation', 15, 'create_time', 'N', 'datetime', 'N', 'create_time', '0', '', 'N', 'N', NULL, 'N');

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES ('dw_log_operation', 16, 'update_name', 'N', 'varchar', 'N', 'update_name', '0', '', 'N', 'N', '50', 'N');

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES ('dw_log_operation', 17, 'update_time', 'N', 'datetime', 'N', 'update_time', '0', '', 'N', 'N', NULL, 'N');

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES ('dw_log_operation', 18, 'traceid', 'N', 'varchar', 'N', 'traceid', '0', '', 'N', 'N', '50', 'N');

CREATE TABLE `dw_log_operation_sql` (
	`log_id` VARCHAR(50) NOT NULL COMMENT '主键' COLLATE 'utf8_bin',
	`operation_log_id` VARCHAR(50) NULL DEFAULT NULL COMMENT '關聯日誌表id' COLLATE 'utf8_bin',
	`log_type` TINYINT(4) NULL DEFAULT '1' COMMENT '日誌類型(1.操作日誌  2.調度日誌)',
	`cmd_table` VARCHAR(100) NULL DEFAULT NULL COMMENT '執行表名(预留，還可以加操作)' COLLATE 'utf8_bin',
	`cmd_statement` LONGTEXT NULL DEFAULT NULL COMMENT '執行語句' COLLATE 'utf8_bin',
	`cmd_parameters` LONGTEXT NULL DEFAULT NULL COMMENT '執行參數' COLLATE 'utf8_bin',
	`cmd_seq` INT(11) NULL DEFAULT NULL COMMENT '執行顺序',
	`create_time` DATETIME NULL DEFAULT current_timestamp() COMMENT '創建時間',
	PRIMARY KEY (`log_id`) USING BTREE,
	INDEX `SEL_INDEX01` (`operation_log_id`) USING BTREE
)
COMMENT='sql日誌'
COLLATE='utf8_bin'
ENGINE=InnoDB
ROW_FORMAT=COMPACT
;

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES ('dw_log_operation_sql', 1, 'log_id', 'Y', 'varchar', 'N', 'log_id', '0', '', 'N', 'N', '50', 'N');

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES ('dw_log_operation_sql', 2, 'operation_log_id', 'Y', 'varchar', 'N', 'operation_log_id', '0', '', 'N', 'N', '50', 'N');

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES ('dw_log_operation_sql', 3, 'log_type', 'N', 'INT', 'N', 'log_type', '0', '', 'Y', 'N', '4', 'N');

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES ('dw_log_operation_sql', 4, 'cmd_table', 'N', 'varchar', 'N', 'cmd_table', '0', '', 'Y', 'N', '50', 'N');

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES ('dw_log_operation_sql', 5, 'cmd_statement', 'N', 'text', 'N', 'cmd_statement', '0', '', 'Y', 'N', '1000', 'N');

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES ('dw_log_operation_sql', 6, 'cmd_parameters', 'N', 'text', 'N', 'cmd_parameters', '0', '', 'Y', 'N', '1000', 'N');

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES ('dw_log_operation_sql', 7, 'cmd_seq', 'N', 'INT', 'N', 'cmd_seq', '0', '', 'Y', 'N', '11', 'N');

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES ('dw_log_operation_sql', 8, 'create_name', 'N', 'varchar', 'N', 'create_name', '0', '', 'Y', 'N', '50', 'N');

CREATE TABLE `dw_log_operation_error` (
	`log_id` VARCHAR(50) NOT NULL COMMENT '主键' COLLATE 'utf8_bin',
	`operate_no` VARCHAR(50) NULL DEFAULT NULL COMMENT '操作流水號' COLLATE 'utf8_bin',
	`operate_type` VARCHAR(20) NULL DEFAULT NULL COMMENT '操作類型(1:pc 2：pda)' COLLATE 'utf8_bin',
	`operate_desc` VARCHAR(50) NULL DEFAULT NULL COMMENT '操作名稱' COLLATE 'utf8_bin',
	`error_type` VARCHAR(100) NULL DEFAULT NULL COMMENT '異常类型(1:系统異常 2:操作異常 3:集成異常)' COLLATE 'utf8_bin',
	`error_message` VARCHAR(2000) NULL DEFAULT NULL COMMENT '異常資訊' COLLATE 'utf8_bin',
	`menu` VARCHAR(50) NULL DEFAULT NULL COMMENT '選單菜單' COLLATE 'utf8_bin',
	`module` VARCHAR(50) NULL DEFAULT NULL COMMENT '模組' COLLATE 'utf8_bin',
	`url` VARCHAR(256) NULL DEFAULT NULL COMMENT '接口路徑' COLLATE 'utf8_bin',
	`input_value` VARCHAR(5000) NULL DEFAULT NULL COMMENT '操作接口入参' COLLATE 'utf8_bin',
	`client_ip` VARCHAR(50) NULL DEFAULT NULL COMMENT '客户端ip' COLLATE 'utf8_bin',
	`time_consume` VARCHAR(100) NULL DEFAULT NULL COMMENT '本次操作耗時(ms)' COLLATE 'utf8_bin',
	`create_name` VARCHAR(50) NULL DEFAULT NULL COMMENT '創建人' COLLATE 'utf8_bin',
	`create_time` DATETIME NULL DEFAULT current_timestamp() COMMENT '創建時間',
	`update_name` VARCHAR(50) NULL DEFAULT NULL COMMENT '更新人' COLLATE 'utf8_bin',
	`update_time` DATETIME NULL DEFAULT NULL ON UPDATE current_timestamp() COMMENT '更新時間',
	`traceid` VARCHAR(50) NULL DEFAULT NULL COMMENT '追蹤鍵' COLLATE 'utf8_bin',
	`function` VARCHAR(200) NULL DEFAULT NULL COMMENT '代碼方法' COLLATE 'utf8_bin',
	PRIMARY KEY (`log_id`) USING BTREE
)
COMMENT='操作異常日誌'
COLLATE='utf8_bin'
ENGINE=InnoDB
ROW_FORMAT=COMPACT
;

#2021-11-30;

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES ('dw_log_operation_error', 1, 'log_id', 'Y', 'varchar', 'N', 'id', '0', '', 'N', 'N', '50', 'N');

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES ('dw_log_operation_error', 2, 'operate_no', 'N', 'varchar', 'N', 'operate_no', '0', '', 'N', 'N', '50', 'N');

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES ('dw_log_operation_error', 3, 'operate_type', 'N', 'varchar', 'N', 'operate_type', '0', '', 'N', 'N', '20', 'N');

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES ('dw_log_operation_error', 4, 'operate_desc', 'N', 'varchar', 'N', 'operate_desc', '0', '', 'N', 'N', '50', 'N');

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES ('dw_log_operation_error', 5, 'error_type', 'N', 'varchar', 'N', 'error_type', '0', '', 'N', 'N', '20', 'N');

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES ('dw_log_operation_error', 6, 'error_message', 'N', 'varchar', 'N', 'error_message', '0', '', 'N', 'N', '2000', 'N');

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES ('dw_log_operation_error', 8, 'menu', 'N', 'varchar', 'N', 'operate_type', '0', '', 'N', 'N', '50', 'N');

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES ('dw_log_operation_error', 9, 'module', 'N', 'varchar', 'N', 'module', '0', '', 'N', 'N', '50', 'N');

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES ('dw_log_operation_error', 10, 'client_ip', 'N', 'varchar', 'N', 'client_ip', '0', '', 'N', 'N', '50', 'N');

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES ('dw_log_operation_error', 11, 'url', 'N', 'varchar', 'N', 'url', '0', '', 'N', 'N', '1000', 'N');

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES ('dw_log_operation_error', 12, 'input_value', 'N', 'text', 'N', 'input_value', '0', '', 'N', 'N', '4000', 'N');

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES ('dw_log_operation_error', 13, 'return_value', 'N', 'text', 'N', 'return_value', '0', '', 'N', 'N', '4000', 'N');

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES ('dw_log_operation_error', 14, 'time_consume', 'N', 'varchar', 'N', 'time_consume', '0', '', 'N', 'N', '50', 'N');

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES ('dw_log_operation_error', 15, 'create_time', 'N', 'datetime', 'N', 'create_time', '0', '', 'N', 'N', NULL, 'N');

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES ('dw_log_operation_error', 16, 'create_name', 'N', 'varchar', 'N', 'create_name', '0', '', 'N', 'N', '50', 'N');

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES ('dw_log_operation_error', 17, 'traceid', 'N', 'varchar', 'N', 'traceid', '0', '', 'N', 'N', '50', 'N');

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES ('dw_log_operation_error', 18, 'function', 'N', 'varchar', 'N', 'function', '0', '', 'N', 'N', '200', 'N');

INSERT INTO `dw_rdbms_tables`(`table_name`, `table_display_name`, `table_description`, `delete_constraint`, `hint_field`) VALUES ('dw_log_operation', 'dw_log_operation', '操作日誌', 'N', NULL);

INSERT INTO `dw_rdbms_tables`(`table_name`, `table_display_name`, `table_description`, `delete_constraint`, `hint_field`) VALUES ('dw_log_operation_error', 'dw_log_operation_error', '操作異常日誌', 'N', NULL);

INSERT INTO `dw_rdbms_tables`(`table_name`, `table_display_name`, `table_description`, `delete_constraint`, `hint_field`) VALUES ('dw_log_operation_sql', 'dw_log_operation_sql', '操作日誌SQL', 'N', NULL);

INSERT INTO `dw_rdbms_relations` (`PRIMARY_TABLE_NO`, `REFERENCE_TABLE_NO`, `PRIMARY_TABLE_PK`, `REFERENCE_TABLE_PK`) VALUES ('dw_log_operation', 'dw_log_operation_sql', 'log_id', 'operation_log_id');