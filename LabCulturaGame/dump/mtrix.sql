/*~
M-Trix - Social Experiments Application.
Copyright (C) 2015-2016 The M-Trix authors.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
~*/
-- MySQL dump 10.13  Distrib 5.6.11, for Win64 (x86_64)
--
-- Host: localhost    Database: mtrix
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
  `nivelComplexidade` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `objetivo` varchar(255) DEFAULT NULL,
  `ultimosXCiclos` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `condicao`
--

LOCK TABLES `condicao` WRITE;
/*!40000 ALTER TABLE `condicao` DISABLE KEYS */;
INSERT INTO `condicao` VALUES (1,'Weslley',NULL,NULL,4,NULL,NULL,NULL,NULL,0,'FACIL','beta','beta',0),(2,'Weslley',NULL,NULL,3,NULL,NULL,NULL,NULL,0,'FACIL','Teste','Teste',0);
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
INSERT INTO `condicao_pontcult` VALUES (1,1),(2,1);
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
INSERT INTO `condicao_pontindiv` VALUES (1,1),(2,1);
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `condicaoexperimento`
--

LOCK TABLES `condicaoexperimento` WRITE;
/*!40000 ALTER TABLE `condicaoexperimento` DISABLE KEYS */;
INSERT INTO `condicaoexperimento` VALUES (1,1,1,1),(2,2,1,1),(3,1,1,2),(4,2,1,2);
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contato`
--

LOCK TABLES `contato` WRITE;
/*!40000 ALTER TABLE `contato` DISABLE KEYS */;
INSERT INTO `contato` VALUES (1,'','','','no.email@ufpa.br','','','','');
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
  `chat` varchar(255) DEFAULT NULL,
  `justificativa` varchar(255) DEFAULT NULL,
  `liberaVisualizarPontosIndivJogador` tinyint(1) NOT NULL,
  `liberaVisualizarUltimaJogada` tinyint(1) NOT NULL,
  `nivelComplexidade` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `objetivo` varchar(255) DEFAULT NULL,
  `pontInicialCultural` int(11) DEFAULT NULL,
  `pontInicialIndividual` int(11) DEFAULT NULL,
  `porcentagemAcertoCult` int(11) NOT NULL,
  `porcentagemAcertoIndiv` int(11) NOT NULL,
  `quantidadeJogadas` int(11) NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `tamanhoFilaJogadores` int(11) NOT NULL,
  `tipoMatriz` int(11) NOT NULL,
  `tipoRotacao` varchar(255) DEFAULT NULL,
  `ultimosXCiclos` int(11) NOT NULL,
  `pesquisador_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_lcodxex3koah7xem071h8vw30` (`pesquisador_id`),
  CONSTRAINT `FK_lcodxex3koah7xem071h8vw30` FOREIGN KEY (`pesquisador_id`) REFERENCES `pesquisador` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `experimento`
--

LOCK TABLES `experimento` WRITE;
/*!40000 ALTER TABLE `experimento` DISABLE KEYS */;
INSERT INTO `experimento` VALUES (1,'HABILITADO',NULL,1,1,NULL,'beta','beta',NULL,NULL,0,0,3,'EXECUTANDO',2,2,'APÓS X JOGADAS',0,32768),(2,'HABILITADO',NULL,1,1,NULL,'beta','beta',NULL,NULL,0,0,3,'CRIADO',2,2,'APÓS X JOGADAS',0,32768);
/*!40000 ALTER TABLE `experimento` ENABLE KEYS */;
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
INSERT INTO `hibernate_sequences` VALUES ('Usuario',10);
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
  `corSelecionada` varchar(255) DEFAULT NULL,
  `idCondicao` bigint(20) DEFAULT NULL,
  `linhaSelecionada` int(11) NOT NULL,
  `momento` datetime DEFAULT NULL,
  `pontuacaoCultural` int(11) NOT NULL,
  `pontuacaoIndividual` int(11) DEFAULT NULL,
  `rodada` int(11) NOT NULL,
  `experimento_id` bigint(20) NOT NULL,
  `jogador_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_t5cefux6pedlgroinskvxxmym` (`experimento_id`),
  KEY `FK_g39cow18ub0veddqs413xrybr` (`jogador_id`),
  CONSTRAINT `FK_g39cow18ub0veddqs413xrybr` FOREIGN KEY (`jogador_id`) REFERENCES `jogador` (`id`),
  CONSTRAINT `FK_t5cefux6pedlgroinskvxxmym` FOREIGN KEY (`experimento_id`) REFERENCES `experimento` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jogada`
