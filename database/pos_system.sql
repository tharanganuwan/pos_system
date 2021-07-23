-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3308
-- Generation Time: Jul 23, 2021 at 03:18 PM
-- Server version: 8.0.18
-- PHP Version: 7.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pos_system`
--

-- --------------------------------------------------------

--
-- Table structure for table `billing`
--

DROP TABLE IF EXISTS `billing`;
CREATE TABLE IF NOT EXISTS `billing` (
  `invoiceId` varchar(100) NOT NULL,
  `invoiceBy` varchar(100) NOT NULL,
  `date` date NOT NULL,
  `time` time NOT NULL,
  `total` double NOT NULL,
  `discount` double NOT NULL,
  `payment` double NOT NULL,
  `balance` double NOT NULL,
  `invoice` varchar(3000) DEFAULT NULL,
  PRIMARY KEY (`invoiceId`),
  KEY `invoiceBy` (`invoiceBy`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `billing`
--

INSERT INTO `billing` (`invoiceId`, `invoiceBy`, `date`, `time`, `total`, `discount`, `payment`, `balance`, `invoice`) VALUES
('R96Ro21175923', 'C003', '2021-07-09', '17:59:23', 450, 0, 500, 50, 'No'),
('R96Ro21190811', 'C003', '2021-07-09', '19:08:11', 2000, 0, 3000, 1000, 'No'),
('R96Ro21175934', 'C003', '2021-07-09', '17:59:34', 450, 0, 50000, 49550, 'No'),
('R96Ro21180010', 'C003', '2021-07-09', '18:00:10', 450, 0, 50000, 49550, 'No'),
('R96Ro21191217', 'C003', '2021-07-09', '19:12:17', 1150, 0, 2000, 850, 'No'),
('R96Ro21191235', 'C003', '2021-07-09', '19:12:35', 1650, 0, 2000, 350, 'No'),
('R96Th21194214', 'C001', '2021-07-09', '19:42:14', 900, 0, 1000, 100, 'No'),
('R96Th21194322', 'C001', '2021-07-09', '19:43:22', 900, 0, 1000, 100, 'No'),
('R96Th21194412', 'C001', '2021-07-09', '19:44:12', 1250, 0, 13000, 11750, 'No'),
('R96Th21205842', 'C001', '2021-07-09', '20:58:42', 450, 0, 500, 50, 'No'),
('R96Th21205909', 'C001', '2021-07-09', '20:59:09', 700, 0, 1000, 300, 'No'),
('R96Th21211648', 'C001', '2021-07-09', '21:16:48', 450, 0, 500, 50, 'No'),
('R96Th21211701', 'C001', '2021-07-09', '21:17:01', 250, 0, 1000, 750, 'No'),
('R96Th21211733', 'C001', '2021-07-09', '21:17:33', 700, 0, 1000, 300, 'No'),
('R96Th21211959', 'C001', '2021-07-09', '21:19:59', 250, 0, 500, 250, 'No'),
('R96Th21231424', 'C001', '2021-07-09', '23:14:24', 700, 0, 1000, 300, 'No'),
('R106Th21141401', 'C001', '2021-07-10', '14:14:01', 450, 0, 700, 250, 'No'),
('R106Ro21144117', 'C003', '2021-07-10', '14:41:17', 450, 0, 500, 50, 'No'),
('R106Th21152145', 'C001', '2021-07-10', '15:21:45', 450, 0, 500, 50, 'No'),
('R106Th21152219', 'C001', '2021-07-10', '15:22:19', 900, 0, 1000.5, 100.5, 'No'),
('R106Th21152316', 'C001', '2021-07-10', '15:23:16', 900, 10, 1000.5, 110.5, 'No'),
('R106Nu21152516', 'C002', '2021-07-10', '15:25:16', 450, 0.5, 500.5, 51, 'No'),
('R106Th21153044', 'C001', '2021-07-10', '15:30:44', 450, 0, 500, 50, 'No'),
('R106Nu21155805', 'C002', '2021-07-10', '15:58:05', 450, 0, 500, 50, 'No'),
('R116Th21201433', 'C001', '2021-07-11', '20:14:33', 700, 0, 800, 100, 'No');

-- --------------------------------------------------------

--
-- Table structure for table `casher_balance`
--

DROP TABLE IF EXISTS `casher_balance`;
CREATE TABLE IF NOT EXISTS `casher_balance` (
  `balance` double DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `casher_balance`
--

INSERT INTO `casher_balance` (`balance`) VALUES
(5);

-- --------------------------------------------------------

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
CREATE TABLE IF NOT EXISTS `item` (
  `id` varchar(100) NOT NULL,
  `name` varchar(500) NOT NULL,
  `unitPrice` double NOT NULL,
  `quantity` int(11) NOT NULL,
  `mfd` date DEFAULT NULL,
  `exp` date DEFAULT NULL,
  `supplier_id` varchar(100) NOT NULL,
  `buyPrice` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `supplier_id` (`supplier_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `item`
