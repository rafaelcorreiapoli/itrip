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
-- Table structure for table `itnerario`
--

DROP TABLE IF EXISTS `itnerario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `itnerario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `meio_de_transporte` varchar(45) DEFAULT NULL,
  `custo` double DEFAULT NULL,
  `duracao` int(11) DEFAULT NULL,
  `chega_em` int(11) DEFAULT NULL,
  `parte_de` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `itnerario_chega_em_fkey_idx` (`parte_de`),
  KEY `itnerario_chega_em_fkey_idx1` (`chega_em`),
  CONSTRAINT `itnerario_chega_em_fkey` FOREIGN KEY (`chega_em`) REFERENCES `cidade` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `itnerario_parte_de_fkey` FOREIGN KEY (`parte_de`) REFERENCES `cidade` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itnerario`
--

LOCK TABLES `itnerario` WRITE;
/*!40000 ALTER TABLE `itnerario` DISABLE KEYS */;
/*!40000 ALTER TABLE `itnerario` ENABLE KEYS */;
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
