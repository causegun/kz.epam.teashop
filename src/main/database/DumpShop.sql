CREATE DATABASE  IF NOT EXISTS `shop` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `shop`;
-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: shop
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
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `userId` int unsigned NOT NULL,
  `createdAt` datetime NOT NULL,
  `totalPrice` decimal(10,0) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `cart_id_uindex` (`id`),
  KEY `cart_user_info_id_fk` (`userId`),
  CONSTRAINT `cart_user_info_id_fk` FOREIGN KEY (`userId`) REFERENCES `user_info` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_item`
--

DROP TABLE IF EXISTS `cart_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_item` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `productId` int unsigned NOT NULL,
  `createdAt` datetime NOT NULL,
  `quantity` int unsigned NOT NULL,
  `cartId` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `productId` (`productId`),
  KEY `cart_item_cart_id_fk` (`cartId`),
  CONSTRAINT `cart_item_cart_id_fk` FOREIGN KEY (`cartId`) REFERENCES `cart` (`id`),
  CONSTRAINT `cart_item_ibfk_3` FOREIGN KEY (`productId`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_item`
--

LOCK TABLES `cart_item` WRITE;
/*!40000 ALTER TABLE `cart_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `cart_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `languageId` int unsigned NOT NULL,
  `categoryName` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `categoryName_UNIQUE` (`categoryName`),
  KEY `languageId` (`languageId`),
  CONSTRAINT `category_ibfk_1` FOREIGN KEY (`languageId`) REFERENCES `language_info` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,1,'Black Tea'),(2,2,'Черный чай'),(3,1,'Green Tea'),(4,2,'Зеленый чай'),(5,1,'White Tea'),(6,2,'Белый чай');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `language_info`
--

DROP TABLE IF EXISTS `language_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `language_info` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `languageName` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `languageName_UNIQUE` (`languageName`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `language_info`
--

LOCK TABLES `language_info` WRITE;
/*!40000 ALTER TABLE `language_info` DISABLE KEYS */;
INSERT INTO `language_info` VALUES (1,'en'),(2,'ru');
/*!40000 ALTER TABLE `language_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `languageId` int unsigned NOT NULL,
  `categoryId` int unsigned NOT NULL,
  `productName` varchar(30) NOT NULL,
  `productDescription` mediumtext,
  `price` decimal(10,0) NOT NULL,
  `pathToPicture` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `languageId` (`languageId`),
  KEY `categoryId` (`categoryId`),
  CONSTRAINT `product_ibfk_1` FOREIGN KEY (`languageId`) REFERENCES `language_info` (`id`),
  CONSTRAINT `product_ibfk_2` FOREIGN KEY (`categoryId`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (15,1,1,'Bombai Chai','Transport yourself to new places with the rich and exotic flavor of Bombay Chai Black Chai Tea. A unique twist on traditional Indian black chai tea, Revolution Tea’s Bombay Chai Tea bags brings together a collection of bold flavors like ginger, cloves, nutmeg, cinnamon chips and premium full leaf Assam team leaves.',1000,'pictures/Bombay_Chai.jpg'),(16,2,2,'Бомбейский чай','Перенеситесь в новые места с богатым и экзотическим вкусом. Бомбейский чай в пакетиках представляет собой уникальный вариант традиционного индийского черного чая. Он объединяет в себе коллекцию ярких вкусов, таких как имбирь, гвоздика, мускатный орех, чипсы с корицей и цельные листья сборной Ассама премиум-класса.',1000,'pictures/Bombay_Chai.jpg'),(17,1,1,'Earl Grey','This award-winning Lavender Earl Grey Black Tea blend brings a new twist to a classic favorite. At the Darjeeling estate where our teas are grown, Ceylon and Oolong tea leaves are flavored with a hint of Oil of Bergamot. From there they are joined with French blue lavender to add the perfect amount of sweetness. ',1100,'pictures/Earl_Grey.jpg'),(18,2,2,'Эрл Грэй','Эта отмеченная наградами смесь черного чая с лавандой и графством Грей привносит новый поворот в классический фаворит. В поместье Дарджилинг, где выращивают наши чаи, листья цейлонского и улунского чая приправлены нотками масла бергамота. Оттуда они соединяются с французской голубой лавандой, чтобы добавить идеальное количество сладости.',1100,'pictures/Earl_Grey.jpg'),(19,1,1,'Raspberry','Wake up in the best way possible with a delicious Raspberry Black Tea! This twist on traditional black tea marries fresh picked raspberries, strawberry leaves, rose petals and premium full leaf Assam black tea.',1200,'pictures/Raspberry.jpg'),(20,2,2,'Малиновый','Просыпайтесь как можно лучше с восхитительным черным чаем с малиной! Этот вариант традиционного черного чая сочетает в себе свежесобранную малину, листья клубники, лепестки роз и цельнолистовой черный чай высшего качества Ассам.',1200,'pictures/Raspberry.jpg'),(21,1,3,'Orange Chocolate','Orange Chocolate Green Tea is one of the most unique and luscious flavors you\'ll ever brew.',1050,'pictures/Orange_Chocolate.jpg'),(22,2,4,'Апельсин-шоколад','Апельсин-шоколад с зеленым чаем - один из самых уникальных и сочных ароматов, которые вы когда-либо варили.',1050,'pictures/Orange_Chocolate.jpg'),(23,1,3,'Peach Mango','Unusually light and smooth, this refreshing tea blends the juicy flavor of peaches with the mellow zing of mangoes.',1150,'pictures/Peach_Mango.jpg'),(24,2,4,'Манго Персик','Необычайно легкий и мягкий, этот освежающий чай сочетает в себе сочный аромат персиков с мягким привкусом манго.',1150,'pictures/Peach_Mango.jpg'),(25,1,3,'Tropical','Tropical green tea is an amazing natural blend of Dragonwell green tea, Chinese Young Hyson green tea, and pineapple flavoring.',1250,'pictures/Tropical.jpg'),(26,2,4,'Тропический','Тропический зеленый чай - это удивительная натуральная смесь зеленого чая Dragonwell, китайского зеленого чая Young Hyson и ароматизатора ананаса.',1250,'pictures/Tropical.jpg'),(27,1,5,'Pear','Delicate and fragrant as a fresh-picked pear, our Pear White tea blend combines white tea, slivers of sweet pears, and natural pear flavor. ',1300,'pictures/Pear.jpg'),(28,2,6,'Груша','Нежный и ароматный, как свежесобранная груша, сочетает в себе белый чай, кусочки сладкой груши и натуральный аромат груши.',1300,'pictures/Pear.jpg'),(29,1,5,'Pomegranate','This antioxidant-rich white tea will delight your afternoon, evening, or morning, taking advantage of the light caffeine content and the robust flavor.',1400,'pictures/Pomegranate.jpg'),(30,2,6,'Гранат','Этот богатый антиоксидантами белый чай порадует вас днем, вечером или утром, благодаря легкому содержанию кофеина и сильному вкусу.',1400,'pictures/Pomegranate.jpg');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_info`
--

DROP TABLE IF EXISTS `user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_info` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `userName` varchar(30) NOT NULL,
  `email` varchar(30) NOT NULL,
  `userPassword` varchar(50) NOT NULL,
  `phoneNumber` varchar(30) NOT NULL,
  `isAdmin` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `user_info_email_uindex` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_info`
--

LOCK TABLES `user_info` WRITE;
/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;
INSERT INTO `user_info` VALUES (1,'admin','admin@mail.com','C05F4B14F55B1659103C5FECC9510DAA','9999999999',1),(14,'ad','ad@mail.ru','827CCB0EEA8A706C4C34A16891F84E7B','7000000000',0),(16,'Майк','mike@mail','18126E7BD3F84B3F3E4DF094DEF5B7DE','80000000000',0);
/*!40000 ALTER TABLE `user_info` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-02-02  3:30:13
