/*
SQLyog Ultimate v11.11 (32 bit)
MySQL - 5.7.27-0ubuntu0.16.04.1 : Database - shobardokan
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`shobardokan` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `shobardokan`;

/*Table structure for table `cart` */

DROP TABLE IF EXISTS `cart`;

CREATE TABLE `cart` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL,
  `promo_code` varchar(45) DEFAULT NULL,
  `delivery_fee` double DEFAULT NULL,
  `total_amount` double NOT NULL DEFAULT '0',
  `discount_amount` double DEFAULT NULL,
  `discount_type` enum('PERCENTAGE','FIXED_AMOUNT') DEFAULT NULL,
  `paid_amount` double DEFAULT NULL,
  `due_amount` double DEFAULT NULL,
  `delivery_date` date DEFAULT NULL,
  `user_addresses_id` int(10) DEFAULT NULL,
  `is_confirmed` tinyint(1) DEFAULT NULL,
  `is_complete` tinyint(1) DEFAULT NULL,
  `is_canceled` tinyint(1) DEFAULT NULL,
  `status` enum('WAITING','PROCESSING','SHIPPING','DELIVERED','CANCELED') DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT NULL,
  `delivered_at` date DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cart_user_id_idx` (`user_id`),
  KEY `fk_cart_user_addresses_id_idx` (`user_addresses_id`),
  CONSTRAINT `fk_cart_user_addresses_id` FOREIGN KEY (`user_addresses_id`) REFERENCES `user_addresses` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_cart_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

/*Data for the table `cart` */

insert  into `cart`(`id`,`user_id`,`promo_code`,`delivery_fee`,`total_amount`,`discount_amount`,`discount_type`,`paid_amount`,`due_amount`,`delivery_date`,`user_addresses_id`,`is_confirmed`,`is_complete`,`is_canceled`,`status`,`created_at`,`updated_at`,`delivered_at`,`deleted_at`) values (10,3,'895',8956.23,123654.36,25874.63,'FIXED_AMOUNT',8796.63,2541.85,'2018-10-21',1,1,1,0,'DELIVERED','2018-10-21 15:07:38',NULL,'2018-10-21',NULL),(11,3,'895',8956.23,123654.36,25874.63,'FIXED_AMOUNT',8796.63,2541.85,'2018-10-21',1,1,1,0,'DELIVERED','2018-10-21 15:11:21',NULL,'2018-10-21',NULL),(12,3,'895',8956.23,123654.36,25874.63,'FIXED_AMOUNT',8796.63,2541.85,'2018-10-29',1,1,1,0,'DELIVERED','2018-10-29 10:21:42',NULL,'2018-10-29',NULL),(13,3,'895',8956.23,123654.36,25874.63,'FIXED_AMOUNT',8796.63,2541.85,'2018-10-29',1,1,1,0,'DELIVERED','2018-10-29 10:24:42',NULL,'2018-10-29',NULL);

/*Table structure for table `cart_items` */

DROP TABLE IF EXISTS `cart_items`;

CREATE TABLE `cart_items` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `cart_id` int(10) NOT NULL,
  `product_id` int(10) NOT NULL,
  `unit_price` double DEFAULT NULL,
  `unit_cost` double DEFAULT NULL,
  `quantity` int(2) NOT NULL,
  `tax_amount` double DEFAULT NULL,
  `total_amount` double NOT NULL DEFAULT '0',
  `total_cost` double DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_cart_items_cart_id_idx` (`cart_id`),
  KEY `fk_cart_items_product_id_idx` (`product_id`),
  CONSTRAINT `fk_cart_items_cart_id` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_cart_items_product_id` FOREIGN KEY (`product_id`) REFERENCES `product_details` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `cart_items` */

/*Table structure for table `category` */

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `image_id` int(10) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `category_image_id_idx` (`image_id`),
  CONSTRAINT `category_ibfk_1` FOREIGN KEY (`image_id`) REFERENCES `image` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `category` */

insert  into `category`(`id`,`name`,`image_id`,`created_at`) values (2,'Healthcare',2,'2018-10-02 12:42:28'),(6,'Food',2,'2018-10-21 16:04:58'),(8,'Jewelary',2,'2018-10-21 16:24:39');

/*Table structure for table `image` */

DROP TABLE IF EXISTS `image`;

CREATE TABLE `image` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `data` longblob,
  `file_name` varchar(64) DEFAULT NULL,
  `file_size` int(11) DEFAULT NULL,
  `image_hash` varchar(256) DEFAULT NULL,
  `file_type` varchar(20) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `image` */

insert  into `image`(`id`,`data`,`file_name`,`file_size`,`image_hash`,`file_type`,`created_date`) values (2,NULL,'test_img',0,NULL,NULL,'2018-10-21 16:01:29'),(3,NULL,'test_img',0,NULL,NULL,'2018-10-21 16:04:08'),(4,NULL,'test_img',0,NULL,NULL,'2018-10-21 16:23:44');

/*Table structure for table `payment` */

DROP TABLE IF EXISTS `payment`;

CREATE TABLE `payment` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `cart_id` int(10) NOT NULL,
  `paid_amount` double NOT NULL DEFAULT '0',
  `payment_method` enum('CARD','MFS','COD') NOT NULL,
  `is_confirmed` tinyint(1) DEFAULT NULL,
  `token` varchar(45) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_payment_cart_idx` (`cart_id`),
  CONSTRAINT `fk_payment_cart` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `payment` */

