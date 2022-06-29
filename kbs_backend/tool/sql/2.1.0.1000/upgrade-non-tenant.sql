CREATE TABLE dw_myhome (
  level VARCHAR(255) NOT NULL COMMENT '首頁層級,common/user',
  userid VARCHAR(40) NOT NULL COMMENT '用戶id',
  version INT(11) NOT NULL DEFAULT 1 COMMENT '版本',
  type VARCHAR(20) DEFAULT NULL COMMENT '首頁型態,作業/報表/外部連接',
  path VARCHAR(2000) DEFAULT NULL COMMENT '作業/報表/外部連接路徑',
  PRIMARY KEY (userid, level)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='自定義首頁主表'
;

CREATE TABLE dw_myhome_parameter (
  level VARCHAR(255) NOT NULL COMMENT '首頁層級,common/user',
  userid VARCHAR(100) NOT NULL COMMENT '用戶id',
  name VARCHAR(200) NOT NULL COMMENT '參數名稱',
  value VARCHAR(200) DEFAULT NULL COMMENT '參數值',
  PRIMARY KEY (userid, level, name)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='自定義首頁參數表'
;

INSERT INTO dw_rdbms_fields(TABLE_NAME, SEQ, FIELD_NAME, IS_KEY, FIELD_TYPE, STATUS_CODE, FIELD_DISPLAY_NAME, SCALE, DEFAULT_VALUE, NULLABLE, IS_VERSION, SIZE) VALUES
('dw_myhome', 1, 'userid', 'Y', 'varchar', 'N', 'userid', '', '', 'N', 'N', '40'),
('dw_myhome', 2, 'level', 'Y', 'varchar', 'N', 'level', '', '', 'N', 'N', '255'),
('dw_myhome', 3, 'type', 'N', 'varchar', 'N', 'type', '', '', 'Y', 'N', '20'),
('dw_myhome', 4, 'path', 'N', 'varchar', 'N', 'path', '', '', 'Y', 'N', '2000'),
('dw_myhome', 5, 'version', 'N', 'int', 'N', 'version', '', '', 'Y', 'Y', '11'),
('dw_myhome_parameter', 1, 'userid', 'Y', 'varchar', 'N', 'userid', '', '', 'N', 'N', '40'),
('dw_myhome_parameter', 2, 'level', 'Y', 'varchar', 'N', 'level', '', '', 'N', 'N', '255'),
('dw_myhome_parameter', 3, 'name', 'Y', 'varchar', 'N', 'name', '', '', 'N', 'N', '200'),
('dw_myhome_parameter', 4, 'value', 'N', 'varchar', 'N', 'value', '', '', 'Y', 'N', '200')
;

INSERT INTO dw_rdbms_relations(PRIMARY_TABLE_NO, REFERENCE_TABLE_NO, PRIMARY_TABLE_PK, REFERENCE_TABLE_PK) VALUES
('dw_myhome', 'dw_myhome_parameter', 'userid,level', 'userid,level')
;
