/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.5.49 : Database - blog
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`blog` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `blog`;

/*Table structure for table `blog` */

DROP TABLE IF EXISTS `blog`;

CREATE TABLE `blog` (
  `blogId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '博客表主键id',
  `blogTitle` varchar(200) CHARACTER SET utf8 NOT NULL COMMENT '博客标题',
  `blogSubUrl` varchar(200) CHARACTER SET utf8 NOT NULL COMMENT '博客自定义路径url',
  `blogCoverImage` varchar(200) CHARACTER SET utf8 NOT NULL COMMENT '博客封面图',
  `blogContent` mediumtext CHARACTER SET utf8 NOT NULL COMMENT '博客内容',
  `blogCategoryId` int(11) NOT NULL COMMENT '博客分类id',
  `blogCategoryName` varchar(50) CHARACTER SET utf8 NOT NULL COMMENT '博客分类(冗余字段)',
  `blogTags` varchar(200) CHARACTER SET utf8 NOT NULL COMMENT '博客标签',
  `blogStatus` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0-草稿 1-发布',
  `blogViews` bigint(20) NOT NULL DEFAULT '0' COMMENT '阅读量',
  `enableComment` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0-允许评论 1-不允许评论',
  `createTime` datetime DEFAULT NULL COMMENT '添加时间',
  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `description` varchar(200) CHARACTER SET utf8 NOT NULL COMMENT '文章描述',
  PRIMARY KEY (`blogId`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;

/*Table structure for table `category` */

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `categoryId` int(11) NOT NULL AUTO_INCREMENT COMMENT '分类表主键',
  `categoryName` varchar(50) NOT NULL COMMENT '分类的名称',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`categoryId`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

/*Table structure for table `comment` */

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `commentId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `blogId` bigint(20) NOT NULL DEFAULT '0' COMMENT '关联的blog主键',
  `nick` varchar(50) NOT NULL DEFAULT '' COMMENT '昵称',
  `email` varchar(100) NOT NULL DEFAULT '' COMMENT '邮箱',
  `link` varchar(50) NOT NULL DEFAULT '' COMMENT '网址',
  `content` varchar(200) NOT NULL DEFAULT '' COMMENT '内容',
  `commentatorIP` varchar(20) NOT NULL DEFAULT '' COMMENT 'ip地址',
  `createTime` datetime DEFAULT NULL COMMENT '评论时间',
  `isReply` int(4) DEFAULT '0' COMMENT '是否是回复 0为否 1为是',
  `commentStatus` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否审核通过 0-未审核 1-审核通过',
  `commentUrl` varchar(100) NOT NULL COMMENT '评论的地址',
  PRIMARY KEY (`commentId`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;

/*Table structure for table `link` */

DROP TABLE IF EXISTS `link`;

CREATE TABLE `link` (
  `linkId` int(11) NOT NULL AUTO_INCREMENT COMMENT '友链表主键id',
  `avatar` varbinary(100) NOT NULL DEFAULT 'https://api.btstu.cn/sjtx/api.php?lx=c1&format=images' COMMENT '头像',
  `linkName` varchar(50) NOT NULL COMMENT '网站名称',
  `linkUrl` varchar(100) NOT NULL COMMENT '网站链接',
  `linkDescription` varchar(100) NOT NULL COMMENT '网站描述',
  `createTime` datetime DEFAULT NULL COMMENT '添加时间',
  `linkRank` int(10) NOT NULL DEFAULT '0' COMMENT '排序',
  PRIMARY KEY (`linkId`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

/*Table structure for table `tag` */

DROP TABLE IF EXISTS `tag`;

CREATE TABLE `tag` (
  `tagId` int(11) NOT NULL AUTO_INCREMENT COMMENT '标签表主键id',
  `tagName` varchar(100) NOT NULL COMMENT '标签名称',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`tagId`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(4) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `author` varchar(10) CHARACTER SET utf8 NOT NULL COMMENT '作者',
  `name` varchar(10) CHARACTER SET utf8 NOT NULL COMMENT '用户名',
  `password` varchar(50) CHARACTER SET utf8 NOT NULL COMMENT '密码',
  `description` varchar(300) CHARACTER SET utf8 NOT NULL COMMENT '描述',
  `title` varchar(20) CHARACTER SET utf8 NOT NULL COMMENT '标题',
  `background` varbinary(100) NOT NULL COMMENT '背景颜色',
  `notice` varchar(999) CHARACTER SET utf8 DEFAULT NULL COMMENT '公告',
  `subtitle` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '副标题',
  `running_time` datetime NOT NULL COMMENT '已运行时间',
  `favicon` varchar(500) CHARACTER SET utf8 NOT NULL COMMENT '博客图标',
  `avatar` varchar(500) CHARACTER SET utf8 NOT NULL COMMENT '头像',
  `email` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '邮箱',
  `icp` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT 'ICP备案',
  `about` mediumtext CHARACTER SET utf8 NOT NULL COMMENT '关于我',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
