/*
Navicat MySQL Data Transfer

Source Server         : mysql_pc
Source Server Version : 50636
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50636
File Encoding         : 65001

Date: 2018-10-07 20:03:13
*/

SET FOREIGN_KEY_CHECKS=0;

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
-- Records of cxf_interface
-- ----------------------------
INSERT INTO `cxf_interface` VALUES ('7303622741624619008', '/incomeService', 'save', 'N', null, 'Y');
INSERT INTO `cxf_interface` VALUES ('7303622741624619009', '/incomeService', 'update', 'N', null, 'Y');
INSERT INTO `cxf_interface` VALUES ('7303622741624619010', '/incomeService', 'delete', 'N', null, 'Y');
INSERT INTO `cxf_interface` VALUES ('7303622741624619011', '/billService', 'SetBillMailed', 'Y', null, 'Y');
