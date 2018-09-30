/*
SQLyog Ultimate v11.11 (32 bit)
MySQL - 5.7.23-0ubuntu0.16.04.1 : Database - demo_app_db
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`demo_app_db` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `demo_app_db`;

/*Table structure for table `employee` */

DROP TABLE IF EXISTS `employee`;

CREATE TABLE `employee` (
  `Id` int(32) NOT NULL AUTO_INCREMENT,
  `E_Id` int(32) DEFAULT NULL,
  `Salary` varchar(255) DEFAULT NULL,
  `Address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `employee` */

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `Id` int(32) NOT NULL AUTO_INCREMENT,
  `name` enum('ADMIN','USER') DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `role` */

insert  into `role`(`Id`,`name`) values (1,'ADMIN'),(2,'USER');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `ID` int(32) NOT NULL AUTO_INCREMENT,
  `Username` varchar(255) DEFAULT NULL,
  `Password` varchar(255) DEFAULT NULL,
  `First Name` varchar(255) DEFAULT NULL,
  `Middle Name` varchar(255) DEFAULT NULL,
  `Last Name` varchar(255) DEFAULT NULL,
  `role_id` int(32) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `user` */

insert  into `user`(`ID`,`Username`,`Password`,`First Name`,`Middle Name`,`Last Name`,`role_id`) values (1,'admin','$2a$10$PnEdfedxahfy2f8OVq0/E.t86lhGBA7PmKMy0vQ8sNfgtIX0ASMBG','Mr','Oliver','Queen',1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
