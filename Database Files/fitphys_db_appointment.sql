-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: fitphys_db
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `appointment`
--

DROP TABLE IF EXISTS `appointment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointment` (
  `appointment_id` int NOT NULL AUTO_INCREMENT,
  `Customer_customer_id` int NOT NULL,
  `Employee_employee_id` int NOT NULL,
  `Therapy_therapy_id` int NOT NULL,
  `appointment_startDateTime` varchar(20) NOT NULL,
  `appointment_endDateTime` varchar(20) NOT NULL,
  `appointment_desc` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`appointment_id`,`Customer_customer_id`,`Employee_employee_id`,`Therapy_therapy_id`),
  UNIQUE KEY `appointment_id_UNIQUE` (`appointment_id`),
  KEY `fk_Appointment_Customer1_idx` (`Customer_customer_id`),
  KEY `fk_Appointment_Employee1_idx` (`Employee_employee_id`),
  KEY `fk_Appointment_Therapy1_idx` (`Therapy_therapy_id`),
  CONSTRAINT `fk_Appointment_Customer1` FOREIGN KEY (`Customer_customer_id`) REFERENCES `customer` (`customer_id`),
  CONSTRAINT `fk_Appointment_Employee1` FOREIGN KEY (`Employee_employee_id`) REFERENCES `employee` (`employee_id`),
  CONSTRAINT `fk_Appointment_Therapy1` FOREIGN KEY (`Therapy_therapy_id`) REFERENCES `therapy` (`therapy_id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment`
--

LOCK TABLES `appointment` WRITE;
/*!40000 ALTER TABLE `appointment` DISABLE KEYS */;
INSERT INTO `appointment` VALUES (43,45,2,16,'2022-05-14 08:00:00','2022-05-14 09:00:00',''),(44,46,5,10,'2022-04-30 08:00:00','2022-04-30 09:00:00',''),(45,47,8,13,'2022-05-13 08:00:00','2022-05-13 09:00:00',''),(46,48,8,2,'2022-05-14 13:00:00','2022-05-14 14:00:00',''),(47,49,21,2,'2022-05-03 10:00:00','2022-05-03 11:00:00',''),(48,50,5,10,'2022-05-19 10:00:00','2022-05-19 11:00:00',''),(49,51,18,16,'2022-05-02 12:00:00','2022-05-02 13:00:00',''),(50,52,20,10,'2022-04-19 10:00:00','2022-04-19 11:00:00',''),(51,53,21,3,'2022-05-21 09:00:00','2022-05-21 10:00:00',''),(52,54,20,13,'2022-05-06 12:00:00','2022-05-06 13:00:00',''),(53,55,7,4,'2022-05-05 09:00:00','2022-05-05 10:00:00',''),(54,56,15,15,'2022-05-02 10:00:00','2022-05-02 11:00:00',''),(55,57,5,10,'2022-05-03 10:00:00','2022-05-03 11:00:00',''),(56,58,18,9,'2022-05-21 11:00:00','2022-05-21 12:00:00',''),(57,59,18,3,'2022-05-16 12:00:00','2022-05-16 13:00:00',''),(58,60,19,8,'2022-04-25 10:00:00','2022-04-25 11:00:00',''),(59,61,36,1,'2022-04-15 11:00:00','2022-04-15 12:00:00','');
/*!40000 ALTER TABLE `appointment` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-04-14 21:48:38