--

INSERT INTO `item` (`id`, `name`, `unitPrice`, `quantity`, `mfd`, `exp`, `supplier_id`, `buyPrice`) VALUES
('I00006', 'cocakola 2L', 220, 200, '2021-07-11', '2021-08-07', 'S0003', 200),
('I00005', 'cocakola 1L', 150, 50, '2021-07-12', '2021-07-31', 'S0003', 100),
('I00003', 'solt 1kg packet', 200, 11, '2021-07-12', '2021-07-24', 'S0002', 150),
('I00004', 'solt 2kg packet', 400, 400, '2021-06-27', '2021-07-12', 'S0002', 300),
('I00001', 'suger 1kg packet', 500, 60, '2021-07-13', '2021-07-15', 'S0001', 450),
('I00002', 'suger 2kg packet', 1000, 2, '2021-07-13', '2021-07-31', 'S0001', 900),
('I00008', 'marker pen', 20, 20, NULL, NULL, 'S0004', 15),
('I00009', 'A4 100 sheets', 400, 5, '2021-07-10', '2021-08-07', 'S0005', 350),
('I00010', 'Mask', 20, 500, NULL, NULL, 'S0006', 15),
('I00011', 'power Bank 5000mAh', 2000, 20, NULL, NULL, 'S0007', 1750),
('I00012', 'iphone Cable', 1000, 50, NULL, NULL, 'S0008', 900),
('I00013', 'Dhal 500g packet', 200, 50, '2021-06-27', '2021-08-05', 'S0002', 190),
('I00014', 'Dhal 1Kg', 400, 45, '2021-06-28', '2021-07-29', 'S0003', 350),
('I00015', 'Dahal 2Kg packet', 800, 5, '2021-07-07', '2021-07-30', 'S0003', 750),
('I00016', '7Up 1L', 200, 20, '2021-07-11', '2021-09-11', 'S0002', 150);

-- --------------------------------------------------------

--
-- Table structure for table `loginhistory`
--

