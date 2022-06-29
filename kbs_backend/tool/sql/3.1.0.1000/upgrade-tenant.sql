CREATE TABLE `dw_theme` (
  `tenantsid` bigint(20) NOT NULL COMMENT '租戶sid',
  `prop` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '屬性key名稱',
  `value` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '屬性值',
  `userid` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '用戶id',
  PRIMARY KEY (`tenantsid`,`prop`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `dw_rdbms_fields` ( `TABLE_NAME`, `SEQ`, `FIELD_NAME`, `FIELD_TYPE`, `FIELD_DISPLAY_NAME`, `SCALE`, `NULLABLE`, `SIZE`, `IS_KEY` ) VALUES ( 'dw_theme', '1', 'tenantsid', 'bigint', 'tenantsid', '0', 'N', '20', 'Y' );

INSERT INTO `dw_rdbms_fields` ( `TABLE_NAME`, `SEQ`, `FIELD_NAME`, `FIELD_TYPE`, `FIELD_DISPLAY_NAME`, `SCALE`, `NULLABLE`, `SIZE`, `IS_KEY` ) VALUES ( 'dw_theme', '2', 'prop', 'varchar', 'prop', '0', 'N', '50', 'Y' );

INSERT INTO `dw_rdbms_fields` ( `TABLE_NAME`, `SEQ`, `FIELD_NAME`, `FIELD_TYPE`, `FIELD_DISPLAY_NAME`, `SCALE`, `NULLABLE`, `SIZE`, `IS_KEY` ) VALUES ( 'dw_theme', '3', 'value', 'varchar', 'value', '0', 'Y', '500', 'N' );

INSERT INTO `dw_rdbms_fields` ( `TABLE_NAME`, `SEQ`, `FIELD_NAME`, `FIELD_TYPE`, `FIELD_DISPLAY_NAME`, `SCALE`, `NULLABLE`, `SIZE`, `IS_KEY` ) VALUES ( 'dw_theme', '4', 'userid', 'varchar', 'userid', '0', 'Y', '50', 'N' );

INSERT INTO `dw_rdbms_tables` ( `TABLE_NAME`, `TABLE_DISPLAY_NAME`, `TABLE_DESCRIPTION` ) VALUES ( 'dw_theme', '企業主題主表', '企業主題主表' );

CREATE TABLE `dw_theme_filelog` (
`fileid`  varchar(50) NOT NULL COMMENT '文件ID(DMC)' ,
`create_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '創建時間' ,
`messege`  varchar(500) NULL COMMENT '描述' ,
PRIMARY KEY (`fileid`)
);

INSERT INTO `dw_rdbms_fields` ( `TABLE_NAME`, `SEQ`, `FIELD_NAME`, `FIELD_TYPE`, `FIELD_DISPLAY_NAME`, `SCALE`, `NULLABLE`, `SIZE`, `IS_KEY` ) VALUES ( 'dw_theme_filelog', '1', 'fileid', 'varchar', 'fileid', '0', 'N', '50', 'Y' );

INSERT INTO `dw_rdbms_fields` ( `TABLE_NAME`, `SEQ`, `FIELD_NAME`, `FIELD_TYPE`, `FIELD_DISPLAY_NAME`, `SCALE`, `NULLABLE`, `SIZE`, `IS_KEY` ) VALUES ( 'dw_theme_filelog', '2', 'create_time', 'timestamp', 'create_time', '0', 'N', '0', 'N' );

INSERT INTO `dw_rdbms_fields` ( `TABLE_NAME`, `SEQ`, `FIELD_NAME`, `FIELD_TYPE`, `FIELD_DISPLAY_NAME`, `SCALE`, `NULLABLE`, `SIZE`, `IS_KEY` ) VALUES ( 'dw_theme_filelog', '3', 'messege', 'varchar', 'messege', '0', 'Y', '500', 'N' );

INSERT INTO `dw_rdbms_tables` ( `TABLE_NAME`, `TABLE_DISPLAY_NAME`, `TABLE_DESCRIPTION` ) VALUES ( 'dw_theme_filelog', '企業主題文件上傳日誌', '企業主題文件上傳日誌' );

CREATE TABLE `dw_custom_column` (
  `tenantsid` bigint(20) NOT NULL COMMENT '租戶sid',
  `table_id` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '表格id',
  `userid` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '用戶id',
  `config` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '配置內容',
  PRIMARY KEY (`tenantsid`,`table_id`,`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `dw_rdbms_fields` ( `TABLE_NAME`, `SEQ`, `FIELD_NAME`, `FIELD_TYPE`, `FIELD_DISPLAY_NAME`, `SCALE`, `NULLABLE`, `SIZE`, `IS_KEY` ) VALUES ( 'dw_custom_column', '1', 'tenantsid', 'bigint', 'tenantsid', '0', 'N', '20', 'Y' );

INSERT INTO `dw_rdbms_fields` ( `TABLE_NAME`, `SEQ`, `FIELD_NAME`, `FIELD_TYPE`, `FIELD_DISPLAY_NAME`, `SCALE`, `NULLABLE`, `SIZE`, `IS_KEY` ) VALUES ( 'dw_custom_column', '2', 'table_id', 'varchar', 'table_id', '0', 'N', '50', 'Y' );

INSERT INTO `dw_rdbms_fields` ( `TABLE_NAME`, `SEQ`, `FIELD_NAME`, `FIELD_TYPE`, `FIELD_DISPLAY_NAME`, `SCALE`, `NULLABLE`, `SIZE`, `IS_KEY` ) VALUES ( 'dw_custom_column', '3', 'userid', 'varchar', 'userid', '0', 'N', '50', 'Y' );

INSERT INTO `dw_rdbms_fields` ( `TABLE_NAME`, `SEQ`, `FIELD_NAME`, `FIELD_TYPE`, `FIELD_DISPLAY_NAME`, `SCALE`, `NULLABLE`, `SIZE`, `IS_KEY` ) VALUES ( 'dw_custom_column', '4', 'config', 'varchar', 'config', '0', 'Y', '500', 'N' );

INSERT INTO `dw_rdbms_tables` ( `TABLE_NAME`, `TABLE_DISPLAY_NAME`, `TABLE_DESCRIPTION` ) VALUES ( 'dw_custom_column', '個性化欄位表', '個性化欄位表' );