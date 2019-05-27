/*
SQLyog Trial v12.5.0 (64 bit)
MySQL - 5.5.56 : Database - spr
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`spr` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `spr`;

/*Table structure for table `account` */

DROP TABLE IF EXISTS `account`;

CREATE TABLE `account` (
  `username` varchar(22) NOT NULL,
  `password` varchar(22) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `dept` */

DROP TABLE IF EXISTS `dept`;

CREATE TABLE `dept` (
  `id` int(11) DEFAULT NULL,
  `name` varchar(13) DEFAULT NULL,
  `addr` varchar(22) DEFAULT NULL,
  `sheng` bigint(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `emp` */

DROP TABLE IF EXISTS `emp`;

CREATE TABLE `emp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(13) DEFAULT NULL,
  `age` int(3) DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `dept` int(11) DEFAULT NULL COMMENT '部门',
  PRIMARY KEY (`id`),
  KEY `idx_emp_name` (`name`),
  KEY `idx_emp_dept` (`dept`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Table structure for table `sheng` */

DROP TABLE IF EXISTS `sheng`;

CREATE TABLE `sheng` (
  `shengId` bigint(11) NOT NULL,
  `sheng_name` varchar(23) DEFAULT NULL,
  PRIMARY KEY (`shengId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `skill_type` */

DROP TABLE IF EXISTS `skill_type`;

CREATE TABLE `skill_type` (
  `skill_type_id` bigint(32) NOT NULL,
  `skill_type_name` varchar(22) DEFAULT NULL,
  `parent` bigint(32) DEFAULT NULL,
  `level` tinyint(20) DEFAULT NULL,
  `is_leaf` tinyint(1) DEFAULT NULL,
  `type` int(3) DEFAULT NULL,
  PRIMARY KEY (`skill_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `wz` */

DROP TABLE IF EXISTS `wz`;

CREATE TABLE `wz` (
  `wz_id` bigint(32) NOT NULL AUTO_INCREMENT,
  `title` varchar(64) DEFAULT NULL,
  `content` varchar(999) DEFAULT NULL,
  `late_upd_date` datetime DEFAULT NULL,
  `skill_type` bigint(32) DEFAULT NULL,
  PRIMARY KEY (`wz_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1131200746365161474 DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
