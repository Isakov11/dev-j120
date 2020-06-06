CREATE TABLE `config` (
  `key` varchar(64) NOT NULL,
  `value` varchar(64) NOT NULL,
  PRIMARY KEY (`key`),
  UNIQUE KEY `key_UNIQUE` (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `goods` (
  `article` bigint NOT NULL,
  `variety` varchar(255) NOT NULL,
  `color` varchar(255) DEFAULT NULL,
  `price` decimal(8,2) NOT NULL,
  `instock` bigint NOT NULL DEFAULT '0',
  PRIMARY KEY (`article`),
  UNIQUE KEY `article_UNIQUE` (`article`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `addreses` (
  `id` int NOT NULL AUTO_INCREMENT,
  `city` varchar(50) NOT NULL,
  `street` varchar(50) NOT NULL,
  `building` varchar(50) NOT NULL,
  `flat` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `persons` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `surname` varchar(100) NOT NULL,
  `address` int NOT NULL,
  `phoneNumber` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `address_idx` (`address`),
  CONSTRAINT `address` FOREIGN KEY (`address`) REFERENCES `addreses` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `orders` (
  `orderNumber` int NOT NULL AUTO_INCREMENT,
  `orderDate` datetime NOT NULL,
  `person` int NOT NULL,
  `discount` int NOT NULL,
  `orderStatus` enum('PREPARING','SHIPPED','CANCELED') NOT NULL DEFAULT 'PREPARING',
  PRIMARY KEY (`orderNumber`),
  UNIQUE KEY `orderNumber_UNIQUE` (`orderNumber`),
  KEY `person_idx` (`person`),
  CONSTRAINT `pers` FOREIGN KEY (`person`) REFERENCES `persons` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `ordereditems` (
  `id` varchar(45) NOT NULL,
  `orderNumber` int NOT NULL,
  `article` bigint NOT NULL,
  `variety` varchar(255) NOT NULL,
  `color` varchar(255) DEFAULT NULL,
  `fixedPrice` decimal(8,2) NOT NULL,
  `orderedQuantity` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `orderNumber_idx` (`orderNumber`),
  CONSTRAINT `orderNumber` FOREIGN KEY (`orderNumber`) REFERENCES `orders` (`orderNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci