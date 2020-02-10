SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


--
-- Database: `java2019`
--
CREATE DATABASE IF NOT EXISTS `java2019` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `java2019`;

-- --------------------------------------------------------

--
-- Table structure for table `Categories`
--

CREATE TABLE IF NOT EXISTS `Categories` (
  `categoryID` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL UNIQUE,
  PRIMARY KEY (`categoryID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `Categories`
--

INSERT INTO `Categories` (`categoryID`, `name`) VALUES
(1, 'Home'),
(2, 'Money'),
(3, 'Work'),
(4, 'Urgently');

-- --------------------------------------------------------

--
-- Table structure for table `Statuses`
--

CREATE TABLE IF NOT EXISTS `Statuses` (
  `statusID` int NOT NULL AUTO_INCREMENT,
  `name` set("checked","unchecked") NOT NULL,
  PRIMARY KEY (`statusID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `Statuses`
--

INSERT INTO `Statuses` (`statusID`, `name`) VALUES
(1, 'checked'),
(2, 'unchecked');

-- --------------------------------------------------------

--
-- Table structure for table `Tasks`
--

CREATE TABLE IF NOT EXISTS `Tasks` (
  `taskID` int NOT NULL AUTO_INCREMENT,
  `shortDescription` varchar(255) NOT NULL,
  `longDescription` text NOT NULL,
  `categoryID` int NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `start` date NOT NULL,
  `end` date NOT NULL,
  `statusID` int NOT NULL DEFAULT 2,
  PRIMARY KEY (`taskID`),
  KEY `fk_task_category` (`categoryID`),
  KEY `fk_task_status` (`statusID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Tasks`
--
ALTER TABLE `Tasks`
  ADD CONSTRAINT `fk_task_category` FOREIGN KEY (`categoryID`) REFERENCES `Categories` (`categoryID`),
  ADD CONSTRAINT `fk_task_status` FOREIGN KEY (`statusID`) REFERENCES `Statuses` (`statusID`);
COMMIT;

