/*
Navicat MySQL Data Transfer

Source Server         : mysql_pc
Source Server Version : 50636
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50636
File Encoding         : 65001

Date: 2020-12-02 21:44:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Procedure structure for proc_acc_stat_by_month
-- ----------------------------
DROP PROCEDURE IF EXISTS `proc_acc_stat_by_month`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_acc_stat_by_month`(IN `user_id` int(11), IN `nd_str` varchar(10), IN `yf_str` varchar(2))
BEGIN
	

	DECLARE tab_str varchar(20) ;
	DECLARE date_from datetime;
	DECLARE date_to datetime;
	DECLARE first_date_of_year datetime;
	DECLARE last_date_of_year datetime;
	
	SELECT table_name into tab_str FROM information_schema.TABLES WHERE table_name ='stat_payment_nd';
	SELECT STR_TO_DATE(CONCAT(nd_str,'-',yf_str,'-01'),'%Y-%m-%d %H:%i:%s') into date_from;
	SELECT date_add(date_from, interval 1 MONTH)  into date_to;
	SELECT STR_TO_DATE(CONCAT(nd_str,'-01-01'), '%Y-%m-%d %H:%i:%s') into first_date_of_year;
	SELECT STR_TO_DATE(CONCAT(nd_str,'-12-31 23:59:59'), '%Y-%m-%d %H:%i:%s') into last_date_of_year;
	
  IF tab_str is null THEN
		create table stat_payment_nd(
			nd varchar(4),
			yf varchar(2),
			je float,
			user_id  int(11) NULL DEFAULT NULL ,
			proc_time  datetime NULL DEFAULT NULL 
		);
		
  ELSE
		DELETE t FROM stat_payment_nd t WHERE t.nd = nd_str AND t.yf = yf_str AND t.user_id = user_id;
  END IF;

	INSERT INTO stat_payment_nd 
  SELECT nd_str, yf_str, TMP.je, user_id, NOW() FROM
	(SELECT
			ROUND(sum(t.je), 2) je
		FROM
			test.account_payment_detail t,
			test.account_detail d
		WHERE
			t.accuuid = d.accuuid
		AND d.yxbz = 'Y'
		AND t.yxbz = 'Y'
		AND t.shijian >= date_from
		AND t.shijian < date_to
		AND t.user_id = user_id) TMP
    WHERE TMP.je IS NOT NULL;
		

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
		DELETE t FROM stat_income_nd t  WHERE t.nd = nd_str AND t.yf = yf_str AND t.user_id = user_id;
  END IF;

	INSERT INTO stat_income_nd
	SELECT nd_str, yf_str, je, je_salary, je_finproduct, user_id, NOW() FROM (
  SELECT
			ROUND(sum(t.je), 2) je,
     ROUND(sum(CASE t.lb_dm 
				WHEN  '2002' 	THEN t.je
				WHEN '2003' 	THEN t.je
				WHEN '2004' 	THEN t.je
        WHEN '2014'   THEN t.je
			ELSE 0 END), 2) je_salary,
		ROUND(sum(CASE t.lb_dm 
				WHEN  '2001' 	THEN t.je
				WHEN '2009' 	THEN t.je
			ELSE 0 END), 2) je_finproduct
		FROM
			test.account_income_detail t,
			test.account_detail d
		WHERE
			t.accuuid = d.accuuid
		AND d.yxbz = 'Y'
		AND t.yxbz = 'Y'
		AND t.shijian >= date_from 
		AND t.shijian < date_to
		AND t.user_id = user_id) TMP
WHERE TMP.je IS NOT NULL;

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
  WHERE p.shijian >= first_date_of_year
	AND p.shijian <= last_date_of_year
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
	WHERE t.shijian >= first_date_of_year
	AND t.shijian <= last_date_of_year
	AND t.user_id = user_id
  AND t.yxbz = 'Y'
  GROUP BY t.lb_dm;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for proc_acc_stat_nd
-- ----------------------------
DROP PROCEDURE IF EXISTS `proc_acc_stat_nd`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_acc_stat_nd`(IN `nd_str` varchar(20), IN `user_id` int(11))
BEGIN
	#Routine body goes here...

	DECLARE tab_str varchar(20) ;
	DECLARE first_date_of_year datetime;
	DECLARE last_date_of_year datetime;

	#payment stat
	SELECT table_name into tab_str FROM information_schema.TABLES WHERE table_name ='stat_payment_nd';

	SELECT STR_TO_DATE(CONCAT(nd_str,'-01-01'), '%Y-%m-%d %H:%i:%s') into first_date_of_year;
	SELECT STR_TO_DATE(CONCAT(nd_str,'-12-31 23:59:59'), '%Y-%m-%d %H:%i:%s') into last_date_of_year;

  
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
		AND t.shijian >= first_date_of_year	
		AND t.shijian <= last_date_of_year
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
		ROUND(sum(CASE t.lb_dm 
				WHEN  '2002' 	THEN t.je
				WHEN '2003' 	THEN t.je
				WHEN '2004' 	THEN t.je
        WHEN '2014'   THEN t.je
			ELSE 0 END), 2) je_salary,
		ROUND(sum(CASE t.lb_dm 
				WHEN  '2001' 	THEN t.je
				WHEN '2009' 	THEN t.je
			ELSE 0 END), 2) je_finproduct,
			t.user_id as user_id,
			NOW() as proc_time
		FROM
			test.account_income_detail t,
			test.account_detail d
		WHERE
			t.accuuid = d.accuuid
		AND d.yxbz = 'Y'
		AND t.yxbz = 'Y'
		AND t.shijian >= first_date_of_year
		AND t.shijian <= last_date_of_year
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
  WHERE p.shijian >= first_date_of_year
	AND p.shijian <= last_date_of_year
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
	WHERE t.shijian >= first_date_of_year
	AND t.shijian <= last_date_of_year
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

	CALL proc_acc_stat_by_month(user_id, thisyear, thismonth);
	
END
;;
DELIMITER ;
