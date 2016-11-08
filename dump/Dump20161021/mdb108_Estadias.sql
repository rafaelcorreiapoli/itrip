-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: 143.107.102.14    Database: mdb108
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
-- Table structure for table `Estadias`
--

DROP TABLE IF EXISTS `Estadias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Estadias` (
  `idEstadia` int(11) NOT NULL AUTO_INCREMENT,
  `dataChegada` datetime DEFAULT NULL,
  `dataSaida` datetime DEFAULT NULL,
  `custo` double DEFAULT NULL,
  `idCidade` int(11) DEFAULT NULL,
  `idHotel` int(11) DEFAULT NULL,
  `idRoteiro` int(11) DEFAULT NULL,
  PRIMARY KEY (`idEstadia`),
  KEY `idHoteis_idx` (`idHotel`),
  KEY `idCidades_idx` (`idCidade`),
  KEY `Roteiro_idx` (`idRoteiro`),
  CONSTRAINT `Cidade` FOREIGN KEY (`idCidade`) REFERENCES `Cidades` (`idCidade`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `EstadiaRoteiroFK` FOREIGN KEY (`idRoteiro`) REFERENCES `Roteiros` (`idRoteiro`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `Hotel` FOREIGN KEY (`idHotel`) REFERENCES `Hoteis` (`idHotel`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Estadias`
--

LOCK TABLES `Estadias` WRITE;
/*!40000 ALTER TABLE `Estadias` DISABLE KEYS */;
INSERT INTO `Estadias` VALUES (1,'0000-00-00 00:00:00','0000-00-00 00:00:00',500,0,1,1);
/*!40000 ALTER TABLE `Estadias` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-10-21 16:35:11
