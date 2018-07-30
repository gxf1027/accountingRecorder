/*
Navicat MySQL Data Transfer

Source Server         : mysql_pc
Source Server Version : 50636
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50636
File Encoding         : 65001

Date: 2018-07-30 20:57:45
*/

SET FOREIGN_KEY_CHECKS=0;

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
-- Records of rpc_interface_control
-- ----------------------------
INSERT INTO `rpc_interface_control` VALUES ('keepacc_client', '25715822378876928', '2018-07-29 07:15:13', 'Y');
INSERT INTO `rpc_interface_control` VALUES ('keepacc_client', '25715822378876929', '2018-07-29 07:15:47', 'Y');
