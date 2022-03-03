-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 03, 2022 at 08:33 AM
-- Server version: 10.1.30-MariaDB
-- PHP Version: 7.2.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tms`
--

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

CREATE TABLE `account` (
  `user_id` int(100) NOT NULL,
  `Names` varchar(100) NOT NULL,
  `username` varchar(100) NOT NULL,
  `phonenumber` int(10) NOT NULL,
  `role` varchar(50) NOT NULL,
  `password` varchar(20) NOT NULL,
  `confirmpassword` varchar(50) NOT NULL,
  `security_question` varchar(100) NOT NULL,
  `answer` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`user_id`, `Names`, `username`, `phonenumber`, `role`, `password`, `confirmpassword`, `security_question`, `answer`) VALUES
(2, 'Evanson Kinyajui', 'Evans', 720055645, 'Secretary', 'qwerty', 'qwerty', 'What is your Favorite Dream Country?', 'U.S.A'),
(3, 'qwerty', 'quartz', 7889911, 'Project Manager', '1212', '1212', 'What is your Favorite Pet?', 'cat'),
(5, 'Denis Chege', 'dc', 709876543, 'Treasurer', '1999', '1999', 'What is your Hobby?', 'reading'),
(6, 'Mike Faraday', 'Mike', 123456780, 'Admin', '1234567890', '1234567890', 'What is Favourite Pet?', 'Cat'),
(7, 'MIKE', 'MK', 123456789, 'Treasurer', 'qwert1', 'qwert1', 'What is your Favorite Pet?', 'CAT');

-- --------------------------------------------------------

--
-- Table structure for table `calendar_of_activities`
--

CREATE TABLE `calendar_of_activities` (
  `id` int(5) NOT NULL,
  `Event` varchar(1000) NOT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `clinicevents`
--

CREATE TABLE `clinicevents` (
  `clid` varchar(50) NOT NULL,
  `hardware_troubleshooting` int(5) NOT NULL,
  `windows_installation` int(100) NOT NULL,
  `dust_cleaning` int(100) NOT NULL,
  `software_installation` int(5) NOT NULL,
  `antivirus_installation` int(5) NOT NULL,
  `windows_updation` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `clinicevents`
--

INSERT INTO `clinicevents` (`clid`, `hardware_troubleshooting`, `windows_installation`, `dust_cleaning`, `software_installation`, `antivirus_installation`, `windows_updation`) VALUES
('Amount', 0, 450, 300, 300, 0, 0),
('service_count', 0, 3, 1, 1, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `clinicservices`
--

CREATE TABLE `clinicservices` (
  `servid` int(5) NOT NULL,
  `servtype` varchar(200) NOT NULL,
  `Amount` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `clinicservices`
--

INSERT INTO `clinicservices` (`servid`, `servtype`, `Amount`) VALUES
(1, 'hardware_troubleshooting', 250),
(2, 'windows_installation', 150),
(3, 'dust_cleaning', 300),
(4, 'software_installation\r\n\r\n', 100),
(5, 'antivirus_installation', 100),
(6, 'windows_updation', 200);

-- --------------------------------------------------------

--
-- Table structure for table `clubproperties`
--

CREATE TABLE `clubproperties` (
  `property_id` int(5) NOT NULL,
  `regno` varchar(15) NOT NULL,
  `name` varchar(100) NOT NULL,
  `phonenumber` int(10) NOT NULL,
  `item_name` varchar(100) NOT NULL,
  `dateissued` varchar(15) NOT NULL,
  `returndate` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `clubproperties`
--

INSERT INTO `clubproperties` (`property_id`, `regno`, `name`, `phonenumber`, `item_name`, `dateissued`, `returndate`) VALUES
(1, 'EB1/12345/18', 'DC', 798765432, 'Network Switch', 'Sep 1, 2021', 'Sep 8, 2021'),
(2, 'EB1/34343/12', 'MIKE', 798123456, 'Projector', '03 - 09 - 2021', '01 - 09 - 2021');

-- --------------------------------------------------------

--
-- Table structure for table `computer_clinics`
--

CREATE TABLE `computer_clinics` (
  `receipt_id` int(100) NOT NULL,
  `Type_services` varchar(100) NOT NULL,
  `cost` int(3) NOT NULL,
  `Amount` int(6) NOT NULL,
  `date_time` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `eventattendance`
--

CREATE TABLE `eventattendance` (
  `Event_id` int(5) NOT NULL,
  `regnumber` varchar(20) NOT NULL,
  `names` varchar(100) NOT NULL,
  `phonenumber` int(10) NOT NULL,
  `email` varchar(50) NOT NULL,
  `eventname` varchar(100) NOT NULL,
  `eventdate` varchar(100) NOT NULL,
  `status` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `facilitators`
--

CREATE TABLE `facilitators` (
  `Number_id` int(5) NOT NULL,
  `names` varchar(100) NOT NULL,
  `phonenumber` int(10) NOT NULL,
  `Event_name` varchar(100) NOT NULL,
  `Event_Date` varchar(15) NOT NULL,
  `Bio` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `facilitators`
--

INSERT INTO `facilitators` (`Number_id`, `names`, `phonenumber`, `Event_name`, `Event_Date`, `Bio`) VALUES
(3, 'Jeff Bezos', 712387678, 'Crypto company', '04 - 08 - 2021', 'Developer and Data Analyst');

-- --------------------------------------------------------

--
-- Table structure for table `finance`
--

CREATE TABLE `finance` (
  `finance_id` int(5) NOT NULL,
  `regnumber` varchar(20) NOT NULL,
  `name` varchar(100) NOT NULL,
  `phonenumber` int(10) NOT NULL,
  `year_sem` varchar(20) NOT NULL,
  `purpose` varchar(50) NOT NULL,
  `amount` int(6) NOT NULL,
  `datetime` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `finance`
--

INSERT INTO `finance` (`finance_id`, `regnumber`, `name`, `phonenumber`, `year_sem`, `purpose`, `amount`, `datetime`) VALUES
(1, 'EB1/44556/17', 'Evans Kinyajui', 711223344, 'Y4S1', 'registration', 250, '2021-09-01 22:19:49.818488'),
(2, 'EB3/12345/17', 'Mike Turin', 700001234, 'Y1S2', 'registration', 100, '2021-09-03 11:08:34.616913'),
(3, 'EB1/12345/18', 'DC', 798765432, 'Y2S2', 'registration', 100, '2021-09-03 11:09:37.639867'),
(4, 'EB1/112233/19', 'Evans Muchai', 799887766, 'Y3S2', 'registration', 100, '2021-09-03 11:10:54.050161'),
(5, 'EB1/112233/19', 'Evans Muchai', 799887766, 'Y3S2', 'subscription', 50, '2021-09-03 11:11:18.961588'),
(6, 'EB3/12345/17', 'Mike Turin', 700001234, 'Y1S2', 'subscription', 50, '2021-09-03 11:11:56.334700'),
(7, 'EB1/12345/18', 'DC', 798765432, 'Y2S2', 'subscription', 50, '2021-09-03 11:12:27.432081'),
(8, 'EBS1/12345/20', 'Wilson Mwiti', 123456789, 'Y4S1', 'registration', 200, '2021-09-03 11:53:57.081510'),
(9, 'EB1/12345/18', 'DC', 798765432, 'Y2S2', 'registration', 200, '2021-09-16 11:06:51.017083'),
(10, 'EB1/12345/18', 'DC', 798765432, 'Y2S2', 'subscription', 100, '2021-09-16 11:08:01.255889'),
(11, 'EB1/12345/18', 'DC', 798765432, 'Y2S2', 'registration', 100, '2021-09-16 12:00:05.476532');

-- --------------------------------------------------------

--
-- Table structure for table `incomeexpenditures`
--

CREATE TABLE `incomeexpenditures` (
  `Financial_id` int(5) NOT NULL,
  `Type` varchar(50) NOT NULL,
  `Source` varchar(100) NOT NULL,
  `Amount` int(5) NOT NULL,
  `Date` timestamp(5) NOT NULL DEFAULT CURRENT_TIMESTAMP(5) ON UPDATE CURRENT_TIMESTAMP(5)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `incomeexpenditures`
--

INSERT INTO `incomeexpenditures` (`Financial_id`, `Type`, `Source`, `Amount`, `Date`) VALUES
(1, 'Expenses', 'purchasing ethernet cable', 200, '2021-09-03 13:03:10.48832'),
(2, 'Incomes', ' clinic', 2000, '2021-09-03 13:03:33.66354'),
(3, 'Expenses', ' transport', 1000, '2021-09-03 13:03:52.04223'),
(4, 'Expenses', 'pay facilitator', 1800, '2021-09-03 13:50:17.03803'),
(5, 'Expenses', 'WRE', 200, '2021-09-16 11:09:09.85045');

-- --------------------------------------------------------

--
-- Table structure for table `members`
--

CREATE TABLE `members` (
  `members_id` int(5) NOT NULL,
  `regno` varchar(20) NOT NULL,
  `names` varchar(100) NOT NULL,
  `phonenumbers` int(10) NOT NULL,
  `course` varchar(100) NOT NULL,
  `area_specialization` varchar(100) NOT NULL,
  `year_semester` varchar(50) NOT NULL,
  `date` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `members`
--

INSERT INTO `members` (`members_id`, `regno`, `names`, `phonenumbers`, `course`, `area_specialization`, `year_semester`, `date`) VALUES
(2, 'rexmMIKE KEITH', 'MIKE KEITH', 798123456, 'Computer Science', 'Big Data', '1', '08 - 08 - 2021'),
(3, 'rexmshiku jmm', 'shiku jmm', 712345098, 'Applied Computer Science', 'Machine Learning', '1', '10 - 08 - 2021'),
(5, 'EB1/44556/17', 'Evans Kinyajui', 711223344, 'Computer Science', 'Dgital Forensic', 'Y4S1', '28 - 08 - 2021'),
(6, 'EB3/12345/17', 'Mike Turin', 700001234, 'Applied Computer Science', 'BlockChain', 'Y1S2', '03 - 09 - 2021'),
(7, 'EB1/12345/18', 'DC', 798765432, 'Computer Science', 'HPC', 'Y2S2', '04 - 09 - 2021'),
(9, 'EB1/112233/19', 'Evans Muchai', 799887766, 'Computer Science', 'Machine Learning', 'Y3S2', '03 - 09 - 2021'),
(10, 'EBS1/12345/20', 'Wilson Mwiti', 123456789, 'Computer Science', 'Software Engineering', 'Y4S1', '03 - 09 - 2021');

-- --------------------------------------------------------

--
-- Table structure for table `members_attendance`
--

CREATE TABLE `members_attendance` (
  `Attendance_id` int(5) NOT NULL,
  `regno` varchar(100) NOT NULL,
  `name` varchar(50) NOT NULL,
  `phonenumber` int(10) NOT NULL,
  `course` varchar(50) NOT NULL,
  `year_semester` varchar(20) NOT NULL,
  `status` varchar(50) NOT NULL,
  `date_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `members_attendance`