insert  into `payment`(`id`,`cart_id`,`paid_amount`,`payment_method`,`is_confirmed`,`token`,`created_at`,`updated_at`) values (4,11,8974.36,'CARD',1,'8547SSS','2018-10-21 16:41:16',NULL);

/*Table structure for table `product_details` */

DROP TABLE IF EXISTS `product_details`;

CREATE TABLE `product_details` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `request_id` int(10) NOT NULL,
  `category_id` int(10) NOT NULL,
  `url` varchar(255) CHARACTER SET utf8 NOT NULL,
  `product_description` text COLLATE utf8_unicode_ci,
  `unit_price` double DEFAULT NULL COMMENT 'Selling price',
  `delivery_fee` double DEFAULT NULL,
  `status` enum('WAITING','FINISHED','ON_PROCESS','ACCEPTED') CHARACTER SET utf8 DEFAULT NULL,
  `cost_price` double DEFAULT NULL COMMENT 'Our buying price',
  `estimated_delivery_date` date DEFAULT NULL,
  `comments` text COLLATE utf8_unicode_ci,
  `user_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `product_user_id_idx` (`user_id`,`request_id`),
  KEY `product_category_id_fk_idx` (`category_id`),
  KEY `fk_product_request_id_idx` (`request_id`),
  CONSTRAINT `fk_product_category_id` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_product_request_id` FOREIGN KEY (`request_id`) REFERENCES `user_requests` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_product_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `product_details` */

insert  into `product_details`(`id`,`request_id`,`category_id`,`url`,`product_description`,`unit_price`,`delivery_fee`,`status`,`cost_price`,`estimated_delivery_date`,`comments`,`user_id`) values (3,1,2,'fdasf',NULL,52,23,'ACCEPTED',555,'2018-10-31','asdfasdf',12),(4,2,2,'https://shoppingcart.aliexpress.com/shopcart/shopcartDetail.htm?spm=a2g0s.13010208.1000002.2.f2e33c00IPMC1K','asdf',23,32,'WAITING',666,'2018-10-31','asdf',12),(5,3,2,'https://mail.google.com/mail/u/0/#inbox','fsdaf',23,23,'WAITING',777,'2018-11-01','dsf',12);

/*Table structure for table `purchase_by_traveler` */

DROP TABLE IF EXISTS `purchase_by_traveler`;

