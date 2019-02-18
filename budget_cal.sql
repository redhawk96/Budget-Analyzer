-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 18, 2019 at 06:19 PM
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
  `e_category` varchar(255) NOT NULL,
  `e_item` varchar(255) NOT NULL,
  `e_date` date NOT NULL,
  `e_amount` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `expenses`
--

INSERT INTO `expenses` (`e_id`, `e_category`, `e_item`, `e_date`, `e_amount`) VALUES
(1, 'MS', 'Item', '2019-01-01', '0'),
(2, 'MS', 'Item', '2019-02-01', '0'),
(3, 'MS', 'Item', '2019-03-01', '0'),
(4, 'MS', 'Item', '2019-04-01', '0'),
(5, 'MS', 'Item', '2019-05-01', '0'),
(6, 'MS', 'Item', '2019-06-01', '0'),
(7, 'MS', 'Item', '2019-07-01', '0'),
(8, 'MS', 'Item', '2019-08-01', '0'),
(9, 'MS', 'Item', '2019-09-01', '0'),
(10, 'MS', 'Item', '2019-10-01', '0'),
(11, 'MS', 'Item', '2019-11-01', '0'),
(12, 'MS', 'Item', '2019-12-01', '0');

-- --------------------------------------------------------

--
-- Table structure for table `income`
--

CREATE TABLE `income` (
  `i_id` int(11) NOT NULL,
  `i_category` varchar(255) NOT NULL,
  `i_date` date NOT NULL,
  `i_amount` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `income`
--

INSERT INTO `income` (`i_id`, `i_category`, `i_date`, `i_amount`) VALUES
(1, 'MS', '2019-01-01', '0'),
(2, 'MS', '2019-02-01', '0'),
(3, 'MS', '2019-03-01', '0'),
(4, 'MS', '2019-04-01', '0'),
(5, 'MS', '2019-05-01', '0'),
(6, 'MS', '2019-06-01', '0'),
(7, 'MS', '2019-07-01', '0'),
(8, 'MS', '2019-08-01', '0'),
(9, 'MS', '2019-09-01', '0'),
(10, 'MS', '2019-10-01', '0'),
(11, 'MS', '2019-11-01', '0'),
(12, 'MS', '2019-12-01', '0');

-- --------------------------------------------------------

--
-- Table structure for table `savings`
--

CREATE TABLE `savings` (
  `t_id` int(11) NOT NULL,
  `t_category` varchar(255) NOT NULL,
  `t_date` date NOT NULL,
  `t_amount` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `savings`
--

INSERT INTO `savings` (`t_id`, `t_category`, `t_date`, `t_amount`) VALUES
(1, 'Deposit', '2019-01-01', '0'),
(2, 'Withdrawal', '2019-01-01', '0'),
(3, 'Deposit', '2019-02-01', '0'),
(4, 'Withdrawal', '2019-02-01', '0'),
(5, 'Deposit', '2019-03-01', '0'),
(6, 'Withdrawal', '2019-03-01', '0'),
(7, 'Deposit', '2019-04-01', '0'),
(8, 'Withdrawal', '2019-04-01', '0'),
(9, 'Deposit', '2019-05-01', '0'),
(10, 'Withdrawal', '2019-05-01', '0'),
(11, 'Deposit', '2019-06-01', '0'),
(12, 'Withdrawal', '2019-06-01', '0'),
(13, 'Deposit', '2019-07-01', '0'),
(14, 'Withdrawal', '2019-07-01', '0'),
(15, 'Deposit', '2019-08-01', '0'),
(16, 'Withdrawal', '2019-08-01', '0'),
(17, 'Deposit', '2019-09-01', '0'),
(18, 'Withdrawal', '2019-09-01', '0'),
(19, 'Deposit', '2019-10-01', '0'),
(20, 'Withdrawal', '2019-10-01', '0'),
(21, 'Deposit', '2019-11-01', '0'),
(22, 'Withdrawal', '2019-11-01', '0'),
(23, 'Deposit', '2019-12-01', '0'),
(24, 'Withdrawal', '2019-12-01', '0');

-- --------------------------------------------------------

--
-- Table structure for table `system`
--

CREATE TABLE `system` (
  `year` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `system`
--

INSERT INTO `system` (`year`) VALUES
(2019);

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
-- Indexes for table `system`
--
ALTER TABLE `system`
  ADD PRIMARY KEY (`year`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `expenses`
--
ALTER TABLE `expenses`
  MODIFY `e_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `income`
--
ALTER TABLE `income`
  MODIFY `i_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `savings`
--
ALTER TABLE `savings`
  MODIFY `t_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
