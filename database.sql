-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: database
-- ------------------------------------------------------
-- Server version	8.0.22

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
-- Table structure for table `faculity`
--

DROP TABLE IF EXISTS `faculity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `faculity` (
  `Faculity_id` int NOT NULL AUTO_INCREMENT,
  `Faculity_Name` varchar(45) NOT NULL,
  PRIMARY KEY (`Faculity_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `faculity`
--

LOCK TABLES `faculity` WRITE;
/*!40000 ALTER TABLE `faculity` DISABLE KEYS */;
INSERT INTO `faculity` VALUES (1,'FTR'),(2,'FSO'),(3,'RAFT'),(5,'wert'),(7,'erre'),(8,'sadf'),(9,'safsagf'),(10,'asagdsghdfg'),(12,'rty'),(13,'ytr'),(15,'trfg'),(16,'test2');
/*!40000 ALTER TABLE `faculity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `groups`
--

DROP TABLE IF EXISTS `groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `groups` (
  `Group_id` int NOT NULL AUTO_INCREMENT,
  `Faculity_id` int NOT NULL,
  `Group_Name` varchar(45) NOT NULL,
  PRIMARY KEY (`Group_id`),
  KEY `id_idx` (`Faculity_id`),
  CONSTRAINT `Faculity_id` FOREIGN KEY (`Group_id`) REFERENCES `faculity` (`Faculity_id`),
  CONSTRAINT `id` FOREIGN KEY (`Faculity_id`) REFERENCES `faculity` (`Faculity_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groups`
--

LOCK TABLES `groups` WRITE;
/*!40000 ALTER TABLE `groups` DISABLE KEYS */;
INSERT INTO `groups` VALUES (8,2,'fdhgh'),(9,5,'ASS'),(10,2,'safdsaf');
/*!40000 ALTER TABLE `groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `students`
--

DROP TABLE IF EXISTS `students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `students` (
  `Student_id` int NOT NULL AUTO_INCREMENT,
  `Student_Name` varchar(80) NOT NULL,
  `Group_id` int NOT NULL,
  `DateOfEnrollment` varchar(45) NOT NULL,
  PRIMARY KEY (`Student_id`),
  KEY `Gr_idx` (`Group_id`),
  CONSTRAINT `gr` FOREIGN KEY (`Group_id`) REFERENCES `groups` (`Group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students`
--

LOCK TABLES `students` WRITE;
/*!40000 ALTER TABLE `students` DISABLE KEYS */;
INSERT INTO `students` VALUES (1,'sagsdg dsgsgr rdfgbdfgh',8,'2131.12412.121'),(3,'asdasd',8,'dasfasgf 1223'),(4,'dsafa dsad asdas',9,'213 12321'),(5,'asdsa fsaf',10,'12/22/231');
/*!40000 ALTER TABLE `students` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'database'
--

--
-- Dumping routines for database 'database'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-23 19:01:43
