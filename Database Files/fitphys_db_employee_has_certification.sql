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
-- Table structure for table `employee_has_certification`
--

DROP TABLE IF EXISTS `employee_has_certification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee_has_certification` (
  `Employee_employee_id` int NOT NULL AUTO_INCREMENT,
  `Certification_certification_id` int NOT NULL,
  PRIMARY KEY (`Employee_employee_id`,`Certification_certification_id`),
  KEY `fk_Employee_has_Certification_Certification1_idx` (`Certification_certification_id`),
  KEY `fk_Employee_has_Certification_Employee1_idx` (`Employee_employee_id`),
  CONSTRAINT `fk_Employee_has_Certification_Certification1` FOREIGN KEY (`Certification_certification_id`) REFERENCES `certification` (`certification_id`),
  CONSTRAINT `fk_Employee_has_Certification_Employee1` FOREIGN KEY (`Employee_employee_id`) REFERENCES `employee` (`employee_id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee_has_certification`
--

LOCK TABLES `employee_has_certification` WRITE;
/*!40000 ALTER TABLE `employee_has_certification` DISABLE KEYS */;
INSERT INTO `employee_has_certification` VALUES (2,1),(6,1),(7,1),(15,1),(19,1),(36,1),(3,2),(5,2),(8,2),(18,2),(21,2),(4,3),(18,3),(20,3),(21,3),(2,4),(4,4),(7,4),(16,4),(3,5),(4,5),(9,5),(15,5),(19,5),(1,6),(9,6),(20,6),(36,6),(2,7),(7,7),(11,7),(20,7),(2,8),(7,8),(11,8),(13,8),(19,8),(20,8),(2,9),(7,9),(10,9),(14,9),(18,9),(5,10),(17,10),(20,10),(1,11),(2,11),(7,11),(9,11),(18,11),(2,12),(4,12),(16,12),(8,13),(20,13),(10,14),(12,14),(2,15),(7,15),(13,15),(15,15),(19,15),(2,16),(3,16),(8,16),(10,16),(18,16),(20,16),(21,16);
/*!40000 ALTER TABLE `employee_has_certification` ENABLE KEYS */;
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
