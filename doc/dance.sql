/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : dance

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2018-08-16 12:29:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `administrator`
-- ----------------------------
DROP TABLE IF EXISTS `administrator`;
CREATE TABLE `administrator` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `loginname` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '登录名',
  `password` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `username` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `gender` tinyint(1) NOT NULL,
  `mobile` char(11) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '手机号码',
  `status` tinyint(1) DEFAULT '0' COMMENT '状态',
  `createtime` datetime NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='管理员信息表';

-- ----------------------------
-- Records of administrator
-- ----------------------------
INSERT INTO `administrator` VALUES ('1', 'lanning', '123456', 'qwer', '0', '1234568965', '0', '2018-08-14 17:29:43');
INSERT INTO `administrator` VALUES ('2', 'liufeng', '123456', '刘丰', '0', '1234568965', '0', '2018-08-16 12:15:15');

-- ----------------------------
-- Table structure for `course`
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `coursename` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '课程名',
  `note` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '课程简介',
  `begintime` datetime NOT NULL COMMENT '课程开始时间',
  `endtime` datetime NOT NULL COMMENT '结束时间',
  `duration` tinyint(4) NOT NULL COMMENT '课程时长，分钟为单位',
  `numlimit` int(11) DEFAULT '15' COMMENT '上课人数上限',
  `currNum` int(11) DEFAULT '0' COMMENT '当前学员人数',
  `teacherId` int(11) NOT NULL DEFAULT '0' COMMENT '上课老师',
  `status` tinyint(1) DEFAULT '0' COMMENT '状态',
  `createtime` datetime NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='课程信息表';

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('1', '拉丁舞', '123456', '2018-08-15 18:00:00', '2018-08-15 19:00:00', '60', '15', '1', '1', '0', '2018-08-15 10:18:28');
INSERT INTO `course` VALUES ('2', '肚皮舞', '123456', '2018-08-16 18:00:00', '2018-08-16 20:00:00', '120', '15', '0', '1', '0', '2018-08-15 10:22:18');
INSERT INTO `course` VALUES ('3', '肚皮舞A', '123456', '2018-08-17 13:00:00', '2018-08-17 14:00:00', '60', '15', '0', '2', '0', '2018-08-15 10:25:53');
INSERT INTO `course` VALUES ('4', '民族舞', '阿萨德的二', '2018-08-15 10:00:00', '2018-08-15 12:00:00', '120', '10', '0', '2', '0', '2018-08-16 12:22:39');

-- ----------------------------
-- Table structure for `course_record`
-- ----------------------------
DROP TABLE IF EXISTS `course_record`;
CREATE TABLE `course_record` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `studentId` int(11) NOT NULL COMMENT '学员ID',
  `courseId` int(11) NOT NULL COMMENT '课程ID',
  `status` tinyint(1) DEFAULT '0' COMMENT '状态',
  `createtime` datetime NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='学员选课记录';

-- ----------------------------
-- Records of course_record
-- ----------------------------
INSERT INTO `course_record` VALUES ('1', '1', '1', '1', '2018-08-15 18:02:44');
INSERT INTO `course_record` VALUES ('2', '1', '1', '0', '2018-08-15 18:08:32');

-- ----------------------------
-- Table structure for `student`
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `loginname` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '登录名',
  `password` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `username` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `gender` tinyint(1) NOT NULL,
  `mobile` char(11) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '手机号码',
  `address` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '住址',
  `remainnum` int(11) DEFAULT '0' COMMENT '卡剩余次数',
  `status` tinyint(1) DEFAULT '0' COMMENT '状态',
  `createtime` datetime NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='学员信息表';

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('1', 'linxiao', '123456q', 'qwer', '0', '123456', '', '9', '0', '2018-08-13 10:03:58');
INSERT INTO `student` VALUES ('2', 'bbbbb', '123456', 'qwer', '0', '123456', null, '0', '0', '2018-08-13 18:10:45');
INSERT INTO `student` VALUES ('3', 'xifa', '123456', '周伦', '0', '1234568965', '北京市', '5', '0', '2018-08-16 10:12:23');

-- ----------------------------
-- Table structure for `teacher`
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `loginname` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '登录名',
  `password` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `username` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `gender` tinyint(1) NOT NULL,
  `mobile` char(11) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '手机号码',
  `address` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '住址',
  `remark` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT '老师简介',
  `status` tinyint(1) DEFAULT '0' COMMENT '状态',
  `createtime` datetime NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='教师信息表';

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES ('1', 'asdfg', '123456', 'qwer', '0', '123456', null, '多台能', '0', '2018-08-14 16:28:51');
INSERT INTO `teacher` VALUES ('2', 'xifa', '123456', '李老师', '0', '1234568965', '北京市', '身材好', '0', '2018-08-16 10:56:52');
