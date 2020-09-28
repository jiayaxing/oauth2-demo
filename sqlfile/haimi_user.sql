/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : haimi_user

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2020-09-28 15:31:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(255) NOT NULL COMMENT '客户端标\r\n识',
  `resource_ids` varchar(255) DEFAULT NULL COMMENT '接入资源列表',
  `client_secret` varchar(255) DEFAULT NULL COMMENT '客户端秘钥',
  `scope` varchar(255) DEFAULT NULL,
  `authorized_grant_types` varchar(255) DEFAULT NULL,
  `web_server_redirect_uri` varchar(255) DEFAULT NULL,
  `authorities` varchar(255) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` longtext,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `archived` tinyint(4) DEFAULT NULL,
  `trusted` tinyint(4) DEFAULT NULL,
  `autoapprove` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='接入客户端信息';

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES ('c1', 'res1,res2', '$2a$10$xpd/MW3DMZh93kYtWrhzBOssmN0/oCZ2FowHBJBefD4xUfej9oq1W', 'all,read', 'client_credentials,password,authorization_code,implicit,refresh_token', 'http://127.0.0.1:8081/login', null, '7200', '259200', null, '2020-09-25 17:57:05', '0', '0', 'false');
INSERT INTO `oauth_client_details` VALUES ('c2', 'res2', '$2a$10$xpd/MW3DMZh93kYtWrhzBOssmN0/oCZ2FowHBJBefD4xUfej9oq1W', 'all', 'client_credentials,password,authorization_code,implicit,refresh_token', 'http://127.0.0.1:8082/login', null, '31536000', '2592000', null, '2020-09-25 15:49:48', '0', '0', 'false');

-- ----------------------------
-- Table structure for oauth_code
-- ----------------------------
DROP TABLE IF EXISTS `oauth_code`;
CREATE TABLE `oauth_code` (
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `code` varchar(255) DEFAULT NULL,
  `authentication` blob,
  KEY `code_index` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of oauth_code
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL COMMENT '用户名:rjsc用户是用其他软件平台上传的数据',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `role` varchar(255) DEFAULT NULL COMMENT '角色',
  `encode_password` varchar(255) DEFAULT NULL,
  `uuid` varchar(50) DEFAULT NULL,
  `delete_flag` int(1) DEFAULT '0' COMMENT '1是该用户删除，0是该用户未删除',
  `group_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('9', 'admin', 'admin', 'ROLE_admin', '$2a$10$fiMF3lMQ5ulwaTCEf0wO.eeMlyeqADz.v0th.t6Z8HevGYN/wrwnK', 'fbe5d78b2b404d1d94f39275e0bbf4b5', '1', '管理员组');
INSERT INTO `user` VALUES ('11', 'user', '123456', 'ROLE_user', '$2a$10$tRN4kFjviEC/aNJ8l9VXx.g3jO7XgdHVK6AJ1JDGJGAINfvDCuTve', 'b3b03e488da14644a8d134075349166c', '0', '测试员组');
