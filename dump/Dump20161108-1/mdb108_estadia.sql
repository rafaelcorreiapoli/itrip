-- MySQL dump 10.13  Distrib 5.7.13, for linux-glibc2.5 (x86_64)
--
-- Host: localhost    Database: mdb108
-- ------------------------------------------------------
-- Server version	5.5.5-10.1.18-MariaDB-1~xenial

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `estadia`
--

DROP TABLE IF EXISTS `estadia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `estadia` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data_chegada` datetime DEFAULT NULL,
  `data_saida` datetime DEFAULT NULL,
  `custo` double DEFAULT NULL,
  `cidade_id` int(11) NOT NULL,
  `hotel_id` int(11) NOT NULL,
  `roteiro_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `estadia_roteiro_fkey_idx` (`roteiro_id`),
  KEY `estadia_hotel_fkey_idx` (`hotel_id`),
  KEY `estadia_cidade_fkey_idx` (`cidade_id`),
  CONSTRAINT `estadia_cidade_fkey` FOREIGN KEY (`cidade_id`) REFERENCES `cidade` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `estadia_hotel_fkey` FOREIGN KEY (`hotel_id`) REFERENCES `hotel` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `estadia_roteiro_fkey` FOREIGN KEY (`roteiro_id`) REFERENCES `roteiro` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estadia`
--

LOCK TABLES `estadia` WRITE;
/*!40000 ALTER TABLE `estadia` DISABLE KEYS */;
/*!40000 ALTER TABLE `estadia` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-11-08  3:06:00
