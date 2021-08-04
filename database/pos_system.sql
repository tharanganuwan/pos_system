-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3308
-- Generation Time: Aug 04, 2021 at 10:14 AM
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
('R27Ad21211443', 'C001', '2021-08-02', '21:14:43', 65, 0, 450, 385, 'No'),
('R27Ad21214932', 'C001', '2021-08-02', '21:49:32', 90, 0, 100, 10, 'No');

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
(155);

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
('I00001', 'sd', 4, -1, NULL, NULL, 'S001 ', 44),
('I00002', 'aa', 45, 3, NULL, NULL, 'S001 ', 22),
('I00003', 'as', 44, 5, NULL, NULL, 'S001 ', 40),
('I00004', 'sa', 45, 42, NULL, NULL, 'S001 ', 45),
('I00005', 'ww', 66, 66, NULL, NULL, 'S005', 66),
('I00006', 'mango', 50, 40, '2021-08-03', '2021-08-04', 'S006', 45);

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
('2021-07-27', 'C001', '14:13:52', '14:14:48', '500'),
('2021-07-27', 'C001', '14:21:29', '14:21:44', '500'),
('2021-07-27', 'C001', '15:16:46', '15:17:05', '500'),
('2021-07-27', 'C002', '15:38:16', '15:38:27', '500'),
('2021-07-27', 'C002', '18:37:33', '18:38:21', '500'),
('2021-07-27', 'C001', '19:11:14', '19:12:09', '500'),
('2021-07-27', 'C004', '22:34:47', '22:38:20', '500'),
('2021-07-27', 'C001', '22:39:06', '22:39:09', '500'),
('2021-07-27', 'C004', '22:39:23', '22:39:33', '500'),
('2021-07-27', 'C002', '22:40:09', '22:40:56', '500'),
('2021-07-27', 'C001', '22:42:19', '22:43:29', '500'),
('2021-07-27', 'C001', '22:47:07', '22:47:45', '500'),
('2021-07-27', 'C001', '22:47:52', '22:48:00', '500'),
('2021-07-27', 'C004', '22:48:08', '22:48:29', '500'),
('2021-07-27', 'C001', '22:49:49', '22:49:55', '500'),
('2021-07-27', 'C001', '22:50:20', '22:52:08', '500'),
('2021-07-27', 'I001', '23:09:18', '23:09:53', '500'),
('2021-07-27', 'I001', '23:10:40', '23:16:33', '500'),
('2021-07-27', 'C003', '23:16:43', '23:16:53', '500'),
('2021-07-27', 'C002', '23:17:01', '23:17:03', '500'),
('2021-07-27', 'C001', '23:21:46', '23:22:17', '500'),
('2021-07-27', 'C001', '23:27:48', '23:28:09', '500'),
('2021-07-27', 'C001', '23:29:01', '23:29:09', '500'),
('2021-07-27', 'C003', '23:31:11', '23:33:30', '500'),
('2021-07-28', 'C001', '09:52:42', '09:53:48', '500'),
('2021-07-28', 'C001', '10:08:43', '10:10:15', '500'),
('2021-07-28', 'C001', '10:11:36', '10:13:20', '500'),
('2021-07-28', 'C001', '10:18:18', '10:22:29', '500'),
('2021-07-28', 'C001', '10:32:48', '10:33:15', '500'),
('2021-07-28', 'C001', '10:58:25', '10:58:38', '500'),
('2021-07-28', 'C001', '11:00:01', '11:00:27', '500'),
('2021-07-28', 'C001', '11:02:37', '11:02:54', '500'),
('2021-07-28', 'C007', '11:14:05', '11:16:46', '500'),
('2021-07-28', 'C008', '11:24:58', '11:28:05', '500'),
('2021-07-28', 'C001', '11:50:38', '11:51:04', '500'),
('2021-07-28', 'C001', '11:57:04', '11:58:43', '500'),
('2021-07-28', 'C001', '12:03:02', '12:03:50', '500'),
('2021-07-28', 'C001', '12:06:41', '12:07:14', '500'),
('2021-07-28', 'C001', '12:08:25', '12:14:02', '500'),
('2021-08-02', 'C001', '09:21:27', '09:22:00', '500'),
('2021-08-02', 'C001', '09:44:49', '09:45:02', '500'),
('2021-08-02', 'C001', '09:52:04', '09:52:14', '500'),
('2021-08-02', 'C001', '09:54:09', '09:54:28', '500'),
('2021-08-02', 'C002', '15:37:30', '15:37:34', '500'),
('2021-08-02', 'C002', '15:42:50', '15:43:18', '500'),
('2021-08-02', 'C001', '15:56:44', '15:57:11', '500'),
('2021-08-02', 'C002', '16:01:58', '16:02:45', '500'),
('2021-08-02', 'C001', '16:12:32', '16:12:49', '500'),
('2021-08-02', 'C001', '16:13:34', '16:13:51', '500'),
('2021-08-02', 'C001', '16:15:18', '16:15:35', '500'),
('2021-08-02', 'C001', '19:16:21', '19:16:35', '500'),
('2021-08-02', 'C001', '19:36:00', '19:36:08', '500'),
('2021-08-02', 'C001', '20:35:01', '20:35:05', '500'),
('2021-08-02', 'C001', '20:45:52', '20:45:53', '500'),
('2021-08-02', 'C001', '20:55:26', '20:55:31', '500'),
('2021-08-02', 'C001', '21:05:20', '21:06:37', '500'),
('2021-08-02', 'C001', '21:12:44', '21:15:48', '500'),
('2021-08-02', 'C001', '21:36:52', '21:37:19', '500'),
('2021-08-02', 'C002', '21:39:15', '21:46:27', '500'),
('2021-08-02', 'C001', '21:48:05', '21:53:28', '500'),
('2021-08-02', 'C001', '22:49:06', '22:49:11', '500'),
('2021-08-02', 'C001', '22:50:29', '22:51:00', '500'),
('2021-08-04', 'C001', '13:48:48', '13:48:56', '500'),
('2021-08-04', 'C001', '14:59:53', '15:00:17', '500'),
('2021-08-04', 'C001', '15:19:49', '15:20:29', '500');

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
('S001', 'KKK'),
('S005', 'hal6'),
('S003', 'Tharanga nuwan'),
('S004', 'nuwan'),
('S006', 'sdfs');

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
  `role` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `systemuser`
--

INSERT INTO `systemuser` (`id`, `userName`, `email`, `phone`, `nic`, `password`, `imageURL`, `role`) VALUES
('C002', 'user1', 'user1@gmail..com', '0750750687', '9765321250V', '12345', 'src/images/default.jpg', 'User'),
('C003', 'Admin1', 'admin@gmail.com', '07894561231', '457896231V', '12345', 'src/images/IMGC003.jpeg', 'Admin'),
('C001', 'Admin', 'admin@gmail.com', '0776803526', '9685968950V', '12345', 'src/images/default.jpg', 'Admin');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
