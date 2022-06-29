#2019-12-17;

CREATE TABLE `dw_screen` (
	`screen_id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '大屏id',
	`screen_name` VARCHAR(255) NULL DEFAULT NULL COMMENT '大屏名稱' COLLATE 'utf8_bin',
	`screen_config` TEXT NULL DEFAULT NULL COMMENT '配置' COLLATE 'utf8_bin',
	`modify_date` DATETIME NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '最新異動時間',
	PRIMARY KEY (`screen_id`)
)
COMMENT='大屏設定主表'
COLLATE='utf8_bin'
ENGINE=InnoDB
AUTO_INCREMENT=1;

#2019-12-17;

CREATE TABLE `dw_screen_unit` (
	`unit_id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '應用單元id',
	`screen_id` BIGINT(20) NOT NULL COMMENT '歸屬的大屏id',
	`unit_name` VARCHAR(255) NULL DEFAULT NULL COMMENT '應用單元名稱' COLLATE 'utf8_bin',
	`unit_config` TEXT NULL DEFAULT NULL COMMENT '配置' COLLATE 'utf8_bin',
	`chain_id` BIGINT(20) NULL DEFAULT NULL COMMENT '串接大屏id',
	PRIMARY KEY (`unit_id`, `screen_id`)
)
COMMENT='大屏應用單元'
COLLATE='utf8_bin'
ENGINE=InnoDB
AUTO_INCREMENT=1
AVG_ROW_LENGTH=1638;

#2019-12-17;

CREATE TABLE `dw_screen_extra` (
	`screen_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '大屏id',
	`screen_default` CHAR(1) NULL DEFAULT 'Y' COMMENT '是否為預設大屏, 是:Y' COLLATE 'utf8_bin',
	PRIMARY KEY (`screen_id`)
)
COMMENT='大屏設定主表'
COLLATE='utf8_bin'
ENGINE=InnoDB
ROW_FORMAT=DYNAMIC;

#2019-12-17;

INSERT INTO dw_rdbms_tables(table_name, table_display_name, table_description) VALUES
('dw_screen', '大屏設定主表', '大屏設定主表');

#2019-12-17;

INSERT INTO dw_rdbms_tables(table_name, table_display_name, table_description) VALUES
('dw_screen_unit', '大屏應用單元', '大屏應用單元');

#2019-12-17;

INSERT INTO dw_rdbms_tables(table_name, table_display_name, table_description) VALUES
('dw_screen_extra', '大屏進階配置', '大屏進階配置');

#2019-12-17;

INSERT INTO dw_rdbms_fields(TABLE_NAME, SEQ, FIELD_NAME, IS_KEY, FIELD_TYPE, STATUS_CODE, FIELD_DISPLAY_NAME, SCALE, DEFAULT_VALUE, NULLABLE, IS_VERSION, SIZE, IS_AUTO_INCREMENT) VALUES
('dw_screen', 1, 'screen_id', 'Y', 'bigint', 'N', 'screen_id', '', '', 'N', 'N', '20', 'Y'),
('dw_screen', 2, 'screen_name', 'N', 'varchar', 'N', 'screen_name', '', '', 'Y', 'N', '255', 'N'),
('dw_screen', 3, 'screen_config', 'N', 'varchar', 'N', 'screen_config', '', '', 'Y', 'N', '2000', 'N'),
('dw_screen', 4, 'modify_date', 'N', 'datetime', 'N', 'modify_date', '', '', 'Y', 'N', '20', 'N');

#2019-12-17;

INSERT INTO dw_rdbms_fields(TABLE_NAME, SEQ, FIELD_NAME, IS_KEY, FIELD_TYPE, STATUS_CODE, FIELD_DISPLAY_NAME, SCALE, DEFAULT_VALUE, NULLABLE, IS_VERSION, SIZE, IS_AUTO_INCREMENT) VALUES
('dw_screen_unit', 1, 'unit_id', 'Y', 'bigint', 'N', 'unit_id', '', '', 'N', 'N', '20', 'Y'),
('dw_screen_unit', 2, 'screen_id', 'Y', 'bigint', 'N', 'screen_id', '', '', 'N', 'N', '20', 'N'),
('dw_screen_unit', 3, 'unit_name', 'N', 'varchar', 'N', 'unit_name', '', '', 'Y', 'N', '255', 'N'),
('dw_screen_unit', 4, 'unit_config', 'N', 'varchar', 'N', 'unit_config', '', '', 'Y', 'N', '2000', 'N'),
('dw_screen_unit', 5, 'chain_id', 'N', 'bigint', 'N', 'chain_id', '', '', 'Y', 'N', '20', 'N');

#2019-12-17;

INSERT INTO dw_rdbms_fields(TABLE_NAME, SEQ, FIELD_NAME, IS_KEY, FIELD_TYPE, STATUS_CODE, FIELD_DISPLAY_NAME, SCALE, DEFAULT_VALUE, NULLABLE, IS_VERSION, SIZE, IS_AUTO_INCREMENT) VALUES
('dw_screen_extra', 1, 'screen_id', 'Y', 'bigint', 'N', 'screen_id', '', '', 'N', 'N', '20', 'N'),
('dw_screen_extra', 2, 'screen_default', 'N', 'char', 'N', 'screen_default', '', '', 'Y', 'N', '1', 'N');

#2019-12-17;

INSERT INTO dw_rdbms_relations(PRIMARY_TABLE_NO, REFERENCE_TABLE_NO, PRIMARY_TABLE_PK, REFERENCE_TABLE_PK) VALUES
('dw_screen', 'dw_screen_unit', 'screen_id', 'screen_id');

#2019-12-17;

INSERT INTO dw_rdbms_relations(PRIMARY_TABLE_NO, REFERENCE_TABLE_NO, PRIMARY_TABLE_PK, REFERENCE_TABLE_PK) VALUES
('dw_screen', 'dw_screen_extra', 'screen_id', 'screen_id');

#2019-12-17;

CREATE TABLE `dw_screen_preset` (
	`preset_config` TEXT NULL DEFAULT NULL COMMENT '配置' COLLATE 'utf8_bin'
)
COMMENT='大屏預設樣式'
COLLATE='utf8_bin'
ENGINE=InnoDB
ROW_FORMAT=DYNAMIC;

#2019-12-17;

INSERT INTO dw_rdbms_tables(table_name, table_display_name, table_description) VALUES
('dw_screen_preset', '大屏預設樣式', '大屏預設樣式');

#2019-12-17;

INSERT INTO dw_rdbms_fields(TABLE_NAME, SEQ, FIELD_NAME, IS_KEY, FIELD_TYPE, STATUS_CODE, FIELD_DISPLAY_NAME, SCALE, DEFAULT_VALUE, NULLABLE, IS_VERSION, SIZE, IS_AUTO_INCREMENT) VALUES
('dw_screen_preset', 1, 'preset_config', 'N', 'varchar', 'N', 'unit_config', '', '', 'Y', 'N', '2000', 'N');
