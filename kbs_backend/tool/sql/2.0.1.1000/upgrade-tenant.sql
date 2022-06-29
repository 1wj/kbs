#2019-02-25;

UPDATE `dw_rdbms_fields` SET `FIELD_NAME` = 'tenantsid', `FIELD_DISPLAY_NAME` = 'tenantsid', `field_type` = 'bigint', `SIZE` = '20' WHERE `TABLE_NAME` LIKE 'dw_schedule%' AND `FIELD_NAME` = 'tenantid';

#2019-02-25;

ALTER TABLE dw_schedule CHANGE COLUMN tenantid tenantsid BIGINT (20) NOT NULL COMMENT '租戶sid';

#2019-02-25;

ALTER TABLE dw_schedule_contact CHANGE COLUMN tenantid tenantsid BIGINT (20) NOT NULL COMMENT '租戶sid';

#2019-02-25;

ALTER TABLE dw_schedule_record CHANGE COLUMN tenantid tenantsid BIGINT (20) NOT NULL COMMENT '租戶sid';

#2019-02-25;

ALTER TABLE dw_schedule_time CHANGE COLUMN tenantid tenantsid BIGINT (20) NOT NULL COMMENT '租戶sid';

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES ('dw_schedule', 16, 'program_code', 'N', 'varchar', 'Y', 'program_code', '0', '', 'Y', 'N', '50', 'N');

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES ('dw_schedule_record', 12, 'program_code', 'N', 'varchar', 'Y', 'program_code', '0', '', 'Y', 'N', '50', 'N');

INSERT INTO `dw_rdbms_fields` ( `TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT` ) VALUES ( 'dw_schedule_record', 13, 'schedule_name', 'N', 'varchar', 'Y', 'schedule_name', '0', '', 'Y', 'N', '50', 'N' );

INSERT INTO `dw_rdbms_fields` ( `TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT` ) VALUES ( 'dw_schedule_record', 14, 'executor', 'N', 'varchar', 'Y', 'executor', '0', '', 'Y', 'N', '50', 'N' );