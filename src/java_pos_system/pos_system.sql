-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 28, 2023 at 05:37 PM
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
-- Table structure for table `cart`
--

CREATE TABLE `cart` (
  `memberid` varchar(10) NOT NULL,
  `productid` varchar(10) NOT NULL,
  `productquantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `checkout`
--

CREATE TABLE `checkout` (
  `checkoutid` varchar(10) NOT NULL,
  `memberid` varchar(10) NOT NULL,
  `paymentid` varchar(10) DEFAULT NULL,
  `productid` varchar(10) NOT NULL,
  `productsubtotal` float NOT NULL,
  `productquantity` int(11) NOT NULL,
  `paymentmethod` varchar(50) NOT NULL,
  `checkoutdatetime` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `member`
--

CREATE TABLE `member` (
  `memberid` varchar(10) NOT NULL,
  `memberpassword` varchar(16) NOT NULL,
  `membername` text NOT NULL,
  `memberaddress` text NOT NULL,
  `memberphone` varchar(12) NOT NULL,
  `memberemail` varchar(50) NOT NULL,
  `memberbalance` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `notification`
--

CREATE TABLE `notification` (
  `memberid` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `payment_method`
--

CREATE TABLE `payment_method` (
  `paymentid` varchar(10) NOT NULL,
  `memberid` varchar(10) NOT NULL,
  `paymentmethod` text NOT NULL,
  `cardnumber` varchar(16) DEFAULT NULL,
  `expirydate` varchar(5) DEFAULT NULL,
  `cvv` varchar(3) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `productid` varchar(10) NOT NULL,
  `productbrand` varchar(50) NOT NULL,
  `productname` varchar(50) NOT NULL,
  `productprice` float NOT NULL,
  `productdescription` text NOT NULL,
  `productcategory` varchar(50) NOT NULL,
  `productquantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`productid`, `productbrand`, `productname`, `productprice`, `productdescription`, `productcategory`, `productquantity`) VALUES
('PROD000001', 'FlyDigi', 'Wee 2T', 220, 'FlyDigi Wee 2T is a mobile device game controller.', 'Game Controller', 5),
('PROD000002', 'Apple', 'iPhone 12 Pro Max Screen Protector', 36.99, 'High-quality tempered glass screen protector for iPhone 12 Pro Max.', 'Screen Protector', 50),
('PROD000003', 'Samsung', 'Galaxy S21 Ultra Phone Case', 59.99, 'Durable and stylish phone case designed for the Samsung Galaxy S21 Ultra.', 'Phone Case', 30),
('PROD000004', 'Xiaomi', 'Mi 11 Phone Charger', 69.99, 'Fast charging USB-C charger for Xiaomi Mi 11 smartphones.', 'Phone Charger', 20),
('PROD000005', 'Huawei', 'Huawei P40 Pro Phone Cable', 35.99, 'High-speed USB-C to USB-A cable for Huawei P40 Pro devices.', 'Phone Cable', 40),
('PROD000006', 'Oppo', 'Oppo Find X3 Pro Power Bank', 99.99, 'Portable power bank with a capacity of 10,000mAh for charging Oppo Find X3 Pro on the go.', 'Power Bank', 15),
('PROD000007', 'Vivo', 'Vivo X60 Wired Earphone', 109.99, 'High-quality wired earphones designed for Vivo X60 smartphones.', 'Wired Earphone', 25),
('PROD000008', 'Realme', 'Realme Buds Air Wireless Earphone', 139.99, 'True wireless earphones with Bluetooth connectivity for Realme smartphones.', 'Wireless Earphone', 10),
('PROD000009', 'Sony', 'Sony WH-1000XM4 Wireless Headphone', 999.99, 'Industry-leading noise-canceling headphones with exceptional sound quality from Sony.', 'Wireless Headphone', 8),
('PROD000010', 'Pineng', 'Pineng 10000mAh Power Bank', 59.99, 'Slim and portable power bank with a capacity of 10000mAh from Pineng.', 'Power Bank', 20),
('PROD000011', 'Remax', 'Remax Fast Charging USB Wall Charger', 69.99, 'Quick charge USB wall charger with multiple USB ports from Remax.', 'Phone Charger', 15),
('PROD000012', 'JBL', 'JBL Flip 5 Portable Bluetooth Speaker', 399.99, 'Compact and waterproof Bluetooth speaker with powerful sound output from JBL.', 'Others', 12);

-- --------------------------------------------------------

--
-- Table structure for table `registercode`
--

CREATE TABLE `registercode` (
  `staff` varchar(10) NOT NULL,
  `admin` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `registercode`
--

INSERT INTO `registercode` (`staff`, `admin`) VALUES
('STAFFCODE1', 'ADMINCODE1');

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

-- --------------------------------------------------------

--
-- Table structure for table `support`
--

CREATE TABLE `support` (
  `memberid` varchar(10) NOT NULL,
  `membername` varchar(50) NOT NULL,
  `problem` text NOT NULL,
  `date` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `topup_transaction`
--

CREATE TABLE `topup_transaction` (
  `transactionid` varchar(10) NOT NULL,
  `memberid` varchar(10) NOT NULL,
  `paymentid` varchar(10) NOT NULL,
  `topupamount` float NOT NULL,
  `newbalance` float NOT NULL,
  `date` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cart`
--
ALTER TABLE `cart`
  ADD KEY `id1` (`memberid`),
  ADD KEY `id2` (`productid`);

--
-- Indexes for table `checkout`
--
ALTER TABLE `checkout`
  ADD KEY `id3` (`productid`),
  ADD KEY `id4` (`memberid`),
  ADD KEY `id5` (`paymentid`);

--
-- Indexes for table `member`
--
ALTER TABLE `member`
  ADD PRIMARY KEY (`memberid`);

--
-- Indexes for table `payment_method`
--
ALTER TABLE `payment_method`
  ADD PRIMARY KEY (`paymentid`),
  ADD KEY `id` (`memberid`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`productid`);

--
-- Indexes for table `staff`
--
ALTER TABLE `staff`
  ADD PRIMARY KEY (`staffid`);

--
-- Indexes for table `topup_transaction`
--
ALTER TABLE `topup_transaction`
  ADD KEY `membid` (`memberid`),
  ADD KEY `payid` (`paymentid`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cart`
--
ALTER TABLE `cart`
  ADD CONSTRAINT `id1` FOREIGN KEY (`memberid`) REFERENCES `member` (`memberid`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `id2` FOREIGN KEY (`productid`) REFERENCES `product` (`productid`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `checkout`
--
ALTER TABLE `checkout`
  ADD CONSTRAINT `id3` FOREIGN KEY (`productid`) REFERENCES `product` (`productid`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `id4` FOREIGN KEY (`memberid`) REFERENCES `member` (`memberid`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `id5` FOREIGN KEY (`paymentid`) REFERENCES `payment_method` (`paymentid`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `payment_method`
--
ALTER TABLE `payment_method`
  ADD CONSTRAINT `id` FOREIGN KEY (`memberid`) REFERENCES `member` (`memberid`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `topup_transaction`
--
ALTER TABLE `topup_transaction`
  ADD CONSTRAINT `membid` FOREIGN KEY (`memberid`) REFERENCES `member` (`memberid`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `payid` FOREIGN KEY (`paymentid`) REFERENCES `payment_method` (`paymentid`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
