/*
Navicat MySQL Data Transfer

Source Server         : mysql_pc
Source Server Version : 50636
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50636
File Encoding         : 65001

Date: 2018-10-07 20:03:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for cxf_ip_blocked_tmp
-- ----------------------------
DROP TABLE IF EXISTS `cxf_ip_blocked_tmp`;
CREATE TABLE `cxf_ip_blocked_tmp` (
  `ip` varchar(20) NOT NULL,
  `lrrq` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
