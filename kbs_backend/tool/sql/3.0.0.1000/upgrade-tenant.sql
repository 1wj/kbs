#2020-07-31;

ALTER TABLE `dw_schedule` MODIFY COLUMN `tenantsid` BIGINT (20) NOT NULL COMMENT '租戶sid';

#2020-07-31;

ALTER TABLE `dw_schedule_contact` MODIFY COLUMN`tenantsid` BIGINT (20) NOT NULL COMMENT '租戶sid';

#2020-07-31;

ALTER TABLE `dw_schedule_record` MODIFY COLUMN `tenantsid` BIGINT (20) NOT NULL COMMENT '租戶sid';

#2020-07-31;

ALTER TABLE `dw_schedule_time` MODIFY COLUMN `tenantsid` BIGINT (20) NOT NULL COMMENT '租戶sid';