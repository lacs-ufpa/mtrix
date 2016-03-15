-- MySQL dump 10.13  Distrib 5.6.11, for Win64 (x86_64)
--
-- Host: localhost    Database: labgame
-- ------------------------------------------------------
-- Server version	5.6.11

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
-- Table structure for table `autorizacao`
--

DROP TABLE IF EXISTS `autorizacao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `autorizacao` (
  `nome` varchar(255) NOT NULL,
  PRIMARY KEY (`nome`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `autorizacao`
--

LOCK TABLES `autorizacao` WRITE;
/*!40000 ALTER TABLE `autorizacao` DISABLE KEYS */;
/*!40000 ALTER TABLE `autorizacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `condicao`
--

DROP TABLE IF EXISTS `condicao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `condicao` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `criador` varchar(255) DEFAULT NULL,
  `fimPorPorcentAcertoCult` int(11) DEFAULT NULL,
  `fimPorPorcentAcertoIndiv` int(11) DEFAULT NULL,
  `fimPorQuantCiclo` int(11) NOT NULL,
  `identificador` varchar(255) DEFAULT NULL,
  `incrementoPont` varchar(255) DEFAULT NULL,
  `limitePorcenAcertoIncrem` varchar(255) DEFAULT NULL,
  `limiteRodadasIncrem` varchar(255) DEFAULT NULL,
  `minimoCiclos` int(11) NOT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `objetivo` varchar(255) DEFAULT NULL,
  `ultimosXCiclos` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `condicao`
--

LOCK TABLES `condicao` WRITE;
/*!40000 ALTER TABLE `condicao` DISABLE KEYS */;
INSERT INTO `condicao` VALUES (1,'Adailton',NULL,NULL,4,NULL,NULL,NULL,NULL,0,'Teste','Testar',0),(2,'Adailton',NULL,NULL,0,NULL,NULL,NULL,NULL,0,'Condicao01','teste',0),(3,'Adailton',NULL,NULL,3,NULL,NULL,NULL,NULL,0,'Teste condicao','Testar mais uma vez',0),(4,'Adailton',NULL,NULL,3,NULL,NULL,NULL,NULL,0,'Teste 0','testar',0),(5,'Adailton',NULL,NULL,6,NULL,NULL,NULL,NULL,0,'Teste 1','Testar',0),(6,'Adailton',NULL,NULL,3,NULL,NULL,NULL,NULL,0,'Cond01','cond',0),(7,'Adailton',NULL,NULL,3,NULL,NULL,NULL,NULL,0,'Cond02','cond',0),(8,'Adailton',NULL,NULL,0,NULL,NULL,NULL,NULL,0,'Cond03','cond',0),(9,'Adailton',NULL,NULL,3,NULL,NULL,NULL,NULL,0,'Cond04','cond',0);
/*!40000 ALTER TABLE `condicao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `condicao_pontcult`
--

