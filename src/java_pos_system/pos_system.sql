-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 24, 2023 at 04:13 PM
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
-- Table structure for table `member`
--

CREATE TABLE `member` (
  `memberid` varchar(10) NOT NULL,
  `password` varchar(16) NOT NULL,
  `membername` text NOT NULL,
  `memberaddress` text NOT NULL,
  `memberphone` varchar(12) NOT NULL,
  `memberemail` varchar(50) NOT NULL,
  `memberbalance` float NOT NULL
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

--
-- Dumping data for table `sale`
--

INSERT INTO `sale` (`salesID`, `salesItemID`, `customer`, `salesDate`, `totalPrice`, `paymentMethod`) VALUES
('A123123123', 'A123123123', 0, '2022-10-10', 1000, 1);

-- --------------------------------------------------------

--
-- Table structure for table `sales_item_list`
--

CREATE TABLE `sales_item_list` (
  `salesItemID` varchar(10) NOT NULL,
  `itemID` varchar(10) NOT NULL,
  `orderAmount` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `sales_item_list`
--

INSERT INTO `sales_item_list` (`salesItemID`, `itemID`, `orderAmount`) VALUES
('A123123123', 'A123123123', 123);

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE `staff` (
  `staffid` varchar(10) NOT NULL,
  `staffpassword` varchar(16) NOT NULL,
  `staffname` varchar(50) NOT NULL,
  `staffaddress` text NOT NULL,
  `staffphone` varchar(12) NOT NULL,
  `staffemail` varchar(50) NOT NULL,
  `staffsalary` float NOT NULL,
  `staffposition` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `staff`
--

INSERT INTO `staff` (`staffid`, `staffpassword`, `staffname`, `staffaddress`, `staffphone`, `staffemail`, `staffsalary`, `staffposition`) VALUES
('STAF000001', 'fqjwiJ2123', 'Super', 'Superioldexiaorqjgiqwn', '012314566787', 'gay@gay.gay', 1234.45, 'Gay Manager');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `item`
--
ALTER TABLE `item`
  ADD PRIMARY KEY (`itemID`);

--
-- Indexes for table `member`
--
ALTER TABLE `member`
  ADD PRIMARY KEY (`memberid`);

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
  ADD PRIMARY KEY (`staffid`);

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
