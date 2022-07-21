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
-- Table structure for table `therapy`
--

DROP TABLE IF EXISTS `therapy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `therapy` (
  `therapy_id` int NOT NULL AUTO_INCREMENT,
  `therapy_name` varchar(45) NOT NULL,
  `therapy_desc` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`therapy_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `therapy`
--

LOCK TABLES `therapy` WRITE;
/*!40000 ALTER TABLE `therapy` DISABLE KEYS */;
INSERT INTO `therapy` VALUES (1,'Exercise Therapy','Advanced Exercise Therapist'),(2,'Pregnancy/Postpartum Therapy','Certificate of Achievement in Pregnancy and Postpartum Physical Therapy'),(3,'Pelvic Therapy','Certificate of Achievement in Pelvic Physical Therapy'),(4,'Hand Therapy','Certified Hand Therapist'),(5,'Taping Therapy','Certified Kinesio Taping Practitioner'),(6,'Lymphedema Therapy','Certified Lymphedema Therapist'),(7,'Rehabilitative Therapy','Certification in Rehabilitative Exercise'),(8,'Conditioning Therapy','Certified Strength and Conditioning Specialist'),(9,'Yoga Therapy','Certified Yoga Therapist'),(10,'Geriatic Therapy','Geriatric Certified Specialist'),(11,'Massage Therapy','Licensed Massage Therapist'),(12,'Orthopedic Therapy','Orthopedic Certified Specialist'),(13,'Alignment Therapy','Postural Alignment Specialist'),(14,'Pediatric Therapy','Pediatric Certified Specialist'),(15,'Sports Therapy','Sports Certified Specialist'),(16,'Women\'s Therapy','Women\'s Certified Specialist');
/*!40000 ALTER TABLE `therapy` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-04-14 21:48:37
