/*
Navicat MySQL Data Transfer

Source Server         : mysql_pc
Source Server Version : 50636
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50636
File Encoding         : 65001

Date: 2018-06-20 22:32:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
`customerid`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL ,
`balance`  float NOT NULL 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_swedish_ci

;

-- ----------------------------
-- Table structure for account_detail
-- ----------------------------
DROP TABLE IF EXISTS `account_detail`;
CREATE TABLE `account_detail` (
`accuuid`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL ,
`rec_dm`  varchar(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL ,
`user_id`  int(11) NOT NULL ,
`je`  float NOT NULL ,
`shijian`  datetime NOT NULL ,
`yxbz`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL ,
`xgrq`  datetime NULL DEFAULT NULL ,
`lrrq`  datetime NOT NULL ,
PRIMARY KEY (`accuuid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_swedish_ci

;

-- ----------------------------
-- Table structure for account_income_detail
-- ----------------------------
DROP TABLE IF EXISTS `account_income_detail`;
CREATE TABLE `account_income_detail` (
`mxuuid`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL ,
`accuuid`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`user_id`  int(11) NOT NULL ,
`je`  float NOT NULL ,
`lb_dm`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL ,
`fkfmc`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`zh_dm`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`shijian`  datetime NOT NULL ,
`bz`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`yxbz`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`xgrq`  datetime NULL DEFAULT NULL ,
`lrrq`  datetime NOT NULL ,
PRIMARY KEY (`mxuuid`),
INDEX `index_income_lb` (`lb_dm`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_swedish_ci

;

-- ----------------------------
-- Table structure for account_info
-- ----------------------------
DROP TABLE IF EXISTS `account_info`;
CREATE TABLE `account_info` (
`accuuid`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL ,
`ssny`  varchar(6) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`income`  float NULL DEFAULT NULL ,
`salary`  float NULL DEFAULT NULL ,
`bonus`  float NULL DEFAULT NULL ,
`expenditure`  float NULL DEFAULT NULL ,
`netincome`  float NULL DEFAULT NULL ,
`xgrq`  date NULL DEFAULT NULL ,
PRIMARY KEY (`accuuid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_swedish_ci

;

-- ----------------------------
-- Table structure for account_payment_detail
-- ----------------------------
DROP TABLE IF EXISTS `account_payment_detail`;
CREATE TABLE `account_payment_detail` (
`mxuuid`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL ,
`accuuid`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`user_id`  int(11) NOT NULL ,
`yhje`  float NULL DEFAULT NULL ,
`je`  float NOT NULL ,
`dl_dm`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL ,
`xl_dm`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL ,
`seller`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`zh_dm`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`shijian`  datetime NOT NULL ,
`category_dm`  varchar(5) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`bz`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`yxbz`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`xgrq`  datetime NULL DEFAULT NULL ,
`lrrq`  datetime NOT NULL ,
PRIMARY KEY (`mxuuid`),
INDEX `index_paydetail_dl` (`dl_dm`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_swedish_ci

;

-- ----------------------------
-- Table structure for account_transfer_detail
-- ----------------------------
DROP TABLE IF EXISTS `account_transfer_detail`;
CREATE TABLE `account_transfer_detail` (
`mxuuid`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL ,
`accuuid`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`user_id`  int(11) NOT NULL ,
`je`  float NOT NULL ,
`srcZh_dm`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL ,
`tgtZh_dm`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL ,
`zzlx_dm`  varchar(5) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`shijian`  datetime NOT NULL ,
`bz`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`yxbz`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`xgrq`  datetime NULL DEFAULT NULL ,
`lrrq`  datetime NOT NULL ,
PRIMARY KEY (`mxuuid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_swedish_ci

;

-- ----------------------------
-- Table structure for customers
-- ----------------------------
DROP TABLE IF EXISTS `customers`;
CREATE TABLE `customers` (
`customerid`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL ,
`name`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL ,
`age`  int(11) NULL DEFAULT NULL 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_swedish_ci

;

-- ----------------------------
-- Table structure for dm_dl
-- ----------------------------
DROP TABLE IF EXISTS `dm_dl`;
CREATE TABLE `dm_dl` (
`dl_dm`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`dl_mc`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`yxbz`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`xybz`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`dl_dm`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_swedish_ci

;

-- ----------------------------
-- Table structure for dm_financial_product
-- ----------------------------
DROP TABLE IF EXISTS `dm_financial_product`;
CREATE TABLE `dm_financial_product` (
`fin_prod_dm`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL ,
`fin_prod_mc`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`yxbz`  varchar(2) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`xybz`  varchar(2) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
PRIMARY KEY (`fin_prod_dm`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_swedish_ci

;

-- ----------------------------
-- Table structure for dm_fund_type
-- ----------------------------
DROP TABLE IF EXISTS `dm_fund_type`;
CREATE TABLE `dm_fund_type` (
`fund_type_dm`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL ,
`fund_type_mc`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`yxbz`  varchar(2) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`xybz`  varchar(2) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
PRIMARY KEY (`fund_type_dm`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_swedish_ci

;

-- ----------------------------
-- Table structure for dm_outgo_category
-- ----------------------------
DROP TABLE IF EXISTS `dm_outgo_category`;
CREATE TABLE `dm_outgo_category` (
`outgo_category_dm`  varchar(5) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL ,
`outgo_category_mc`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`user_id`  int(11) NULL DEFAULT NULL ,
`yxbz`  varchar(2) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`xybz`  varchar(2) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_swedish_ci

;

-- ----------------------------
-- Table structure for dm_province
-- ----------------------------
DROP TABLE IF EXISTS `dm_province`;
CREATE TABLE `dm_province` (
`province_dm`  varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`province_mc`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_swedish_ci

;

-- ----------------------------
-- Table structure for dm_record
-- ----------------------------
DROP TABLE IF EXISTS `dm_record`;
CREATE TABLE `dm_record` (
`rec_dm`  varchar(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL ,
`rec_mc`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
PRIMARY KEY (`rec_dm`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_swedish_ci

;

-- ----------------------------
-- Table structure for dm_srlb
-- ----------------------------
DROP TABLE IF EXISTS `dm_srlb`;
CREATE TABLE `dm_srlb` (
`srlb_dm`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`srlb_mc`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`yxbz`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`xybz`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`srlb_dm`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_swedish_ci

;

-- ----------------------------
-- Table structure for dm_xl
-- ----------------------------
DROP TABLE IF EXISTS `dm_xl`;
CREATE TABLE `dm_xl` (
`xl_dm`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`xl_mc`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`dl_dm`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`yxbz`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`xybz`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`xl_dm`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_swedish_ci

;

-- ----------------------------
-- Table structure for dm_yh
-- ----------------------------
DROP TABLE IF EXISTS `dm_yh`;
CREATE TABLE `dm_yh` (
`yh_dm`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL ,
`yh_mc`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`yxbz`  varchar(2) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`xybz`  varchar(2) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
PRIMARY KEY (`yh_dm`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_swedish_ci

;

-- ----------------------------
-- Table structure for dm_zhanghu
-- ----------------------------
DROP TABLE IF EXISTS `dm_zhanghu`;
CREATE TABLE `dm_zhanghu` (
`zhanghu_dm`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`zhanghu_mc`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`yxbz`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`xybz`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`zhanghu_dm`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_swedish_ci

;

-- ----------------------------
-- Table structure for dm_zhlx
-- ----------------------------
DROP TABLE IF EXISTS `dm_zhlx`;
CREATE TABLE `dm_zhlx` (
`zhlx_dm`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`zhlx_mc`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
PRIMARY KEY (`zhlx_dm`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_swedish_ci

;

-- ----------------------------
-- Table structure for dm_zzlx
-- ----------------------------
DROP TABLE IF EXISTS `dm_zzlx`;
CREATE TABLE `dm_zzlx` (
`zzlx_dm`  varchar(5) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL ,
`zzlx_mc`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`user_id`  int(11) NULL DEFAULT NULL COMMENT '转账类型代码' ,
`yxbz`  varchar(2) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL ,
`xybz`  varchar(2) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL ,
PRIMARY KEY (`zzlx_dm`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_swedish_ci

;

-- ----------------------------
-- Table structure for exp_info
-- ----------------------------
DROP TABLE IF EXISTS `exp_info`;
CREATE TABLE `exp_info` (
`expuuid`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL ,
`accuuid`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`expenditure`  float NULL DEFAULT NULL ,
`shopping`  float NULL DEFAULT NULL ,
`tax`  float NULL DEFAULT NULL ,
`loan`  float NULL DEFAULT NULL ,
`others`  float NULL DEFAULT NULL ,
`xgrq`  date NULL DEFAULT NULL ,
PRIMARY KEY (`expuuid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_swedish_ci

;

-- ----------------------------
-- Table structure for financial_product_detail
-- ----------------------------
DROP TABLE IF EXISTS `financial_product_detail`;
CREATE TABLE `financial_product_detail` (
`uuid`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL ,
`transferuuid`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '购买理财产品的转账uuid' ,
`redeemuuid`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '赎回理财产品的转账uuid' ,
`returnuuid`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '理财产品收益的收入uuid' ,
`product_name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`product_type`  varchar(12) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`yh_dm`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`date_count`  int(11) NULL DEFAULT NULL ,
`start_date`  date NULL DEFAULT NULL ,
`end_date`  date NULL DEFAULT NULL ,
`expected_returnrate`  float NULL DEFAULT NULL ,
`je`  float NULL DEFAULT NULL ,
`real_return`  float NULL DEFAULT NULL ,
`is_redeem`  varchar(2) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`lrrq`  datetime NULL DEFAULT NULL ,
`xgrq`  datetime NULL DEFAULT NULL ,
`yxbz`  varchar(2) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
PRIMARY KEY (`uuid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_swedish_ci

;

-- ----------------------------
-- Table structure for onlinestat
-- ----------------------------
DROP TABLE IF EXISTS `onlinestat`;
CREATE TABLE `onlinestat` (
`sessionid`  varchar(200) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`user`  varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`hostip`  varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`page`  varchar(200) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_swedish_ci

;

-- ----------------------------
-- Table structure for persistent_logins
-- ----------------------------
DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE `persistent_logins` (
`username`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL ,
`series`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL ,
`token`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL ,
`last_used`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
PRIMARY KEY (`series`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_swedish_ci

;

-- ----------------------------
-- Table structure for role_ss
-- ----------------------------
DROP TABLE IF EXISTS `role_ss`;
CREATE TABLE `role_ss` (
`id`  int(11) NOT NULL DEFAULT 0 COMMENT 'id' ,
`role_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'name' ,
`description`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'description' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='角色表'

;

-- ----------------------------
-- Table structure for spring_session
-- ----------------------------
DROP TABLE IF EXISTS `spring_session`;
CREATE TABLE `spring_session` (
`SESSION_ID`  char(36) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL DEFAULT '' ,
`CREATION_TIME`  bigint(20) NOT NULL ,
`LAST_ACCESS_TIME`  bigint(20) NOT NULL ,
`MAX_INACTIVE_INTERVAL`  int(11) NOT NULL ,
`PRINCIPAL_NAME`  varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
PRIMARY KEY (`SESSION_ID`),
INDEX `SPRING_SESSION_IX1` (`LAST_ACCESS_TIME`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_swedish_ci

;

-- ----------------------------
-- Table structure for spring_session_attributes
-- ----------------------------
DROP TABLE IF EXISTS `spring_session_attributes`;
CREATE TABLE `spring_session_attributes` (
`SESSION_ID`  char(36) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL DEFAULT '' ,
`ATTRIBUTE_NAME`  varchar(200) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL DEFAULT '' ,
`ATTRIBUTE_BYTES`  blob NULL ,
PRIMARY KEY (`SESSION_ID`, `ATTRIBUTE_NAME`),
FOREIGN KEY (`SESSION_ID`) REFERENCES `spring_session` (`SESSION_ID`) ON DELETE CASCADE ON UPDATE RESTRICT,
INDEX `SPRING_SESSION_ATTRIBUTES_IX1` (`SESSION_ID`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_swedish_ci

;

-- ----------------------------
-- Table structure for stat_income_lb
-- ----------------------------
DROP TABLE IF EXISTS `stat_income_lb`;
CREATE TABLE `stat_income_lb` (
`srlb_dm`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`srlb_mc`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`je`  float NULL DEFAULT NULL ,
`nd`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`user_id`  int(11) NULL DEFAULT NULL ,
`proc_time`  datetime NULL DEFAULT NULL 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_swedish_ci

;

-- ----------------------------
-- Table structure for stat_income_nd
-- ----------------------------
DROP TABLE IF EXISTS `stat_income_nd`;
CREATE TABLE `stat_income_nd` (
`nd`  varchar(4) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`yf`  varchar(2) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`je`  float NULL DEFAULT NULL ,
`user_id`  int(11) NULL DEFAULT NULL ,
`proc_time`  datetime NULL DEFAULT NULL 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_swedish_ci

;

-- ----------------------------
-- Table structure for stat_payment_dl
-- ----------------------------
DROP TABLE IF EXISTS `stat_payment_dl`;
CREATE TABLE `stat_payment_dl` (
`dl_dm`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`dl_mc`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`je`  float NULL DEFAULT NULL ,
`nd`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`user_id`  int(11) NULL DEFAULT NULL ,
`proc_time`  datetime NULL DEFAULT NULL 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_swedish_ci

;

-- ----------------------------
-- Table structure for stat_payment_nd
-- ----------------------------
DROP TABLE IF EXISTS `stat_payment_nd`;
CREATE TABLE `stat_payment_nd` (
`nd`  varchar(4) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`yf`  varchar(2) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`je`  float NULL DEFAULT NULL ,
`user_id`  int(11) NULL DEFAULT NULL ,
`proc_time`  datetime NULL DEFAULT NULL 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_swedish_ci

;

-- ----------------------------
-- Table structure for testmqxa
-- ----------------------------
DROP TABLE IF EXISTS `testmqxa`;
CREATE TABLE `testmqxa` (
`uuid`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`lrrq`  datetime NULL DEFAULT NULL 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_swedish_ci

;

-- ----------------------------
-- Table structure for timedtask_acc_stat
-- ----------------------------
DROP TABLE IF EXISTS `timedtask_acc_stat`;
CREATE TABLE `timedtask_acc_stat` (
`userid`  int(11) NOT NULL ,
`proctime`  datetime NOT NULL ,
PRIMARY KEY (`userid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_swedish_ci

;

-- ----------------------------
-- Table structure for transfer_fund_detail
-- ----------------------------
DROP TABLE IF EXISTS `transfer_fund_detail`;
CREATE TABLE `transfer_fund_detail` (
`uuid`  varchar(127) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL ,
`transferuuid`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL ,
`fund_code`  varchar(15) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`fund_name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`fund_type`  varchar(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`unit_net`  float NULL DEFAULT NULL ,
`extra_fee`  float NULL DEFAULT NULL ,
`confirmed_sum`  float NULL DEFAULT NULL ,
`lrrq`  datetime NULL DEFAULT NULL ,
`xgrq`  datetime NULL DEFAULT NULL ,
`yxbz`  varchar(2) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_swedish_ci

;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
`id`  varchar(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`name`  varchar(200) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`addr`  varchar(400) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`age`  int(11) NULL DEFAULT NULL ,
`birthdate`  date NULL DEFAULT NULL ,
`province_dm`  varchar(4) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_swedish_ci

;

-- ----------------------------
-- Table structure for user_role_dzb
-- ----------------------------
DROP TABLE IF EXISTS `user_role_dzb`;
CREATE TABLE `user_role_dzb` (
`user_id`  int(11) NULL DEFAULT NULL COMMENT '用户表_id' ,
`role_id`  int(11) NULL DEFAULT NULL COMMENT '角色表_id' 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='用户角色表'

;

-- ----------------------------
-- Table structure for user_ss
-- ----------------------------
DROP TABLE IF EXISTS `user_ss`;
CREATE TABLE `user_ss` (
`id`  int(11) NOT NULL AUTO_INCREMENT COMMENT 'id' ,
`username`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'username' ,
`password`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'password' ,
`email`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`enabled`  varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'enabled' ,
`description`  varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'description' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='用户表'
AUTO_INCREMENT=8

;

-- ----------------------------
-- Table structure for zh_detail_ccbill
-- ----------------------------
DROP TABLE IF EXISTS `zh_detail_ccbill`;
CREATE TABLE `zh_detail_ccbill` (
`pch`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL ,
`user_id`  int(5) NOT NULL ,
`username`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`emailaddr`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`zh_dm`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`zh_mc`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ssqq`  date NULL DEFAULT NULL ,
`ssqz`  date NULL DEFAULT NULL ,
`yhkje`  float NULL DEFAULT NULL ,
`mailedrq`  datetime NULL DEFAULT NULL ,
`lrrq`  datetime NULL DEFAULT NULL ,
`yxbz`  varchar(2) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
PRIMARY KEY (`pch`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_swedish_ci

;

-- ----------------------------
-- Table structure for zh_detail_ccbill_mx
-- ----------------------------
DROP TABLE IF EXISTS `zh_detail_ccbill_mx`;
CREATE TABLE `zh_detail_ccbill_mx` (
`billuuid`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL ,
`pch`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`user_id`  int(11) NOT NULL ,
`bill_ssqq`  date NOT NULL ,
`bill_ssqz`  date NOT NULL ,
`zh_dm`  varchar(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL ,
`zh_mc`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`jysj`  datetime NOT NULL ,
`jylx`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`jyf`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`je`  float NOT NULL ,
`bz`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`yxbz`  varchar(2) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`lrrq`  datetime NULL DEFAULT NULL ,
`xgrq`  datetime NULL DEFAULT NULL ,
`mailed`  varchar(2) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
PRIMARY KEY (`billuuid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_swedish_ci

;

-- ----------------------------
-- Table structure for zh_detail_creditcard
-- ----------------------------
DROP TABLE IF EXISTS `zh_detail_creditcard`;
CREATE TABLE `zh_detail_creditcard` (
`zh_dm`  varchar(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL ,
`hkr`  int(11) NULL DEFAULT NULL COMMENT '还款日' ,
`zdr`  int(11) NULL DEFAULT NULL COMMENT '账单日' ,
`yxbz`  varchar(2) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL ,
`xgrq`  datetime NULL DEFAULT NULL ,
PRIMARY KEY (`zh_dm`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_swedish_ci

;

-- ----------------------------
-- Table structure for zh_detail_info
-- ----------------------------
DROP TABLE IF EXISTS `zh_detail_info`;
CREATE TABLE `zh_detail_info` (
`zh_dm`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`zh_mc`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`khrmc`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`user_id`  int(11) NOT NULL ,
`zhlx_dm`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`zhhm`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ye`  float NOT NULL ,
`yxbz`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`xybz`  varchar(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
PRIMARY KEY (`zh_dm`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_swedish_ci

;


-- ----------------------------
-- Procedure structure for proc_acc_stat_nd
-- ----------------------------
DROP PROCEDURE IF EXISTS `proc_acc_stat_nd`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_acc_stat_nd`(IN `nd_str` varchar(20), IN `user_id` int(11))
BEGIN
	#Routine body goes here...

	DECLARE tab_str varchar(20) ;

	#payment stat
	SELECT table_name into tab_str FROM information_schema.TABLES WHERE table_name ='stat_payment_nd';

  
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
		AND DATE_FORMAT(t.shijian, '%Y') = nd_str
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
			t.user_id as user_id,
			NOW() as proc_time
		FROM
			test.account_income_detail t,
			test.account_detail d
		WHERE
			t.accuuid = d.accuuid
		AND d.yxbz = 'Y'
		AND t.yxbz = 'Y'
		AND DATE_FORMAT(t.shijian, '%Y') = nd_str
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
  WHERE DATE_FORMAT(p.shijian, '%Y') = nd_str
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
	WHERE DATE_FORMAT(t.shijian, '%Y') = nd_str
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

	#payment stat
	SELECT table_name into tab_str FROM information_schema.TABLES WHERE table_name ='stat_payment_nd';

  
  IF tab_str is null THEN
		create table stat_payment_nd(
			nd varchar(4),
			yf varchar(2),
			je float,
			user_id  int(11) NULL DEFAULT NULL ,
			proc_time  datetime NULL DEFAULT NULL 
		);
		
  ELSE
		DELETE t FROM stat_payment_nd t WHERE t.nd = thisyear AND t.yf = thismonth AND t.user_id = user_id;
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
		AND DATE_FORMAT(t.shijian, '%Y') = thisyear
    AND DATE_FORMAT(t.shijian, '%m') = thismonth 
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
		DELETE t FROM stat_income_nd t  WHERE t.nd = thisyear AND t.yf = thismonth AND t.user_id = user_id;
  END IF;

	INSERT INTO stat_income_nd
  SELECT
			DATE_FORMAT(t.shijian, '%Y') nd,
			DATE_FORMAT(t.shijian, '%m') yf,
			ROUND(sum(t.je), 2) je,
			t.user_id as user_id,
			NOW() as proc_time
		FROM
			test.account_income_detail t,
			test.account_detail d
		WHERE
			t.accuuid = d.accuuid
		AND d.yxbz = 'Y'
		AND t.yxbz = 'Y'
		AND DATE_FORMAT(t.shijian, '%Y') = thisyear
		AND DATE_FORMAT(t.shijian, '%m') = thismonth 
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
		DELETE t FROM stat_payment_dl t WHERE t.nd = thisyear AND t.user_id = user_id;
  END IF;

  INSERT INTO stat_payment_dl
	SELECT p.dl_dm dl_dm, 
		(select dl_mc from dm_dl d where d.dl_dm = p.dl_dm) dl_mc,
		SUM(p.je) je,
		DATE_FORMAT(p.shijian, '%Y'),
		p.user_id as user_id,
		NOW() proc_time
  FROM account_payment_detail p
  WHERE DATE_FORMAT(p.shijian, '%Y') = thisyear
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
		DELETE t FROM stat_income_lb t WHERE t.nd = thisyear and t.user_id = user_id;
  END IF;

  INSERT INTO stat_income_lb
	SELECT t.lb_dm, 
		(select b.srlb_mc from dm_srlb b where b.srlb_dm = t.lb_dm) lb_mc,
		SUM(t.je) je, 
		DATE_FORMAT(t.shijian, '%Y'),
		t.user_id as user_id,
		NOW() proc_time
	FROM account_income_detail t
	WHERE DATE_FORMAT(t.shijian, '%Y') = thisyear
	AND t.user_id = user_id
  AND t.yxbz = 'Y'
  GROUP BY t.lb_dm;
END
;;
DELIMITER ;

-- ----------------------------
-- Auto increment value for user_ss
-- ----------------------------
ALTER TABLE `user_ss` AUTO_INCREMENT=8;
