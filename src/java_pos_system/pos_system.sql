-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 25, 2023 at 11:11 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
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
-- Table structure for table `item`
--

CREATE TABLE `item` (
  `itemID` varchar(10) NOT NULL,
  `itemName` varchar(50) NOT NULL,
  `itemPrice` double NOT NULL,
  `itemDescription` text NOT NULL,
  `itemSupplier` text NOT NULL,
  `itemAvailable` int(11) NOT NULL,
  `forSale` tinyint(1) NOT NULL,
  `dateImported` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `sale`
--

CREATE TABLE `sale` (
  `salesID` varchar(10) NOT NULL,
  `salesItemID` varchar(10) NOT NULL,
  `customer` int(11) NOT NULL,
  `salesDate` date NOT NULL,
  `totalPrice` double NOT NULL,
  `paymentMethod` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `sales_item_list`
--

CREATE TABLE `sales_item_list` (
  `salesItemID` varchar(10) NOT NULL,
  `itemID` varchar(10) NOT NULL,
  `orderAmount` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE `staff` (
  `staffID` varchar(10) NOT NULL,
  `staffName` varchar(50) NOT NULL,
  `staffPassword` varchar(16) NOT NULL,
  `staffContactNo` varchar(11) NOT NULL,
  `staffSalary` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `item`
--
ALTER TABLE `item`
  ADD PRIMARY KEY (`itemID`);

--
-- Indexes for table `sale`
--
ALTER TABLE `sale`
  ADD PRIMARY KEY (`salesID`),
  ADD KEY `salesItemID` (`salesItemID`);

--
-- Indexes for table `sales_item_list`
--
ALTER TABLE `sales_item_list`
  ADD PRIMARY KEY (`salesItemID`);

--
-- Indexes for table `staff`
--
ALTER TABLE `staff`
  ADD PRIMARY KEY (`staffID`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `sale`
--
ALTER TABLE `sale`
  ADD CONSTRAINT `salesItemID` FOREIGN KEY (`salesItemID`) REFERENCES `sales_item_list` (`salesItemID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
