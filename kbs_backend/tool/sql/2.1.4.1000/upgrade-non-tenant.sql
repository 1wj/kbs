#2020-04-24;

INSERT INTO dw_rdbms_fields(`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `FIELD_TYPE`, `FIELD_DISPLAY_NAME`, `SCALE`, `NULLABLE`, `SIZE`) VALUES ('dw_schedule', '16', 'module_name', 'varchar', 'module_name', '0', 'Y', '50');

#2020-04-24;

INSERT INTO dw_rdbms_fields(`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `FIELD_TYPE`, `FIELD_DISPLAY_NAME`, `SCALE`, `NULLABLE`, `SIZE`) VALUES ('dw_schedule', '17', 'secretKey', 'varchar', 'secretKey', '0', 'Y', '200');

#2020-04-27;

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `FIELD_TYPE`, `FIELD_DISPLAY_NAME`, `SCALE`, `NULLABLE`, `SIZE`) VALUES ('dw_schedule_time', '30', 'exclude_date', 'varchar', 'exclude_date', '0', 'Y', '255');

#2020-04-27;

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `FIELD_TYPE`, `FIELD_DISPLAY_NAME`, `SCALE`, `NULLABLE`, `SIZE`) VALUES ('dw_schedule_time', '31', 'exclude_time', 'varchar', 'exclude_time', '0', 'Y', '255');

#2020-04-27;

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `IS_KEY`, `FIELD_TYPE`, `STATUS_CODE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `NULLABLE`, `IS_VERSION`, `SIZE`, `IS_AUTO_INCREMENT`) VALUES ('dw_schedule_time', 32, 'retry_after_misfire_sec', 'N', 'INT', 'Y', 'retry_after_misfire_sec', '0', '', 'Y', 'N', '10', 'N');

#2020-04-27;

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `FIELD_TYPE`, `FIELD_DISPLAY_NAME`, `SCALE`, `DEFAULT_VALUE`, `SIZE`) VALUES ('dw_schedule_record', '11', 'is_misfire', 'varchar', 'is_misfire', '0', 'N', '1');

#2020-04-27;

INSERT INTO `dw_rdbms_fields` (`TABLE_NAME`, `SEQ`, `FIELD_NAME`, `FIELD_TYPE`, `FIELD_DISPLAY_NAME`, `SCALE`, `SIZE`) VALUES ('dw_schedule_time', '33', 'is_retry', 'varchar', 'is_retry', '0', '1');