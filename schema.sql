/*
 Navicat MySQL Data Transfer

 Source Server         : rm-bp14t37l6w6xxcb40go.mysql.rds.aliyuncs.com
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : rm-bp14t37l6w6xxcb40go.mysql.rds.aliyuncs.com:3306
 Source Schema         : saas_online

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 27/01/2019 09:58:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ss_admin_user
-- ----------------------------
DROP TABLE IF EXISTS `ss_admin_user`;
CREATE TABLE `ss_admin_user`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `merchant_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信openid',
  `register_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `modify_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT 1
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ss_bank_card
-- ----------------------------
DROP TABLE IF EXISTS `ss_bank_card`;
CREATE TABLE `ss_bank_card`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `bank_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '银行卡用户名',
  `bank_no` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '银行卡号',
  `bank_branch` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '开户支行',
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '对应用户',
  `bank_province` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '银行卡 省',
  `bank_city` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '银行卡市',
  `bank_area` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '银行卡 区',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `modify_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 104 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户银行卡' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ss_banner
-- ----------------------------
DROP TABLE IF EXISTS `ss_banner`;
CREATE TABLE `ss_banner`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `image_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片路径',
  `href_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '跳转路径',
  `href_type` int(1) NULL DEFAULT NULL COMMENT '跳转类型',
  `theme` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'banner主题',
  `queue_number` int(11) NULL DEFAULT NULL COMMENT '排序号',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '说明',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `available` int(11) NULL DEFAULT NULL,
  `merchant_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商户id',
  `is_show` int(1) NULL DEFAULT NULL COMMENT '是否展示',
  `port_type` int(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ss_class_category
-- ----------------------------
DROP TABLE IF EXISTS `ss_class_category`;
CREATE TABLE `ss_class_category`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程分类名称',
  `is_show` int(1) NOT NULL DEFAULT 0 COMMENT '该字段 是否发布状态 默认0不发布 1发布',
  `merchant_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属商户id',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `modify_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ss_class_info
-- ----------------------------
DROP TABLE IF EXISTS `ss_class_info`;
CREATE TABLE `ss_class_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `class_set_id` int(11) NULL DEFAULT NULL COMMENT '所属套课id',
  `category_id` bigint(11) NULL DEFAULT NULL COMMENT '如果节课程没有所属的套课，则存category_id',
  `merchant_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属商户id',
  `audio_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '音频url',
  `logo_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `is_show` int(1) NULL DEFAULT 1 COMMENT '该字段 是否发布状态 默认0不发布 1发布',
  `listen_count` int(11) NULL DEFAULT NULL COMMENT '收听人数',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `modify_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT 0,
  `content_text` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '不带格式的介绍',
  `style` int(1) NULL DEFAULT NULL,
  `is_recommend` int(1) NULL DEFAULT NULL,
  `play_time` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 74 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ss_class_info_comment
-- ----------------------------
DROP TABLE IF EXISTS `ss_class_info_comment`;
CREATE TABLE `ss_class_info_comment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `class_info_id` bigint(20) NULL DEFAULT NULL COMMENT '节课id',
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `comment` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评论',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `modify_time` timestamp(0) NULL DEFAULT NULL,
  `available` int(1) NULL DEFAULT NULL,
  `merchant_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ss_class_info_statistics
-- ----------------------------
DROP TABLE IF EXISTS `ss_class_info_statistics`;
CREATE TABLE `ss_class_info_statistics`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `class_info_id` bigint(20) NULL DEFAULT NULL COMMENT '节课id',
  `listen_count` bigint(20) NULL DEFAULT NULL COMMENT '收听人数',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `modify_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `节课程id`(`class_info_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 59 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ss_class_set
-- ----------------------------
DROP TABLE IF EXISTS `ss_class_set`;
CREATE TABLE `ss_class_set`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '堂标题',
  `banner_url` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '横幅广告图',
  `content` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `category_id` int(11) NULL DEFAULT NULL,
  `merchant_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属商户id',
  `is_show` tinyint(1) NULL DEFAULT 0 COMMENT '该字段 是否发布状态 默认0不发布 1发布',
  `required_member_level` int(2) NULL DEFAULT NULL COMMENT '观看要求的最低会员等级',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `modify_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT 1,
  `content_text` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '不带格式的介绍',
  `style` int(1) NULL DEFAULT NULL,
  `is_recommend` int(1) NULL DEFAULT NULL,
  `front_cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ss_class_set_statistics
-- ----------------------------
DROP TABLE IF EXISTS `ss_class_set_statistics`;
CREATE TABLE `ss_class_set_statistics`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `class_set_id` bigint(20) NULL DEFAULT NULL COMMENT '套课id',
  `browse_count` int(20) NULL DEFAULT NULL COMMENT '粉丝人数',
  `share_count` int(20) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `modify_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `套课程id`(`class_set_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ss_gift
-- ----------------------------
DROP TABLE IF EXISTS `ss_gift`;
CREATE TABLE `ss_gift`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `gift_main_title` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主标题',
  `gift_vice_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '副标题',
  `min_requirement` int(11) NOT NULL COMMENT '购买最低要求',
  `gift_main_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产品主图',
  `gift_vice_img_1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产品附图1',
  `gift_vice_img_2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产品附图2',
  `gift_vice_img_3` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产品附图3',
  `gift_share_back` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '二维码背景图',
  `price` int(11) NULL DEFAULT NULL COMMENT '价格',
  `express_fee` int(11) NULL DEFAULT NULL COMMENT '快递费',
  `stock` int(11) NULL DEFAULT NULL COMMENT '库存',
  `gift_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `is_recommend` int(1) NULL DEFAULT NULL COMMENT '是否推荐，0不推荐，1推荐',
  `is_show` int(1) NULL DEFAULT NULL COMMENT '是否展示，0不展示，1展示',
  `merchant_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商户号',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `modify_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT 1,
  `buy_count` int(11) NULL DEFAULT NULL COMMENT '销量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 101 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ss_icon
-- ----------------------------
DROP TABLE IF EXISTS `ss_icon`;
CREATE TABLE `ss_icon`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '   ',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标名称',
  `picture_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `href_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标跳转地址',
  `available` int(1) NULL DEFAULT NULL COMMENT '是否可用',
  `merchant_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商户号',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `href_type` int(1) NULL DEFAULT NULL,
  `port_type` int(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ss_information
-- ----------------------------
DROP TABLE IF EXISTS `ss_information`;
CREATE TABLE `ss_information`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '资讯连接',
  `merchant_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属商户id',
  `url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '外部链接',
  `is_show` int(1) NULL DEFAULT 0 COMMENT '是否发布状态 默认0不发布 1发布',
  `title` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `logo_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `audio_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '音频地址',
  `content` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `create_time` datetime(0) NOT NULL,
  `modify_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT 1,
  `content_text` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '不带格式的介绍',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 105 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ss_information_category
-- ----------------------------
DROP TABLE IF EXISTS `ss_information_category`;
CREATE TABLE `ss_information_category`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `is_show` int(1) NULL DEFAULT 0 COMMENT '该字段 是否发布状态 默认0不发布 1发布',
  `merchant_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `modify_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ss_maid_info
-- ----------------------------
DROP TABLE IF EXISTS `ss_maid_info`;
CREATE TABLE `ss_maid_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '获得分佣的userid',
  `maid_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产生分佣的userid',
  `maid_money` int(11) NOT NULL COMMENT '分佣金额按照分为单位存储',
  `maid_percent` int(11) NOT NULL,
  `order_actual_money` int(11) NOT NULL,
  `merchant_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `product_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `product_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `level` smallint(1) NULL DEFAULT NULL,
  `level_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `modify_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `order_no_index`(`order_no`) USING BTREE COMMENT '订单号唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 4678 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ss_maid_info_by_month
-- ----------------------------
DROP TABLE IF EXISTS `ss_maid_info_by_month`;
CREATE TABLE `ss_maid_info_by_month`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '获得分佣的userid',
  `maid_money` int(11) NOT NULL COMMENT '分佣金额按照分为单位存储',
  `maid_percent` int(11) NOT NULL COMMENT '百分比',
  `total_performance` int(11) NOT NULL COMMENT '总业绩',
  `maid_type` int(1) NULL DEFAULT NULL COMMENT '0团队奖1纵向奖',
  `merchant_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `record_time` date NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `modify_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1006 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ss_merchant
-- ----------------------------
DROP TABLE IF EXISTS `ss_merchant`;
CREATE TABLE `ss_merchant`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `merchant_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商户号',
  `merchant_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `creator` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者id',
  `app_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '小程序appId',
  `app_secret` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '小程序appsecret',
  `wx_pay_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信支付key',
  `gz_app_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公众号appId',
  `gz_app_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公众号key',
  `buy_template_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公众号购买模版',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `modify_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ss_offline_activity
-- ----------------------------
DROP TABLE IF EXISTS `ss_offline_activity`;
CREATE TABLE `ss_offline_activity`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activity_address` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动地点',
  `apply_start_time` datetime(0) NULL DEFAULT NULL COMMENT '报名开始时间',
  `apply_end_time` datetime(0) NULL DEFAULT NULL COMMENT '报名结束时间',
  `activity_start_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `activity_end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `activity_price` int(11) NULL DEFAULT NULL COMMENT '价格',
  `is_retraining` int(11) NULL DEFAULT NULL COMMENT '是否复训 0不是1是',
  `retraining_price` int(11) NULL DEFAULT NULL COMMENT '复训价格',
  `activity_theme_id` int(11) NULL DEFAULT NULL COMMENT '活动主题id',
  `limit_count` int(11) NULL DEFAULT NULL COMMENT '容纳人数',
  `buy_count` int(11) NULL DEFAULT NULL COMMENT '购买人数',
  `is_recommend` int(1) NULL DEFAULT NULL COMMENT '是否推荐，0不推荐，1推荐',
  `is_show` int(1) NULL DEFAULT NULL COMMENT '是否展示，0不展示，1展示',
  `is_rebuy` int(1) NULL DEFAULT NULL COMMENT '是否可用重复购买,0不可用,1可以',
  `is_maid` int(1) NULL DEFAULT NULL COMMENT '是否返佣，0不返佣，1返佣',
  `min_requirement` int(2) NULL DEFAULT NULL COMMENT '购买最低要求',
  `merchant_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商户号',
  `create_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `modify_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT NULL,
  `activity_type` int(1) NULL DEFAULT NULL COMMENT '活动类型 0线下活动 1沙龙',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ss_offline_activity_code
-- ----------------------------
DROP TABLE IF EXISTS `ss_offline_activity_code`;
CREATE TABLE `ss_offline_activity_code`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `activity_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_used` int(1) NULL DEFAULT NULL COMMENT '0未使用1已使用',
  `activity_theme_id` int(11) NULL DEFAULT NULL COMMENT '主题id',
  `activity_id` int(11) NULL DEFAULT NULL COMMENT '活动id',
  `merchant_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `modify_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT NULL,
  `qr_code_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '二维码地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 83 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ss_offline_activity_operator
-- ----------------------------
DROP TABLE IF EXISTS `ss_offline_activity_operator`;
CREATE TABLE `ss_offline_activity_operator`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `operator` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作员手机',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `activity_theme_id` int(11) NULL DEFAULT NULL COMMENT '活动主题id',
  `merchant_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `modify_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ss_offline_activity_order
-- ----------------------------
DROP TABLE IF EXISTS `ss_offline_activity_order`;
CREATE TABLE `ss_offline_activity_order`  (
  `order_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `activity_id` int(11) NULL DEFAULT NULL COMMENT '线下活动id',
  `activity_theme_id` int(11) NULL DEFAULT NULL COMMENT '主题id',
  `activity_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动消费码',
  `activity_price` int(11) NULL DEFAULT NULL COMMENT '活动费用',
  `actual_money` int(11) NULL DEFAULT NULL COMMENT '实际支付金额',
  `order_status` int(1) NULL DEFAULT NULL COMMENT '0待支付 1支付成功 -1超时',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` int(1) NULL DEFAULT NULL COMMENT '0男 1女',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `real_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `city` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所在城市',
  `id_card_num` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证号码',
  `payment_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付流水号',
  `payment_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '支付时间',
  `merchant_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商户号',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `modify_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT NULL,
  `is_maid` int(1) NULL DEFAULT NULL COMMENT '是否返佣，0不返佣，1返佣',
  `is_retraining` int(1) NULL DEFAULT NULL COMMENT '是否复训 0不是1是',
  `sl_referrer` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动类型 0线下活动 1沙龙',
  `order_type` int(3) NULL DEFAULT NULL COMMENT '活动类型 0线下活动 1沙龙',
  `wechat_no` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信号',
  `activity_date` datetime(0) NULL DEFAULT NULL COMMENT '活动时间',
  `province` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '省',
  PRIMARY KEY (`order_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ss_offline_activity_theme
-- ----------------------------
DROP TABLE IF EXISTS `ss_offline_activity_theme`;
CREATE TABLE `ss_offline_activity_theme`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `theme_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主题名称',
  `theme_href_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片地址或者视频地址',
  `theme_type` int(255) NULL DEFAULT NULL COMMENT '类型 0图片 1视频',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `content_text` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `buy_count` int(11) NULL DEFAULT NULL COMMENT '收听人数',
  `is_recommend` int(1) NULL DEFAULT NULL COMMENT '是否推荐，0不推荐，1推荐',
  `is_show` int(1) NULL DEFAULT NULL COMMENT '是否展示，0不展示，1展示',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片地址',
  `activity_time` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '时长 3天2夜',
  `create_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `modify_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT NULL,
  `merchant_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `activity_type` int(1) NULL DEFAULT NULL COMMENT '活动类型 0线下活动 1沙龙',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ss_operate_user_record
-- ----------------------------
DROP TABLE IF EXISTS `ss_operate_user_record`;
CREATE TABLE `ss_operate_user_record`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `operator` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人员',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `merchant_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户所属商户id',
  `before_level` smallint(1) NULL DEFAULT NULL COMMENT '修改之前的会员等级',
  `after_level` smallint(1) NULL DEFAULT NULL COMMENT '修改之后的会员等级',
  `create_time` datetime(0) NOT NULL,
  `modify_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ss_order
-- ----------------------------
DROP TABLE IF EXISTS `ss_order`;
CREATE TABLE `ss_order`  (
  `order_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `product_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `actual_money` int(11) NOT NULL COMMENT '实际支付金额',
  `money` int(11) NULL DEFAULT NULL COMMENT '订单金额按照分为单位存储',
  `maid_percent` int(11) NOT NULL,
  `pay_way` smallint(1) NULL DEFAULT NULL COMMENT '支付方式：目前只支持微信1',
  `level` int(11) NULL DEFAULT NULL,
  `level_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `validity_time` int(11) NULL DEFAULT NULL COMMENT '有效期，天为单位',
  `order_status` smallint(1) NULL DEFAULT 0 COMMENT '订单状态  0待支付 1成功  -1超时关闭',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `merchant_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `real_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '下单人',
  `payment_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信支付订单号',
  `payment_time` datetime(0) NULL DEFAULT NULL COMMENT '微信到账成功实践',
  `create_time` datetime(0) NOT NULL,
  `modify_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `available` smallint(1) NULL DEFAULT 1,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`order_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ss_order_flow
-- ----------------------------
DROP TABLE IF EXISTS `ss_order_flow`;
CREATE TABLE `ss_order_flow`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `order_status` smallint(1) NULL DEFAULT NULL,
  `actual_money` int(11) NOT NULL,
  `money` int(11) NULL DEFAULT NULL,
  `payment_no` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `payment_time` datetime(0) NULL DEFAULT NULL,
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12255 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ss_product
-- ----------------------------
DROP TABLE IF EXISTS `ss_product`;
CREATE TABLE `ss_product`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `level` int(1) NOT NULL COMMENT '会员等级',
  `level_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '等级名称',
  `product_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品名称',
  `maid_percent` int(3) NOT NULL COMMENT '分佣比例 /100',
  `validity_time` int(10) NOT NULL COMMENT '有效期按照天为单位存储',
  `price` int(11) NOT NULL COMMENT '价格',
  `merchant_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `image_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品图片url',
  `content` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '商品介绍（带格式）',
  `content_text` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品介绍（不带格式）',
  `buy_count` int(11) NULL DEFAULT NULL COMMENT '购买的人数',
  `create_time` datetime(0) NOT NULL,
  `modify_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `available` smallint(1) NOT NULL DEFAULT 1,
  `is_show` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 101 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ss_teacher_user
-- ----------------------------
DROP TABLE IF EXISTS `ss_teacher_user`;
CREATE TABLE `ss_teacher_user`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `merchant_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信openid',
  `register_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `modify_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT 1
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ss_user
-- ----------------------------
DROP TABLE IF EXISTS `ss_user`;
CREATE TABLE `ss_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `wechat_openid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信openid',
  `wechat_union_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信unionID',
  `gzh_openid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公众号openid',
  `merchant_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商户号',
  `register_from` int(1) NULL DEFAULT 1 COMMENT '从什么渠道注册。1、wechat 微信注册 2、线下导入',
  `register_mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '绑定手机号',
  `real_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `logo_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `referrer` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '推荐人user_id ',
  `member_level` smallint(2) NULL DEFAULT 0 COMMENT '会员等级',
  `level_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会员等级名称',
  `is_buy` int(1) NULL DEFAULT NULL COMMENT '是否购买',
  `expires_time` datetime(0) NULL DEFAULT NULL COMMENT '会员到期时间',
  `register_time` datetime(0) NULL DEFAULT NULL COMMENT '注册时间',
  `modify_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `available` int(1) NULL DEFAULT 1,
  `province` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省',
  `city` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '市',
  `gender` int(1) NULL DEFAULT NULL COMMENT '性别 0男 1女',
  `team_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '团队编号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `userid·`(`user_id`) USING BTREE,
  UNIQUE INDEX `微信openid唯一索引`(`wechat_openid`, `merchant_id`) USING BTREE COMMENT '同一个商户号，微信openid唯一'
) ENGINE = InnoDB AUTO_INCREMENT = 11540 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ss_user_account
-- ----------------------------
DROP TABLE IF EXISTS `ss_user_account`;
CREATE TABLE `ss_user_account`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `balance` int(11) NOT NULL COMMENT '可用余额',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `modify_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `available` smallint(1) NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 319 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ss_user_certification
-- ----------------------------
DROP TABLE IF EXISTS `ss_user_certification`;
CREATE TABLE `ss_user_certification`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `status` int(1) NOT NULL DEFAULT 0 COMMENT '0未提交1审核中 2审核通过 3未通过',
  `operator` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人员id',
  `realname` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '真实姓名',
  `id_card` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '身份证',
  `img_1_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '身份证正面照',
  `img_2_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '身份证国徽面',
  `img_3_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手持身份证',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `merchant_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商户所属id',
  `reject_reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '驳回原因',
  `audit_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '审核时间',
  `create_time` datetime(0) NOT NULL,
  `modify_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 496 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ss_user_copy1
-- ----------------------------
DROP TABLE IF EXISTS `ss_user_copy1`;
CREATE TABLE `ss_user_copy1`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `wechat_openid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信openid',
  `wechat_union_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信unionID',
  `merchant_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商户号',
  `register_from` bigint(1) NULL DEFAULT 1 COMMENT '从什么渠道注册。1、wechat 微信注册 2、线下导入',
  `register_mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '绑定手机号',
  `real_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `logo_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `referrer` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '推荐人user_id ',
  `member_level` smallint(2) NULL DEFAULT 0 COMMENT '会员等级',
  `level_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会员等级名称',
  `is_buy` int(1) NULL DEFAULT NULL COMMENT '是否购买',
  `expires_time` datetime(0) NULL DEFAULT NULL COMMENT '会员到期时间',
  `register_time` datetime(0) NULL DEFAULT NULL COMMENT '注册时间',
  `modify_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `available` int(1) NULL DEFAULT 1,
  `form_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `userid·`(`user_id`) USING BTREE,
  UNIQUE INDEX `微信openid唯一索引`(`wechat_openid`, `merchant_id`) USING BTREE COMMENT '同一个商户号，微信openid唯一'
) ENGINE = InnoDB AUTO_INCREMENT = 586 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ss_user_gift
-- ----------------------------
DROP TABLE IF EXISTS `ss_user_gift`;
CREATE TABLE `ss_user_gift`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `gift_id` int(10) NULL DEFAULT NULL COMMENT '兑换的商品编号',
  `gift_title` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '书籍名称',
  `price` int(11) NULL DEFAULT NULL COMMENT '价格单位分',
  `gift_main_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '礼品展示图',
  `phone` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货人手机',
  `province` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省',
  `city` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '市',
  `area` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货人地址',
  `express_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '快递单号',
  `express_company` int(1) NULL DEFAULT NULL COMMENT '快递公司 1韵达 2圆通 3EMS 4申通',
  `merchant_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商户号',
  `order_state` int(3) NULL DEFAULT NULL COMMENT '订单状态 0:待发货 1:已发货',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `modify_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT 1,
  `real_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货人姓名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 463 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ss_user_gift_copy1
-- ----------------------------
DROP TABLE IF EXISTS `ss_user_gift_copy1`;
CREATE TABLE `ss_user_gift_copy1`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `gift_id` int(10) NULL DEFAULT NULL COMMENT '兑换的商品编号',
  `gift_title` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '书籍名称',
  `price` int(11) NULL DEFAULT NULL COMMENT '价格单位分',
  `gift_main_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '礼品展示图',
  `phone` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货人手机',
  `province` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省',
  `city` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '市',
  `area` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货人地址',
  `express_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '快递单号',
  `express_company` int(1) NULL DEFAULT NULL COMMENT '快递公司 1韵达 2圆通 3EMS 4申通',
  `merchant_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商户号',
  `order_state` int(3) NULL DEFAULT NULL COMMENT '订单状态 0:待发货 1:已发货',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `modify_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 208 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ss_user_info
-- ----------------------------
DROP TABLE IF EXISTS `ss_user_info`;
CREATE TABLE `ss_user_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_name` varchar(14) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录用户名、与绑定手机号一致',
  `nick_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户昵称，用于小程序显示',
  `wechat_mobile` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '绑定微信的手机号',
  `referrer` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '推荐人id',
  `logo_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `member_level` int(20) NULL DEFAULT NULL COMMENT '会员等级',
  `is_certification` int(1) NULL DEFAULT NULL COMMENT '是否已实名认证',
  `is_bind_mobile` int(1) NULL DEFAULT NULL COMMENT '是否绑定手机号',
  `register_mobile` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第一次注册手机号，用来登录 （与user_name、binding_mobile一致）',
  `modify_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `register_time` datetime(0) NULL DEFAULT NULL COMMENT '注册时间',
  `available` int(1) NOT NULL COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `用户id唯一`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10836 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ss_user_maid_percent
-- ----------------------------
DROP TABLE IF EXISTS `ss_user_maid_percent`;
CREATE TABLE `ss_user_maid_percent`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `member_level` int(3) NULL DEFAULT NULL COMMENT '等级',
  `maid_percent_1` int(3) NULL DEFAULT NULL COMMENT '学员分佣比例',
  `maid_percent_4` int(3) NULL DEFAULT NULL COMMENT 'VIP学员分佣比例',
  `maid_percent_6` int(3) NULL DEFAULT NULL COMMENT '院长分佣比例',
  `merchant_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NOT NULL,
  `modify_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ss_user_quota_count
-- ----------------------------
DROP TABLE IF EXISTS `ss_user_quota_count`;
CREATE TABLE `ss_user_quota_count`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'userid',
  `count` int(11) NOT NULL COMMENT '剩余可用邀请小班',
  `total_count` int(11) NULL DEFAULT NULL COMMENT '可用邀请小班总数',
  `type` int(11) NOT NULL COMMENT '1为小班',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `modify_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ss_user_receivingaddress
-- ----------------------------
DROP TABLE IF EXISTS `ss_user_receivingaddress`;
CREATE TABLE `ss_user_receivingaddress`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `receiving_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货人',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `province` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省',
  `city` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '市',
  `area` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区',
  `address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
  `is_default` int(1) NOT NULL COMMENT '是否默认地址',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `merchant_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `modify_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 495 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ss_user_wechat
-- ----------------------------
DROP TABLE IF EXISTS `ss_user_wechat`;
CREATE TABLE `ss_user_wechat`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `wechat_account` varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信账号',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关联账号',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `modify_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 267 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户绑定微信账号 \r\n(目前微信账号是手输的    why)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ss_video
-- ----------------------------
DROP TABLE IF EXISTS `ss_video`;
CREATE TABLE `ss_video`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '视频图片地址',
  `video_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '视频地址',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '视频标题',
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '商品介绍（带格式）',
  `content_text` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '商品介绍（不带格式）',
  `play_time` int(11) NULL DEFAULT NULL COMMENT '视频时长',
  `merchant_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商户号',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `available` int(11) NULL DEFAULT NULL COMMENT '是否可用',
  `is_recommend` int(1) NULL DEFAULT NULL COMMENT '是否推荐，0不推荐，1推荐',
  `is_show` int(1) NULL DEFAULT NULL COMMENT '是否展示，0不展示，1展示',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ss_withdraw
-- ----------------------------
DROP TABLE IF EXISTS `ss_withdraw`;
CREATE TABLE `ss_withdraw`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `withdraw_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `money` int(11) NOT NULL COMMENT '提现金额',
  `balance` int(11) NULL DEFAULT NULL COMMENT '提现之后余额',
  `open_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `real_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `account` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收款账户',
  `status` int(1) NOT NULL DEFAULT 0 COMMENT ' 0未提交 1 审核中 2审核通过(前端显示到款中) 3未通过 4成功（到账）',
  `pay_way` int(1) NOT NULL COMMENT '收款方式: 微信1 余额2 银行卡3',
  `operator` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人员',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `success_time` datetime(0) NULL DEFAULT NULL COMMENT '审核通过时间',
  `merchant_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `payment_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '提现微信订单号',
  `payment_time` datetime(0) NULL DEFAULT NULL COMMENT '到账时间',
  `bank_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '银行卡姓名',
  `bank_province` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '银行卡 省',
  `bank_city` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '银行卡  市',
  `bank_area` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '银行卡区',
  `bank_branch` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '银行卡开户支行',
  `create_time` datetime(0) NOT NULL,
  `modify_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 482 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ss_withdraw_flow
-- ----------------------------
DROP TABLE IF EXISTS `ss_withdraw_flow`;
CREATE TABLE `ss_withdraw_flow`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `withdraw_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `success_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `payment_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `payment_no` int(11) NULL DEFAULT NULL,
  `status` int(1) NULL DEFAULT NULL,
  `operator` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `merchant_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `available` smallint(1) NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 454 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ss_work_order
-- ----------------------------
DROP TABLE IF EXISTS `ss_work_order`;
CREATE TABLE `ss_work_order`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `referrer_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '推荐人userId',
  `referrer_level` int(11) NULL DEFAULT NULL COMMENT '推荐人等级',
  `maid_money` int(11) NULL DEFAULT NULL COMMENT '需要修复返佣金额',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '购买人userId',
  `order_money` int(11) NULL DEFAULT NULL COMMENT '订单支付金额',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '购买人手机',
  `order_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '购买订单号',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NOT NULL,
  `modify_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `available` int(1) NULL DEFAULT 1,
  `status` int(1) NULL DEFAULT NULL COMMENT '0待处理 1处理完成 ',
  `referrer_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '推荐人手机号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户操作',
  `time` int(11) NULL DEFAULT NULL COMMENT '响应时间',
  `method` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方法',
  `params` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '请求参数',
  `ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5454 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_macro
-- ----------------------------
DROP TABLE IF EXISTS `sys_macro`;
CREATE TABLE `sys_macro`  (
  `macro_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type_id` bigint(255) NULL DEFAULT NULL COMMENT '父级id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `value` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '值',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态，0：隐藏   1：显示',
  `type` tinyint(20) NULL DEFAULT NULL COMMENT '类型,0:目录，1:参数配置',
  `order_num` int(11) NULL DEFAULT NULL COMMENT '排序',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`macro_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '通用字典表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) NULL DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) NULL DEFAULT NULL COMMENT '排序',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 193 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `role_sign` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色标识',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `user_id_create` bigint(255) NULL DEFAULT NULL COMMENT '创建用户id',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 63 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NULL DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4635 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色与菜单对应关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `status` tinyint(255) NULL DEFAULT NULL COMMENT '状态 0:禁用，1:正常',
  `user_id_create` bigint(255) NULL DEFAULT NULL COMMENT '创建用户id',
  `gmt_create` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `merchant_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商户号',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 130 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 112 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户与角色对应关系' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
