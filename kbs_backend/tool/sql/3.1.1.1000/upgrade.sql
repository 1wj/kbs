#2021-01-16;

ALTER TABLE `dw_custom_column`
MODIFY COLUMN `config`  text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT '配置內容' AFTER `userid`;