-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 22, 2019 at 06:22 PM
-- Server version: 10.1.30-MariaDB
-- PHP Version: 7.2.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `budget_cal`
--

-- --------------------------------------------------------

--
-- Table structure for table `expenses`
--

CREATE TABLE `expenses` (
  `e_id` int(11) NOT NULL,
  `e_date` date NOT NULL,
  `e_category` varchar(255) NOT NULL,
  `e_item` varchar(255) NOT NULL,
  `e_add_details` varchar(255) DEFAULT NULL,
  `e_amount` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `expenses`
--

INSERT INTO `expenses` (`e_id`, `e_date`, `e_category`, `e_item`, `e_add_details`, `e_amount`) VALUES
(1, '2019-02-22', 'Bills', 'Electricity', 'Paid via BOC bank', 3500);

-- --------------------------------------------------------

--
-- Table structure for table `income`
--

CREATE TABLE `income` (
  `i_id` int(11) NOT NULL,
  `i_date` date NOT NULL,
  `i_category` varchar(255) NOT NULL,
  `i_type` varchar(255) DEFAULT NULL,
  `i_add_details` varchar(255) DEFAULT NULL,
  `i_amount` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `income`
--

INSERT INTO `income` (`i_id`, `i_date`, `i_category`, `i_type`, `i_add_details`, `i_amount`) VALUES
(1, '2019-02-22', 'Other', 'Loan interest', '3% loan interest from fixed deposit', 4500);

-- --------------------------------------------------------

--
-- Table structure for table `savings`
--

CREATE TABLE `savings` (
  `t_id` int(11) NOT NULL,
  `t_date` date NOT NULL,
  `t_category` varchar(255) NOT NULL,
  `t_type` varchar(255) DEFAULT NULL,
  `t_add_details` varchar(255) DEFAULT NULL,
  `t_amount` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `savings`
--

INSERT INTO `savings` (`t_id`, `t_date`, `t_category`, `t_type`, `t_add_details`, `t_amount`) VALUES
(1, '2019-02-22', 'Deposit', 'BOC', 'John\'s account on Colombo branch', 3000),
(2, '2019-02-23', 'Withdrawal', 'BOC', 'John\'s account on Colombo branch', 2000);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `expenses`
--
ALTER TABLE `expenses`
  ADD PRIMARY KEY (`e_id`);

--
-- Indexes for table `income`
--
ALTER TABLE `income`
  ADD PRIMARY KEY (`i_id`);

--
-- Indexes for table `savings`
--
ALTER TABLE `savings`
  ADD PRIMARY KEY (`t_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `expenses`
--
ALTER TABLE `expenses`
  MODIFY `e_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `income`
--
ALTER TABLE `income`
  MODIFY `i_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `savings`
--
ALTER TABLE `savings`
  MODIFY `t_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
