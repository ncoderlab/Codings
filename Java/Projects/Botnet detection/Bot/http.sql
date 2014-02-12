/*
SQLyog Ultimate v8.55 
MySQL - 5.1.57-community : Database - httpbotnet
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`httpbotnet` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `httpbotnet`;

/*Table structure for table `blacklist` */

DROP TABLE IF EXISTS `blacklist`;

CREATE TABLE `blacklist` (
  `ID` bigint(20) NOT NULL,
  `BLACKLISTS` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `blacklist` */

insert  into `blacklist`(`ID`,`BLACKLISTS`) values (1,'192.168.1.6;');

/*Table structure for table `graylist` */

DROP TABLE IF EXISTS `graylist`;

CREATE TABLE `graylist` (
  `ID` bigint(20) NOT NULL,
  `GRAYLISTS` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `graylist` */

/*Table structure for table `standarddeviations` */

DROP TABLE IF EXISTS `standarddeviations`;

CREATE TABLE `standarddeviations` (
  `standarddeviation_id` int(11) NOT NULL,
  `standarddeviation` double DEFAULT NULL,
  PRIMARY KEY (`standarddeviation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `standarddeviations` */

insert  into `standarddeviations`(`standarddeviation_id`,`standarddeviation`) values (1,1.50155232576409);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