--

INSERT INTO `members_attendance` (`Attendance_id`, `regno`, `name`, `phonenumber`, `course`, `year_semester`, `status`, `date_time`) VALUES
(1, 'EB1/12345/18', 'DC', 798765432, 'Computer Science', 'Y2S2', 'Present', '2021-09-16 10:35:37.851745');

-- --------------------------------------------------------

--
-- Table structure for table `notices`
--

CREATE TABLE `notices` (
  `notice_id` int(5) NOT NULL,
  `noticename` varchar(1000) NOT NULL,
  `date_recorded` timestamp(5) NOT NULL DEFAULT CURRENT_TIMESTAMP(5) ON UPDATE CURRENT_TIMESTAMP(5)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `notices`
--

INSERT INTO `notices` (`notice_id`, `noticename`, `date_recorded`) VALUES
(1, 'Heey i will not attend meeting today', '2021-09-16 10:20:16.81703'),
(2, 'Heey Guys', '2021-09-16 10:38:19.79008'),
(3, 'am ben', '2021-09-16 11:19:22.31349'),
(4, 'am not near', '2021-09-16 11:27:18.37247');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `phonenumber` (`phonenumber`);

--
-- Indexes for table `calendar_of_activities`
--
ALTER TABLE `calendar_of_activities`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `clinicevents`
--
ALTER TABLE `clinicevents`
  ADD PRIMARY KEY (`clid`);

--
-- Indexes for table `clinicservices`
--
ALTER TABLE `clinicservices`
  ADD PRIMARY KEY (`servid`);

--
-- Indexes for table `clubproperties`
--
ALTER TABLE `clubproperties`
  ADD PRIMARY KEY (`property_id`);

--
-- Indexes for table `computer_clinics`
--
ALTER TABLE `computer_clinics`
  ADD PRIMARY KEY (`receipt_id`);

--
-- Indexes for table `eventattendance`
--
ALTER TABLE `eventattendance`
  ADD PRIMARY KEY (`Event_id`);

--
-- Indexes for table `facilitators`
--
ALTER TABLE `facilitators`
  ADD PRIMARY KEY (`Number_id`),
  ADD UNIQUE KEY `phonenumber` (`phonenumber`);

--
-- Indexes for table `finance`
--
ALTER TABLE `finance`
  ADD PRIMARY KEY (`finance_id`);

--
-- Indexes for table `incomeexpenditures`
--
ALTER TABLE `incomeexpenditures`
  ADD PRIMARY KEY (`Financial_id`);

--
-- Indexes for table `members`
--
ALTER TABLE `members`
  ADD PRIMARY KEY (`members_id`),
  ADD UNIQUE KEY `phonenumbers` (`phonenumbers`),
  ADD UNIQUE KEY `regno` (`regno`);

--
-- Indexes for table `members_attendance`
--
ALTER TABLE `members_attendance`
  ADD PRIMARY KEY (`Attendance_id`);

--
-- Indexes for table `notices`
--
ALTER TABLE `notices`
  ADD PRIMARY KEY (`notice_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `account`
--
ALTER TABLE `account`
  MODIFY `user_id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `calendar_of_activities`
--
ALTER TABLE `calendar_of_activities`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `clinicservices`
--
ALTER TABLE `clinicservices`
  MODIFY `servid` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `clubproperties`
--
ALTER TABLE `clubproperties`
  MODIFY `property_id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `computer_clinics`
--
ALTER TABLE `computer_clinics`
  MODIFY `receipt_id` int(100) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `eventattendance`
--
ALTER TABLE `eventattendance`
  MODIFY `Event_id` int(5) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `facilitators`
--
ALTER TABLE `facilitators`
  MODIFY `Number_id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `finance`
--
ALTER TABLE `finance`
  MODIFY `finance_id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `incomeexpenditures`
--
ALTER TABLE `incomeexpenditures`
  MODIFY `Financial_id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `members`
--
ALTER TABLE `members`
  MODIFY `members_id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `members_attendance`
--
ALTER TABLE `members_attendance`
  MODIFY `Attendance_id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `notices`
--
ALTER TABLE `notices`
  MODIFY `notice_id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