DROP TABLE IF EXISTS `condicao_pontcult`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `condicao_pontcult` (
  `condicao_id` bigint(20) NOT NULL,
  `pontCult_id` bigint(20) NOT NULL,
  KEY `FK_3kpu4gxuilhpjeoewtwijbhf` (`pontCult_id`),
  KEY `FK_g2d5o03f06hyeqxe4srgr5jos` (`condicao_id`),
  CONSTRAINT `FK_3kpu4gxuilhpjeoewtwijbhf` FOREIGN KEY (`pontCult_id`) REFERENCES `pontuacaocultural` (`id`),
  CONSTRAINT `FK_g2d5o03f06hyeqxe4srgr5jos` FOREIGN KEY (`condicao_id`) REFERENCES `condicao` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `condicao_pontcult`
--

LOCK TABLES `condicao_pontcult` WRITE;
/*!40000 ALTER TABLE `condicao_pontcult` DISABLE KEYS */;
INSERT INTO `condicao_pontcult` VALUES (1,1),(2,1),(3,1),(4,1),(5,1),(6,2),(7,2),(8,3),(9,2);
/*!40000 ALTER TABLE `condicao_pontcult` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `condicao_pontindiv`
--

DROP TABLE IF EXISTS `condicao_pontindiv`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `condicao_pontindiv` (
  `condicao_id` bigint(20) NOT NULL,
  `pontIndiv_id` bigint(20) NOT NULL,
  KEY `FK_hrjj4kbqde3gpsi5q4l0acm7u` (`pontIndiv_id`),
  KEY `FK_h75ujno3ly1s5a9lqvvo3o0al` (`condicao_id`),
  CONSTRAINT `FK_h75ujno3ly1s5a9lqvvo3o0al` FOREIGN KEY (`condicao_id`) REFERENCES `condicao` (`id`),
  CONSTRAINT `FK_hrjj4kbqde3gpsi5q4l0acm7u` FOREIGN KEY (`pontIndiv_id`) REFERENCES `pontuacaoindividual` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `condicao_pontindiv`
--

LOCK TABLES `condicao_pontindiv` WRITE;
/*!40000 ALTER TABLE `condicao_pontindiv` DISABLE KEYS */;
INSERT INTO `condicao_pontindiv` VALUES (1,1),(2,1),(3,1),(4,1),(5,1),(6,1),(7,2),(8,3),(9,4);
/*!40000 ALTER TABLE `condicao_pontindiv` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `condicaoexperimento`
--

DROP TABLE IF EXISTS `condicaoexperimento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `condicaoexperimento` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `posicao` int(11) NOT NULL,
  `condicao_id` bigint(20) DEFAULT NULL,
  `experimento_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_4kncdneewiqeff5f1iqfqhtfg` (`condicao_id`),
  KEY `FK_p9ttdwnm7baydblph4gd9peqc` (`experimento_id`),
  CONSTRAINT `FK_4kncdneewiqeff5f1iqfqhtfg` FOREIGN KEY (`condicao_id`) REFERENCES `condicao` (`id`),
  CONSTRAINT `FK_p9ttdwnm7baydblph4gd9peqc` FOREIGN KEY (`experimento_id`) REFERENCES `experimento` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `condicaoexperimento`
--

LOCK TABLES `condicaoexperimento` WRITE;
/*!40000 ALTER TABLE `condicaoexperimento` DISABLE KEYS */;
INSERT INTO `condicaoexperimento` VALUES (1,1,1,2),(2,2,1,2),(3,3,1,2),(4,1,1,3),(5,4,1,3),(6,5,1,3),(7,2,2,3),(8,3,2,3),(9,2,4,4),(10,3,4,4),(11,5,4,4),(12,1,5,4),(13,4,5,4),(14,2,6,5),(15,4,6,5),(16,1,7,5),(17,5,7,5),(18,3,8,5),(19,1,6,6),(20,4,6,6),(21,2,8,6),(22,5,8,6),(23,4,7,8),(24,5,7,8),(25,4,7,8),(26,5,7,8),(27,1,6,8),(28,3,6,8),(29,1,6,8),(30,3,6,8),(31,1,6,9),(32,3,6,9),(33,1,6,9),(34,3,6,9),(35,2,7,9),(36,5,7,9),(37,2,7,9),(38,4,7,9),(39,4,8,10),(40,5,8,10),(41,2,8,10),(42,4,8,10),(43,1,6,10),(44,3,6,10),(45,1,6,10),(46,3,6,10),(47,1,7,11),(48,4,7,11),(49,1,7,11),(50,4,7,11),(51,2,9,11),(52,5,9,11),(53,2,9,11),(54,5,9,11),(55,1,6,12),(56,2,6,12),(57,1,6,12),(58,2,6,12),(59,5,7,12),(60,6,7,12),(61,3,7,12),(62,4,7,12),(63,1,7,13),(64,2,7,13),(65,2,8,13),(66,3,8,13),(67,1,7,14),(68,2,7,14),(69,5,7,14),(70,3,9,14),(71,4,9,14);
/*!40000 ALTER TABLE `condicaoexperimento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contato`
--

DROP TABLE IF EXISTS `contato`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contato` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bairro` varchar(255) DEFAULT NULL,
  `cep` varchar(255) DEFAULT NULL,
  `cidade` varchar(255) DEFAULT NULL,
  `email1` varchar(255) DEFAULT NULL,
  `email2` varchar(255) DEFAULT NULL,
  `rua` varchar(255) DEFAULT NULL,
  `telefone1` varchar(255) DEFAULT NULL,
  `telefone2` varchar(255) DEFAULT NULL,
  `usuario_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contato`
--

LOCK TABLES `contato` WRITE;
/*!40000 ALTER TABLE `contato` DISABLE KEYS */;
/*!40000 ALTER TABLE `contato` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `experimento`
--

DROP TABLE IF EXISTS `experimento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `experimento` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  `objetivo` varchar(255) DEFAULT NULL,
  `pontInicialCultural` int(11) DEFAULT NULL,
  `pontInicialIndividual` int(11) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `tipoMatriz` int(11) NOT NULL,
  `pesquisador_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_lcodxex3koah7xem071h8vw30` (`pesquisador_id`),
  CONSTRAINT `FK_lcodxex3koah7xem071h8vw30` FOREIGN KEY (`pesquisador_id`) REFERENCES `pesquisador` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `experimento`
--

LOCK TABLES `experimento` WRITE;
/*!40000 ALTER TABLE `experimento` DISABLE KEYS */;
INSERT INTO `experimento` VALUES (1,'Teste Experimento','Testar Experimento',4,NULL,'EXECUTANDO',2,1),(2,'Teste Condição Posição','Testar',NULL,NULL,'CRIADO',2,1),(3,'Teste Condição Posição 02','testar',NULL,NULL,'CRIADO',2,1),(4,'Teste Consistência','Testar',8,2,'EXECUTANDO',2,1),(5,'Teste Ordenação Condições','Testar',NULL,NULL,'EXECUTANDO',2,1),(6,'Validação das posições','teste',NULL,NULL,'CRIADO',2,1),(7,'Validar condição 01','testar',NULL,NULL,'CRIADO',2,1),(8,'Teste Condição Posição 02','test',NULL,NULL,'CRIADO',2,1),(9,'Teste Condição Posição 03','test',NULL,NULL,'CRIADO',2,1),(10,'Teste Condição Posição 04','test',NULL,NULL,'CRIADO',2,1),(11,'Teste Condição Posição 05','test',NULL,NULL,'CRIADO',2,1),(12,'Teste Condição Posição 06','te',NULL,NULL,'CRIADO',2,1),(13,'Teste Condição Posição 07','test',NULL,NULL,'CRIADO',2,1),(14,'Teste Condição Posição 08','test',NULL,NULL,'EXECUTANDO',2,1);
/*!40000 ALTER TABLE `experimento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `experimento_condicao`
--

DROP TABLE IF EXISTS `experimento_condicao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `experimento_condicao` (
  `experimento_id` bigint(20) NOT NULL,
  `condicao_id` bigint(20) NOT NULL,
  KEY `FK_eq2y3vloeh19okv771xxdulfv` (`condicao_id`),
  KEY `FK_3y8so6f1wwkq6jixbr65fn6oo` (`experimento_id`),
  CONSTRAINT `FK_3y8so6f1wwkq6jixbr65fn6oo` FOREIGN KEY (`experimento_id`) REFERENCES `experimento` (`id`),
  CONSTRAINT `FK_eq2y3vloeh19okv771xxdulfv` FOREIGN KEY (`condicao_id`) REFERENCES `condicao` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `experimento_condicao`
--

LOCK TABLES `experimento_condicao` WRITE;
/*!40000 ALTER TABLE `experimento_condicao` DISABLE KEYS */;
INSERT INTO `experimento_condicao` VALUES (1,1);
/*!40000 ALTER TABLE `experimento_condicao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequences`
--

DROP TABLE IF EXISTS `hibernate_sequences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) DEFAULT NULL,
  `sequence_next_hi_value` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequences`
--

LOCK TABLES `hibernate_sequences` WRITE;
/*!40000 ALTER TABLE `hibernate_sequences` DISABLE KEYS */;
INSERT INTO `hibernate_sequences` VALUES ('Usuario',13);
/*!40000 ALTER TABLE `hibernate_sequences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jogada`
--

DROP TABLE IF EXISTS `jogada`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jogada` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `colunaSelecionada` varchar(255) DEFAULT NULL,
  `linhaSelecionada` int(11) NOT NULL,
  `jogador_id` bigint(20) NOT NULL,
  `momento` datetime DEFAULT NULL,
  `pontuacaoCultural` int(11) NOT NULL,
  `pontuacaoIndividual` int(11) NOT NULL,
  `rodada` int(11) NOT NULL,
  `experimento_id` bigint(20) NOT NULL,
  `idCondicao` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_g39cow18ub0veddqs413xrybr` (`jogador_id`),
  CONSTRAINT `FK_g39cow18ub0veddqs413xrybr` FOREIGN KEY (`jogador_id`) REFERENCES `jogador` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=391 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jogada`
--

LOCK TABLES `jogada` WRITE;
/*!40000 ALTER TABLE `jogada` DISABLE KEYS */;
INSERT INTO `jogada` VALUES (1,NULL,4,32768,NULL,0,0,0,0,NULL),(2,NULL,6,32769,NULL,0,0,0,0,NULL),(3,NULL,4,32770,NULL,0,0,0,0,NULL),(4,NULL,2,32768,NULL,0,0,0,0,NULL),(5,NULL,5,32769,NULL,0,0,0,0,NULL),(6,NULL,4,32770,NULL,0,0,0,0,NULL),(7,NULL,5,32768,NULL,0,0,0,0,NULL),(8,NULL,5,32769,NULL,0,0,0,0,NULL),(9,NULL,3,32770,NULL,0,0,0,0,NULL),(10,NULL,2,32768,NULL,0,0,0,0,NULL),(11,NULL,6,32769,NULL,0,0,0,0,NULL),(12,NULL,6,32770,NULL,0,0,0,0,NULL),(13,NULL,2,32768,NULL,0,0,0,0,NULL),(14,NULL,4,32769,NULL,0,0,0,0,NULL),(15,NULL,2,32770,NULL,0,0,0,0,NULL),(16,NULL,2,32768,NULL,0,0,0,0,NULL),(17,NULL,6,32769,NULL,0,0,0,0,NULL),(18,NULL,8,32770,NULL,0,0,0,0,NULL),(19,NULL,3,32768,NULL,0,0,0,0,NULL),(20,NULL,4,32769,NULL,0,0,0,0,NULL),(21,NULL,4,32770,NULL,0,0,0,0,NULL),(22,NULL,3,32768,NULL,0,0,0,0,NULL),(23,NULL,3,32769,NULL,0,0,0,0,NULL),(24,NULL,4,32770,NULL,0,0,0,0,NULL),(25,NULL,3,32768,NULL,0,0,0,0,NULL),(26,NULL,4,32769,NULL,0,0,0,0,NULL),(27,NULL,3,32770,NULL,0,0,0,0,NULL),(28,NULL,3,32768,NULL,0,0,0,0,NULL),(29,NULL,2,32769,NULL,0,0,0,0,NULL),(30,NULL,4,32770,NULL,0,0,0,0,NULL),(31,NULL,2,32768,NULL,0,0,0,0,NULL),(32,NULL,3,32769,NULL,0,0,0,0,NULL),(33,NULL,3,32770,NULL,0,0,0,0,NULL),(34,NULL,2,32768,NULL,0,0,0,0,NULL),(35,NULL,2,32769,NULL,0,0,0,0,NULL),(36,NULL,3,32770,NULL,0,0,0,0,NULL),(37,NULL,3,32768,NULL,0,0,0,0,NULL),(38,NULL,4,32769,NULL,0,0,0,0,NULL),(39,NULL,5,32770,NULL,0,0,0,0,NULL),(40,NULL,3,32768,NULL,0,0,0,0,NULL),(41,NULL,2,32769,NULL,0,0,0,0,NULL),(42,NULL,3,32770,NULL,0,0,0,0,NULL),(43,NULL,2,32768,NULL,0,0,0,0,NULL),(44,NULL,4,32769,NULL,0,0,0,0,NULL),(45,NULL,2,32770,NULL,0,0,0,0,NULL),(46,NULL,2,32768,NULL,0,0,0,0,NULL),(47,NULL,2,32769,NULL,0,0,0,0,NULL),(48,NULL,2,32770,NULL,0,0,0,0,NULL),(49,NULL,3,32768,NULL,0,0,0,0,NULL),(50,NULL,2,32769,NULL,0,0,0,0,NULL),(51,NULL,2,32770,NULL,0,0,0,0,NULL),(52,NULL,2,32768,NULL,0,0,0,0,NULL),(53,NULL,3,32769,NULL,0,0,0,0,NULL),(54,NULL,3,32770,NULL,0,0,0,0,NULL),(55,NULL,3,32768,NULL,0,0,0,0,NULL),(56,NULL,3,32769,NULL,0,0,0,0,NULL),(57,NULL,3,32770,NULL,0,0,0,0,NULL),(58,NULL,7,32768,NULL,0,0,0,0,NULL),(59,NULL,3,32769,NULL,0,0,0,0,NULL),(60,NULL,5,32770,NULL,0,0,0,0,NULL),(61,NULL,4,32768,NULL,0,0,0,0,NULL),(62,NULL,4,32769,NULL,0,0,0,0,NULL),(63,NULL,7,32770,NULL,0,0,0,0,NULL),(64,NULL,7,32768,NULL,0,0,0,0,NULL),(65,NULL,5,32769,NULL,0,0,0,0,NULL),(66,NULL,2,32770,NULL,0,0,0,0,NULL),(67,NULL,2,32768,NULL,0,0,0,0,NULL),(68,NULL,2,32769,NULL,0,0,0,0,NULL),(69,NULL,3,32770,NULL,0,0,0,0,NULL),(70,NULL,1,32768,NULL,0,0,0,0,NULL),(71,NULL,2,32769,NULL,0,0,0,0,NULL),(72,NULL,4,32770,NULL,0,0,0,0,NULL),(73,NULL,1,32768,NULL,0,0,0,0,NULL),(74,NULL,1,32769,NULL,0,0,0,0,NULL),(75,NULL,5,32770,NULL,0,0,0,0,NULL),(76,NULL,10,32768,NULL,0,0,0,0,NULL),(77,NULL,6,32769,NULL,0,0,0,0,NULL),(78,NULL,1,32770,NULL,0,0,0,0,NULL),(79,NULL,2,32768,NULL,0,0,0,0,NULL),(80,NULL,4,32769,NULL,0,0,0,0,NULL),(81,NULL,3,32770,NULL,0,0,0,0,NULL),(82,NULL,4,32768,NULL,0,0,0,0,NULL),(83,NULL,6,32769,NULL,0,0,0,0,NULL),(84,NULL,4,32770,NULL,0,0,0,0,NULL),(85,NULL,2,32768,NULL,0,0,0,0,NULL),(86,NULL,5,32769,NULL,0,0,0,0,NULL),(87,NULL,4,32770,NULL,0,0,0,0,NULL),(88,NULL,2,32768,NULL,0,0,0,0,NULL),(89,NULL,4,32769,NULL,0,0,0,0,NULL),(90,NULL,4,32770,NULL,0,0,0,0,NULL),(91,NULL,8,32768,NULL,0,0,0,0,NULL),(92,NULL,5,32769,NULL,0,0,0,0,NULL),(93,NULL,2,32770,NULL,0,0,0,0,NULL),(94,NULL,10,32768,NULL,0,0,0,0,NULL),(95,NULL,6,32769,NULL,0,0,0,0,NULL),(96,NULL,3,32770,NULL,0,0,0,0,NULL),(97,NULL,6,32768,NULL,0,0,0,0,NULL),(98,NULL,4,32769,NULL,0,0,0,0,NULL),(99,NULL,3,32770,NULL,0,0,0,0,NULL),(100,NULL,5,32768,NULL,0,0,0,0,NULL),(101,NULL,4,32769,NULL,0,0,0,0,NULL),(102,NULL,2,32770,NULL,0,0,0,0,NULL),(103,NULL,5,32768,NULL,0,0,0,0,NULL),(104,NULL,7,32769,NULL,0,0,0,0,NULL),(105,NULL,10,32770,NULL,0,0,0,0,NULL),(106,NULL,8,32768,NULL,0,0,0,0,NULL),(107,NULL,3,32769,NULL,0,0,0,0,NULL),(108,NULL,5,32770,NULL,0,0,0,0,NULL),(109,NULL,1,32768,NULL,0,0,0,0,NULL),(110,NULL,6,32769,NULL,0,0,0,0,NULL),(111,NULL,3,32770,NULL,0,0,0,0,NULL),(112,NULL,8,32768,NULL,0,0,0,0,NULL),(113,NULL,2,32769,NULL,0,0,0,0,NULL),(114,NULL,4,32770,NULL,0,0,0,0,NULL),(115,NULL,10,32768,NULL,0,0,0,0,NULL),(116,NULL,4,32769,NULL,0,0,0,0,NULL),(117,NULL,6,32770,NULL,0,0,0,0,NULL),(118,NULL,2,32768,NULL,0,0,0,0,NULL),(119,NULL,2,32769,NULL,0,0,0,0,NULL),(120,NULL,2,32770,NULL,0,0,0,0,NULL),(121,NULL,2,32768,NULL,0,0,0,0,NULL),(122,NULL,2,32769,NULL,0,0,0,0,NULL),(123,NULL,2,32770,NULL,0,0,0,0,NULL),(124,NULL,2,32768,NULL,0,0,0,0,NULL),(125,NULL,2,32769,NULL,0,0,0,0,NULL),(126,NULL,5,32770,NULL,0,0,0,0,NULL),(127,NULL,1,32768,NULL,0,0,0,0,NULL),(128,NULL,2,32769,NULL,0,0,0,0,NULL),(129,NULL,2,32770,NULL,0,0,0,0,NULL),(130,NULL,2,32768,NULL,0,0,0,0,NULL),(131,NULL,2,32769,NULL,0,0,0,0,NULL),(132,NULL,2,32770,NULL,0,0,0,0,NULL),(133,NULL,2,32768,NULL,0,0,0,0,NULL),(134,NULL,2,32769,NULL,0,0,0,0,NULL),(135,NULL,3,32770,NULL,0,0,0,0,NULL),(136,NULL,2,32768,NULL,0,0,0,0,NULL),(137,NULL,2,32769,NULL,0,0,0,0,NULL),(138,NULL,5,32770,NULL,0,0,0,0,NULL),(139,NULL,2,32768,NULL,0,0,0,0,NULL),(140,NULL,2,32769,NULL,0,0,0,0,NULL),(141,NULL,2,32770,NULL,0,0,0,0,NULL),(142,NULL,2,32768,NULL,0,0,0,0,NULL),(143,NULL,2,32769,NULL,0,0,0,0,NULL),(144,NULL,4,32770,NULL,0,0,0,0,NULL),(145,NULL,2,32768,NULL,0,0,0,0,NULL),(146,NULL,2,32769,NULL,0,0,0,0,NULL),(147,NULL,3,32770,NULL,0,0,0,0,NULL),(148,NULL,2,32768,NULL,0,0,0,0,NULL),(149,NULL,2,32769,NULL,0,0,0,0,NULL),(150,NULL,2,32770,NULL,0,0,0,0,NULL),(151,NULL,2,65536,'2014-07-01 00:00:00',0,6,1,4,NULL),(152,NULL,2,65537,'2014-07-01 00:00:00',0,4,1,4,NULL),(153,NULL,2,65538,'2014-07-01 00:00:00',0,4,1,4,NULL),(154,NULL,3,98304,'2014-07-02 00:00:00',0,0,1,5,NULL),(155,NULL,8,98305,'2014-07-02 00:00:00',0,6,1,5,NULL),(156,NULL,8,98306,'2014-07-02 00:00:00',0,6,1,5,NULL),(157,NULL,4,98304,'2014-07-02 00:00:00',0,0,1,5,NULL),(158,NULL,4,98305,'2014-07-02 00:00:00',0,0,1,5,NULL),(159,NULL,4,98306,'2014-07-02 00:00:00',0,0,1,5,NULL),(160,NULL,6,98304,'2014-07-02 00:00:00',0,0,1,5,NULL),(161,NULL,3,98305,'2014-07-02 00:00:00',0,0,1,5,NULL),(162,NULL,6,98306,'2014-07-02 00:00:00',0,0,1,5,NULL),(163,NULL,2,98304,'2014-07-02 00:00:00',0,0,1,5,NULL),(164,NULL,2,98305,'2014-07-02 00:00:00',0,0,1,5,NULL),(165,NULL,5,98306,'2014-07-02 00:00:00',0,0,1,5,NULL),(166,NULL,5,98304,'2014-07-02 00:00:00',0,0,1,5,NULL),(167,NULL,4,98305,'2014-07-02 00:00:00',0,0,1,5,NULL),(168,NULL,9,98306,'2014-07-02 00:00:00',0,0,1,5,NULL),(169,NULL,8,393216,'2014-07-05 00:00:00',0,6,1,14,NULL),(170,NULL,2,393217,'2014-07-05 00:00:00',0,0,1,14,NULL),(171,NULL,3,393216,'2014-07-05 00:00:00',0,0,1,14,NULL),(172,NULL,8,393217,'2014-07-05 00:00:00',0,6,1,14,NULL),(173,NULL,8,393216,'2014-07-05 00:00:00',0,12,1,14,NULL),(174,NULL,8,393217,'2014-07-05 00:00:00',0,12,1,14,NULL),(175,NULL,7,393216,'2014-07-05 00:00:00',0,0,1,14,NULL),(176,NULL,3,393216,'2014-07-07 00:00:00',0,0,1,14,NULL),(177,NULL,8,393217,'2014-07-07 00:00:00',0,18,1,14,NULL),(178,NULL,3,393216,'2014-07-07 00:00:00',0,0,1,14,NULL),(179,NULL,3,393216,'2014-07-07 00:00:00',0,0,1,14,NULL),(180,NULL,7,393217,'2014-07-07 00:00:00',0,0,1,14,NULL),(181,NULL,3,393216,'2014-07-07 00:00:00',0,0,1,14,NULL),(182,NULL,5,393217,'2014-07-07 00:00:00',0,0,1,14,NULL),(183,NULL,8,393216,'2014-07-07 00:00:00',0,6,1,14,NULL),(184,NULL,4,393217,'2014-07-07 00:00:00',0,0,1,14,NULL),(185,NULL,4,393216,'2014-07-07 00:00:00',0,0,1,14,NULL),(186,NULL,7,393217,'2014-07-07 00:00:00',0,0,1,14,NULL),(187,NULL,8,393216,'2014-07-07 00:00:00',0,6,1,14,NULL),(188,NULL,8,393217,'2014-07-07 00:00:00',0,6,1,14,NULL),(189,NULL,4,393216,'2014-07-07 00:00:00',0,0,1,14,NULL),(190,NULL,6,393217,'2014-07-07 00:00:00',0,0,1,14,NULL),(191,NULL,3,393216,'2014-07-07 00:00:00',0,0,1,14,NULL),(192,NULL,5,393217,'2014-07-07 00:00:00',0,0,1,14,NULL),(193,NULL,2,393216,'2014-07-07 00:00:00',0,0,1,14,NULL),(194,NULL,8,393217,'2014-07-07 00:00:00',0,6,1,14,NULL),(195,NULL,3,393216,'2014-07-07 00:00:00',0,0,1,14,NULL),(196,NULL,5,393217,'2014-07-07 00:00:00',0,0,1,14,NULL),(197,NULL,3,393216,'2014-07-07 00:00:00',0,0,1,14,NULL),(198,NULL,7,393217,'2014-07-07 00:00:00',0,0,1,14,NULL),(199,NULL,3,393216,'2014-07-07 00:00:00',0,0,1,14,NULL),(200,NULL,7,393217,'2014-07-07 00:00:00',0,0,1,14,NULL),(201,NULL,5,393216,'2014-07-07 00:00:00',2,0,1,14,NULL),(202,NULL,9,393217,'2014-07-07 00:00:00',2,0,1,14,NULL),(203,NULL,8,393216,'2014-07-07 00:00:00',0,6,1,14,NULL),(204,NULL,4,393217,'2014-07-07 00:00:00',0,0,1,14,NULL),(205,NULL,8,393216,'2014-07-07 00:00:00',0,6,1,14,NULL),(206,NULL,2,393217,'2014-07-07 00:00:00',0,0,1,14,NULL),(207,NULL,2,393216,'2014-07-07 00:00:00',0,0,1,14,NULL),(208,NULL,3,393217,'2014-07-07 00:00:00',0,0,1,14,NULL),(209,NULL,3,393216,'2014-07-07 00:00:00',0,0,1,14,NULL),(210,NULL,4,393217,'2014-07-07 00:00:00',0,0,1,14,NULL),(211,NULL,2,393216,'2014-07-07 00:00:00',0,0,1,14,NULL),(212,NULL,6,393217,'2014-07-07 00:00:00',0,0,1,14,NULL),(213,NULL,8,393216,'2014-07-07 00:00:00',0,6,1,14,NULL),(214,NULL,8,393217,'2014-07-07 00:00:00',0,6,1,14,NULL),(215,NULL,7,393216,'2014-07-07 00:00:00',0,0,1,14,NULL),(216,NULL,8,393217,'2014-07-07 00:00:00',0,0,1,14,NULL),(217,NULL,8,393216,'2014-07-07 00:00:00',0,6,1,14,NULL),(218,NULL,8,393217,'2014-07-07 00:00:00',0,6,1,14,NULL),(219,NULL,3,393216,'2014-07-07 00:00:00',0,0,1,14,NULL),(220,NULL,7,393217,'2014-07-07 00:00:00',0,0,1,14,NULL),(221,NULL,8,393216,'2014-07-07 00:00:00',0,6,1,14,NULL),(222,NULL,5,393217,'2014-07-07 00:00:00',0,0,1,14,NULL),(223,NULL,3,393216,'2014-07-07 00:00:00',2,0,1,14,NULL),(224,NULL,9,393217,'2014-07-07 00:00:00',2,0,1,14,NULL),(225,NULL,9,393216,'2014-07-07 00:00:00',0,0,1,14,NULL),(226,NULL,3,393217,'2014-07-07 00:00:00',0,0,1,14,NULL),(227,NULL,3,393216,'2014-07-07 00:00:00',0,0,1,14,NULL),(228,NULL,5,393217,'2014-07-07 00:00:00',0,0,1,14,NULL),(229,NULL,3,393216,'2014-07-07 00:00:00',2,0,1,14,NULL),(230,NULL,5,393217,'2014-07-07 00:00:00',2,0,1,14,NULL),(231,NULL,3,393216,'2014-07-07 00:00:00',2,0,1,14,NULL),(232,NULL,9,393217,'2014-07-07 00:00:00',2,0,1,14,NULL),(233,NULL,8,393216,'2014-07-07 00:00:00',0,6,1,14,NULL),(234,NULL,3,393217,'2014-07-07 00:00:00',0,0,1,14,NULL),(235,NULL,5,393216,'2014-07-07 00:00:00',0,0,1,14,NULL),(236,NULL,8,393217,'2014-07-07 00:00:00',0,6,1,14,NULL),(237,NULL,4,393216,'2014-07-07 00:00:00',0,0,1,14,NULL),(238,NULL,6,393217,'2014-07-07 00:00:00',0,0,1,14,NULL),(239,NULL,3,393216,'2014-07-07 00:00:00',2,0,1,14,NULL),(240,NULL,7,393217,'2014-07-07 00:00:00',2,0,1,14,NULL),(241,NULL,3,393216,'2014-07-07 00:00:00',0,0,1,14,NULL),(242,NULL,8,393217,'2014-07-07 00:00:00',0,6,1,14,NULL),(243,NULL,5,393216,'2014-07-07 00:00:00',2,0,1,14,NULL),(244,NULL,7,393217,'2014-07-07 00:00:00',2,0,1,14,NULL),(245,NULL,8,393216,'2014-07-07 00:00:00',0,6,1,14,NULL),(246,NULL,5,393217,'2014-07-07 00:00:00',0,0,1,14,NULL),(247,NULL,5,393216,'2014-07-07 00:00:00',0,0,1,14,NULL),(248,NULL,4,393217,'2014-07-07 00:00:00',0,0,1,14,NULL),(249,NULL,6,393216,'2014-07-07 00:00:00',0,0,1,14,NULL),(250,NULL,3,393217,'2014-07-07 00:00:00',0,0,1,14,NULL),(251,NULL,4,393216,'2014-07-08 00:00:00',0,0,1,14,NULL),(252,NULL,7,393217,'2014-07-08 00:00:00',0,0,1,14,NULL),(253,NULL,7,393216,'2014-07-08 00:00:00',2,0,1,14,NULL),(254,NULL,3,393217,'2014-07-08 00:00:00',2,0,1,14,NULL),(255,NULL,8,393216,'2014-07-08 00:00:00',0,6,1,14,NULL),(256,NULL,3,393217,'2014-07-08 00:00:00',0,0,1,14,NULL),(257,NULL,5,393216,'2014-07-08 00:00:00',2,0,1,14,NULL),(258,NULL,7,393217,'2014-07-08 00:00:00',2,0,1,14,NULL),(259,NULL,4,393216,'2014-07-08 00:00:00',0,0,1,14,NULL),(260,NULL,8,393217,'2014-07-08 00:00:00',0,6,1,14,NULL),(261,NULL,8,393216,'2014-07-08 00:00:00',0,6,1,14,NULL),(262,NULL,4,393217,'2014-07-08 00:00:00',0,0,1,14,NULL),(263,NULL,6,393216,'2014-07-08 00:00:00',0,0,1,14,NULL),(264,NULL,8,393217,'2014-07-08 00:00:00',0,6,1,14,NULL),(265,NULL,8,393216,'2014-07-08 00:00:00',0,6,1,14,NULL),(266,NULL,7,393217,'2014-07-08 00:00:00',0,0,1,14,NULL),(267,'C',3,393216,'2014-07-09 22:35:04',0,0,1,14,NULL),(268,'F',4,393217,'2014-07-09 22:35:29',0,0,1,14,NULL),(269,'J',4,393216,'2014-07-09 22:36:25',0,0,1,14,NULL),(270,'J',8,393217,'2014-07-09 22:36:42',0,6,1,14,NULL),(271,'B',3,393216,'2014-07-09 22:37:04',2,0,1,14,NULL),(272,'F',7,393217,'2014-07-09 22:37:11',2,0,1,14,NULL),(273,'F',4,393216,'2014-07-12 17:44:07',0,0,1,14,7),(274,'B',4,393217,'2014-07-12 17:44:30',0,0,1,14,7),(275,'D',8,393216,'2014-07-12 17:44:44',0,6,1,14,7),(276,'J',6,393217,'2014-07-12 17:45:02',0,0,1,14,7),(277,'H',4,393216,'2014-07-12 19:17:27',0,0,1,14,7),(278,'H',7,393217,'2014-07-12 19:17:33',0,0,1,14,7),(279,'A',3,393216,'2014-07-12 19:24:25',0,0,1,14,7),(280,'D',8,393217,'2014-07-12 19:24:30',0,6,1,14,7),(281,'I',7,393216,'2014-07-12 19:24:35',0,0,1,14,7),(282,'H',6,393217,'2014-07-12 19:24:39',0,0,1,14,7),(283,'D',8,393216,'2014-07-12 19:24:46',0,6,1,14,7),(284,'I',8,393217,'2014-07-12 19:24:50',0,6,1,14,7),(285,'G',3,393216,'2014-07-12 19:25:33',0,0,1,14,7),(286,'G',8,393217,'2014-07-12 19:25:38',0,6,1,14,7),(287,'J',5,393216,'2014-07-12 19:25:48',0,0,1,14,7),(288,'C',5,393217,'2014-07-12 19:25:58',0,0,1,14,7),(289,'A',2,393216,'2014-07-14 17:03:33',0,0,1,14,7),(290,'E',8,393217,'2014-07-14 17:03:43',0,6,1,14,7),(291,'D',8,393216,'2014-07-14 17:03:50',0,6,1,14,7),(292,'B',5,393217,'2014-07-14 17:03:56',0,0,1,14,7),(293,'F',2,393216,'2014-07-14 17:09:18',0,0,1,14,7),(294,'F',8,393217,'2014-07-14 17:09:41',0,6,1,14,7),(295,'A',3,393216,'2014-07-14 17:09:48',2,0,1,14,7),(296,'J',7,393217,'2014-07-14 17:09:52',2,0,1,14,7),(297,'C',8,393216,'2014-07-14 17:09:58',0,6,1,14,7),(298,'H',8,393217,'2014-07-14 17:10:11',0,6,1,14,7),(299,'G',4,393216,'2014-07-14 17:12:04',0,0,1,14,7),(300,'D',3,393217,'2014-07-14 17:12:12',0,0,1,14,7),(301,'G',8,393216,'2014-07-14 17:12:31',0,6,1,14,7),(302,'G',8,393217,'2014-07-14 17:12:38',0,6,1,14,7),(303,'A',4,393216,'2014-07-14 17:12:52',0,0,1,14,7),(304,'A',4,393217,'2014-07-14 17:12:57',0,0,1,14,7),(305,'H',8,393216,'2014-07-14 17:13:03',0,6,1,14,7),(306,'B',5,393217,'2014-07-14 17:13:15',0,0,1,14,7),(307,'D',8,393216,'2014-07-14 17:13:36',0,6,1,14,7),(308,'D',5,393217,'2014-07-14 17:13:52',0,0,1,14,7),(309,'I',8,393216,'2014-07-14 17:14:02',0,0,1,14,9),(310,'D',8,393217,'2014-07-14 17:14:12',0,0,1,14,9),(311,'A',8,393216,'2014-07-14 17:23:25',0,6,1,14,7),(312,'G',8,393217,'2014-07-14 17:23:30',0,6,1,14,7),(313,'H',4,393216,'2014-07-14 17:23:35',0,0,1,14,7),(314,'B',8,393217,'2014-07-14 17:23:52',0,6,1,14,7),(315,'H',8,393216,'2014-07-14 17:24:13',0,6,1,14,7),(316,'I',8,393217,'2014-07-14 17:24:23',0,6,1,14,7),(317,'I',5,393216,'2014-07-14 17:24:29',2,0,1,14,7),(318,'I',9,393217,'2014-07-14 17:24:34',2,0,1,14,7),(319,'H',8,393216,'2014-07-14 18:59:33',0,6,1,14,7),(320,'E',8,393217,'2014-07-14 18:59:47',0,6,1,14,7),(321,'J',7,393216,'2014-07-14 19:07:49',0,0,1,14,7),(322,'I',6,393217,'2014-07-14 19:07:56',0,0,1,14,7),(323,'B',7,393216,'2014-07-14 19:21:24',0,0,1,14,7),(324,'H',8,393217,'2014-07-14 19:21:28',0,6,1,14,7),(325,'A',8,393216,'2014-07-14 19:21:52',0,6,1,14,7),(326,'J',8,393217,'2014-07-14 19:21:59',0,6,1,14,7),(327,'J',8,393216,'2014-07-14 19:22:23',0,6,1,14,7),(328,'B',8,393217,'2014-07-14 19:22:29',0,6,1,14,7),(329,'G',7,393216,'2014-07-14 19:22:51',0,0,1,14,7),(330,'I',7,393217,'2014-07-14 19:23:02',0,0,1,14,7),(331,'J',8,393216,'2014-07-14 19:25:40',0,6,1,14,7),(332,'H',8,393217,'2014-07-14 19:25:58',0,6,1,14,7),(333,'I',7,393216,'2014-07-15 00:59:11',0,0,1,14,7),(334,'A',8,393217,'2014-07-15 00:59:17',0,6,1,14,7),(335,'A',4,393216,'2014-07-15 01:03:47',0,0,1,14,7),(336,'H',8,393217,'2014-07-15 01:03:58',0,6,1,14,7),(337,'I',8,393216,'2014-07-15 01:04:24',0,6,1,14,7),(338,'I',8,393217,'2014-07-15 01:04:37',0,6,1,14,7),(339,'I',7,393216,'2014-07-15 01:04:51',0,0,1,14,7),(340,'I',8,393217,'2014-07-15 01:05:07',0,6,1,14,7),(341,'J',9,393216,'2014-07-15 01:05:53',0,0,1,14,7),(342,'H',8,393217,'2014-07-15 01:06:14',0,6,1,14,7),(343,'H',4,393216,'2014-07-15 01:26:24',0,0,1,14,7),(344,'B',9,393217,'2014-07-15 01:29:47',0,0,1,14,7),(345,'B',3,393216,'2014-07-15 01:31:20',2,0,1,14,7),(346,'B',9,393217,'2014-07-15 01:31:26',2,0,1,14,7),(347,'G',4,393216,'2014-07-15 01:41:24',0,0,1,14,7),(348,'E',8,393217,'2014-07-15 01:41:30',0,6,1,14,7),(349,'G',8,393216,'2014-07-15 22:30:59',0,6,1,14,7),(350,'F',8,393216,'2014-07-15 22:57:44',0,6,1,14,7),(351,'I',8,393217,'2014-07-15 22:57:59',0,6,1,14,7),(352,'G',5,393216,'2014-07-15 22:59:00',2,0,1,14,7),(353,'G',3,393217,'2014-07-15 22:59:10',2,0,1,14,7),(354,'I',9,393216,'2014-07-15 23:00:19',2,0,1,14,7),(355,'F',3,393217,'2014-07-15 23:00:25',2,0,1,14,7),(356,'F',7,393216,'2014-07-15 23:00:35',2,0,1,14,7),(357,'I',5,393217,'2014-07-15 23:01:01',2,0,1,14,7),(358,'A',7,393216,'2014-07-15 23:21:30',2,0,1,14,7),(359,'D',9,393217,'2014-07-15 23:21:36',2,0,1,14,7),(360,'F',8,393216,'2014-07-15 23:22:35',0,6,1,14,7),(361,'I',8,393217,'2014-07-15 23:22:42',0,6,1,14,7),(362,'D',8,393216,'2014-07-15 23:24:21',0,6,1,14,7),(363,'A',8,393217,'2014-07-15 23:24:28',0,6,1,14,7),(364,'H',8,393216,'2014-07-15 23:24:55',0,6,1,14,7),(365,'G',8,393217,'2014-07-15 23:25:00',0,6,1,14,7),(366,'E',9,393216,'2014-07-15 23:25:11',0,0,1,14,7),(367,'E',4,393217,'2014-07-15 23:26:00',0,0,1,14,7),(368,'C',8,393216,'2014-07-15 23:26:43',0,6,1,14,7),(369,'E',8,393217,'2014-07-15 23:26:51',0,6,1,14,7),(370,'E',8,393216,'2014-07-15 23:27:21',0,6,1,14,7),(371,'J',8,393217,'2014-07-15 23:27:42',0,6,1,14,7),(372,'C',6,393216,'2014-07-15 23:28:03',0,0,1,14,7),(373,'E',8,393217,'2014-07-15 23:28:33',0,6,1,14,7),(374,'H',3,393216,'2014-07-16 10:28:31',0,0,1,14,7),(375,'C',3,393216,'2014-07-16 10:30:12',0,0,1,14,7),(376,'D',8,393217,'2014-07-16 10:30:20',0,6,1,14,7),(377,'I',8,393216,'2014-07-16 10:50:34',0,6,1,14,7),(378,'I',8,393217,'2014-07-16 10:50:40',0,6,1,14,7),(379,'A',8,393216,'2014-07-16 10:53:49',0,6,1,14,7),(380,'A',8,393217,'2014-07-16 10:53:55',0,6,1,14,7),(381,'J',6,393216,'2014-07-16 10:54:28',0,0,1,14,7),(382,'D',9,393217,'2014-07-16 10:54:36',0,0,1,14,7),(383,'B',8,393216,'2014-07-16 10:54:45',0,6,1,14,7),(384,'B',6,393217,'2014-07-16 10:55:07',0,0,1,14,7),(385,'B',8,393216,'2014-07-16 10:55:26',0,6,1,14,7),(386,'D',8,393217,'2014-07-16 10:55:39',0,6,1,14,7),(387,'E',3,393216,'2014-07-16 10:55:51',2,0,1,14,7),(388,'A',9,393217,'2014-07-16 10:55:57',2,0,1,14,7),(389,'C',8,393216,'2014-07-16 18:28:06',0,6,1,14,7),(390,'H',8,393217,'2014-07-16 18:28:14',0,6,1,14,7);
/*!40000 ALTER TABLE `jogada` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jogador`
--

DROP TABLE IF EXISTS `jogador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jogador` (
  `id` bigint(20) NOT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `geracao` int(11) NOT NULL,
  `ordem` int(11) NOT NULL,
  `pontuacao` int(11) NOT NULL,
  `pontuacaoCult` int(11) NOT NULL,
  `rodadaFim` int(11) NOT NULL,
  `rodadaInicio` int(11) NOT NULL,
  `experimento_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_lqnohuodp2enip7fmpln18iq7` (`experimento_id`),
  CONSTRAINT `FK_lqnohuodp2enip7fmpln18iq7` FOREIGN KEY (`experimento_id`) REFERENCES `experimento` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jogador`
--

LOCK TABLES `jogador` WRITE;
/*!40000 ALTER TABLE `jogador` DISABLE KEYS */;
INSERT INTO `jogador` VALUES (32768,'pedro','123','jog1',0,0,58,24,0,0,1),(32769,'joão','123','jog2',0,0,32,24,0,0,1),(32770,'jorge','123','jog3',0,0,14,24,0,0,1),(65536,'jogador1','123','joga1',0,0,2,4,0,0,4),(65537,'jogador2','123','joga2',0,0,2,4,0,0,4),(65538,'jogador3','123','joga3',0,0,2,4,0,0,4),(98304,'jogador4','123','jog4',0,0,6,0,0,0,5),(98305,'jogador5','123','jog5',0,0,0,0,0,0,5),(98306,'jogador6','123','jog6',0,0,0,0,0,0,5),(131072,'weslley','123','weslley',0,0,0,0,0,0,6),(163840,'sammyr','123','sammyr',0,0,0,0,0,0,6),(196608,'kaique','123','kaique',0,0,0,0,0,0,8),(196609,'patrick','123','patrick',0,0,0,0,0,0,8),(229376,'pedro','123','pedro',0,0,0,0,0,0,9),(229377,'paulo','123','paulo',0,0,0,0,0,0,9),(262144,'tamy','123','tamy',0,0,0,0,0,0,10),(262145,'lima','123','lima',0,0,0,0,0,0,10),(294912,'araujo','123','araujo',0,0,0,0,0,0,11),(294913,'araujo1','123','araujo1',0,0,0,0,0,0,11),(327680,'weslley1','123','weslley1',0,0,0,0,0,0,12),(327681,'weslley2','123','weslley2',0,0,0,0,0,0,12),(360448,'sammyr1','123','sammyr1',0,0,0,0,0,0,13),(360449,'sammyr2','123','sammyr2',0,0,0,0,0,0,13),(393216,'sammyr3','123','sammyr3',0,0,120,2,0,0,14),(393217,'sammyr4','123','sammyr4',0,0,54,2,0,0,14);
/*!40000 ALTER TABLE `jogador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pesquisador`
--

DROP TABLE IF EXISTS `pesquisador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pesquisador` (
  `id` bigint(20) NOT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `curso` varchar(255) DEFAULT NULL,
  `lattes` varchar(255) DEFAULT NULL,
  `matricula` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pesquisador`
--

LOCK TABLES `pesquisador` WRITE;
/*!40000 ALTER TABLE `pesquisador` DISABLE KEYS */;
INSERT INTO `pesquisador` VALUES (1,'Adailton','1234','pesquisador1','SI','www.lattes.com','11111');
/*!40000 ALTER TABLE `pesquisador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pontuacaocultural`
--

DROP TABLE IF EXISTS `pontuacaocultural`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pontuacaocultural` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ciclos` int(11) DEFAULT NULL,
  `condicional` varchar(255) DEFAULT NULL,
  `coresSelecionadas` varchar(255) DEFAULT NULL,
  `incrementoPontoAcertoFixo` int(11) DEFAULT NULL,
  `linhasSelecionadas` varchar(255) DEFAULT NULL,
  `maxIncrementoPontoAcertoRand` int(11) DEFAULT NULL,
  `minIncrementoPontoAcertoRand` int(11) DEFAULT NULL,
  `pontoAcerto` int(11) DEFAULT NULL,
  `sinalSelecionado` varchar(255) DEFAULT NULL,
  `tipoAcrescimoPont` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pontuacaocultural`
--

LOCK TABLES `pontuacaocultural` WRITE;
/*!40000 ALTER TABLE `pontuacaocultural` DISABLE KEYS */;
INSERT INTO `pontuacaocultural` VALUES (1,NULL,'OU','AMARELO',NULL,'PAR',NULL,NULL,4,'+',NULL),(2,NULL,'E','DIFERENTES',NULL,'IMPAR',NULL,NULL,2,'+',NULL),(3,NULL,'E','DIFERENTES',NULL,'PAR',NULL,NULL,1,'+',NULL);
/*!40000 ALTER TABLE `pontuacaocultural` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pontuacaoindividual`
--

DROP TABLE IF EXISTS `pontuacaoindividual`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pontuacaoindividual` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ciclos` int(11) DEFAULT NULL,
  `condicional` varchar(255) DEFAULT NULL,
  `corSelecionada` varchar(255) DEFAULT NULL,
  `incrementoPontoAcertoFixo` int(11) DEFAULT NULL,
  `linhaSelecionada` varchar(255) DEFAULT NULL,
  `maxIncrementoPontoAcertoRand` int(11) DEFAULT NULL,
  `minIncrementoPontoAcertoRand` int(11) DEFAULT NULL,
  `pontoAcerto` int(11) DEFAULT NULL,
  `sinalSelecionado` varchar(255) DEFAULT NULL,
  `tipoAcrescimoPont` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pontuacaoindividual`
--

LOCK TABLES `pontuacaoindividual` WRITE;
/*!40000 ALTER TABLE `pontuacaoindividual` DISABLE KEYS */;
INSERT INTO `pontuacaoindividual` VALUES (1,NULL,'E','VERDE',NULL,'PAR',NULL,NULL,2,'+',NULL),(2,NULL,'E','AMARELO',NULL,'PAR',NULL,NULL,6,'+',NULL),(3,NULL,'E','VERMELHO',NULL,'IMPAR',NULL,NULL,5,'+',NULL),(4,NULL,'E','ROXO',NULL,'TODAS',NULL,NULL,3,'+',NULL);
/*!40000 ALTER TABLE `pontuacaoindividual` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `id` bigint(20) NOT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-07-16 20:33:54
