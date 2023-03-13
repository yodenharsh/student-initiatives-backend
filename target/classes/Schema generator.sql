CREATE DATABASE `student-init` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

-- `student-init`.clubs definition

use `student-init`;

-- `student-init`.schools definition

CREATE TABLE `schools` (
  `school_id` int NOT NULL AUTO_INCREMENT,
  `school_name` varchar(255) COLLATE utf8mb4_bg_0900_ai_ci NOT NULL,
  PRIMARY KEY (`school_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bg_0900_ai_ci;

-- `student-init`.users definition

CREATE TABLE `users` (
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `clubs` (
  `club_id` int NOT NULL AUTO_INCREMENT,
  `club_name` varchar(255) NOT NULL,
  `president_name` varchar(255) NOT NULL,
  `vice_president_name` varchar(255) NOT NULL,
  `president_email` varchar(255) DEFAULT NULL,
  `school_id` int DEFAULT NULL,
  `mission` mediumtext,
  `vision` mediumtext,
  PRIMARY KEY (`club_id`),
  KEY `president_email` (`president_email`),
  KEY `school_id` (`school_id`),
  CONSTRAINT `clubs_ibfk_1` FOREIGN KEY (`president_email`) REFERENCES `users` (`email`),
  CONSTRAINT `clubs_ibfk_3` FOREIGN KEY (`school_id`) REFERENCES `schools` (`school_id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO `student-init`.schools (school_name) VALUES
	 ('School of Technology'),
	 ('School of Business'),
	 ('School of Arts & Design'),
	 ('School of Architecture & Planning'),
	 ('School of Liberal Arts & Humanities'),
	 ('School of Law'),
	 ('null');
	 
