-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.5.25a - MySQL Community Server (GPL)
-- Server OS:                    Win32
-- HeidiSQL version:             7.0.0.4053
-- Date/time:                    2015-12-10 19:58:44
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;

-- Dumping database structure for faculty
DROP DATABASE IF EXISTS `faculty`;
CREATE DATABASE IF NOT EXISTS `faculty` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `faculty`;


-- Dumping structure for table faculty.professor
DROP TABLE IF EXISTS `professor`;
CREATE TABLE IF NOT EXISTS `professor` (
  `professortype` varchar(100) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `office` varchar(100) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `otherInfo` varchar(200) DEFAULT NULL,
  `id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table faculty.registeredstudent
DROP TABLE IF EXISTS `registeredstudent`;
CREATE TABLE IF NOT EXISTS `registeredstudent` (
  `subjectID` int(10) NOT NULL,
  `StudentID` int(10) NOT NULL,
  KEY `FK_registeredstudent_subject` (`subjectID`),
  KEY `FK_registeredstudent_student` (`StudentID`),
  CONSTRAINT `FK_registeredstudent_student` FOREIGN KEY (`StudentID`) REFERENCES `student` (`id`),
  CONSTRAINT `FK_registeredstudent_subject` FOREIGN KEY (`subjectID`) REFERENCES `subject` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table faculty.student
DROP TABLE IF EXISTS `student`;
CREATE TABLE IF NOT EXISTS `student` (
  `id` int(10) NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table faculty.subject
DROP TABLE IF EXISTS `subject`;
CREATE TABLE IF NOT EXISTS `subject` (
  `name` varchar(50) DEFAULT NULL,
  `professorid` int(11) DEFAULT NULL,
  `timing` varchar(50) DEFAULT NULL,
  `otherInfo` varchar(50) DEFAULT NULL,
  `id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FK_subject_professor` (`professorid`),
  CONSTRAINT `FK_subject_professor` FOREIGN KEY (`professorid`) REFERENCES `professor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table faculty.users
DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `userName` varchar(50) DEFAULT NULL,
  `user_Password` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
/*!40014 SET FOREIGN_KEY_CHECKS=1 */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
