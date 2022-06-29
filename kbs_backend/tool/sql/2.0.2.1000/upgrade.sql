#2019-04-17;

ALTER TABLE dw_schedule_time CHANGE COLUMN frequency frequency INT(10) NULL DEFAULT NULL COMMENT '週期頻率' COLLATE 'utf8_bin';

#2019-04-17;

ALTER TABLE dw_schedule_time CHANGE COLUMN repeat_type repeat_type INT(10) NULL DEFAULT NULL COMMENT '區間類型  1.每幾分 2.每幾小時 3.每幾天' COLLATE 'utf8_bin';