--

LOCK TABLES `jogada` WRITE;
/*!40000 ALTER TABLE `jogada` DISABLE KEYS */;
INSERT INTO `jogada` VALUES (1,'G','azul',1,4,'2014-10-22 15:49:49',0,0,1,1,32770),(2,'D','vermelho',1,6,'2014-10-22 15:49:58',0,0,1,1,32772),(3,'G','roxo',1,5,'2014-10-23 16:26:51',0,0,1,1,32770),(4,'C','azul',1,4,'2014-10-23 16:27:27',0,0,1,1,32772),(5,'B','azul',1,4,'2014-10-23 16:36:50',0,0,2,1,32770),(6,'B','roxo',1,5,'2014-10-23 16:39:31',0,0,2,1,32772),(7,'D','vermelho',1,6,'2014-10-23 16:45:40',0,0,1,1,32770),(8,'E','roxo',1,5,'2014-10-23 16:45:55',0,0,1,1,32772),(9,'J','vermelho',1,6,'2014-10-23 17:03:38',4,0,1,1,32770),(10,'I','vermelho',1,6,'2014-10-23 17:04:03',4,0,1,1,32772),(11,'D','azul',1,4,'2014-10-23 17:28:24',0,0,1,1,32770),(12,'D','vermelho',1,3,'2014-10-23 17:29:50',0,0,1,1,32772),(13,'C','roxo',1,5,'2014-10-23 17:48:44',0,0,1,1,32770),(14,'E','roxo',1,5,'2014-10-23 17:49:01',0,0,1,1,32772),(15,'G','azul',1,4,'2014-10-23 18:09:47',4,0,1,1,32770),(16,'E','azul',1,4,'2014-10-23 18:10:14',4,0,1,1,32772),(17,'A','vermelho',1,3,'2014-10-23 18:10:42',0,0,2,1,32770),(18,'C','azul',1,4,'2014-10-23 18:11:00',0,0,2,1,32772),(19,'D','amarelo',1,1,'2014-10-23 18:12:51',0,3,3,1,32770),(20,'I','azul',1,4,'2014-10-23 18:13:01',0,0,3,1,32772),(21,'A','vermelho',1,3,'2014-10-23 18:20:15',0,0,1,1,32770),(22,'E','vermelho',1,3,'2014-10-23 18:20:27',0,0,1,1,32772),(23,'A','roxo',1,5,'2014-10-23 18:21:37',0,0,2,1,32770),(24,'F','roxo',1,10,'2014-10-23 18:21:47',0,0,2,1,32772),(25,'I','azul',1,4,'2014-10-23 18:25:35',0,0,3,1,32770),(26,'G','amarelo',1,8,'2014-10-23 18:25:44',0,0,3,1,32772),(27,'D','amarelo',1,8,'2014-10-23 18:37:07',0,0,1,1,32770),(28,'J','verde',1,7,'2014-10-23 18:37:35',0,0,1,1,32772),(29,'C','azul',1,4,'2014-10-23 18:40:17',0,0,2,1,32770),(30,'G','amarelo',1,8,'2014-10-23 18:40:27',0,0,2,1,32772),(31,'C','vermelho',1,3,'2014-10-23 18:40:47',0,0,3,1,32770),(32,'C','roxo',1,5,'2014-10-23 18:40:52',0,0,3,1,32772),(33,'F','azul',1,4,'2014-10-24 16:13:42',0,0,1,1,32770),(34,'E','vermelho',1,6,'2014-10-24 16:13:50',0,0,1,1,32772),(35,'A','azul',1,4,'2014-10-24 16:14:07',0,0,2,1,32770),(36,'C','amarelo',1,8,'2014-10-24 16:14:14',0,0,2,1,32772),(37,'A','vermelho',1,3,'2014-10-24 16:14:22',0,0,3,1,32770),(38,'D','vermelho',1,3,'2014-10-24 16:14:30',0,0,3,1,32772),(39,'D','vermelho',1,3,'2014-10-24 16:26:44',0,0,1,1,32770),(40,'A','roxo',1,5,'2014-10-24 16:26:52',0,0,1,1,32772),(41,'G','azul',1,4,'2014-10-24 16:27:03',0,0,2,1,32770),(42,'F','verde',1,7,'2014-10-24 16:27:10',0,0,2,1,32772),(43,'B','vermelho',1,3,'2014-10-24 16:27:15',0,0,3,1,32770),(44,'J','azul',1,9,'2014-10-24 16:27:22',0,0,3,1,32772),(45,'G','vermelho',1,6,'2014-10-24 16:29:17',0,0,4,1,32772),(46,'A','azul',1,4,'2014-10-24 16:29:23',0,0,4,1,32771),(47,'G','verde',1,7,'2014-10-24 16:34:52',0,0,5,1,32772),(48,'G','amarelo',1,8,'2014-10-24 16:34:58',0,0,5,1,32771),(49,'G','roxo',1,5,'2014-10-24 16:35:01',0,0,6,1,32772),(50,'D','vermelho',1,6,'2014-10-24 16:35:05',0,0,6,1,32771),(51,'A','vermelho',1,3,'2014-10-24 16:35:10',0,0,7,1,32772),(52,'J','azul',1,4,'2014-10-24 16:35:21',0,0,7,1,32771),(53,'C','vermelho',1,3,'2014-10-24 16:35:29',0,0,8,1,32772),(54,'E','vermelho',1,3,'2014-10-24 16:35:34',0,0,8,1,32771),(55,'I','vermelho',1,3,'2014-10-24 16:37:36',0,0,9,1,32772),(56,'E','roxo',1,5,'2014-10-24 16:37:41',0,0,9,1,32771),(57,'C','verde',1,2,'2014-10-24 16:37:46',0,0,10,1,32772),(58,'G','azul',1,4,'2014-10-24 16:37:50',0,0,10,1,32771),(59,'E','roxo',1,5,'2014-10-24 18:09:57',0,0,1,1,32770),(60,'E','azul',1,4,'2014-10-24 18:10:05',0,0,1,1,32772),(61,'B','azul',1,4,'2014-10-24 18:10:14',0,0,2,1,32770),(62,'F','roxo',1,5,'2014-10-24 18:10:24',0,0,2,1,32772),(63,'A','azul',1,4,'2014-10-24 18:10:37',0,0,3,1,32770),(64,'F','vermelho',1,6,'2014-10-24 18:10:43',0,0,3,1,32772),(65,'C','azul',1,4,'2014-10-24 18:27:35',0,0,1,1,32770),(66,'J','roxo',1,5,'2014-10-24 18:28:03',0,0,1,1,32772),(67,'E','roxo',1,5,'2014-10-24 18:28:08',0,0,2,1,32770),(68,'I','amarelo',1,8,'2014-10-24 18:28:12',0,0,2,1,32772),(69,'D','azul',1,4,'2014-10-24 18:28:17',0,0,3,1,32770),(70,'J','roxo',1,5,'2014-10-24 18:28:23',0,0,3,1,32772),(71,'H','roxo',1,5,'2014-10-24 18:28:29',0,0,4,1,32772),(72,'G','verde',1,7,'2014-10-24 18:28:37',0,0,4,1,32771),(73,'B','azul',1,4,'2014-10-24 18:28:44',0,0,5,1,32772),(74,'C','azul',1,9,'2014-10-24 18:28:50',0,0,5,1,32771),(75,'D','vermelho',1,3,'2014-10-24 18:37:50',0,0,1,1,32770),(76,'G','verde',1,7,'2014-10-24 18:37:55',0,0,1,1,32772),(77,'C','vermelho',1,3,'2014-10-24 18:38:00',0,0,2,1,32770),(78,'I','verde',1,2,'2014-10-24 18:38:04',0,0,2,1,32772),(79,'F','amarelo',1,1,'2014-10-24 18:38:09',0,3,3,1,32770),(80,'G','vermelho',1,3,'2014-10-24 18:38:15',0,0,3,1,32772),(81,'I','verde',1,7,'2014-10-24 18:38:19',0,0,4,1,32772),(82,'D','roxo',1,5,'2014-10-24 18:38:26',0,0,4,1,32771),(83,'I','roxo',1,5,'2014-10-24 18:38:31',0,0,5,1,32772),(84,'J','verde',1,2,'2014-10-24 18:38:35',0,0,5,1,32771),(85,'C','roxo',1,10,'2014-10-24 18:38:39',0,0,6,1,32772),(86,'A','azul',1,4,'2014-10-24 18:38:43',0,0,6,1,32771),(87,'I','azul',1,4,'2014-10-24 18:38:48',0,0,7,1,32772),(88,'A','verde',1,2,'2014-10-24 18:38:52',0,0,7,1,32771),(89,'F','vermelho',1,3,'2014-10-24 18:39:32',0,0,8,1,32772),(90,'F','roxo',1,5,'2014-10-24 18:39:37',0,0,8,1,32771),(91,'B','verde',1,2,'2014-10-24 18:45:17',0,0,1,1,32770),(92,'D','vermelho',1,3,'2014-10-24 18:45:26',0,0,1,1,32772),(93,'A','vermelho',1,3,'2014-10-24 18:45:31',0,0,2,1,32770),(94,'A','amarelo',1,1,'2014-10-24 18:45:36',0,3,2,1,32772),(95,'H','verde',1,2,'2014-10-24 18:45:43',0,0,3,1,32770),(96,'J','amarelo',1,1,'2014-10-24 18:45:48',0,3,3,1,32772),(97,'C','roxo',1,5,'2014-10-24 18:45:53',0,0,4,1,32772),(98,'H','amarelo',1,1,'2014-10-24 18:47:15',0,3,4,1,32771),(99,'D','vermelho',1,3,'2014-10-24 18:48:11',0,0,5,1,32772),(100,'H','vermelho',1,6,'2014-10-24 18:48:36',0,0,5,1,32771),(101,'J','roxo',1,5,'2014-10-24 18:49:03',0,0,6,1,32772),(102,'D','verde',1,7,'2014-10-24 18:49:09',0,0,6,1,32771),(103,'J','vermelho',1,6,'2014-10-27 19:54:09',0,0,1,1,32770),(104,'H','vermelho',1,3,'2014-10-27 19:54:14',0,0,1,1,32772),(105,'C','vermelho',1,3,'2014-10-27 19:54:58',0,0,2,1,32770),(106,'D','roxo',1,10,'2014-10-27 19:55:05',0,0,2,1,32772),(107,'F','verde',1,2,'2014-10-27 19:55:38',0,0,3,1,32770),(108,'F','vermelho',1,3,'2014-10-27 19:55:45',0,0,3,1,32772),(109,'C','verde',1,2,'2014-10-27 19:56:05',0,0,4,1,32772),(110,'I','vermelho',1,3,'2014-10-27 19:56:16',0,0,4,1,32771),(111,'D','vermelho',1,3,'2014-10-27 20:17:39',0,0,1,1,32770),(112,'H','roxo',1,5,'2014-10-27 20:17:45',0,0,1,1,32772),(113,'G','azul',1,4,'2014-10-27 20:21:29',0,0,2,1,32770),(114,'B','verde',1,7,'2014-10-27 20:21:33',0,0,2,1,32772),(115,'C','amarelo',1,1,'2014-10-27 20:21:37',0,3,3,1,32770),(116,'G','amarelo',1,8,'2014-10-27 20:21:42',0,0,3,1,32772),(117,'G','roxo',1,5,'2014-10-27 20:22:21',0,0,4,1,32772),(118,'D','roxo',1,5,'2014-10-27 20:22:29',0,0,4,1,32771),(119,'H','roxo',1,5,'2014-10-27 20:27:29',0,0,5,1,32772),(120,'C','vermelho',1,3,'2014-10-27 20:27:37',0,0,5,1,32771),(121,'H','azul',1,4,'2014-10-27 20:47:39',4,0,1,1,32770),(122,'F','azul',1,4,'2014-10-27 20:47:42',4,0,1,1,32772);
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
  `contato_id` bigint(20) DEFAULT NULL,
  `geracao` int(11) NOT NULL,
  `ordem` int(11) NOT NULL,
  `pontuacao` int(11) NOT NULL,
  `pontuacaoCult` int(11) NOT NULL,
  `rodadaFim` int(11) NOT NULL,
  `rodadaInicio` int(11) NOT NULL,
  `experimento_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_lqnohuodp2enip7fmpln18iq7` (`experimento_id`),
  KEY `FK_movhfgqmy14do53dyhdijyqa3` (`contato_id`),
  CONSTRAINT `FK_lqnohuodp2enip7fmpln18iq7` FOREIGN KEY (`experimento_id`) REFERENCES `experimento` (`id`),
  CONSTRAINT `FK_movhfgqmy14do53dyhdijyqa3` FOREIGN KEY (`contato_id`) REFERENCES `contato` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jogador`
--

LOCK TABLES `jogador` WRITE;
/*!40000 ALTER TABLE `jogador` DISABLE KEYS */;
INSERT INTO `jogador` VALUES (32769,'tamy','A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3','tamy',NULL,0,0,0,0,0,0,NULL),(32770,'jogador01','A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3','jog01',NULL,0,1,9,4,0,0,1),(32771,'jogador02','A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3','jog02',NULL,0,3,3,0,0,0,1),(32772,'jogador03','A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3','jog03',NULL,0,2,6,4,0,0,1),(65536,'jogador04','A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3','jog04',NULL,0,0,0,0,0,0,NULL),(294912,'jogador05','A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3','jog05',NULL,0,0,0,0,0,0,NULL);
/*!40000 ALTER TABLE `jogador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mensagem`
--

DROP TABLE IF EXISTS `mensagem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mensagem` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cicloExperimento` varchar(255) DEFAULT NULL,
  `conteudoMensagem` varchar(255) DEFAULT NULL,
  `emissor` bigint(20) DEFAULT NULL,
  `estadoParticipante` varchar(255) DEFAULT NULL,
  `experimento` bigint(20) DEFAULT NULL,
  `momento` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mensagem`
--

LOCK TABLES `mensagem` WRITE;
/*!40000 ALTER TABLE `mensagem` DISABLE KEYS */;
INSERT INTO `mensagem` VALUES (1,NULL,'Teste',32770,NULL,1,'2014-10-22 15:27:17'),(2,NULL,'Teste',32768,NULL,1,'2014-10-22 15:40:05'),(3,NULL,'Teste',32768,NULL,1,'2014-10-22 15:46:47'),(4,NULL,'Envia',32768,NULL,1,'2014-10-22 15:47:05'),(5,NULL,'Teste',32768,NULL,1,'2014-10-23 16:31:59'),(6,NULL,'teste',32772,NULL,1,'2014-10-23 16:32:11'),(7,NULL,'Teste',32770,NULL,1,'2014-10-23 16:32:21'),(8,NULL,'t',32770,NULL,1,'2014-10-23 16:32:40'),(9,NULL,'',32770,NULL,1,'2014-10-23 16:32:41'),(10,NULL,'',32770,NULL,1,'2014-10-23 16:32:41'),(11,NULL,'',32770,NULL,1,'2014-10-23 16:32:41'),(12,NULL,'',32770,NULL,1,'2014-10-23 16:32:41'),(13,NULL,'',32770,NULL,1,'2014-10-23 16:32:42'),(14,NULL,'',32770,NULL,1,'2014-10-23 16:32:42'),(15,NULL,'',32770,NULL,1,'2014-10-23 16:32:42'),(16,NULL,'',32770,NULL,1,'2014-10-23 16:32:43'),(17,NULL,'',32770,NULL,1,'2014-10-23 16:32:43');
/*!40000 ALTER TABLE `mensagem` ENABLE KEYS */;
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
  `contato_id` bigint(20) DEFAULT NULL,
  `curso` varchar(255) DEFAULT NULL,
  `lattes` varchar(255) DEFAULT NULL,
  `matricula` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_6blwuq3r5jo79gl4gjkr13fda` (`contato_id`),
  CONSTRAINT `FK_6blwuq3r5jo79gl4gjkr13fda` FOREIGN KEY (`contato_id`) REFERENCES `contato` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pesquisador`
--

LOCK TABLES `pesquisador` WRITE;
/*!40000 ALTER TABLE `pesquisador` DISABLE KEYS */;
INSERT INTO `pesquisador` VALUES (1,'Pesquisador','03AC674216F3E15C761EE1A5E255F067953623C8B388B4459E13F978D7C846F4','pesquisador',NULL,'SI','www.lattes.com','11111');
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pontuacaocultural`
--

LOCK TABLES `pontuacaocultural` WRITE;
/*!40000 ALTER TABLE `pontuacaocultural` DISABLE KEYS */;
INSERT INTO `pontuacaocultural` VALUES (1,NULL,'E','amarelo',NULL,'PAR',NULL,NULL,4,'+',NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pontuacaoindividual`
--

LOCK TABLES `pontuacaoindividual` WRITE;
/*!40000 ALTER TABLE `pontuacaoindividual` DISABLE KEYS */;
INSERT INTO `pontuacaoindividual` VALUES (1,NULL,'E','amarelo',NULL,'IMPAR',NULL,NULL,3,'+',NULL);
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
  `contato_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ta9xy2a0igokj7t3r34pqmsbk` (`contato_id`),
  CONSTRAINT `FK_ta9xy2a0igokj7t3r34pqmsbk` FOREIGN KEY (`contato_id`) REFERENCES `contato` (`id`)
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

-- Dump completed on 2014-10-28 22:26:45
