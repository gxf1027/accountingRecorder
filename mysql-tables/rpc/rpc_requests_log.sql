/*
Navicat MySQL Data Transfer

Source Server         : mysql_pc
Source Server Version : 50636
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50636
File Encoding         : 65001

Date: 2018-08-30 21:08:54
*/

SET FOREIGN_KEY_CHECKS=0;

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
