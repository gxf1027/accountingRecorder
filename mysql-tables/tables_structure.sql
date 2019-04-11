/*
Navicat MySQL Data Transfer

Source Server         : mysql_pc
Source Server Version : 50636
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50636
File Encoding         : 65001

Date: 2019-04-11 20:37:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for account_detail
-- ----------------------------
DROP TABLE IF EXISTS `account_detail`;
CREATE TABLE `account_detail` (
  `accuuid` varchar(255) NOT NULL,
  `rec_dm` varchar(10) NOT NULL,
  `user_id` int(11) NOT NULL,
  `je` float(11,2) NOT NULL,
  `shijian` datetime NOT NULL,
  `yxbz` varchar(255) NOT NULL,
  `xgrq` datetime DEFAULT NULL,
  `lrrq` datetime NOT NULL,
  PRIMARY KEY (`accuuid`,`user_id`),
  KEY `userid_index` (`user_id`) USING HASH,
  KEY `acc_recdm_index` (`rec_dm`) USING HASH,
  KEY `acc_userid_recdm_index` (`user_id`,`rec_dm`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1
/*!50100 PARTITION BY RANGE (user_id)
(PARTITION p0 VALUES LESS THAN (5000) ENGINE = InnoDB,
 PARTITION p1 VALUES LESS THAN (10000) ENGINE = InnoDB,
 PARTITION p2 VALUES LESS THAN (15000) ENGINE = InnoDB,
 PARTITION p3 VALUES LESS THAN MAXVALUE ENGINE = InnoDB) */;

