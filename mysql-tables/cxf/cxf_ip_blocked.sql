/*
Navicat MySQL Data Transfer

Source Server         : mysql_pc
Source Server Version : 50636
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50636
File Encoding         : 65001

Date: 2018-10-07 20:03:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for cxf_ip_blocked
-- ----------------------------
DROP TABLE IF EXISTS `cxf_ip_blocked`;
CREATE TABLE `cxf_ip_blocked` (
  `ip` varchar(20) NOT NULL,
  `lrrq` datetime DEFAULT NULL,
  PRIMARY KEY (`ip`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
