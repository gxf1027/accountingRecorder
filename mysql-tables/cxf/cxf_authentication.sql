/*
Navicat MySQL Data Transfer

Source Server         : mysql_pc
Source Server Version : 50636
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50636
File Encoding         : 65001

Date: 2018-10-07 20:03:08
*/

SET FOREIGN_KEY_CHECKS=0;

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
-- Records of cxf_authentication
-- ----------------------------
INSERT INTO `cxf_authentication` VALUES ('keepacc_client', 'B053806C5AF39581722FF396656CEC99', '2018-07-28 09:01:16', 'Y');
