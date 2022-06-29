#2019-06-13;

ALTER TABLE dw_schedule CHANGE COLUMN schedule_param schedule_param text NULL COMMENT '參數';

INSERT INTO dw_rdbms_tables(table_name, table_display_name, table_description) VALUES
('dw_myhome', 'dw_myhome', '自定義首頁主表'),
('dw_myhome_parameter', 'dw_myhomedw_parameter', '自定義首頁參數表')
;
