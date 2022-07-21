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
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `employee_id` int NOT NULL AUTO_INCREMENT,
  `employee_userTyp` int NOT NULL,
  `employee_fname` varchar(45) NOT NULL,
  `employee_lname` varchar(45) DEFAULT NULL,
  `employee_uname` varchar(45) DEFAULT NULL,
  `employee_password` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`employee_id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,2,'Abraham',NULL,'ROOT','root'),(2,2,'Aeshia',NULL,NULL,NULL),(3,2,'April',NULL,NULL,NULL),(4,2,'Art',NULL,NULL,NULL),(5,2,'Becky',NULL,NULL,NULL),(6,2,'Carmen',NULL,NULL,NULL),(7,2,'Cole',NULL,NULL,NULL),(8,2,'Dennis',NULL,NULL,NULL),(9,2,'Jade',NULL,NULL,NULL),(10,2,'June',NULL,NULL,NULL),(11,2,'Kennan',NULL,NULL,NULL),(12,2,'Larry',NULL,NULL,NULL),(13,2,'Latesha',NULL,NULL,NULL),(14,2,'Mary',NULL,NULL,NULL),(15,2,'Mario',NULL,NULL,NULL),(16,2,'Nathan',NULL,NULL,NULL),(17,2,'Orlando',NULL,NULL,NULL),(18,2,'Rick',NULL,NULL,NULL),(19,2,'Sarah',NULL,NULL,NULL),(20,2,'Sasha',NULL,NULL,NULL),(21,2,'Wilbur',NULL,NULL,NULL),(22,2,'Zelda',NULL,NULL,NULL),(23,1,'Zucchini','Potato','root','root'),(36,2,'John','Adams','John_ZaB3','P@ssword');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
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
