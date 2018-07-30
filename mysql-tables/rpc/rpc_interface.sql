/*
Navicat MySQL Data Transfer

Source Server         : mysql_pc
Source Server Version : 50636
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50636
File Encoding         : 65001

Date: 2018-07-30 20:57:41
*/

SET FOREIGN_KEY_CHECKS=0;

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
-- Records of rpc_interface
-- ----------------------------
INSERT INTO `rpc_interface` VALUES ('25715822378876928', 'cn.gxf.spring.motan.test.SayHi', 'say', 'Y', null, 'Y');
INSERT INTO `rpc_interface` VALUES ('25715822378876929', 'cn.gxf.spring.quartz.job.service.RpcService', 'setFinanProductsNoticeMailed', 'N', null, 'Y');