-- ----------------------------
-- Table structure for account_detail_test
-- ----------------------------
DROP TABLE IF EXISTS `account_detail_test`;
CREATE TABLE `account_detail_test` (
  `accuuid` varchar(255) NOT NULL,
  `rec_dm` varchar(10) NOT NULL,
  `user_id` int(11) NOT NULL,
  `je` float(11,2) NOT NULL,
  `shijian` datetime NOT NULL,
  `yxbz` varchar(255) NOT NULL,
  `xgrq` datetime DEFAULT NULL,
  `lrrq` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1
/*!50100 PARTITION BY RANGE (user_id)
(PARTITION p0 VALUES LESS THAN (2000) ENGINE = InnoDB,
 PARTITION p1 VALUES LESS THAN (4000) ENGINE = InnoDB,
 PARTITION p2 VALUES LESS THAN (6000) ENGINE = InnoDB,
 PARTITION p3 VALUES LESS THAN (8000) ENGINE = InnoDB,
 PARTITION p4 VALUES LESS THAN (10000) ENGINE = InnoDB,
 PARTITION p5 VALUES LESS THAN (12000) ENGINE = InnoDB,
 PARTITION p9 VALUES LESS THAN MAXVALUE ENGINE = InnoDB) */;

-- ----------------------------
-- Table structure for account_income_detail
-- ----------------------------
DROP TABLE IF EXISTS `account_income_detail`;
CREATE TABLE `account_income_detail` (
  `mxuuid` varchar(255) NOT NULL,
  `accuuid` varchar(255) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `je` float(11,2) NOT NULL,
  `lb_dm` varchar(255) NOT NULL,
  `fkfmc` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `zh_dm` varchar(255) DEFAULT NULL,
  `shijian` datetime NOT NULL,
  `bz` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `yxbz` varchar(255) DEFAULT NULL,
  `xgrq` datetime DEFAULT NULL,
  `lrrq` datetime NOT NULL,
  PRIMARY KEY (`mxuuid`,`user_id`),
  KEY `index_income_lb` (`lb_dm`) USING BTREE,
  KEY `index_income_zhdm` (`zh_dm`) USING BTREE,
  KEY `index_income_zhdm_userid` (`user_id`,`zh_dm`) USING HASH,
  KEY `index_income_lb_userid` (`user_id`,`lb_dm`) USING BTREE,
  KEY `index_income_userid` (`user_id`) USING BTREE,
  KEY `index_income_accuuid` (`accuuid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1
/*!50100 PARTITION BY RANGE (user_id)
(PARTITION p0 VALUES LESS THAN (5000) ENGINE = InnoDB,
 PARTITION p1 VALUES LESS THAN (10000) ENGINE = InnoDB,
 PARTITION p2 VALUES LESS THAN (15000) ENGINE = InnoDB,
 PARTITION p3 VALUES LESS THAN MAXVALUE ENGINE = InnoDB) */;

-- ----------------------------
-- Table structure for account_info
-- ----------------------------
DROP TABLE IF EXISTS `account_info`;
CREATE TABLE `account_info` (
  `accuuid` varchar(255) NOT NULL,
  `ssny` varchar(6) DEFAULT NULL,
  `income` float DEFAULT NULL,
  `salary` float DEFAULT NULL,
  `bonus` float DEFAULT NULL,
  `expenditure` float DEFAULT NULL,
  `netincome` float DEFAULT NULL,
  `xgrq` date DEFAULT NULL,
  PRIMARY KEY (`accuuid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for account_payment_detail
-- ----------------------------
DROP TABLE IF EXISTS `account_payment_detail`;
CREATE TABLE `account_payment_detail` (
  `mxuuid` varchar(255) NOT NULL,
  `accuuid` varchar(255) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `yhje` float DEFAULT NULL,
  `je` float(11,2) NOT NULL,
  `dl_dm` varchar(255) NOT NULL,
  `xl_dm` varchar(255) NOT NULL,
  `seller` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `zh_dm` varchar(255) DEFAULT NULL,
  `shijian` datetime NOT NULL,
  `category_dm` varchar(5) DEFAULT NULL,
  `bz` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `yxbz` varchar(255) DEFAULT NULL,
  `xgrq` datetime DEFAULT NULL,
  `lrrq` datetime NOT NULL,
  PRIMARY KEY (`mxuuid`,`user_id`),
  KEY `index_zhdm_userid` (`user_id`,`zh_dm`) USING HASH,
  KEY `index_pay_dl` (`dl_dm`) USING BTREE,
  KEY `index_pay_xl` (`xl_dm`) USING BTREE,
  KEY `indes_pay_zhdm` (`zh_dm`) USING BTREE,
  KEY `index_pay_dl_xl_userid` (`user_id`,`dl_dm`,`xl_dm`) USING BTREE,
  KEY `index_pay_userid` (`user_id`) USING HASH,
  KEY `index_pay_accuuid` (`accuuid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1
/*!50100 PARTITION BY RANGE (user_id)
(PARTITION p0 VALUES LESS THAN (5000) ENGINE = InnoDB,
 PARTITION p1 VALUES LESS THAN (10000) ENGINE = InnoDB,
 PARTITION p2 VALUES LESS THAN (15000) ENGINE = InnoDB,
 PARTITION p3 VALUES LESS THAN MAXVALUE ENGINE = InnoDB) */;

-- ----------------------------
-- Table structure for account_snapshot
-- ----------------------------
DROP TABLE IF EXISTS `account_snapshot`;
CREATE TABLE `account_snapshot` (
  `uuid` varchar(255) NOT NULL,
  `accuuid` varchar(255) NOT NULL,
  `type` varchar(10) NOT NULL,
  `user_id` int(11) NOT NULL,
  `zh_dm` varchar(255) NOT NULL,
  `bdje` float(11,2) NOT NULL,
  `fshje` float(11,2) NOT NULL,
  `lrrq` datetime NOT NULL,
  PRIMARY KEY (`uuid`,`user_id`),
  KEY `index_snap_userid` (`user_id`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for account_transfer_detail
-- ----------------------------
DROP TABLE IF EXISTS `account_transfer_detail`;
CREATE TABLE `account_transfer_detail` (
  `mxuuid` varchar(255) NOT NULL,
  `accuuid` varchar(255) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `je` float(11,2) NOT NULL,
  `srcZh_dm` varchar(255) NOT NULL,
  `tgtZh_dm` varchar(255) NOT NULL,
  `zzlx_dm` varchar(5) DEFAULT NULL,
  `shijian` datetime NOT NULL,
  `bz` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `yxbz` varchar(255) DEFAULT NULL,
  `xgrq` datetime DEFAULT NULL,
  `lrrq` datetime NOT NULL,
  PRIMARY KEY (`mxuuid`),
  KEY `index_transfer_zzlx` (`zzlx_dm`) USING BTREE,
  KEY `index_trans_userid` (`user_id`) USING BTREE,
  KEY `index_trans_userid_zzlx` (`user_id`,`zzlx_dm`) USING BTREE,
  KEY `index_trans_accuuid` (`accuuid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for customers
-- ----------------------------
DROP TABLE IF EXISTS `customers`;
CREATE TABLE `customers` (
  `customerid` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `age` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for cxf_authentication
-- ----------------------------
DROP TABLE IF EXISTS `cxf_authentication`;
CREATE TABLE `cxf_authentication` (
  `username` varchar(128) NOT NULL,
  `confidential_code` varchar(256) NOT NULL,
  `lrrq` datetime DEFAULT NULL,
  `yxbz` varchar(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for cxf_interface
-- ----------------------------
DROP TABLE IF EXISTS `cxf_interface`;
CREATE TABLE `cxf_interface` (
  `uuid` varchar(128) NOT NULL,
  `cxf_interface_name` varchar(255) NOT NULL,
  `cxf_method_name` varchar(255) NOT NULL,
  `readyonly` varchar(2) NOT NULL,
  `comments` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `yxbz` varchar(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for cxf_interface_control
-- ----------------------------
DROP TABLE IF EXISTS `cxf_interface_control`;
CREATE TABLE `cxf_interface_control` (
  `cxf_username` varchar(128) NOT NULL,
  `cxf_interface_uuid` varchar(255) NOT NULL,
  `lrrq` datetime DEFAULT NULL,
  `yxbz` varchar(2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for cxf_ip_blocked
-- ----------------------------
DROP TABLE IF EXISTS `cxf_ip_blocked`;
CREATE TABLE `cxf_ip_blocked` (
  `ip` varchar(20) NOT NULL,
  `lrrq` datetime DEFAULT NULL,
  PRIMARY KEY (`ip`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for cxf_ip_blocked_tmp
-- ----------------------------
DROP TABLE IF EXISTS `cxf_ip_blocked_tmp`;
CREATE TABLE `cxf_ip_blocked_tmp` (
  `ip` varchar(20) NOT NULL,
  `lrrq` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for dm_dl
-- ----------------------------
DROP TABLE IF EXISTS `dm_dl`;
CREATE TABLE `dm_dl` (
  `dl_dm` varchar(255) CHARACTER SET utf8 NOT NULL,
  `dl_mc` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `yxbz` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `xybz` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`dl_dm`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for dm_financial_product
-- ----------------------------
DROP TABLE IF EXISTS `dm_financial_product`;
CREATE TABLE `dm_financial_product` (
  `fin_prod_dm` varchar(255) NOT NULL,
  `fin_prod_mc` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `yxbz` varchar(2) DEFAULT NULL,
  `xybz` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`fin_prod_dm`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for dm_fund_type
-- ----------------------------
DROP TABLE IF EXISTS `dm_fund_type`;
CREATE TABLE `dm_fund_type` (
  `fund_type_dm` varchar(255) NOT NULL,
  `fund_type_mc` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `yxbz` varchar(2) DEFAULT NULL,
  `xybz` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`fund_type_dm`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for dm_outgo_category
-- ----------------------------
DROP TABLE IF EXISTS `dm_outgo_category`;
CREATE TABLE `dm_outgo_category` (
  `outgo_category_dm` varchar(5) NOT NULL,
  `outgo_category_mc` varchar(100) CHARACTER SET utf8 NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `yxbz` varchar(2) DEFAULT NULL,
  `xybz` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`outgo_category_dm`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for dm_province
-- ----------------------------
DROP TABLE IF EXISTS `dm_province`;
CREATE TABLE `dm_province` (
  `province_dm` varchar(4) CHARACTER SET utf8 NOT NULL,
  `province_mc` varchar(20) CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for dm_record
-- ----------------------------
DROP TABLE IF EXISTS `dm_record`;
CREATE TABLE `dm_record` (
  `rec_dm` varchar(10) NOT NULL,
  `rec_mc` varchar(20) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`rec_dm`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for dm_srlb
-- ----------------------------
DROP TABLE IF EXISTS `dm_srlb`;
CREATE TABLE `dm_srlb` (
  `srlb_dm` varchar(255) CHARACTER SET utf8 NOT NULL,
  `srlb_mc` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `yxbz` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `xybz` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`srlb_dm`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for dm_xl
-- ----------------------------
DROP TABLE IF EXISTS `dm_xl`;
CREATE TABLE `dm_xl` (
  `xl_dm` varchar(255) CHARACTER SET utf8 NOT NULL,
  `xl_mc` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `dl_dm` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `yxbz` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `xybz` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`xl_dm`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for dm_yh
-- ----------------------------
DROP TABLE IF EXISTS `dm_yh`;
CREATE TABLE `dm_yh` (
  `yh_dm` varchar(255) NOT NULL,
  `yh_mc` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `yxbz` varchar(2) DEFAULT NULL,
  `xybz` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`yh_dm`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for dm_zhanghu
-- ----------------------------
DROP TABLE IF EXISTS `dm_zhanghu`;
CREATE TABLE `dm_zhanghu` (
  `zhanghu_dm` varchar(255) CHARACTER SET utf8 NOT NULL,
  `zhanghu_mc` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `yxbz` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `xybz` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`zhanghu_dm`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for dm_zhlx
-- ----------------------------
DROP TABLE IF EXISTS `dm_zhlx`;
CREATE TABLE `dm_zhlx` (
  `zhlx_dm` varchar(10) CHARACTER SET utf8 NOT NULL,
  `zhlx_mc` varchar(50) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`zhlx_dm`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for dm_zzlx
-- ----------------------------
DROP TABLE IF EXISTS `dm_zzlx`;
CREATE TABLE `dm_zzlx` (
  `zzlx_dm` varchar(5) NOT NULL,
  `zzlx_mc` varchar(100) CHARACTER SET utf8 NOT NULL,
  `user_id` int(11) DEFAULT NULL COMMENT '转账类型代码',
  `yxbz` varchar(2) NOT NULL,
  `xybz` varchar(2) NOT NULL,
  PRIMARY KEY (`zzlx_dm`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for financial_product_detail
-- ----------------------------
DROP TABLE IF EXISTS `financial_product_detail`;
CREATE TABLE `financial_product_detail` (
  `uuid` varchar(255) NOT NULL,
  `transferuuid` varchar(255) DEFAULT NULL COMMENT '购买理财产品的转账uuid',
  `redeemuuid` varchar(255) DEFAULT NULL COMMENT '赎回理财产品的转账uuid',
  `returnuuid` varchar(255) DEFAULT NULL COMMENT '理财产品收益的收入uuid',
  `product_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `product_type` varchar(12) DEFAULT NULL,
  `yh_dm` varchar(255) DEFAULT NULL,
  `date_count` int(11) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `expected_returnrate` float(11,6) DEFAULT NULL,
  `netv_purchase` float(11,6) DEFAULT NULL,
  `netv_selling` float(11,6) DEFAULT NULL,
  `je` float(11,2) DEFAULT NULL,
  `real_return` float(11,2) DEFAULT NULL,
  `is_redeem` varchar(2) DEFAULT NULL,
  `lrrq` datetime DEFAULT NULL,
  `xgrq` datetime DEFAULT NULL,
  `yxbz` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  KEY `indx_fp_type` (`product_type`) USING HASH,
  KEY `indx_fp_enddate` (`end_date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for financial_product_notice
-- ----------------------------
DROP TABLE IF EXISTS `financial_product_notice`;
CREATE TABLE `financial_product_notice` (
  `uuid` varchar(255) NOT NULL,
  `pch` varchar(255) NOT NULL,
  `user_id` int(11) NOT NULL,
  `emailaddr` varchar(255) DEFAULT NULL,
  `ssqq` varchar(12) DEFAULT NULL,
  `ssqz` varchar(12) DEFAULT NULL,
  `mailedrq` datetime DEFAULT NULL,
  `lrrq` datetime DEFAULT NULL,
  `yxbz` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for onlinestat
-- ----------------------------
DROP TABLE IF EXISTS `onlinestat`;
CREATE TABLE `onlinestat` (
  `sessionid` varchar(200) DEFAULT NULL,
  `user` varchar(20) DEFAULT NULL,
  `hostip` varchar(20) DEFAULT NULL,
  `page` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for persistent_logins
-- ----------------------------
DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE `persistent_logins` (
  `username` varchar(255) NOT NULL,
  `series` varchar(255) NOT NULL,
  `token` varchar(255) NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(200) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for role_ss
-- ----------------------------
DROP TABLE IF EXISTS `role_ss`;
CREATE TABLE `role_ss` (
  `id` int(11) NOT NULL DEFAULT '0' COMMENT 'id',
  `role_name` varchar(50) DEFAULT NULL COMMENT 'name',
  `description` varchar(50) DEFAULT NULL COMMENT 'description',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Table structure for rpc_authentication
-- ----------------------------
DROP TABLE IF EXISTS `rpc_authentication`;
CREATE TABLE `rpc_authentication` (
  `username` varchar(128) NOT NULL,
  `confidential_code` varchar(256) NOT NULL,
  `lrrq` datetime DEFAULT NULL,
  `yxbz` varchar(2) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for rpc_interface
-- ----------------------------
DROP TABLE IF EXISTS `rpc_interface`;
CREATE TABLE `rpc_interface` (
  `uuid` varchar(128) NOT NULL,
  `rpc_interface_name` varchar(255) NOT NULL,
  `rp_method_name` varchar(255) NOT NULL,
  `readyonly` varchar(2) NOT NULL,
  `comments` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `yxbz` varchar(2) NOT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for rpc_interface_control
-- ----------------------------
DROP TABLE IF EXISTS `rpc_interface_control`;
CREATE TABLE `rpc_interface_control` (
  `rpc_username` varchar(128) NOT NULL,
  `rpc_interface_uuid` varchar(255) NOT NULL,
  `lrrq` datetime DEFAULT NULL,
  `yxbz` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`rpc_username`,`rpc_interface_uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for rpc_requests_log
-- ----------------------------
DROP TABLE IF EXISTS `rpc_requests_log`;
CREATE TABLE `rpc_requests_log` (
  `uuid` varchar(128) NOT NULL,
  `req_host` varchar(32) NOT NULL,
  `req_user` varchar(255) DEFAULT NULL,
  `rpc_interface_name` varchar(128) NOT NULL,
  `rpc_method` varchar(255) DEFAULT NULL,
  `rpc_params` varchar(512) CHARACTER SET utf8 DEFAULT NULL,
  `req_time` datetime(3) DEFAULT NULL,
  `ret_time` datetime(3) DEFAULT NULL,
  `denied` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  KEY `uuid_index` (`uuid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for spring_session
-- ----------------------------
DROP TABLE IF EXISTS `spring_session`;
CREATE TABLE `spring_session` (
  `SESSION_ID` char(36) NOT NULL DEFAULT '',
  `CREATION_TIME` bigint(20) NOT NULL,
  `LAST_ACCESS_TIME` bigint(20) NOT NULL,
  `MAX_INACTIVE_INTERVAL` int(11) NOT NULL,
  `PRINCIPAL_NAME` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`SESSION_ID`),
  KEY `SPRING_SESSION_IX1` (`LAST_ACCESS_TIME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for spring_session_attributes
-- ----------------------------
DROP TABLE IF EXISTS `spring_session_attributes`;
CREATE TABLE `spring_session_attributes` (
  `SESSION_ID` char(36) NOT NULL DEFAULT '',
  `ATTRIBUTE_NAME` varchar(200) NOT NULL DEFAULT '',
  `ATTRIBUTE_BYTES` blob,
  PRIMARY KEY (`SESSION_ID`,`ATTRIBUTE_NAME`),
  KEY `SPRING_SESSION_ATTRIBUTES_IX1` (`SESSION_ID`),
  CONSTRAINT `SPRING_SESSION_ATTRIBUTES_FK` FOREIGN KEY (`SESSION_ID`) REFERENCES `spring_session` (`SESSION_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for stat_income_lb
-- ----------------------------
DROP TABLE IF EXISTS `stat_income_lb`;
CREATE TABLE `stat_income_lb` (
  `srlb_dm` varchar(255) CHARACTER SET utf8 NOT NULL,
  `srlb_mc` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `je` float(11,2) DEFAULT NULL,
  `nd` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `proc_time` datetime DEFAULT NULL,
  KEY `statincome_lbnduser_index` (`user_id`,`nd`,`srlb_dm`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for stat_income_nd
-- ----------------------------
DROP TABLE IF EXISTS `stat_income_nd`;
CREATE TABLE `stat_income_nd` (
  `nd` varchar(4) DEFAULT NULL,
  `yf` varchar(2) DEFAULT NULL,
  `je` float(11,2) DEFAULT NULL,
  `je_salary` float(11,2) DEFAULT NULL,
  `je_finproduct` float(11,2) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `proc_time` datetime DEFAULT NULL,
  KEY `statincome_ndyfuser_index` (`user_id`,`nd`,`yf`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for stat_payment_dl
-- ----------------------------
DROP TABLE IF EXISTS `stat_payment_dl`;
CREATE TABLE `stat_payment_dl` (
  `dl_dm` varchar(255) CHARACTER SET utf8 NOT NULL,
  `dl_mc` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `je` float(11,2) DEFAULT NULL,
  `nd` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `proc_time` datetime DEFAULT NULL,
  KEY `statpay_usernddl_index` (`user_id`,`nd`,`dl_dm`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for stat_payment_nd
-- ----------------------------
DROP TABLE IF EXISTS `stat_payment_nd`;
CREATE TABLE `stat_payment_nd` (
  `nd` varchar(4) DEFAULT NULL,
  `yf` varchar(2) DEFAULT NULL,
  `je` float(11,2) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `proc_time` datetime DEFAULT NULL,
  KEY `statpay_userndyf_index` (`user_id`,`nd`,`yf`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for testmqxa
-- ----------------------------
DROP TABLE IF EXISTS `testmqxa`;
CREATE TABLE `testmqxa` (
  `uuid` varchar(255) DEFAULT NULL,
  `lrrq` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for test_tab_1
-- ----------------------------
DROP TABLE IF EXISTS `test_tab_1`;
CREATE TABLE `test_tab_1` (
  `price` float DEFAULT NULL,
  `lrrq` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for timedtask_acc_stat
-- ----------------------------
DROP TABLE IF EXISTS `timedtask_acc_stat`;
CREATE TABLE `timedtask_acc_stat` (
  `userid` int(11) NOT NULL,
  `proctime` datetime NOT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for transfer_fund_detail
-- ----------------------------
DROP TABLE IF EXISTS `transfer_fund_detail`;
CREATE TABLE `transfer_fund_detail` (
  `uuid` varchar(127) NOT NULL,
  `transferuuid` varchar(255) NOT NULL,
  `fund_code` varchar(15) DEFAULT NULL,
  `fund_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `fund_type` varchar(10) DEFAULT NULL,
  `unit_net` float(11,6) DEFAULT NULL,
  `extra_fee` float(11,2) DEFAULT NULL,
  `confirmed_sum` float(11,2) DEFAULT NULL,
  `lrrq` datetime DEFAULT NULL,
  `xgrq` datetime DEFAULT NULL,
  `yxbz` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  KEY `fund_transuuid_index` (`transferuuid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` varchar(10) DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  `addr` varchar(400) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `birthdate` date DEFAULT NULL,
  `province_dm` varchar(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for user_login_info
-- ----------------------------
DROP TABLE IF EXISTS `user_login_info`;
CREATE TABLE `user_login_info` (
  `user_id` int(11) NOT NULL,
  `last_login_time` datetime DEFAULT NULL,
  `last_login_ip` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for user_prepared_stat
-- ----------------------------
DROP TABLE IF EXISTS `user_prepared_stat`;
CREATE TABLE `user_prepared_stat` (
  `user_id` int(11) NOT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for user_role_dzb
-- ----------------------------
DROP TABLE IF EXISTS `user_role_dzb`;
CREATE TABLE `user_role_dzb` (
  `user_id` int(11) DEFAULT NULL COMMENT '用户表_id',
  `role_id` int(11) DEFAULT NULL COMMENT '角色表_id',
  KEY `urdzb_userid_indx` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- ----------------------------
-- Table structure for user_ss
-- ----------------------------
DROP TABLE IF EXISTS `user_ss`;
CREATE TABLE `user_ss` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(50) NOT NULL COMMENT 'username',
  `password` varchar(200) NOT NULL COMMENT 'password',
  `email` varchar(255) DEFAULT NULL,
  `enabled` varchar(1024) NOT NULL COMMENT 'enabled',
  `description` varchar(1024) DEFAULT NULL COMMENT 'description',
  `attempt_limit` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_uname` (`username`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=101001 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Table structure for user_ss_backup
-- ----------------------------
DROP TABLE IF EXISTS `user_ss_backup`;
CREATE TABLE `user_ss_backup` (
  `id` int(11) NOT NULL DEFAULT '0' COMMENT 'id',
  `username` varchar(50) CHARACTER SET utf8 NOT NULL COMMENT 'username',
  `password` varchar(200) CHARACTER SET utf8 NOT NULL COMMENT 'password',
  `email` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `enabled` varchar(1024) CHARACTER SET utf8 NOT NULL COMMENT 'enabled',
  `description` varchar(1024) CHARACTER SET utf8 DEFAULT NULL COMMENT 'description',
  `attempt_limit` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for zh_detail_ccbill
-- ----------------------------
DROP TABLE IF EXISTS `zh_detail_ccbill`;
CREATE TABLE `zh_detail_ccbill` (
  `pch` varchar(255) NOT NULL,
  `user_id` int(5) NOT NULL,
  `username` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `emailaddr` varchar(255) DEFAULT NULL,
  `zh_dm` varchar(255) DEFAULT NULL,
  `zh_mc` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `ssqq` date DEFAULT NULL,
  `ssqz` date DEFAULT NULL,
  `yhkje` float(11,2) DEFAULT NULL,
  `mailedrq` datetime DEFAULT NULL,
  `lrrq` datetime DEFAULT NULL,
  `yxbz` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`pch`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for zh_detail_ccbill_consumer
-- ----------------------------
DROP TABLE IF EXISTS `zh_detail_ccbill_consumer`;
CREATE TABLE `zh_detail_ccbill_consumer` (
  `pch` varchar(255) NOT NULL,
  `thread_id` varchar(128) DEFAULT NULL,
  `rev_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `send_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `yxbz` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`pch`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for zh_detail_ccbill_mx
-- ----------------------------
DROP TABLE IF EXISTS `zh_detail_ccbill_mx`;
CREATE TABLE `zh_detail_ccbill_mx` (
  `billuuid` varchar(255) NOT NULL,
  `pch` varchar(255) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `bill_ssqq` date NOT NULL,
  `bill_ssqz` date NOT NULL,
  `zh_dm` varchar(10) NOT NULL,
  `zh_mc` varchar(100) CHARACTER SET utf8 NOT NULL,
  `jysj` datetime NOT NULL,
  `jylx` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `jyf` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `je` float(11,2) NOT NULL,
  `bz` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `yxbz` varchar(2) DEFAULT NULL,
  `lrrq` datetime DEFAULT NULL,
  `xgrq` datetime DEFAULT NULL,
  `mailed` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`billuuid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for zh_detail_creditcard
-- ----------------------------
DROP TABLE IF EXISTS `zh_detail_creditcard`;
CREATE TABLE `zh_detail_creditcard` (
  `zh_dm` varchar(10) NOT NULL,
  `hkr` int(11) DEFAULT NULL COMMENT '还款日',
  `zdr` int(11) DEFAULT NULL COMMENT '账单日',
  `yxbz` varchar(2) NOT NULL,
  `xgrq` datetime DEFAULT NULL,
  PRIMARY KEY (`zh_dm`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for zh_detail_info
-- ----------------------------
DROP TABLE IF EXISTS `zh_detail_info`;
CREATE TABLE `zh_detail_info` (
  `zh_dm` varchar(20) CHARACTER SET utf8 NOT NULL,
  `zh_mc` varchar(100) CHARACTER SET utf8 NOT NULL,
  `khrmc` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `zhlx_dm` varchar(10) CHARACTER SET utf8 DEFAULT NULL,
  `zhhm` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `ye` float(11,2) NOT NULL,
  `yxbz` varchar(10) CHARACTER SET utf8 DEFAULT NULL,
  `xybz` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`zh_dm`),
  KEY `zhinfo_userid_index` (`user_id`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Procedure structure for generate_test_data
-- ----------------------------
DROP PROCEDURE IF EXISTS `generate_test_data`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `generate_test_data`(loop_times INT)
BEGIN  
    DECLARE iter INT DEFAULT 0;    
    WHILE iter<loop_times DO    
    SET iter=iter+1;    
    INSERT INTO test.user_ss (id,username,password,email,enabled,description,attempt_limit)   
    VALUES (iter+1000, CONCAT('test',UUID_SHORT()),'21232f297a57a5a743894a0e4a801fc3',null,'1',null,3);    
    END WHILE; 
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for proc_acc_stat_by_month
-- ----------------------------
DROP PROCEDURE IF EXISTS `proc_acc_stat_by_month`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_acc_stat_by_month`(IN `user_id` int(11), IN `nd_str` varchar(10), IN `yf_str` varchar(2))
BEGIN
	

	DECLARE tab_str varchar(20) ;
	DECLARE date_from datetime;
	DECLARE date_to datetime;
	DECLARE first_date_of_year datetime;
	DECLARE last_date_of_year datetime;
	
	SELECT table_name into tab_str FROM information_schema.TABLES WHERE table_name ='stat_payment_nd';
	SELECT STR_TO_DATE(CONCAT(nd_str,'-',yf_str,'-01'),'%Y-%m-%d %H:%i:%s') into date_from;
	SELECT date_add(date_from, interval 1 MONTH)  into date_to;
	SELECT STR_TO_DATE(CONCAT(nd_str,'-01-01'), '%Y-%m-%d %H:%i:%s') into first_date_of_year;
	SELECT STR_TO_DATE(CONCAT(nd_str,'-12-31 23:59:59'), '%Y-%m-%d %H:%i:%s') into last_date_of_year;
	
  IF tab_str is null THEN
		create table stat_payment_nd(
			nd varchar(4),
			yf varchar(2),
			je float,
			user_id  int(11) NULL DEFAULT NULL ,
			proc_time  datetime NULL DEFAULT NULL 
		);
		
  ELSE
		DELETE t FROM stat_payment_nd t WHERE t.nd = nd_str AND t.yf = yf_str AND t.user_id = user_id;
  END IF;

	INSERT INTO stat_payment_nd 
  SELECT nd_str, yf_str, TMP.je, user_id, NOW() FROM
	(SELECT
			ROUND(sum(t.je), 2) je
		FROM
			test.account_payment_detail t,
			test.account_detail d
		WHERE
			t.accuuid = d.accuuid
		AND d.yxbz = 'Y'
		AND t.yxbz = 'Y'
		AND t.shijian >= date_from
		AND t.shijian < date_to
		AND t.user_id = user_id) TMP
    WHERE TMP.je IS NOT NULL;
		

	# income stat
	SELECT table_name into tab_str FROM information_schema.TABLES WHERE table_name ='stat_income_nd';

	IF tab_str is null THEN
		create table stat_income_nd(
			nd varchar(4),
			yf varchar(2),
			je float,
			user_id  int(11) NULL DEFAULT NULL ,
			proc_time  datetime NULL DEFAULT NULL 
		);
		
  ELSE
		DELETE t FROM stat_income_nd t  WHERE t.nd = nd_str AND t.yf = yf_str AND t.user_id = user_id;
  END IF;

	INSERT INTO stat_income_nd
	SELECT nd_str, yf_str, je, je_salary, je_finproduct, user_id, NOW() FROM (
  SELECT
			ROUND(sum(t.je), 2) je,
     ROUND(sum(CASE t.lb_dm 
				WHEN  '2002' 	THEN t.je
				WHEN '2003' 	THEN t.je
				WHEN '2004' 	THEN t.je
        WHEN '2014'   THEN t.je
			ELSE 0 END), 2) je_salary,
		ROUND(sum(CASE t.lb_dm 
				WHEN  '2001' 	THEN t.je
				WHEN '2009' 	THEN t.je
			ELSE 0 END), 2) je_finproduct
		FROM
			test.account_income_detail t,
			test.account_detail d
		WHERE
			t.accuuid = d.accuuid
		AND d.yxbz = 'Y'
		AND t.yxbz = 'Y'
		AND t.shijian >= date_from 
		AND t.shijian < date_to
		AND t.user_id = user_id) TMP
WHERE TMP.je IS NOT NULL;

	# payment stat by dl_dm
	SELECT table_name into tab_str FROM information_schema.TABLES WHERE table_name ='stat_payment_dl';
	IF tab_str is null THEN
		CREATE TABLE stat_payment_dl (
			dl_dm  varchar(255) not null ,
			dl_mc  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
			je  float NULL DEFAULT NULL ,
			nd  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
			user_id  int(11) NULL DEFAULT NULL ,
			proc_time  datetime NULL DEFAULT NULL 
		);
  ELSE
		DELETE t FROM stat_payment_dl t WHERE t.nd = nd_str AND t.user_id = user_id;
  END IF;

  INSERT INTO stat_payment_dl
	SELECT p.dl_dm dl_dm, 
		(select dl_mc from dm_dl d where d.dl_dm = p.dl_dm) dl_mc,
		SUM(p.je) je,
		DATE_FORMAT(p.shijian, '%Y'),
		p.user_id as user_id,
		NOW() proc_time
  FROM account_payment_detail p
  WHERE p.shijian >= first_date_of_year
	AND p.shijian <= last_date_of_year
	AND p.user_id = user_id
	AND p.yxbz='Y'
  GROUP BY p.dl_dm;

	# income stat by srlb_dm
	SELECT table_name into tab_str FROM information_schema.TABLES WHERE table_name ='stat_income_lb';
	IF tab_str is null THEN
		CREATE TABLE stat_income_lb (
			srlb_dm  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
			srlb_mc  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
			je  float NULL DEFAULT NULL ,
			nd  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
			user_id  int(11) NULL DEFAULT NULL ,
			proc_time  datetime NULL DEFAULT NULL 
		);
  ELSE
		DELETE t FROM stat_income_lb t WHERE t.nd = nd_str and t.user_id = user_id;
  END IF;

  INSERT INTO stat_income_lb
	SELECT t.lb_dm, 
		(select b.srlb_mc from dm_srlb b where b.srlb_dm = t.lb_dm) lb_mc,
		SUM(t.je) je, 
		DATE_FORMAT(t.shijian, '%Y'),
		t.user_id as user_id,
		NOW() proc_time
	FROM account_income_detail t
	WHERE t.shijian >= first_date_of_year
	AND t.shijian <= last_date_of_year
	AND t.user_id = user_id
  AND t.yxbz = 'Y'
  GROUP BY t.lb_dm;
END
;;
DELIMITER ;


-- ----------------------------
-- Procedure structure for proc_acc_stat_nd
-- ----------------------------
DROP PROCEDURE IF EXISTS `proc_acc_stat_nd`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_acc_stat_nd`(IN `nd_str` varchar(20), IN `user_id` int(11))
BEGIN
	#Routine body goes here...

	DECLARE tab_str varchar(20) ;
	DECLARE first_date_of_year datetime;
	DECLARE last_date_of_year datetime;

	#payment stat
	SELECT table_name into tab_str FROM information_schema.TABLES WHERE table_name ='stat_payment_nd';

	SELECT STR_TO_DATE(CONCAT(nd_str,'-01-01'), '%Y-%m-%d %H:%i:%s') into first_date_of_year;
	SELECT STR_TO_DATE(CONCAT(nd_str,'-12-31 23:59:59'), '%Y-%m-%d %H:%i:%s') into last_date_of_year;

  
  IF tab_str is null THEN
		create table stat_payment_nd(
			nd varchar(4),
			yf varchar(2),
			je float,
			user_id  int(11) NULL DEFAULT NULL ,
			proc_time  datetime NULL DEFAULT NULL 
		);
		
  ELSE
		DELETE t FROM stat_payment_nd t WHERE t.nd = nd_str and t.user_id = user_id;
  END IF;

	INSERT INTO stat_payment_nd 
	SELECT
			DATE_FORMAT(t.shijian, '%Y'),
			DATE_FORMAT(t.shijian, '%m'),
			ROUND(sum(t.je), 2) je,
			t.user_id as user_id,
			NOW() as proc_time
		FROM
			test.account_payment_detail t,
			test.account_detail d
		WHERE
			t.accuuid = d.accuuid
		AND d.yxbz = 'Y'
		AND t.yxbz = 'Y'
		AND t.shijian >= first_date_of_year	
		AND t.shijian <= last_date_of_year
		AND t.user_id = user_id
		GROUP BY
			DATE_FORMAT(t.shijian, '%Y-%m');

	# income stat
	SELECT table_name into tab_str FROM information_schema.TABLES WHERE table_name ='stat_income_nd';

	IF tab_str is null THEN
		create table stat_income_nd(
			nd varchar(4),
			yf varchar(2),
			je float,
			user_id  int(11) NULL DEFAULT NULL ,
			proc_time  datetime NULL DEFAULT NULL 
		);
		
  ELSE
		DELETE t FROM stat_income_nd t  WHERE t.nd = nd_str AND t.user_id = user_id;
  END IF;

	INSERT INTO stat_income_nd
  SELECT
			DATE_FORMAT(t.shijian, '%Y') nd,
			DATE_FORMAT(t.shijian, '%m') yf,
			ROUND(sum(t.je), 2) je,
		ROUND(sum(CASE t.lb_dm 
				WHEN  '2002' 	THEN t.je
				WHEN '2003' 	THEN t.je
				WHEN '2004' 	THEN t.je
			ELSE 0 END), 2) je_salary,
		ROUND(sum(CASE t.lb_dm 
				WHEN  '2001' 	THEN t.je
				WHEN '2009' 	THEN t.je
			ELSE 0 END), 2) je_finproduct,
			t.user_id as user_id,
			NOW() as proc_time
		FROM
			test.account_income_detail t,
			test.account_detail d
		WHERE
			t.accuuid = d.accuuid
		AND d.yxbz = 'Y'
		AND t.yxbz = 'Y'
		AND t.shijian >= first_date_of_year
		AND t.shijian <= last_date_of_year
		AND t.user_id = user_id
		GROUP BY
			DATE_FORMAT(t.shijian, '%Y-%m');

	# payment stat by dl_dm
	SELECT table_name into tab_str FROM information_schema.TABLES WHERE table_name ='stat_payment_dl';
	IF tab_str is null THEN
		CREATE TABLE stat_payment_dl (
			dl_dm  varchar(255) not null ,
			dl_mc  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
			je  float NULL DEFAULT NULL ,
			nd  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
			user_id  int(11) NULL DEFAULT NULL ,
			proc_time  datetime NULL DEFAULT NULL 
		);
  ELSE
		DELETE t FROM stat_payment_dl t WHERE t.nd = nd_str AND t.user_id = user_id;
  END IF;

  INSERT INTO stat_payment_dl
	SELECT p.dl_dm dl_dm, 
		(select dl_mc from dm_dl d where d.dl_dm = p.dl_dm) dl_mc,
		SUM(p.je) je,
		DATE_FORMAT(p.shijian, '%Y'),
		p.user_id as user_id,
		NOW() proc_time
  FROM account_payment_detail p
  WHERE p.shijian >= first_date_of_year
	AND p.shijian <= last_date_of_year
	AND p.user_id = user_id
	AND p.yxbz='Y'
  GROUP BY p.dl_dm;

	# income stat by srlb_dm
	SELECT table_name into tab_str FROM information_schema.TABLES WHERE table_name ='stat_income_lb';
	IF tab_str is null THEN
		CREATE TABLE stat_income_lb (
			srlb_dm  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
			srlb_mc  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
			je  float NULL DEFAULT NULL ,
			nd  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
			user_id  int(11) NULL DEFAULT NULL ,
			proc_time  datetime NULL DEFAULT NULL 
		);
  ELSE
		DELETE t FROM stat_income_lb t WHERE t.nd = nd_str and t.user_id = user_id;
  END IF;

  INSERT INTO stat_income_lb
	SELECT t.lb_dm, 
		(select b.srlb_mc from dm_srlb b where b.srlb_dm = t.lb_dm) lb_mc,
		SUM(t.je) je, 
		DATE_FORMAT(t.shijian, '%Y'),
		t.user_id as user_id,
		NOW() proc_time
	FROM account_income_detail t
	WHERE t.shijian >= first_date_of_year
	AND t.shijian <= last_date_of_year
	AND t.user_id = user_id
  AND t.yxbz = 'Y'
  GROUP BY t.lb_dm;
	
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for proc_acc_stat_this_month
-- ----------------------------
DROP PROCEDURE IF EXISTS `proc_acc_stat_this_month`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_acc_stat_this_month`(IN `user_id` int(11))
BEGIN
	#Routine body goes here...

	DECLARE tab_str varchar(20) ;
  DECLARE thisyear varchar(4) ;
  DECLARE thismonth varchar(2) ;

	select DATE_FORMAT(sysdate(), '%Y') INTO thisyear;
  select DATE_FORMAT(sysdate(), '%m') INTO thismonth;

	CALL proc_acc_stat_by_month(user_id, thisyear, thismonth);
	
END
;;
DELIMITER ;
