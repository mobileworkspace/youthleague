/*
Navicat MySQL Data Transfer

Source Server         : conn1
Source Server Version : 50512
Source Host           : localhost:3306
Source Database       : youthleague

Target Server Type    : MYSQL
Target Server Version : 50512
File Encoding         : 65001

Date: 2013-06-19 16:02:18
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `department`
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 NOT NULL,
  `note` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES ('19', '祖庙街道团委', '');
INSERT INTO `department` VALUES ('20', '南庄镇团委', '');
INSERT INTO `department` VALUES ('21', '张槎街道团委', '');
INSERT INTO `department` VALUES ('22', '石湾镇街道团委', '');
INSERT INTO `department` VALUES ('23', '南海区书记室', '');
INSERT INTO `department` VALUES ('24', '团市委书记室', '');
INSERT INTO `department` VALUES ('25', '团市委办公室', '');

-- ----------------------------
-- Table structure for `organization`
-- ----------------------------
DROP TABLE IF EXISTS `organization`;
CREATE TABLE `organization` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 NOT NULL,
  `address` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `super_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of organization
-- ----------------------------
INSERT INTO `organization` VALUES ('-1', '佛山市团委', '佛山市市委市政府', '-100');
INSERT INTO `organization` VALUES ('18', '团市委', '', '-1');
INSERT INTO `organization` VALUES ('19', '团禅城区委', '', '18');
INSERT INTO `organization` VALUES ('20', '团南海区委', '', '18');
INSERT INTO `organization` VALUES ('22', '团市委书记室', '', '18');
INSERT INTO `organization` VALUES ('23', '团市委办公室', '', '18');
INSERT INTO `organization` VALUES ('24', '团高明区委', '', '18');
INSERT INTO `organization` VALUES ('25', '团顺德区委', '', '18');
INSERT INTO `organization` VALUES ('26', '团三水区委', '', '18');
INSERT INTO `organization` VALUES ('27', '市直机关团工委', '', '18');
INSERT INTO `organization` VALUES ('28', '市直学校团工委', '', '18');
INSERT INTO `organization` VALUES ('29', '市直企业团工委', '', '18');

-- ----------------------------
-- Table structure for `position_`
-- ----------------------------
DROP TABLE IF EXISTS `position_`;
CREATE TABLE `position_` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 NOT NULL,
  `note` varchar(255) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of position_
-- ----------------------------
INSERT INTO `position_` VALUES ('8', '书  记', '');
INSERT INTO `position_` VALUES ('9', '副书记', '');
INSERT INTO `position_` VALUES ('10', '兼职副书记', '');
INSERT INTO `position_` VALUES ('11', '挂职副书记', '');
INSERT INTO `position_` VALUES ('12', '副主任科员    （主持工作）', '');

-- ----------------------------
-- Table structure for `staff`
-- ----------------------------
DROP TABLE IF EXISTS `staff`;
CREATE TABLE `staff` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mobile` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `organization_id` int(11) NOT NULL,
  `department_id` int(11) NOT NULL,
  `position_id` int(11) NOT NULL,
  `is_administrator` int(11) NOT NULL DEFAULT '0',
  `is_leader` int(11) NOT NULL DEFAULT '0',
  `is_hipe` int(11) NOT NULL DEFAULT '0',
  `is_departure` int(11) NOT NULL DEFAULT '0',
  `is_warrant` int(11) NOT NULL DEFAULT '0',
  `password_` varchar(255) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`id`),
  KEY `organization_id` (`organization_id`),
  CONSTRAINT `organization_id` FOREIGN KEY (`organization_id`) REFERENCES `organization` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of staff
-- ----------------------------
INSERT INTO `staff` VALUES ('1', 'admin', 'admin', '-1', '2', '2', '1', '0', '0', '0', '1', '123456', 'admin');
INSERT INTO `staff` VALUES ('64', '13566666666', '075743212345', '20', '23', '8', '0', '0', '0', '0', '1', '123456', '张应统');
INSERT INTO `staff` VALUES ('65', '13577777777', '', '20', '23', '9', '0', '0', '0', '0', '1', '123456', '王晓娟');
INSERT INTO `staff` VALUES ('66', '13929998844', '', '19', '19', '8', '0', '0', '0', '0', '1', '123456', '谢  恩');
INSERT INTO `staff` VALUES ('67', '13827719958', '', '19', '20', '8', '0', '0', '0', '0', '1', '123456', '黄  超');
INSERT INTO `staff` VALUES ('68', '13924800002', '', '19', '21', '8', '0', '0', '0', '0', '1', '123456', '肖映泽');
INSERT INTO `staff` VALUES ('69', '13925431695', '', '19', '22', '8', '0', '0', '0', '0', '1', '123456', '屠  强');
INSERT INTO `staff` VALUES ('70', '13823469666', '075723654123', '22', '24', '8', '0', '0', '0', '0', '1', '123456', '曹洪彬');
INSERT INTO `staff` VALUES ('71', '18675708166', '', '23', '25', '12', '0', '0', '0', '0', '1', '123456', '刘  峰');
INSERT INTO `staff` VALUES ('72', '13450157612', '', '25', '19', '8', '0', '0', '0', '0', '1', '12345', '曹青春');

-- ----------------------------
-- Table structure for `update_database`
-- ----------------------------
DROP TABLE IF EXISTS `update_database`;
CREATE TABLE `update_database` (
  `last_date` varchar(100) CHARACTER SET utf8 NOT NULL DEFAULT '197001010001'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of update_database
-- ----------------------------
INSERT INTO `update_database` VALUES ('201306152046');