CREATE TABLE `purchase_by_traveler` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL,
  `requested_product_id` int(10) NOT NULL,
  `requested_status` enum('WAITING','PURCHASED','CANCEL','ACCEPT','DELIVERED') COLLATE utf8_unicode_ci NOT NULL,
  `purchase_invoice_image_id` int(11) DEFAULT NULL,
  `estimated_date` date DEFAULT NULL,
  `delivered_date` timestamp NULL DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `comments` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `purchase_by_traveler` */

insert  into `purchase_by_traveler`(`id`,`user_id`,`requested_product_id`,`requested_status`,`purchase_invoice_image_id`,`estimated_date`,`delivered_date`,`created_date`,`comments`) values (1,3,1,'WAITING',2,'2018-10-21','2018-10-21 17:05:09','2018-10-21 17:06:29','Comments');

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int(2) NOT NULL AUTO_INCREMENT,
  `name` varchar(15) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `role` */

insert  into `role`(`id`,`name`) values (1,'ADMIN'),(2,'USER'),(3,'TRAVELER');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8 NOT NULL,
  `phone` varchar(11) CHARACTER SET utf8 DEFAULT NULL,
  `email` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `user_status` enum('ACTIVE','INACTIVE','UNVERIFIED_REGISTRATION') CHARACTER SET utf8 DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  `last_active` datetime DEFAULT NULL,
  `image_id` int(10) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `phone_UNIQUE` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `user` */

insert  into `user`(`id`,`username`,`phone`,`email`,`password`,`user_status`,`is_active`,`last_active`,`image_id`,`created_date`) values (1,'masum','01916170061',NULL,'$2a$10$6nFpCcrw67hkXIATxEwgueRT0rAcDYE5.KPf0audHyPbK9odUUeqS','ACTIVE',NULL,NULL,NULL,'2018-10-22 11:12:34'),(3,'Gazi Opu','01620454785',NULL,'RTOtxaiI','ACTIVE',NULL,NULL,NULL,'2018-10-22 10:21:12'),(12,'tuser',NULL,'testemail@gmail.com','$2a$10$6nFpCcrw67hkXIATxEwgueRT0rAcDYE5.KPf0audHyPbK9odUUeqS','ACTIVE',NULL,NULL,NULL,'2018-10-29 14:38:34'),(20,'testsamir','01620457869','testsamir@gmail.com','$2a$10$ukGTa3YRTmnedDciet22pOhmMgZzJAFuMq7yWmdGlAutKycKl1l5W','INACTIVE',NULL,NULL,NULL,'2018-10-29 17:39:20'),(23,'testnoman','01620451232','testa@gmail.com','$2a$10$u6yhev2.lFENCQWh9bw0uOq6k9pNfxY4fVtfaMMZB2o6mV8nhF1Wi','INACTIVE',NULL,NULL,NULL,'2018-10-30 12:09:46'),(24,'meraihan','01722680407','sayedmahmudraihan@gmail.com','$2a$10$lxWU1rf5tVGjlZ5brS3Ue.M0ewohg57D7pvjMMGz4MFmaPc.5ky.6','INACTIVE',NULL,NULL,NULL,'2018-11-01 09:53:07'),(25,'admin','01917821640','admin@gmail.com','$2a$10$jDN97QSGr3LVMV8RPkvCM.DumEEkuhJl07hUaYZISGomGn/PEFuLe','INACTIVE',0,NULL,NULL,'2019-04-23 12:48:05');

/*Table structure for table `user_addresses` */

DROP TABLE IF EXISTS `user_addresses`;

CREATE TABLE `user_addresses` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `street_address_1` varchar(100) NOT NULL,
  `street_address_2` varchar(100) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `state` varchar(45) DEFAULT NULL,
  `zip_code` varchar(45) DEFAULT NULL,
  `user_id` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_addresses_user_id_idx` (`user_id`),
  CONSTRAINT `fk_user_addresses_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `user_addresses` */

insert  into `user_addresses`(`id`,`street_address_1`,`street_address_2`,`city`,`state`,`zip_code`,`user_id`) values (1,'ABC','DEF','Dhaka','Dhaka','1207',3),(2,'Address One','Address Two','Dhaka','Dhaka','1207',3);

/*Table structure for table `user_details` */

DROP TABLE IF EXISTS `user_details`;

CREATE TABLE `user_details` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL,
  `first_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `last_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `gender` enum('MALE','FEMALE') COLLATE utf8_unicode_ci DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `present_address` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `mailing_address` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `emergency_contact_no` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `user_details` */

insert  into `user_details`(`id`,`user_id`,`first_name`,`last_name`,`gender`,`date_of_birth`,`present_address`,`mailing_address`,`emergency_contact_no`,`created_date`) values (1,3,'Abdullah','Al Noman','MALE','2018-10-21','Test Present Address','Test Mailing Address','000000000','2018-10-21 12:13:14');

/*Table structure for table `user_requests` */

DROP TABLE IF EXISTS `user_requests`;

CREATE TABLE `user_requests` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL,
  `product_url` varchar(255) NOT NULL,
  `quantity` int(2) DEFAULT NULL,
  `status` enum('OPEN','CLOSED') NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `comments` text COMMENT 'User''s comments/Specifications',
  PRIMARY KEY (`id`),
  KEY `fk_user_requests_user_id_idx` (`user_id`),
  CONSTRAINT `fk_user_requests_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=latin1;

/*Data for the table `user_requests` */

insert  into `user_requests`(`id`,`user_id`,`product_url`,`quantity`,`status`,`created_at`,`updated_at`,`comments`) values (1,3,'abc',10,'OPEN','2018-10-21 11:23:31','2018-10-25 11:34:25',NULL),(2,12,'https://shoppingcart.aliexpress.com/shopcart/shopcartDetail.htm?spm=a2g0s.13010208.1000002.2.f2e33c00IPMC1K',12,'OPEN','2018-10-29 16:27:12','2018-10-29 16:27:12','aaaaaaaaaaaaaaa'),(3,12,'https://mail.google.com/mail/u/0/#inbox',13,'OPEN','2018-10-29 16:31:42','2018-10-29 16:31:42','atasrsafasdf'),(12,12,'https://www.netflix.com/search?q=Animal%20world&jbv=80239565&jbp=4&jbr=0',5,'OPEN','2018-10-29 16:38:48','2018-10-29 16:38:48','aaaaaaaaaaaaaaaaa'),(15,12,'https://www.imdb.com/title/tt7946836/',54,'OPEN','2018-10-29 16:40:41','2018-10-29 16:40:41','aaaaaaaaaaaaaaaaaaaaaa'),(18,12,'https://localhost:8443/requestProduct/vendor/bootstrap/css/bootstrap.min.css',3,'OPEN','2018-10-29 16:56:15','2018-10-29 16:56:15','aaaaaaaaaaaa'),(19,12,'https://localhost:8443/requestProduct/vendor/bootstrap/css/bootstrap.min.css',3,'OPEN','2018-10-29 16:56:31','2018-10-29 16:56:31','aaaaaaaaaaaa'),(20,12,'https://shoppingcart.aliexpress.com/shopcart/shopcartDetail.htm?spm=a2g0s.13010208.1000002.2.f2e33c00IPMC1K',23,'OPEN','2018-10-29 17:20:48','2018-10-29 17:20:48','sdasaaaaaaaaaaaaaaaa'),(21,12,'https://mail.google.com/mail/u/0/#inbox',3,'OPEN','2018-10-29 17:22:09','2018-10-29 17:22:09','aaaaaaaaaaaaaaaaaaa'),(22,12,'https://stackoverflow.com/questions/9805926/jquery-fadeout-only-works-once',2,'OPEN','2018-10-29 17:40:56','2018-10-29 17:40:56','aaaaaaaaaaaaaa'),(23,12,'https://mail.google.com/mail/u/0/#inbox',4,'OPEN','2018-10-29 17:44:10','2018-10-29 17:44:10','aaaaaaaaaaaaaaaa'),(24,12,'https://mail.google.com/mail/u/0/#inbox',5,'OPEN','2018-10-29 17:54:47','2018-10-29 17:54:47','aaaaaaaaaaaaa'),(25,12,'https://mail.google.com/mail/u/0/#inbox',5,'OPEN','2018-10-30 11:36:45','2018-10-30 11:36:45','aaaaaaaaaaaaaa'),(26,12,'https://www.imdb.com/title/tt7946836/',5,'OPEN','2018-10-30 11:46:51','2018-10-30 11:46:51','aaaaaaaaaaaaaaa'),(27,12,'http://mail.datasoft-bd.com/zimbra/#1',5,'OPEN','2018-10-30 14:12:40','2018-10-30 14:12:40','aaaaaaaaaaaaa'),(28,12,'https://mail.google.com/mail/u/0/#inbox',5,'OPEN','2018-10-30 14:14:26','2018-10-30 14:14:26','aaaaaaaaaaaaaaaaa'),(29,12,'https://www.imdb.com/title/tt7946836/',3,'OPEN','2018-10-30 14:15:40','2018-10-30 14:15:40','aaaaaaaaaaaaa'),(30,12,'https://mail.google.com/mail/u/0/#inbox',5,'OPEN','2018-10-30 14:17:39','2018-10-30 14:17:39','aaaaaaaaaa'),(31,1,'http:www.test.com',15,'OPEN','2018-10-30 14:19:24','2018-10-30 14:19:24','comments here'),(32,12,'www.tewst.com',50,'OPEN','2018-10-30 14:34:13','2018-10-30 14:34:13','asdfasf'),(33,12,'www.tewst.com',5,'OPEN','2018-10-30 14:41:03','2018-10-30 14:41:03','asdfasdf'),(34,12,'https://mail.google.com/mail/u/0/#inbox',5,'OPEN','2018-10-31 15:53:50','2018-10-31 15:53:50','aaaaaaaaaaaaaaaaaaaaaaaaa'),(35,24,'https://mail.google.com/mail/u/0/#inbox',5,'OPEN','2018-11-01 10:06:51','2018-11-01 10:06:51','aaaaaaaaaaaaaaaaaaa'),(36,12,'ht',NULL,'OPEN','2018-11-01 17:49:55','2018-11-01 17:49:55','');

/*Table structure for table `users_roles` */

DROP TABLE IF EXISTS `users_roles`;

CREATE TABLE `users_roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL,
  `role_id` int(2) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `users_roles_user_id_fk` (`user_id`),
  KEY `users_roles_role_id_fk` (`role_id`),
  CONSTRAINT `users_roles_role_id_fk` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `users_roles_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `users_roles` */

insert  into `users_roles`(`id`,`user_id`,`role_id`,`created_at`,`updated_at`,`deleted_at`) values (1,3,3,'2018-10-21 17:44:10',NULL,NULL),(2,1,1,'2018-10-22 11:20:14','2018-10-22 11:21:39',NULL),(3,12,2,'2018-10-29 11:56:14',NULL,NULL),(10,20,2,'2018-10-29 17:39:20',NULL,NULL),(13,23,2,'2018-10-30 12:09:46',NULL,NULL),(14,24,2,'2018-11-01 09:53:07',NULL,NULL),(15,25,2,'2019-04-23 12:48:07',NULL,NULL);

/*Table structure for table `verification` */

DROP TABLE IF EXISTS `verification`;

CREATE TABLE `verification` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `code` varchar(10) DEFAULT NULL,
  `type` enum('PHONE','EMAIL') NOT NULL,
  `code_sent_at` timestamp NULL DEFAULT NULL,
  `code_sent_through` enum('SMS','EMAIL','BOTH') DEFAULT NULL,
  `user_id` int(10) NOT NULL,
  `valid_till` datetime DEFAULT NULL,
  `is_successfull` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `verification_user_id_fk_idx` (`user_id`),
  CONSTRAINT `verification_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `verification` */

insert  into `verification`(`id`,`code`,`type`,`code_sent_at`,`code_sent_through`,`user_id`,`valid_till`,`is_successfull`) values (1,'8956','PHONE',NULL,'BOTH',3,'2018-10-21 12:43:00',NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
