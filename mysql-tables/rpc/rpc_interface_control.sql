/*
Navicat MySQL Data Transfer

Source Server         : mysql_pc
Source Server Version : 50636
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50636
File Encoding         : 65001

Date: 2018-09-24 09:05:56
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
INSERT INTO `rpc_interface_control` VALUES ('keepacc_client', '25715822378876930', '2018-08-21 21:32:42', 'Y');
INSERT INTO `rpc_interface_control` VALUES ('keepacc_client', '7303563979324915715', '2018-08-21 21:32:42', 'Y');
INSERT INTO `rpc_interface_control` VALUES ('keepacc_client', '7303563979324915716', '2018-08-21 21:32:42', 'Y');
INSERT INTO `rpc_interface_control` VALUES ('keepacc_client', '7303563979324915717', '2018-08-21 21:32:42', 'Y');
INSERT INTO `rpc_interface_control` VALUES ('keepacc_client', '7303563979324915718', '2018-08-21 21:32:42', 'Y');
INSERT INTO `rpc_interface_control` VALUES ('keepacc_client', '7303563979324915719', '2018-08-21 21:32:42', 'Y');
INSERT INTO `rpc_interface_control` VALUES ('keepacc_client', '7303606667692736521', '2018-09-22 09:55:00', 'Y');
INSERT INTO `rpc_interface_control` VALUES ('keepacc_client', '7303606667692849594', '2018-09-24 09:05:19', 'Y');
INSERT INTO `rpc_interface_control` VALUES ('keepacc_client', '7303606667692849595', '2018-09-24 09:05:41', 'Y');