DROP TABLE IF EXISTS `loginhistory`;
CREATE TABLE IF NOT EXISTS `loginhistory` (
  `date` date NOT NULL,
  `userId` varchar(100) NOT NULL,
  `loginTime` time NOT NULL,
  `logoutTime` time NOT NULL,
  `Selling` varchar(100) DEFAULT NULL,
  KEY `userId` (`userId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `loginhistory`
--

INSERT INTO `loginhistory` (`date`, `userId`, `loginTime`, `logoutTime`, `Selling`) VALUES
('2021-07-13', 'C002', '10:02:01', '10:02:34', '500'),
('2021-07-13', 'C001', '10:11:35', '10:12:50', '500'),
('2021-07-13', 'C001', '10:56:04', '10:56:53', '500'),
('2021-07-13', 'C001', '10:58:45', '10:59:25', '500'),
('2021-07-13', 'C001', '11:21:30', '11:21:45', '500'),
('2021-07-13', 'C001', '11:26:55', '11:27:09', '500'),
('2021-07-13', 'C002', '13:15:09', '13:15:26', '500'),
('2021-07-13', 'C001', '13:32:30', '13:32:47', '500'),
('2021-07-13', 'C001', '13:36:19', '13:37:27', '500'),
('2021-07-13', 'C001', '13:41:45', '13:42:32', '500'),
('2021-07-13', 'C001', '13:44:38', '13:50:13', '500'),
('2021-07-13', 'C001', '13:53:23', '13:53:58', '500'),
('2021-07-13', 'C001', '14:02:59', '14:03:40', '500'),
('2021-07-13', 'C001', '14:05:50', '14:06:21', '500'),
('2021-07-13', 'C001', '14:07:20', '14:07:41', '500'),
('2021-07-13', 'C001', '14:10:43', '14:11:51', '500'),
('2021-07-13', 'C001', '14:12:08', '14:12:14', '500'),
('2021-07-13', 'C002', '14:14:20', '14:15:00', '500'),
('2021-07-13', 'C001', '14:20:14', '14:20:35', '500'),
('2021-07-19', 'C002', '22:38:19', '22:48:18', '500'),
('2021-07-19', 'C002', '22:52:47', '22:54:04', '500'),
('2021-07-19', 'C001', '22:57:33', '22:58:11', '500'),
('2021-07-19', 'C001', '22:59:58', '23:01:17', '500'),
('2021-07-19', 'C001', '23:08:17', '23:08:38', '500'),
('2021-07-19', 'C001', '23:09:01', '23:09:42', '500'),
('2021-07-19', 'C001', '23:11:27', '23:11:37', '500'),
('2021-07-19', 'C002', '23:14:41', '23:14:59', '500'),
('2021-07-19', 'C001', '23:31:26', '23:31:48', '500'),
('2021-07-19', 'C001', '23:32:36', '23:33:06', '500'),
('2021-07-19', 'C002', '23:34:08', '23:34:24', '500'),
('2021-07-19', 'C002', '23:39:09', '23:40:51', '500'),
('2021-07-19', 'C001', '23:42:48', '23:43:00', '500'),
('2021-07-19', 'C001', '23:43:42', '23:43:51', '500'),
('2021-07-19', 'C002', '23:44:39', '23:44:48', '500'),
('2021-07-19', 'C002', '23:46:09', '23:46:19', '500'),
('2021-07-20', 'C001', '10:20:50', '10:21:14', '500'),
('2021-07-20', 'C002', '10:22:12', '10:22:19', '500'),
('2021-07-20', 'C002', '10:24:26', '10:25:04', '500'),
('2021-07-20', 'C002', '18:24:09', '18:24:27', '500'),
('2021-07-20', 'C001', '18:26:16', '18:26:27', '500'),
('2021-07-21', 'C001', '21:07:40', '21:09:32', '500'),
('2021-07-21', 'C001', '22:15:18', '22:16:20', '500');

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
CREATE TABLE IF NOT EXISTS `supplier` (
  `id` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`id`, `name`) VALUES
('S0001', 'Munchi'),
('S0002', 'Meliban'),
('S0003', 'Kothmale'),
('S0004', 'AAA'),
('S0005', 'AAAC'),
('S0006', 'AABC'),
('S0007', 'AABD'),
('S0008', 'ABBD'),
('S0009', 'ABBE'),
('S0010', 'ABCE');

-- --------------------------------------------------------

--
-- Table structure for table `systemuser`
--

DROP TABLE IF EXISTS `systemuser`;
CREATE TABLE IF NOT EXISTS `systemuser` (
  `id` varchar(100) NOT NULL,
  `userName` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `phone` varchar(100) NOT NULL,
  `nic` varchar(100) DEFAULT NULL,
  `password` varchar(100) NOT NULL,
  `imageURL` varchar(100) DEFAULT NULL,
  `role` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `userName` (`userName`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `nic` (`nic`),
  UNIQUE KEY `imageURL` (`imageURL`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `systemuser`
--

INSERT INTO `systemuser` (`id`, `userName`, `email`, `phone`, `nic`, `password`, `imageURL`, `role`) VALUES
('C001', 'Tharanga', 'rocketnuwan30@gmail.com', '0750750621', '973523430V', '5503', 'src/images/pic.jpeg', 'admin'),
('C002', 'Nuwan', 'nuwan99@gmail.com', '0750750622', '973555430V', '5503', 'src/images/pic2.jpeg', 'user'),
('C003', 'Rocketnuwan', 'rocket@gmail.com', '0703411941', '965864790V', '750621', 'src/images/default.jpg', 'admin');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
