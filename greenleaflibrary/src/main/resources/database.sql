SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

--
-- create database (Host: localhost  Database: libraryappdb ) 
--
DROP SCHEMA IF EXISTS `libraryappdb`;
CREATE SCHEMA `libraryappdb` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `libraryappdb`;

-- 
-- table structure for table users
--
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `userId` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `cardNumber` varchar(11) NOT NULL,
  `userName` varchar(45) NOT NULL,
  `password` varchar(60) NOT NULL,
  `fullName` varchar(70) NOT NULL,
  `dateOfBirth` date NOT NULL,
  `email` varchar(80) NOT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `userName_UNIQUE` (`userName`)
  ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
  
-- 
-- table structure for table roles
--
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `roleId` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `roleName` varchar(20) NOT NULL,
  PRIMARY KEY (`roleId`),
  UNIQUE KEY `roleName_UNIQUE` (`roleName`)
  ) ENGINE=InnoDB DEFAULT CHARSET=latin1; 
  
-- 
-- table structure for table user_role
--
DROP TABLE IF EXISTS `users_roles`;
CREATE TABLE `user_role` (
  `userId` int(11) UNSIGNED NOT NULL,
  `roleId` int(11) UNSIGNED NOT NULL, 
  PRIMARY KEY (`userId`, `roleId`),
  KEY `fk_userrole_role_idx` (`roleId`), 
  CONSTRAINT `fk_userrole_user` FOREIGN KEY (`userId`) REFERENCES `users`(`userId`),
  CONSTRAINT `fk_userrole_role` FOREIGN KEY (`roleId`) REFERENCES `roles`(`roleId`)
  ) ENGINE=InnoDB DEFAULT CHARSET=latin1; 
  
-- 
-- table structure for table publisher
--
DROP TABLE IF EXISTS `publishers`;
CREATE TABLE `publishers` (
  `publisherId` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `publisherName` varchar(100) NOT NULL,
  PRIMARY KEY (`publisherId`),
  UNIQUE KEY `publisherName_UNIQUE` (`publisherName`)
  ) ENGINE=InnoDB DEFAULT CHARSET=latin1;  
   
-- 
-- table structure for table books
--
DROP TABLE IF EXISTS `books`;
CREATE TABLE `books` (
  `bookId` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `barcode` varchar(13) NOT NULL,
  `title` varchar(100) NOT NULL,
  `description` text,
  `edition` varchar(20) DEFAULT NULL,
  `imageUri` varchar(100) DEFAULT NULL,
  `authors` varchar(100) NOT NULL,
  `publisherId` int(11) UNSIGNED NOT NULL,
  PRIMARY KEY (`bookId`),
  CONSTRAINT `fk_books_publisher` FOREIGN KEY (`publisherId`) REFERENCES `publishers`(`publisherId`)
  ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
   
-- 
-- table structure for table ratings
--
DROP TABLE IF EXISTS `ratings`;
CREATE TABLE `ratings` (
  `ratingId` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `score` int(2) UNSIGNED NOT NULL,
  `rateDate` timestamp NOT NULL,
  `userId` int(11) UNSIGNED NOT NULL,
  `bookId` int(11) UNSIGNED NOT NULL,
  PRIMARY KEY (`ratingId`),
  UNIQUE KEY `ratings_UNIQUE` (`userId`, `bookId`),
  CONSTRAINT `fk_ratings_user` FOREIGN KEY (`userId`) REFERENCES `users`(`userId`),
  CONSTRAINT `fk_ratings_book` FOREIGN KEY (`bookId`) REFERENCES `books`(`bookId`) 
  ) ENGINE=InnoDB DEFAULT CHARSET=latin1;  

-- 
-- table structure for table lending_record
--
DROP TABLE IF EXISTS `lending_records`;
CREATE TABLE `lending_records` (
  `recordId` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `userId` int(11) UNSIGNED NOT NULL,
  `bookId` int(11) UNSIGNED NOT NULL,
  `issueDate` timestamp NOT NULL,
  `returnDate` timestamp DEFAULT NULL,
  PRIMARY KEY (`recordId`),
  CONSTRAINT `fk_lendingrecords_user` FOREIGN KEY (`userId`) REFERENCES `users`(`userId`),
  CONSTRAINT `fk_lendingrecords_book` FOREIGN KEY (`bookId`) REFERENCES `books`(`bookId`) 
  ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
