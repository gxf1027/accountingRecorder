/*
Navicat MySQL Data Transfer

Source Server         : mysql_pc
Source Server Version : 50636
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50636
File Encoding         : 65001

Date: 2018-10-07 20:03:18
*/

SET FOREIGN_KEY_CHECKS=0;

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
-- Records of cxf_interface_control
-- ----------------------------
INSERT INTO `cxf_interface_control` VALUES ('keepacc_client', '7303622741624619008', '2018-10-03 07:33:05', 'Y');
INSERT INTO `cxf_interface_control` VALUES ('keepacc_client', '7303622741624619009', '2018-10-03 07:33:25', 'Y');
INSERT INTO `cxf_interface_control` VALUES ('keepacc_client', '7303622741624619010', '2018-10-03 07:33:39', 'Y');
INSERT INTO `cxf_interface_control` VALUES ('keepacc_client', '7303622741624619011', '2018-10-04 08:15:48', 'Y');
