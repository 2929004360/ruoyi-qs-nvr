/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50744
 Source Host           : 127.0.0.1:3306
 Source Schema         : ry-cloud

 Target Server Type    : MySQL
 Target Server Version : 50744
 File Encoding         : 65001

 Date: 16/05/2026 00:14:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
DROP TABLE IF EXISTS `gen_table`;
CREATE TABLE `gen_table`  (
  `table_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '表名称',
  `table_comment` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '表描述',
  `sub_table_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '关联子表的表名',
  `sub_table_fk_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '子表关联的外键名',
  `class_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '实体类名称',
  `tpl_category` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作）',
  `tpl_web_type` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '前端模板类型（element-ui模版 element-plus模版）',
  `package_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '生成模块名',
  `business_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '生成业务名',
  `function_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '生成功能名',
  `function_author` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '生成功能作者',
  `gen_type` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '0' COMMENT '生成代码方式（0zip压缩包 1自定义路径）',
  `gen_path` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '/' COMMENT '生成路径（不填默认项目路径）',
  `options` varchar(1000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '其它生成选项',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`table_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '代码生成业务表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of gen_table
-- ----------------------------
INSERT INTO `gen_table` VALUES (4, 'zlm_cloud_record', '云端录像表', NULL, NULL, 'ZlmCloudRecord', 'crud', 'element-plus-typescript', 'com.ruoyi.zlm', 'zlm', 'cloudRecord', '云端录像', 'fengcheng', '0', '/', '{}', 'admin', '2026-04-10 20:41:51', '', '2026-04-10 20:42:50', NULL);
INSERT INTO `gen_table` VALUES (5, 'zlm_record_plan', '录像计划表', NULL, NULL, 'ZlmRecordPlan', 'crud', 'element-plus-typescript', 'com.ruoyi.zlm', 'zlm', 'recordPlan', '录像计划', 'fengcheng', '0', '/', '{}', 'admin', '2026-04-11 22:24:58', '', '2026-04-11 22:26:26', NULL);
INSERT INTO `gen_table` VALUES (6, 'zlm_record_plan_item', '录像计划管理通道表', NULL, NULL, 'ZlmRecordPlanItem', 'crud', 'element-plus-typescript', 'com.ruoyi.zlm', 'zlm', 'recordPlanItem', '录像计划管理通道', 'fengcheng', '0', '/', '{}', 'admin', '2026-04-11 22:24:58', '', '2026-04-11 22:25:44', NULL);

-- ----------------------------
-- Table structure for gen_table_column
-- ----------------------------
DROP TABLE IF EXISTS `gen_table_column`;
CREATE TABLE `gen_table_column`  (
  `column_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` bigint(20) NULL DEFAULT NULL COMMENT '归属表编号',
  `column_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '列名称',
  `column_comment` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '列描述',
  `column_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '列类型',
  `java_type` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否主键（1是）',
  `is_increment` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否自增（1是）',
  `is_required` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否必填（1是）',
  `is_insert` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否为插入字段（1是）',
  `is_edit` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否编辑字段（1是）',
  `is_list` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否列表字段（1是）',
  `is_query` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否查询字段（1是）',
  `query_type` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  `dict_type` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '字典类型',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`column_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 104 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '代码生成业务表字段' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of gen_table_column
-- ----------------------------
INSERT INTO `gen_table_column` VALUES (77, 4, 'id', '主键', 'bigint(20) unsigned', 'Long', 'id', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, 'admin', '2026-04-10 20:41:51', '', '2026-04-10 20:42:51');
INSERT INTO `gen_table_column` VALUES (78, 4, 'app', '应用名', 'varchar(255)', 'String', 'app', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 2, 'admin', '2026-04-10 20:41:51', '', '2026-04-10 20:42:51');
INSERT INTO `gen_table_column` VALUES (79, 4, 'stream', '流id', 'varchar(255)', 'String', 'stream', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 3, 'admin', '2026-04-10 20:41:51', '', '2026-04-10 20:42:51');
INSERT INTO `gen_table_column` VALUES (80, 4, 'call_id', '健全ID', 'varchar(255)', 'String', 'callId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 4, 'admin', '2026-04-10 20:41:51', '', '2026-04-10 20:42:51');
INSERT INTO `gen_table_column` VALUES (81, 4, 'start_time', '开始时间', 'bigint(20)', 'Long', 'startTime', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 5, 'admin', '2026-04-10 20:41:51', '', '2026-04-10 20:42:51');
INSERT INTO `gen_table_column` VALUES (82, 4, 'end_time', '结束时间', 'bigint(20)', 'Long', 'endTime', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 6, 'admin', '2026-04-10 20:41:51', '', '2026-04-10 20:42:51');
INSERT INTO `gen_table_column` VALUES (83, 4, 'media_server_id', 'ZLM Id', 'varchar(50)', 'String', 'mediaServerId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 7, 'admin', '2026-04-10 20:41:51', '', '2026-04-10 20:42:51');
INSERT INTO `gen_table_column` VALUES (84, 4, 'server_id', '所属服务ID', 'varchar(50)', 'String', 'serverId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 8, 'admin', '2026-04-10 20:41:51', '', '2026-04-10 20:42:51');
INSERT INTO `gen_table_column` VALUES (85, 4, 'file_name', '文件名称', 'varchar(255)', 'String', 'fileName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 9, 'admin', '2026-04-10 20:41:51', '', '2026-04-10 20:42:51');
INSERT INTO `gen_table_column` VALUES (86, 4, 'folder', '文件夹', 'varchar(500)', 'String', 'folder', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'textarea', '', 10, 'admin', '2026-04-10 20:41:51', '', '2026-04-10 20:42:51');
INSERT INTO `gen_table_column` VALUES (87, 4, 'file_path', '文件路径', 'varchar(500)', 'String', 'filePath', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'textarea', '', 11, 'admin', '2026-04-10 20:41:51', '', '2026-04-10 20:42:51');
INSERT INTO `gen_table_column` VALUES (88, 4, 'collect', '收藏，收藏的文件不移除', 'tinyint(1)', 'Integer', 'collect', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 12, 'admin', '2026-04-10 20:41:51', '', '2026-04-10 20:42:51');
INSERT INTO `gen_table_column` VALUES (89, 4, 'file_size', '文件大小', 'bigint(20)', 'Long', 'fileSize', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 13, 'admin', '2026-04-10 20:41:51', '', '2026-04-10 20:42:51');
INSERT INTO `gen_table_column` VALUES (90, 4, 'time_len', '文件时长', 'double', 'Long', 'timeLen', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 14, 'admin', '2026-04-10 20:41:51', '', '2026-04-10 20:42:51');
INSERT INTO `gen_table_column` VALUES (91, 4, 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'datetime', '', 15, 'admin', '2026-04-10 20:41:51', '', '2026-04-10 20:42:51');
INSERT INTO `gen_table_column` VALUES (92, 4, 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'datetime', '', 16, 'admin', '2026-04-10 20:41:51', '', '2026-04-10 20:42:51');
INSERT INTO `gen_table_column` VALUES (93, 5, 'id', 'ID', 'bigint(20) unsigned', 'Long', 'id', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, 'admin', '2026-04-11 22:24:58', '', '2026-04-11 22:26:26');
INSERT INTO `gen_table_column` VALUES (94, 5, 'snap', '计划名称', 'tinyint(1)', 'Integer', 'snap', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 2, 'admin', '2026-04-11 22:24:58', '', '2026-04-11 22:26:26');
INSERT INTO `gen_table_column` VALUES (95, 5, 'name', '是否开启定时截图', 'varchar(255)', 'String', 'name', '0', '0', '0', '1', '1', '1', '0', 'EQ', 'input', '', 3, 'admin', '2026-04-11 22:24:58', '', '2026-04-11 22:26:26');
INSERT INTO `gen_table_column` VALUES (96, 5, 'status', '是否启用', 'tinyint(1)', 'Integer', 'status', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'radio', '', 4, 'admin', '2026-04-11 22:24:58', '', '2026-04-11 22:26:26');
INSERT INTO `gen_table_column` VALUES (97, 5, 'create_time', '创建时间', 'varchar(50)', 'String', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 5, 'admin', '2026-04-11 22:24:58', '', '2026-04-11 22:26:26');
INSERT INTO `gen_table_column` VALUES (98, 5, 'update_time', '更新时间', 'varchar(50)', 'String', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'input', '', 6, 'admin', '2026-04-11 22:24:58', '', '2026-04-11 22:26:26');
INSERT INTO `gen_table_column` VALUES (99, 6, 'id', NULL, 'bigint(20) unsigned', 'Long', 'id', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, 'admin', '2026-04-11 22:24:58', '', '2026-04-11 22:25:44');
INSERT INTO `gen_table_column` VALUES (100, 6, 'start', NULL, 'int(11)', 'Long', 'start', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 2, 'admin', '2026-04-11 22:24:58', '', '2026-04-11 22:25:44');
INSERT INTO `gen_table_column` VALUES (101, 6, 'stop', NULL, 'int(11)', 'Long', 'stop', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 3, 'admin', '2026-04-11 22:24:58', '', '2026-04-11 22:25:44');
INSERT INTO `gen_table_column` VALUES (102, 6, 'week_day', NULL, 'int(11)', 'Long', 'weekDay', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 4, 'admin', '2026-04-11 22:24:58', '', '2026-04-11 22:25:44');
INSERT INTO `gen_table_column` VALUES (103, 6, 'plan_id', NULL, 'int(11)', 'Long', 'planId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 5, 'admin', '2026-04-11 22:24:58', '', '2026-04-11 22:25:44');

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `blob_data` blob NULL COMMENT '存放持久化Trigger对象',
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'Blob类型的触发器表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qrtz_blob_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars`  (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '调度名称',
  `calendar_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '日历名称',
  `calendar` blob NOT NULL COMMENT '存放持久化calendar对象',
  PRIMARY KEY (`sched_name`, `calendar_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '日历信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qrtz_calendars
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `cron_expression` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'cron表达式',
  `time_zone_id` varchar(80) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '时区',
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'Cron类型的触发器表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '调度名称',
  `entry_id` varchar(95) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '调度器实例id',
  `trigger_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `instance_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '调度器实例名',
  `fired_time` bigint(13) NOT NULL COMMENT '触发的时间',
  `sched_time` bigint(13) NOT NULL COMMENT '定时器制定的时间',
  `priority` int(11) NOT NULL COMMENT '优先级',
  `state` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '状态',
  `job_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '任务名称',
  `job_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '任务组名',
  `is_nonconcurrent` varchar(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否并发',
  `requests_recovery` varchar(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否接受恢复执行',
  PRIMARY KEY (`sched_name`, `entry_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '已触发的触发器表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details`  (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '调度名称',
  `job_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '任务名称',
  `job_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '任务组名',
  `description` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '相关介绍',
  `job_class_name` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '执行任务类名称',
  `is_durable` varchar(1) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '是否持久化',
  `is_nonconcurrent` varchar(1) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '是否并发',
  `is_update_data` varchar(1) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '是否更新数据',
  `requests_recovery` varchar(1) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '是否接受恢复执行',
  `job_data` blob NULL COMMENT '存放持久化job对象',
  PRIMARY KEY (`sched_name`, `job_name`, `job_group`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '任务详细信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks`  (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '调度名称',
  `lock_name` varchar(40) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '悲观锁名称',
  PRIMARY KEY (`sched_name`, `lock_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '存储的悲观锁信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps`  (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '调度名称',
  `trigger_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  PRIMARY KEY (`sched_name`, `trigger_group`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '暂停的触发器表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qrtz_paused_trigger_grps
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state`  (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '调度名称',
  `instance_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '实例名称',
  `last_checkin_time` bigint(13) NOT NULL COMMENT '上次检查时间',
  `checkin_interval` bigint(13) NOT NULL COMMENT '检查间隔时间',
  PRIMARY KEY (`sched_name`, `instance_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '调度器状态表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `repeat_count` bigint(7) NOT NULL COMMENT '重复的次数统计',
  `repeat_interval` bigint(12) NOT NULL COMMENT '重复的间隔时间',
  `times_triggered` bigint(10) NOT NULL COMMENT '已经触发的次数',
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '简单触发器的信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qrtz_simple_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `str_prop_1` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'String类型的trigger的第一个参数',
  `str_prop_2` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'String类型的trigger的第二个参数',
  `str_prop_3` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'String类型的trigger的第三个参数',
  `int_prop_1` int(11) NULL DEFAULT NULL COMMENT 'int类型的trigger的第一个参数',
  `int_prop_2` int(11) NULL DEFAULT NULL COMMENT 'int类型的trigger的第二个参数',
  `long_prop_1` bigint(20) NULL DEFAULT NULL COMMENT 'long类型的trigger的第一个参数',
  `long_prop_2` bigint(20) NULL DEFAULT NULL COMMENT 'long类型的trigger的第二个参数',
  `dec_prop_1` decimal(13, 4) NULL DEFAULT NULL COMMENT 'decimal类型的trigger的第一个参数',
  `dec_prop_2` decimal(13, 4) NULL DEFAULT NULL COMMENT 'decimal类型的trigger的第二个参数',
  `bool_prop_1` varchar(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'Boolean类型的trigger的第一个参数',
  `bool_prop_2` varchar(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'Boolean类型的trigger的第二个参数',
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '同步机制的行锁表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qrtz_simprop_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '触发器的名字',
  `trigger_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '触发器所属组的名字',
  `job_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'qrtz_job_details表job_name的外键',
  `job_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'qrtz_job_details表job_group的外键',
  `description` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '相关介绍',
  `next_fire_time` bigint(13) NULL DEFAULT NULL COMMENT '上一次触发时间（毫秒）',
  `prev_fire_time` bigint(13) NULL DEFAULT NULL COMMENT '下一次触发时间（默认为-1表示不触发）',
  `priority` int(11) NULL DEFAULT NULL COMMENT '优先级',
  `trigger_state` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '触发器状态',
  `trigger_type` varchar(8) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '触发器的类型',
  `start_time` bigint(13) NOT NULL COMMENT '开始时间',
  `end_time` bigint(13) NULL DEFAULT NULL COMMENT '结束时间',
  `calendar_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '日程表名称',
  `misfire_instr` smallint(2) NULL DEFAULT NULL COMMENT '补偿执行的策略',
  `job_data` blob NULL COMMENT '存放持久化job对象',
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  INDEX `sched_name`(`sched_name`, `job_name`, `job_group`) USING BTREE,
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `qrtz_job_details` (`sched_name`, `job_name`, `job_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '触发器详细信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qs_common_group
-- ----------------------------
DROP TABLE IF EXISTS `qs_common_group`;
CREATE TABLE `qs_common_group`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `device_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '区域国标编号',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '区域名称',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父分组ID',
  `parent_device_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '父区域国标ID',
  `business_group` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '所属的业务分组国标编号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `civil_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '行政区划',
  `alias` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '别名， 此别名为唯一值，可以对接第三方是存储对方的ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `id`(`id`) USING BTREE,
  UNIQUE INDEX `uk_common_group_device_platform`(`device_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for qs_common_region
-- ----------------------------
DROP TABLE IF EXISTS `qs_common_region`;
CREATE TABLE `qs_common_region`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `device_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '区域国标编号',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '区域名称',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父区域ID',
  `parent_device_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '父区域国标ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `id`(`id`) USING BTREE,
  UNIQUE INDEX `uk_common_region_device_id`(`device_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for qs_device
-- ----------------------------
DROP TABLE IF EXISTS `qs_device`;
CREATE TABLE `qs_device`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `device_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '设备唯一标识',
  `device_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备名称',
  `ip_address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `port` int(10) NULL DEFAULT NULL COMMENT '端口号',
  `user_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'HIKVISION' COMMENT '直播流接入类型(1=RTSP,2=RTMP,3=FLV,4=HLS,5=ONVIF,6=视频文件,7=海康SDK,8=海康ISUP,9=大华SDK,10=宇视SDK,11=天地伟业SDK,12=国标28181,13=PUSH,14=部标1078)',
  `device_type` int(2) NULL DEFAULT NULL COMMENT '设备类型(0=IPC, 1=NVR)',
  `live_address` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '直播流地址',
  `channel` int(5) NULL DEFAULT NULL COMMENT '通道号',
  `alarm_channel_id` int(10) NULL DEFAULT NULL COMMENT '报警通道号',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'DEACTIVATE' COMMENT '状态(ENABLE/DEACTIVATE)',
  `stream_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '码流类型(1=主码流,2=子码流,3=第三码流)',
  `longitude` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '经度',
  `latitude` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '纬度',
  `gb_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '国标编码',
  `protocol` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'TCP' COMMENT '传输协议(UDP/TCP)',
  `device_status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备状态(OFFLINE=离线,ON=在线)',
  `online_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上线类型(1=主动添加, 2=主动注册)',
  `enable_audio` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '开启音频(0=关闭, 1=开启)',
  `enable_mp4` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '开启mp4录制(0=关闭, 1=开启)',
  `stream_status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '流状态(0=停止,1=直播中)',
  `media_serverId` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '当前拉流使用的流媒体服务ID',
  `stream_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '拉流代理时zlm返回的key，用于停止拉流代理',
  `enable_disable_none_reader` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否 无人观看时自动停用(0=不处理, 1=停用)',
  `snap` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '截图',
  `flv_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'flv 类型（ws/flv）',
  `onvif_auth` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'omvif 验证类型（1=WS-UsernameToken,2=Digest）',
  `onvif_host_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'onvif 主机名',
  `record_plan_id` bigint(20) NULL DEFAULT NULL COMMENT '录制计划id',
  `gb_civil_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '国标-行政区域',
  `gb_business_group_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '国标-义务分组',
  `gb_parent_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '国标-父节点ID',
  `manufacturer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生产厂商',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '安装地址',
  `ptz_type` int(10) NULL DEFAULT NULL COMMENT '摄像机结构类型,标识摄像机类型: 1-球机; 2-半球; 3-固定枪机; 4-遥控枪机;5-遥控半球;6-多目设备的全景/拼接通道;7-多目设备的分割通道',
  `gb_device_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '国标设备id',
  `gb_channel_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '国标通道id',
  `stream_mode` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据流传输模式',
  `jt_mobile_no` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部标手机号',
  `jt_plate_no` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部标车牌号',
  `jt_plate_color` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部标车牌颜色',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `playback_stream_status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '回放流状态(0=停止,1=回放中)',
  `playback_media_server_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '回放使用的流媒体服务ID',
  `playback_stream_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '回放时zlm返回的key，用于停止回放',
  `gb_manufacturer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '国标设备厂商',
  `gb_model` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '国标设备型号',
  `gb_owner` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '国标设备归属',
  `gb_block` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '国标警区',
  `gb_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '国标安装地址',
  `gb_parental` int(11) NULL DEFAULT NULL COMMENT '是否有子设备：1-是，0-否',
  `gb_safety_way` int(11) NULL DEFAULT NULL COMMENT '信令安全模式',
  `gb_register_way` int(11) NULL DEFAULT NULL COMMENT '注册方式',
  `gb_cert_num` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '证书序列号',
  `gb_certifiable` int(11) NULL DEFAULT NULL COMMENT '证书有效标识：1-有效，0-无效',
  `gb_err_code` int(11) NULL DEFAULT NULL COMMENT '无效原因码',
  `gb_end_time` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '证书终止有效期',
  `gb_secrecy` int(11) NULL DEFAULT NULL COMMENT '保密属性：0-不涉密，1-涉密',
  `gb_ip_address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '国标IP地址',
  `gb_port` int(11) NULL DEFAULT NULL COMMENT '国标端口',
  `gb_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '国标密码',
  `gb_status` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '国标设备状态',
  `gb_longitude_double` double NULL DEFAULT NULL COMMENT '国标经度',
  `gb_latitude_double` double NULL DEFAULT NULL COMMENT '国标纬度',
  `gb_position_type` int(11) NULL DEFAULT NULL COMMENT '摄像机位置类型',
  `gb_room_type` int(11) NULL DEFAULT NULL COMMENT '摄像机安装位置属性：1-室外，2-室内',
  `gb_use_type` int(11) NULL DEFAULT NULL COMMENT '摄像机用途属性：1-治安，2-交通，3-重点',
  `gb_supply_light_type` int(11) NULL DEFAULT NULL COMMENT '摄像机补光属性：1-无补光，2-红外补光，3-白光补光，4-激光补光，9-其他',
  `gb_direction_type` int(11) NULL DEFAULT NULL COMMENT '摄像机监视方向属性',
  `gb_resolution` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '摄像机支持的分辨率',
  `gb_download_speed` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '下载倍速',
  `gb_svc_space_support_mod` int(11) NULL DEFAULT NULL COMMENT '空域编码能力',
  `gb_svc_time_support_mode` int(11) NULL DEFAULT NULL COMMENT '时域编码能力',
  `gps_altitude` double NULL DEFAULT NULL COMMENT 'GPS高度',
  `gps_speed` double NULL DEFAULT NULL COMMENT 'GPS速度',
  `gps_direction` double NULL DEFAULT NULL COMMENT 'GPS方向',
  `gps_time` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'GPS时间',
  `enable_broadcast` int(11) NULL DEFAULT NULL COMMENT '是否支持对讲：1-支持，0-不支持',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_device_code`(`device_code`) USING BTREE COMMENT '设备编码唯一',
  INDEX `idx_brand`(`type`) USING BTREE,
  INDEX `idx_status`(`status`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '视频监控设备接入表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for qs_gb28181_platform
-- ----------------------------
DROP TABLE IF EXISTS `qs_gb28181_platform`;
CREATE TABLE `qs_gb28181_platform`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `enable` tinyint(1) NULL DEFAULT 0 COMMENT '是否启用：0-禁用，1-启用',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '平台名称',
  `server_gb_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '平台国标编码（SIP服务器ID）',
  `server_gb_domain` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '平台域（SIP域）',
  `server_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '平台服务器IP地址',
  `server_port` int(11) NULL DEFAULT NULL COMMENT '平台服务器端口',
  `device_gb_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '设备国标编码（本地SIP设备ID）',
  `device_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '设备IP地址',
  `device_port` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '设备端口',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'SIP认证用户名',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'SIP认证密码',
  `expires` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '注册有效期（秒）',
  `keep_timeout` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '心跳超时时间（秒）',
  `transport` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '传输协议：UDP/TCP',
  `civil_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '行政区划编码',
  `manufacturer` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '设备厂商',
  `model` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '设备型号',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '安装地址',
  `character_set` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '字符编码：GB2312/UTF-8',
  `ptz` tinyint(1) NULL DEFAULT 0 COMMENT '是否支持云台控制：0-不支持，1-支持',
  `rtcp` tinyint(1) NULL DEFAULT 0 COMMENT '是否启用RTCP：0-否，1-是',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '状态：0-离线，1-在线',
  `catalog_group` int(11) NULL DEFAULT NULL COMMENT '目录分组',
  `register_way` int(11) NULL DEFAULT NULL COMMENT '注册方式：1-IP注册，2-动态域名，3-主动上报',
  `secrecy` int(11) NULL DEFAULT NULL COMMENT '保密属性',
  `create_time` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '更新时间',
  `as_message_channel` tinyint(1) NULL DEFAULT 0 COMMENT '是否作为消息通道：0-否，1-是',
  `catalog_with_platform` int(11) NULL DEFAULT 1 COMMENT '是否查询平台目录：0-否，1-是',
  `catalog_with_group` int(11) NULL DEFAULT 1 COMMENT '是否查询分组目录：0-否，1-是',
  `catalog_with_region` int(11) NULL DEFAULT 1 COMMENT '是否查询区域目录：0-否，1-是',
  `auto_push_channel` tinyint(1) NULL DEFAULT 1 COMMENT '是否自动推送通道：0-否，1-是',
  `send_stream_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '推流IP地址',
  `server_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '流媒体服务器ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_platform_unique_server_gb_id`(`server_gb_id`) USING BTREE COMMENT '平台国标编码唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '国标GB28181平台配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for qs_gb28181_platform_channel
-- ----------------------------
DROP TABLE IF EXISTS `qs_gb28181_platform_channel`;
CREATE TABLE `qs_gb28181_platform_channel`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `platform_id` bigint(20) NULL DEFAULT NULL COMMENT '国标28181级联id',
  `device_id` bigint(20) NULL DEFAULT NULL COMMENT '设备id',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `id`(`id`) USING BTREE,
  UNIQUE INDEX `uk_platform_gb_channel_platform_id_catalog_id_device_channel_id`(`platform_id`, `device_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `config_id` int(5) NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '参数键值',
  `config_type` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '参数配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (1, '主框架页-默认皮肤样式名称', 'sys.index.skinName', 'skin-blue', 'Y', 'admin', '2026-03-27 13:52:39', '', NULL, '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow');
INSERT INTO `sys_config` VALUES (2, '用户管理-账号初始密码', 'sys.user.initPassword', '123456', 'Y', 'admin', '2026-03-27 13:52:39', '', NULL, '初始化密码 123456');
INSERT INTO `sys_config` VALUES (3, '主框架页-侧边栏主题', 'sys.index.sideTheme', 'theme-dark', 'Y', 'admin', '2026-03-27 13:52:39', '', NULL, '深色主题theme-dark，浅色主题theme-light');
INSERT INTO `sys_config` VALUES (4, '账号自助-是否开启用户注册功能', 'sys.account.registerUser', 'false', 'Y', 'admin', '2026-03-27 13:52:39', '', NULL, '是否开启注册用户功能（true开启，false关闭）');
INSERT INTO `sys_config` VALUES (5, '用户登录-黑名单列表', 'sys.login.blackIPList', '', 'Y', 'admin', '2026-03-27 13:52:39', '', NULL, '设置登录IP黑名单限制，多个匹配项以;分隔，支持匹配（*通配、网段）');
INSERT INTO `sys_config` VALUES (6, '用户管理-初始密码修改策略', 'sys.account.initPasswordModify', '1', 'Y', 'admin', '2026-03-27 13:52:39', '', NULL, '0：初始密码修改策略关闭，没有任何提示，1：提醒用户，如果未修改初始密码，则在登录时就会提醒修改密码对话框');
INSERT INTO `sys_config` VALUES (7, '用户管理-账号密码更新周期', 'sys.account.passwordValidateDays', '0', 'Y', 'admin', '2026-03-27 13:52:39', '', NULL, '密码更新周期（填写数字，数据初始化值为0不限制，若修改必须为大于0小于365的正整数），如果超过这个周期登录系统时，则在登录时就会提醒修改密码对话框');
INSERT INTO `sys_config` VALUES (8, '系统地图默认参数', 'sys.map.param', '116.39122,39.90684,5', 'Y', 'admin', '2026-02-26 11:54:42', 'admin', '2026-02-26 11:54:51', '经度，纬度，比例尺级别');

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父部门id',
  `ancestors` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '部门名称',
  `order_num` int(4) NULL DEFAULT 0 COMMENT '显示顺序',
  `leader` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '邮箱',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 110 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '部门表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (100, 0, '0', '若依科技', 0, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2026-03-27 13:52:39', '', NULL);
INSERT INTO `sys_dept` VALUES (101, 100, '0,100', '深圳总公司', 1, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2026-03-27 13:52:39', '', NULL);
INSERT INTO `sys_dept` VALUES (102, 100, '0,100', '长沙分公司', 2, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2026-03-27 13:52:39', '', NULL);
INSERT INTO `sys_dept` VALUES (103, 101, '0,100,101', '研发部门', 1, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2026-03-27 13:52:39', '', NULL);
INSERT INTO `sys_dept` VALUES (104, 101, '0,100,101', '市场部门', 2, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2026-03-27 13:52:39', '', NULL);
INSERT INTO `sys_dept` VALUES (105, 101, '0,100,101', '测试部门', 3, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2026-03-27 13:52:39', '', NULL);
INSERT INTO `sys_dept` VALUES (106, 101, '0,100,101', '财务部门', 4, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2026-03-27 13:52:39', '', NULL);
INSERT INTO `sys_dept` VALUES (107, 101, '0,100,101', '运维部门', 5, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2026-03-27 13:52:39', '', NULL);
INSERT INTO `sys_dept` VALUES (108, 102, '0,100,102', '市场部门', 1, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2026-03-27 13:52:39', '', NULL);
INSERT INTO `sys_dept` VALUES (109, 102, '0,100,102', '财务部门', 2, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2026-03-27 13:52:39', '', NULL);

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `dict_code` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int(4) NULL DEFAULT 0 COMMENT '字典排序',
  `dict_label` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 127 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '字典数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (1, 1, '男', '0', 'sys_user_sex', '', '', 'Y', '0', 'admin', '2026-03-27 13:52:39', '', NULL, '性别男');
INSERT INTO `sys_dict_data` VALUES (2, 2, '女', '1', 'sys_user_sex', '', '', 'N', '0', 'admin', '2026-03-27 13:52:39', '', NULL, '性别女');
INSERT INTO `sys_dict_data` VALUES (3, 3, '未知', '2', 'sys_user_sex', '', '', 'N', '0', 'admin', '2026-03-27 13:52:39', '', NULL, '性别未知');
INSERT INTO `sys_dict_data` VALUES (4, 1, '显示', '0', 'sys_show_hide', '', 'primary', 'Y', '0', 'admin', '2026-03-27 13:52:39', '', NULL, '显示菜单');
INSERT INTO `sys_dict_data` VALUES (5, 2, '隐藏', '1', 'sys_show_hide', '', 'danger', 'N', '0', 'admin', '2026-03-27 13:52:39', '', NULL, '隐藏菜单');
INSERT INTO `sys_dict_data` VALUES (6, 1, '正常', '0', 'sys_normal_disable', '', 'primary', 'Y', '0', 'admin', '2026-03-27 13:52:39', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (7, 2, '停用', '1', 'sys_normal_disable', '', 'danger', 'N', '0', 'admin', '2026-03-27 13:52:39', '', NULL, '停用状态');
INSERT INTO `sys_dict_data` VALUES (8, 1, '正常', '0', 'sys_job_status', '', 'primary', 'Y', '0', 'admin', '2026-03-27 13:52:39', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (9, 2, '暂停', '1', 'sys_job_status', '', 'danger', 'N', '0', 'admin', '2026-03-27 13:52:39', '', NULL, '停用状态');
INSERT INTO `sys_dict_data` VALUES (10, 1, '默认', 'DEFAULT', 'sys_job_group', '', '', 'Y', '0', 'admin', '2026-03-27 13:52:39', '', NULL, '默认分组');
INSERT INTO `sys_dict_data` VALUES (11, 2, '系统', 'SYSTEM', 'sys_job_group', '', '', 'N', '0', 'admin', '2026-03-27 13:52:39', '', NULL, '系统分组');
INSERT INTO `sys_dict_data` VALUES (12, 1, '是', 'Y', 'sys_yes_no', '', 'primary', 'Y', '0', 'admin', '2026-03-27 13:52:39', '', NULL, '系统默认是');
INSERT INTO `sys_dict_data` VALUES (13, 2, '否', 'N', 'sys_yes_no', '', 'danger', 'N', '0', 'admin', '2026-03-27 13:52:39', '', NULL, '系统默认否');
INSERT INTO `sys_dict_data` VALUES (14, 1, '通知', '1', 'sys_notice_type', '', 'warning', 'Y', '0', 'admin', '2026-03-27 13:52:39', '', NULL, '通知');
INSERT INTO `sys_dict_data` VALUES (15, 2, '公告', '2', 'sys_notice_type', '', 'success', 'N', '0', 'admin', '2026-03-27 13:52:39', '', NULL, '公告');
INSERT INTO `sys_dict_data` VALUES (16, 1, '正常', '0', 'sys_notice_status', '', 'primary', 'Y', '0', 'admin', '2026-03-27 13:52:39', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (17, 2, '关闭', '1', 'sys_notice_status', '', 'danger', 'N', '0', 'admin', '2026-03-27 13:52:39', '', NULL, '关闭状态');
INSERT INTO `sys_dict_data` VALUES (18, 99, '其他', '0', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2026-03-27 13:52:39', '', NULL, '其他操作');
INSERT INTO `sys_dict_data` VALUES (19, 1, '新增', '1', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2026-03-27 13:52:39', '', NULL, '新增操作');
INSERT INTO `sys_dict_data` VALUES (20, 2, '修改', '2', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2026-03-27 13:52:39', '', NULL, '修改操作');
INSERT INTO `sys_dict_data` VALUES (21, 3, '删除', '3', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2026-03-27 13:52:39', '', NULL, '删除操作');
INSERT INTO `sys_dict_data` VALUES (22, 4, '授权', '4', 'sys_oper_type', '', 'primary', 'N', '0', 'admin', '2026-03-27 13:52:39', '', NULL, '授权操作');
INSERT INTO `sys_dict_data` VALUES (23, 5, '导出', '5', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2026-03-27 13:52:39', '', NULL, '导出操作');
INSERT INTO `sys_dict_data` VALUES (24, 6, '导入', '6', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2026-03-27 13:52:39', '', NULL, '导入操作');
INSERT INTO `sys_dict_data` VALUES (25, 7, '强退', '7', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2026-03-27 13:52:39', '', NULL, '强退操作');
INSERT INTO `sys_dict_data` VALUES (26, 8, '生成代码', '8', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2026-03-27 13:52:39', '', NULL, '生成操作');
INSERT INTO `sys_dict_data` VALUES (27, 9, '清空数据', '9', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2026-03-27 13:52:39', '', NULL, '清空操作');
INSERT INTO `sys_dict_data` VALUES (28, 1, '成功', '0', 'sys_common_status', '', 'primary', 'N', '0', 'admin', '2026-03-27 13:52:39', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (29, 2, '失败', '1', 'sys_common_status', '', 'danger', 'N', '0', 'admin', '2026-03-27 13:52:39', '', NULL, '停用状态');
INSERT INTO `sys_dict_data` VALUES (100, 1, '启用', 'ENABLE', 'qs_status', NULL, 'success', 'N', '0', 'admin', '2026-03-27 16:01:48', 'admin', '2026-03-28 15:28:09', NULL);
INSERT INTO `sys_dict_data` VALUES (101, 2, '停用', 'DEACTIVATE', 'qs_status', NULL, 'danger', 'N', '0', 'admin', '2026-03-27 16:02:14', 'admin', '2026-03-28 15:27:56', NULL);
INSERT INTO `sys_dict_data` VALUES (102, 1, 'RTSP', '1', 'qs_live_stream_type', NULL, 'primary', 'N', '0', 'admin', '2026-03-27 16:03:17', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (103, 2, 'RTMP', '2', 'qs_live_stream_type', NULL, 'primary', 'N', '0', 'admin', '2026-03-27 16:03:33', 'admin', '2026-03-27 16:04:03', NULL);
INSERT INTO `sys_dict_data` VALUES (104, 3, 'FLV', '3', 'qs_live_stream_type', NULL, 'primary', 'N', '0', 'admin', '2026-03-27 16:03:44', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (105, 4, 'HLS', '4', 'qs_live_stream_type', NULL, 'primary', 'N', '0', 'admin', '2026-03-27 16:03:58', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (106, 5, 'ONVIF', '5', 'qs_live_stream_type', NULL, 'primary', 'N', '0', 'admin', '2026-03-27 16:04:17', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (107, 6, '视频文件', '6', 'qs_live_stream_type', NULL, 'primary', 'N', '0', 'admin', '2026-03-27 16:04:33', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (108, 7, '海康SDK', '7', 'qs_live_stream_type', NULL, 'primary', 'N', '0', 'admin', '2026-03-27 16:04:45', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (109, 8, '海康ISUP', '8', 'qs_live_stream_type', NULL, 'primary', 'N', '0', 'admin', '2026-03-27 16:04:56', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (110, 9, '大华SDK', '9', 'qs_live_stream_type', NULL, 'primary', 'N', '0', 'admin', '2026-03-27 16:05:15', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (111, 10, '宇视SDK', '10', 'qs_live_stream_type', NULL, 'primary', 'N', '1', 'admin', '2026-03-27 16:05:30', 'admin', '2026-04-15 14:29:20', NULL);
INSERT INTO `sys_dict_data` VALUES (112, 11, '天地伟业SDK', '11', 'qs_live_stream_type', NULL, 'primary', 'N', '1', 'admin', '2026-03-27 16:05:41', 'admin', '2026-04-15 14:29:12', NULL);
INSERT INTO `sys_dict_data` VALUES (113, 12, '国标28181', '12', 'qs_live_stream_type', NULL, 'primary', 'N', '0', 'admin', '2026-03-27 16:05:54', 'admin', '2026-04-25 13:38:11', NULL);
INSERT INTO `sys_dict_data` VALUES (114, 13, 'PUSH', '13', 'qs_live_stream_type', NULL, 'primary', 'N', '0', 'admin', '2026-03-27 16:06:10', 'admin', '2026-04-25 13:38:06', NULL);
INSERT INTO `sys_dict_data` VALUES (115, 14, '部标1078', '14', 'qs_live_stream_type', NULL, 'primary', 'N', '0', 'admin', '2026-03-27 16:06:22', 'admin', '2026-04-26 20:31:03', NULL);
INSERT INTO `sys_dict_data` VALUES (116, 1, '主码流', '1', 'qs_stream_type', NULL, 'primary', 'N', '0', 'admin', '2026-03-30 12:34:27', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (117, 2, '子码流', '2', 'qs_stream_type', NULL, 'warning', 'N', '0', 'admin', '2026-03-30 12:34:39', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (118, 3, '第三码流', '3', 'qs_stream_type', NULL, 'info', 'N', '0', 'admin', '2026-03-30 12:34:48', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (119, 1, 'UDP', 'UDP', 'qs_protocol', NULL, 'info', 'N', '0', 'admin', '2026-03-30 12:35:05', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (120, 2, 'TCP', 'TCP', 'qs_protocol', NULL, 'primary', 'N', '0', 'admin', '2026-03-30 12:35:15', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (121, 1, '离线', 'OFFLINE', 'qs_device_status', NULL, 'danger', 'N', '0', 'admin', '2026-03-30 12:35:31', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (122, 2, '在线', 'ON', 'qs_device_status', NULL, 'success', 'N', '0', 'admin', '2026-03-30 12:35:43', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (123, 1, '主动添加', '1', 'qs_online_type', NULL, 'primary', 'N', '0', 'admin', '2026-03-30 14:14:46', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (124, 2, '主动注册', '2', 'qs_online_type', NULL, 'primary', 'N', '0', 'admin', '2026-03-30 14:14:55', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (125, 1, 'WS-UsernameToken', '1', 'qs_onvif_auth', NULL, 'primary', 'N', '0', 'admin', '2026-04-09 22:06:08', 'admin', '2026-04-09 22:06:37', NULL);
INSERT INTO `sys_dict_data` VALUES (126, 2, 'Digest', '2', 'qs_onvif_auth', NULL, 'primary', 'N', '0', 'admin', '2026-04-09 22:06:28', '', NULL, NULL);

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `dict_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '字典类型',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`) USING BTREE,
  UNIQUE INDEX `dict_type`(`dict_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 107 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '字典类型表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1, '用户性别', 'sys_user_sex', '0', 'admin', '2026-03-27 13:52:39', '', NULL, '用户性别列表');
INSERT INTO `sys_dict_type` VALUES (2, '菜单状态', 'sys_show_hide', '0', 'admin', '2026-03-27 13:52:39', '', NULL, '菜单状态列表');
INSERT INTO `sys_dict_type` VALUES (3, '系统开关', 'sys_normal_disable', '0', 'admin', '2026-03-27 13:52:39', '', NULL, '系统开关列表');
INSERT INTO `sys_dict_type` VALUES (4, '任务状态', 'sys_job_status', '0', 'admin', '2026-03-27 13:52:39', '', NULL, '任务状态列表');
INSERT INTO `sys_dict_type` VALUES (5, '任务分组', 'sys_job_group', '0', 'admin', '2026-03-27 13:52:39', '', NULL, '任务分组列表');
INSERT INTO `sys_dict_type` VALUES (6, '系统是否', 'sys_yes_no', '0', 'admin', '2026-03-27 13:52:39', '', NULL, '系统是否列表');
INSERT INTO `sys_dict_type` VALUES (7, '通知类型', 'sys_notice_type', '0', 'admin', '2026-03-27 13:52:39', '', NULL, '通知类型列表');
INSERT INTO `sys_dict_type` VALUES (8, '通知状态', 'sys_notice_status', '0', 'admin', '2026-03-27 13:52:39', '', NULL, '通知状态列表');
INSERT INTO `sys_dict_type` VALUES (9, '操作类型', 'sys_oper_type', '0', 'admin', '2026-03-27 13:52:39', '', NULL, '操作类型列表');
INSERT INTO `sys_dict_type` VALUES (10, '系统状态', 'sys_common_status', '0', 'admin', '2026-03-27 13:52:39', '', NULL, '登录状态列表');
INSERT INTO `sys_dict_type` VALUES (100, '设备状态', 'qs_status', '0', 'admin', '2026-03-27 16:01:17', 'admin', '2026-03-30 12:32:25', '设备状态列表');
INSERT INTO `sys_dict_type` VALUES (101, '直播流接入类型', 'qs_live_stream_type', '0', 'admin', '2026-03-27 16:02:51', 'admin', '2026-03-30 12:32:34', '直播流接入类型列表');
INSERT INTO `sys_dict_type` VALUES (102, '码流类型', 'qs_stream_type', '0', 'admin', '2026-03-30 12:32:17', '', NULL, '码流类型列表');
INSERT INTO `sys_dict_type` VALUES (103, '传输协议', 'qs_protocol', '0', 'admin', '2026-03-30 12:33:46', '', NULL, '传输协议列表');
INSERT INTO `sys_dict_type` VALUES (104, '设备状态', 'qs_device_status', '0', 'admin', '2026-03-30 12:34:08', '', NULL, '设备状态列表');
INSERT INTO `sys_dict_type` VALUES (105, '上线类型', 'qs_online_type', '0', 'admin', '2026-03-30 14:14:29', '', NULL, '上线类型列表');
INSERT INTO `sys_dict_type` VALUES (106, 'onvif验证类型', 'qs_onvif_auth', '0', 'admin', '2026-04-09 22:05:39', '', NULL, 'onvif验证类型列表');

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job`  (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `job_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '调用目标字符串',
  `cron_expression` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '0' COMMENT '状态（0正常 1暂停）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '备注信息',
  PRIMARY KEY (`job_id`, `job_name`, `job_group`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 109 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '定时任务调度表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_job
-- ----------------------------
INSERT INTO `sys_job` VALUES (100, '海康sdk设备状态任务', 'SYSTEM', 'haiKangTask.task', '0 * * * * ?', '1', '1', '1', 'admin', '2026-03-28 15:08:44', 'admin', '2026-05-08 16:10:42', '');
INSERT INTO `sys_job` VALUES (101, '海康isup设备状态任务', 'SYSTEM', 'haiKangIsupTask.task', '0 * * * * ?', '1', '1', '1', 'admin', '2026-03-30 13:40:49', 'admin', '2026-05-07 21:47:49', '');
INSERT INTO `sys_job` VALUES (102, '大华sdk设备状态任务', 'SYSTEM', 'daHuaTask.task', '0 * * * * ?', '1', '1', '1', 'admin', '2026-03-30 22:49:53', 'admin', '2026-05-07 21:47:50', '');
INSERT INTO `sys_job` VALUES (103, '设备状态任务', 'SYSTEM', 'qsDeviceTask.task', '0 * * * * ?', '1', '1', '1', 'admin', '2026-04-10 02:35:01', 'admin', '2026-05-07 21:47:52', '');
INSERT INTO `sys_job` VALUES (104, 'onvif设备状态', 'SYSTEM', 'onvifTask.task', '0 * * * * ?', '1', '1', '1', 'admin', '2026-04-10 12:41:29', 'admin', '2026-05-07 21:47:54', '');
INSERT INTO `sys_job` VALUES (105, '定时查询待删除的录像文件', 'SYSTEM', 'cloudRecordTask.task', '0 0 0 * * ?', '1', '1', '1', 'admin', '2026-04-11 13:33:40', '', '2026-05-07 21:47:56', '');
INSERT INTO `sys_job` VALUES (106, '录像计划任务', 'SYSTEM', 'recordPlanTask.task', '0 0/10 * * * ?', '1', '1', '1', 'admin', '2026-04-12 22:53:58', 'admin', '2026-05-07 21:47:57', '');
INSERT INTO `sys_job` VALUES (107, 'GB28181 设备状态同步任务', 'SYSTEM', 'gb28181Task.task', '0 * * * * ?', '1', '1', '1', 'admin', '2026-04-26 22:41:58', 'admin', '2026-05-07 21:47:59', '');
INSERT INTO `sys_job` VALUES (108, 'JT1078 设备状态同步任务', 'SYSTEM', 'jt1078Task.task', '0 * * * * ?', '1', '1', '1', 'admin', '2026-04-26 22:42:46', 'admin', '2026-05-07 21:48:00', '');

-- ----------------------------
-- Table structure for sys_job_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_job_log`;
CREATE TABLE `sys_job_log`  (
  `job_log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
  `job_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '调用目标字符串',
  `job_message` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '日志信息',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
  `exception_info` varchar(2000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '异常信息',
  `start_time` datetime NULL DEFAULT NULL COMMENT '执行开始时间',
  `end_time` datetime NULL DEFAULT NULL COMMENT '执行结束时间',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '定时任务调度日志表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_job_log
-- ----------------------------
INSERT INTO `sys_job_log` VALUES (1, '海康sdk设备状态任务', 'SYSTEM', 'haiKangTask.task', '海康sdk设备状态任务 总共耗时：1524毫秒', '0', '', '2026-05-14 13:46:14', '2026-05-14 13:46:15', '2026-05-14 13:46:15');
INSERT INTO `sys_job_log` VALUES (2, '大华sdk设备状态任务', 'SYSTEM', 'daHuaTask.task', '大华sdk设备状态任务 总共耗时：13296毫秒', '0', '', '2026-05-14 23:29:03', '2026-05-14 23:29:16', '2026-05-14 23:29:16');
INSERT INTO `sys_job_log` VALUES (3, '大华sdk设备状态任务', 'SYSTEM', 'daHuaTask.task', '大华sdk设备状态任务 总共耗时：704毫秒', '0', '', '2026-05-14 23:29:30', '2026-05-14 23:29:31', '2026-05-14 23:29:30');
INSERT INTO `sys_job_log` VALUES (4, '海康sdk设备状态任务', 'SYSTEM', 'haiKangTask.task', '海康sdk设备状态任务 总共耗时：2870毫秒', '0', '', '2026-05-15 09:22:02', '2026-05-15 09:22:05', '2026-05-15 09:22:05');
INSERT INTO `sys_job_log` VALUES (5, '海康isup设备状态任务', 'SYSTEM', 'haiKangIsupTask.task', '海康isup设备状态任务 总共耗时：1222毫秒', '0', '', '2026-05-15 09:22:04', '2026-05-15 09:22:06', '2026-05-15 09:22:05');
INSERT INTO `sys_job_log` VALUES (6, '设备状态任务', 'SYSTEM', 'qsDeviceTask.task', '设备状态任务 总共耗时：42毫秒', '0', '', '2026-05-15 09:22:12', '2026-05-15 09:22:12', '2026-05-15 09:22:11');
INSERT INTO `sys_job_log` VALUES (7, 'onvif设备状态', 'SYSTEM', 'onvifTask.task', 'onvif设备状态 总共耗时：2111毫秒', '0', '', '2026-05-15 09:22:14', '2026-05-15 09:22:16', '2026-05-15 09:22:16');
INSERT INTO `sys_job_log` VALUES (8, '大华sdk设备状态任务', 'SYSTEM', 'daHuaTask.task', '大华sdk设备状态任务 总共耗时：11330毫秒', '0', '', '2026-05-15 09:22:07', '2026-05-15 09:22:18', '2026-05-15 09:22:17');
INSERT INTO `sys_job_log` VALUES (9, 'GB28181 设备状态同步任务', 'SYSTEM', 'gb28181Task.task', 'GB28181 设备状态同步任务 总共耗时：292毫秒', '0', '', '2026-05-15 09:22:20', '2026-05-15 09:22:20', '2026-05-15 09:22:20');
INSERT INTO `sys_job_log` VALUES (10, 'JT1078 设备状态同步任务', 'SYSTEM', 'jt1078Task.task', 'JT1078 设备状态同步任务 总共耗时：527毫秒', '0', '', '2026-05-15 09:22:22', '2026-05-15 09:22:22', '2026-05-15 09:22:22');
INSERT INTO `sys_job_log` VALUES (11, '大华sdk设备状态任务', 'SYSTEM', 'daHuaTask.task', '大华sdk设备状态任务 总共耗时：710毫秒', '0', '', '2026-05-15 09:23:02', '2026-05-15 09:23:02', '2026-05-15 09:23:02');
INSERT INTO `sys_job_log` VALUES (12, 'JT1078 设备状态同步任务', 'SYSTEM', 'jt1078Task.task', 'JT1078 设备状态同步任务 总共耗时：126毫秒', '0', '', '2026-05-15 09:23:49', '2026-05-15 09:23:49', '2026-05-15 09:23:48');
INSERT INTO `sys_job_log` VALUES (13, 'onvif设备状态', 'SYSTEM', 'onvifTask.task', 'onvif设备状态 总共耗时：1982毫秒', '0', '', '2026-05-15 17:56:44', '2026-05-15 17:56:46', '2026-05-15 17:56:45');
INSERT INTO `sys_job_log` VALUES (14, '海康sdk设备状态任务', 'SYSTEM', 'haiKangTask.task', '海康sdk设备状态任务 总共耗时：1747毫秒', '0', '', '2026-05-15 22:17:36', '2026-05-15 22:17:37', '2026-05-15 22:17:37');
INSERT INTO `sys_job_log` VALUES (15, '海康isup设备状态任务', 'SYSTEM', 'haiKangIsupTask.task', '海康isup设备状态任务 总共耗时：1172毫秒', '0', '', '2026-05-15 22:17:41', '2026-05-15 22:17:42', '2026-05-15 22:17:41');
INSERT INTO `sys_job_log` VALUES (16, 'GB28181 设备状态同步任务', 'SYSTEM', 'gb28181Task.task', 'GB28181 设备状态同步任务 总共耗时：342毫秒', '0', '', '2026-05-15 22:18:04', '2026-05-15 22:18:04', '2026-05-15 22:18:04');
INSERT INTO `sys_job_log` VALUES (17, '大华sdk设备状态任务', 'SYSTEM', 'daHuaTask.task', '大华sdk设备状态任务 总共耗时：2310毫秒', '0', '', '2026-05-15 22:18:16', '2026-05-15 22:18:18', '2026-05-15 22:18:18');

-- ----------------------------
-- Table structure for sys_logininfor
-- ----------------------------
DROP TABLE IF EXISTS `sys_logininfor`;
CREATE TABLE `sys_logininfor`  (
  `info_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '用户账号',
  `ipaddr` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '登录IP地址',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '提示信息',
  `access_time` datetime NULL DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`info_id`) USING BTREE,
  INDEX `idx_sys_logininfor_s`(`status`) USING BTREE,
  INDEX `idx_sys_logininfor_lt`(`access_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 181 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '系统访问记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_logininfor
-- ----------------------------
INSERT INTO `sys_logininfor` VALUES (100, '', '192.168.2.200', '0', '退出成功', '2026-03-27 13:57:08');
INSERT INTO `sys_logininfor` VALUES (101, 'admin', '192.168.2.200', '0', '登录成功', '2026-03-27 13:57:17');
INSERT INTO `sys_logininfor` VALUES (102, 'admin', '192.168.2.200', '0', '登录成功', '2026-03-27 14:09:23');
INSERT INTO `sys_logininfor` VALUES (103, 'admin', '192.168.2.200', '0', '退出成功', '2026-03-28 13:35:30');
INSERT INTO `sys_logininfor` VALUES (104, 'admin', '192.168.2.200', '0', '登录成功', '2026-03-28 13:35:36');
INSERT INTO `sys_logininfor` VALUES (105, 'admin', '192.168.2.200', '0', '登录成功', '2026-03-28 16:53:18');
INSERT INTO `sys_logininfor` VALUES (106, 'admin', '192.168.2.200', '0', '退出成功', '2026-03-30 12:16:52');
INSERT INTO `sys_logininfor` VALUES (107, 'admin', '192.168.2.200', '0', '登录成功', '2026-03-30 12:16:58');
INSERT INTO `sys_logininfor` VALUES (108, 'admin', '192.168.2.200', '0', '登录成功', '2026-03-30 13:58:23');
INSERT INTO `sys_logininfor` VALUES (109, 'admin', '192.168.2.200', '0', '退出成功', '2026-03-31 13:15:25');
INSERT INTO `sys_logininfor` VALUES (110, 'admin', '192.168.2.200', '0', '登录成功', '2026-03-31 13:29:43');
INSERT INTO `sys_logininfor` VALUES (111, 'admin', '192.168.2.200', '0', '退出成功', '2026-04-01 13:37:07');
INSERT INTO `sys_logininfor` VALUES (112, 'admin', '192.168.2.200', '0', '登录成功', '2026-04-01 13:37:11');
INSERT INTO `sys_logininfor` VALUES (113, 'admin', '192.168.2.200', '0', '退出成功', '2026-04-02 20:50:16');
INSERT INTO `sys_logininfor` VALUES (114, 'admin', '192.168.2.200', '0', '登录成功', '2026-04-02 20:57:05');
INSERT INTO `sys_logininfor` VALUES (115, 'admin', '192.168.2.200', '0', '退出成功', '2026-04-03 12:45:12');
INSERT INTO `sys_logininfor` VALUES (116, 'admin', '192.168.2.200', '0', '登录成功', '2026-04-03 12:45:16');
INSERT INTO `sys_logininfor` VALUES (117, 'admin', '192.168.2.200', '0', '登录成功', '2026-04-03 19:57:27');
INSERT INTO `sys_logininfor` VALUES (118, 'admin', '192.168.2.200', '0', '登录成功', '2026-04-07 11:52:10');
INSERT INTO `sys_logininfor` VALUES (119, 'admin', '192.168.2.200', '0', '退出成功', '2026-04-08 23:04:40');
INSERT INTO `sys_logininfor` VALUES (120, 'admin', '192.168.2.200', '0', '登录成功', '2026-04-08 23:04:47');
INSERT INTO `sys_logininfor` VALUES (121, 'admin', '192.168.2.200', '0', '退出成功', '2026-04-09 12:02:21');
INSERT INTO `sys_logininfor` VALUES (122, 'admin', '192.168.2.200', '0', '登录成功', '2026-04-09 12:02:27');
INSERT INTO `sys_logininfor` VALUES (123, 'admin', '192.168.2.200', '0', '退出成功', '2026-04-10 12:40:47');
INSERT INTO `sys_logininfor` VALUES (124, 'admin', '192.168.2.200', '0', '登录成功', '2026-04-10 12:40:54');
INSERT INTO `sys_logininfor` VALUES (125, 'admin', '192.168.2.200', '0', '退出成功', '2026-04-11 13:28:10');
INSERT INTO `sys_logininfor` VALUES (126, 'admin', '192.168.2.200', '0', '登录成功', '2026-04-11 13:28:14');
INSERT INTO `sys_logininfor` VALUES (127, 'admin', '192.168.2.200', '0', '退出成功', '2026-04-12 16:36:23');
INSERT INTO `sys_logininfor` VALUES (128, 'admin', '192.168.2.200', '0', '登录成功', '2026-04-12 16:36:32');
INSERT INTO `sys_logininfor` VALUES (129, 'admin', '192.168.2.200', '0', '登录成功', '2026-04-12 22:53:17');
INSERT INTO `sys_logininfor` VALUES (130, 'admin', '192.168.2.200', '0', '退出成功', '2026-04-13 12:36:27');
INSERT INTO `sys_logininfor` VALUES (131, 'admin', '192.168.2.200', '0', '登录成功', '2026-04-13 12:36:31');
INSERT INTO `sys_logininfor` VALUES (132, 'admin', '192.168.2.200', '0', '退出成功', '2026-04-14 14:10:44');
INSERT INTO `sys_logininfor` VALUES (133, 'admin', '192.168.2.200', '0', '登录成功', '2026-04-14 14:15:29');
INSERT INTO `sys_logininfor` VALUES (134, 'admin', '192.168.2.200', '0', '退出成功', '2026-04-15 23:33:09');
INSERT INTO `sys_logininfor` VALUES (135, 'admin', '192.168.2.200', '0', '登录成功', '2026-04-15 23:33:19');
INSERT INTO `sys_logininfor` VALUES (136, 'admin', '192.168.2.200', '0', '退出成功', '2026-04-15 23:34:33');
INSERT INTO `sys_logininfor` VALUES (137, 'admin', '192.168.2.200', '0', '登录成功', '2026-04-15 23:34:47');
INSERT INTO `sys_logininfor` VALUES (138, '', '192.168.81.1', '0', '退出成功', '2026-04-25 13:05:43');
INSERT INTO `sys_logininfor` VALUES (139, 'admin', '192.168.81.1', '0', '登录成功', '2026-04-25 13:05:53');
INSERT INTO `sys_logininfor` VALUES (140, 'admin', '192.168.81.1', '0', '退出成功', '2026-04-25 13:33:24');
INSERT INTO `sys_logininfor` VALUES (141, 'admin', '192.168.81.1', '0', '登录成功', '2026-04-25 13:33:35');
INSERT INTO `sys_logininfor` VALUES (142, 'admin', '192.168.81.1', '0', '登录成功', '2026-04-26 20:30:30');
INSERT INTO `sys_logininfor` VALUES (143, 'admin', '192.168.81.1', '0', '退出成功', '2026-04-27 00:56:35');
INSERT INTO `sys_logininfor` VALUES (144, 'admin', '192.168.81.1', '0', '登录成功', '2026-04-27 01:06:09');
INSERT INTO `sys_logininfor` VALUES (145, 'admin', '192.168.81.1', '0', '登录成功', '2026-04-27 20:28:19');
INSERT INTO `sys_logininfor` VALUES (146, 'admin', '192.168.81.1', '0', '退出成功', '2026-04-28 23:25:39');
INSERT INTO `sys_logininfor` VALUES (147, 'admin', '192.168.81.1', '0', '登录成功', '2026-04-28 23:25:53');
INSERT INTO `sys_logininfor` VALUES (148, 'admin', '192.168.81.1', '0', '退出成功', '2026-04-29 15:30:11');
INSERT INTO `sys_logininfor` VALUES (149, 'admin', '192.168.81.1', '0', '登录成功', '2026-04-29 15:30:17');
INSERT INTO `sys_logininfor` VALUES (150, 'admin', '192.168.81.1', '0', '登录成功', '2026-04-29 19:10:02');
INSERT INTO `sys_logininfor` VALUES (151, 'admin', '192.168.81.1', '0', '退出成功', '2026-04-30 11:53:10');
INSERT INTO `sys_logininfor` VALUES (152, 'admin', '192.168.81.1', '0', '登录成功', '2026-04-30 11:54:56');
INSERT INTO `sys_logininfor` VALUES (153, 'admin', '192.168.81.1', '0', '登录成功', '2026-05-05 15:05:54');
INSERT INTO `sys_logininfor` VALUES (154, 'admin', '192.168.81.1', '0', '登录成功', '2026-05-06 10:08:01');
INSERT INTO `sys_logininfor` VALUES (155, 'admin', '192.168.81.1', '0', '登录成功', '2026-05-06 13:29:30');
INSERT INTO `sys_logininfor` VALUES (156, 'admin', '192.168.81.1', '0', '登录成功', '2026-05-06 13:39:12');
INSERT INTO `sys_logininfor` VALUES (157, 'admin', '192.168.81.1', '0', '退出成功', '2026-05-06 21:09:16');
INSERT INTO `sys_logininfor` VALUES (158, 'admin', '192.168.81.1', '0', '登录成功', '2026-05-06 21:53:41');
INSERT INTO `sys_logininfor` VALUES (159, 'admin', '192.168.81.1', '0', '登录成功', '2026-05-07 10:14:16');
INSERT INTO `sys_logininfor` VALUES (160, 'admin', '192.168.81.1', '0', '退出成功', '2026-05-07 12:06:13');
INSERT INTO `sys_logininfor` VALUES (161, 'admin', '192.168.81.1', '0', '登录成功', '2026-05-07 12:06:19');
INSERT INTO `sys_logininfor` VALUES (162, 'admin', '192.168.81.1', '0', '退出成功', '2026-05-07 12:08:48');
INSERT INTO `sys_logininfor` VALUES (163, 'admin', '192.168.81.1', '0', '登录成功', '2026-05-07 12:09:10');
INSERT INTO `sys_logininfor` VALUES (164, 'admin', '192.168.81.1', '0', '登录成功', '2026-05-07 21:47:14');
INSERT INTO `sys_logininfor` VALUES (165, 'admin', '192.168.81.1', '0', '登录成功', '2026-05-09 10:37:58');
INSERT INTO `sys_logininfor` VALUES (166, 'admin', '192.168.81.1', '0', '登录成功', '2026-05-09 15:02:11');
INSERT INTO `sys_logininfor` VALUES (167, 'admin', '192.168.81.1', '0', '登录成功', '2026-05-10 11:25:07');
INSERT INTO `sys_logininfor` VALUES (168, 'admin', '192.168.81.1', '0', '登录成功', '2026-05-11 09:04:33');
INSERT INTO `sys_logininfor` VALUES (169, 'admin', '192.168.81.1', '0', '登录成功', '2026-05-12 10:12:13');
INSERT INTO `sys_logininfor` VALUES (170, 'admin', '192.168.81.1', '0', '退出成功', '2026-05-13 11:34:55');
INSERT INTO `sys_logininfor` VALUES (171, 'admin', '192.168.81.1', '0', '登录成功', '2026-05-13 11:35:02');
INSERT INTO `sys_logininfor` VALUES (172, 'admin', '192.168.81.1', '0', '退出成功', '2026-05-14 02:56:42');
INSERT INTO `sys_logininfor` VALUES (173, 'admin', '192.168.81.1', '0', '登录成功', '2026-05-14 02:56:46');
INSERT INTO `sys_logininfor` VALUES (174, 'admin', '192.168.81.1', '0', '登录成功', '2026-05-14 12:10:07');
INSERT INTO `sys_logininfor` VALUES (175, 'admin', '192.168.81.1', '0', '登录成功', '2026-05-14 14:17:07');
INSERT INTO `sys_logininfor` VALUES (176, 'admin', '192.168.81.1', '0', '登录成功', '2026-05-14 21:52:57');
INSERT INTO `sys_logininfor` VALUES (177, 'admin', '192.168.81.1', '0', '登录成功', '2026-05-15 09:07:09');
INSERT INTO `sys_logininfor` VALUES (178, 'admin', '192.168.81.1', '0', '登录成功', '2026-05-15 09:33:20');
INSERT INTO `sys_logininfor` VALUES (179, 'admin', '192.168.81.1', '0', '退出成功', '2026-05-15 23:35:40');
INSERT INTO `sys_logininfor` VALUES (180, 'admin', '192.168.81.1', '0', '登录成功', '2026-05-15 23:36:02');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父菜单ID',
  `order_num` int(4) NULL DEFAULT 0 COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '组件路径',
  `query` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '路由参数',
  `route_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '路由名称',
  `is_frame` int(1) NULL DEFAULT 1 COMMENT '是否为外链（0是 1否）',
  `is_cache` int(1) NULL DEFAULT 0 COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2011 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '菜单权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, 11, 'system', NULL, '', '', 1, 0, 'M', '0', '0', '', 'system', 'admin', '2026-03-27 13:52:39', 'admin', '2026-05-13 17:59:00', '系统管理目录');
INSERT INTO `sys_menu` VALUES (2, '系统监控', 0, 12, 'monitor', NULL, '', '', 1, 0, 'M', '0', '0', '', 'monitor', 'admin', '2026-03-27 13:52:39', 'admin', '2026-05-13 17:58:56', '系统监控目录');
INSERT INTO `sys_menu` VALUES (3, '系统工具', 0, 13, 'tool', NULL, '', '', 1, 0, 'M', '0', '0', '', 'tool', 'admin', '2026-03-27 13:52:39', 'admin', '2026-05-13 17:58:52', '系统工具目录');
INSERT INTO `sys_menu` VALUES (100, '用户管理', 1, 1, 'user', 'system/user/index', '', '', 1, 0, 'C', '0', '0', 'system:user:list', 'user', 'admin', '2026-03-27 13:52:39', '', NULL, '用户管理菜单');
INSERT INTO `sys_menu` VALUES (101, '角色管理', 1, 2, 'role', 'system/role/index', '', '', 1, 0, 'C', '0', '0', 'system:role:list', 'peoples', 'admin', '2026-03-27 13:52:39', '', NULL, '角色管理菜单');
INSERT INTO `sys_menu` VALUES (102, '菜单管理', 1, 3, 'menu', 'system/menu/index', '', '', 1, 0, 'C', '0', '0', 'system:menu:list', 'tree-table', 'admin', '2026-03-27 13:52:39', '', NULL, '菜单管理菜单');
INSERT INTO `sys_menu` VALUES (103, '部门管理', 1, 4, 'dept', 'system/dept/index', '', '', 1, 0, 'C', '0', '0', 'system:dept:list', 'tree', 'admin', '2026-03-27 13:52:39', '', NULL, '部门管理菜单');
INSERT INTO `sys_menu` VALUES (104, '岗位管理', 1, 5, 'post', 'system/post/index', '', '', 1, 0, 'C', '0', '0', 'system:post:list', 'post', 'admin', '2026-03-27 13:52:39', '', NULL, '岗位管理菜单');
INSERT INTO `sys_menu` VALUES (105, '字典管理', 1, 6, 'dict', 'system/dict/index', '', '', 1, 0, 'C', '0', '0', 'system:dict:list', 'dict', 'admin', '2026-03-27 13:52:39', '', NULL, '字典管理菜单');
INSERT INTO `sys_menu` VALUES (106, '参数设置', 1, 7, 'config', 'system/config/index', '', '', 1, 0, 'C', '0', '0', 'system:config:list', 'edit', 'admin', '2026-03-27 13:52:39', '', NULL, '参数设置菜单');
INSERT INTO `sys_menu` VALUES (107, '通知公告', 1, 8, 'notice', 'system/notice/index', '', '', 1, 0, 'C', '0', '0', 'system:notice:list', 'message', 'admin', '2026-03-27 13:52:39', '', NULL, '通知公告菜单');
INSERT INTO `sys_menu` VALUES (108, '日志管理', 1, 9, 'log', '', '', '', 1, 0, 'M', '0', '0', '', 'log', 'admin', '2026-03-27 13:52:39', '', NULL, '日志管理菜单');
INSERT INTO `sys_menu` VALUES (109, '在线用户', 2, 1, 'online', 'monitor/online/index', '', '', 1, 0, 'C', '0', '0', 'monitor:online:list', 'online', 'admin', '2026-03-27 13:52:39', '', NULL, '在线用户菜单');
INSERT INTO `sys_menu` VALUES (110, '定时任务', 2, 2, 'job', 'monitor/job/index', '', '', 1, 0, 'C', '0', '0', 'monitor:job:list', 'job', 'admin', '2026-03-27 13:52:39', '', NULL, '定时任务菜单');
INSERT INTO `sys_menu` VALUES (111, 'Sentinel控制台', 2, 3, 'http://localhost:8718', '', '', '', 0, 0, 'C', '0', '0', 'monitor:sentinel:list', 'sentinel', 'admin', '2026-03-27 13:52:39', '', NULL, '流量控制菜单');
INSERT INTO `sys_menu` VALUES (112, 'Nacos控制台', 2, 4, 'http://localhost:8848/nacos', '', '', '', 0, 0, 'C', '0', '0', 'monitor:nacos:list', 'nacos', 'admin', '2026-03-27 13:52:39', '', NULL, '服务治理菜单');
INSERT INTO `sys_menu` VALUES (113, 'Admin控制台', 2, 5, 'http://localhost:9100/login', '', '', '', 0, 0, 'C', '0', '0', 'monitor:server:list', 'server', 'admin', '2026-03-27 13:52:39', '', NULL, '服务监控菜单');
INSERT INTO `sys_menu` VALUES (114, '表单构建', 3, 1, 'build', 'tool/build/index', '', '', 1, 0, 'C', '0', '0', 'tool:build:list', 'build', 'admin', '2026-03-27 13:52:39', '', NULL, '表单构建菜单');
INSERT INTO `sys_menu` VALUES (115, '代码生成', 3, 2, 'gen', 'tool/gen/index', '', '', 1, 0, 'C', '0', '0', 'tool:gen:list', 'code', 'admin', '2026-03-27 13:52:39', '', NULL, '代码生成菜单');
INSERT INTO `sys_menu` VALUES (116, '系统接口', 3, 3, 'http://localhost:8080/swagger-ui/index.html', '', '', '', 0, 0, 'C', '0', '0', 'tool:swagger:list', 'swagger', 'admin', '2026-03-27 13:52:39', '', NULL, '系统接口菜单');
INSERT INTO `sys_menu` VALUES (500, '操作日志', 108, 1, 'operlog', 'system/operlog/index', '', '', 1, 0, 'C', '0', '0', 'system:operlog:list', 'form', 'admin', '2026-03-27 13:52:39', '', NULL, '操作日志菜单');
INSERT INTO `sys_menu` VALUES (501, '登录日志', 108, 2, 'logininfor', 'system/logininfor/index', '', '', 1, 0, 'C', '0', '0', 'system:logininfor:list', 'logininfor', 'admin', '2026-03-27 13:52:39', '', NULL, '登录日志菜单');
INSERT INTO `sys_menu` VALUES (1000, '用户查询', 100, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:query', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1001, '用户新增', 100, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:add', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1002, '用户修改', 100, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:edit', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1003, '用户删除', 100, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:remove', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1004, '用户导出', 100, 5, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:export', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1005, '用户导入', 100, 6, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:import', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1006, '重置密码', 100, 7, '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:resetPwd', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1007, '角色查询', 101, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:query', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1008, '角色新增', 101, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:add', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1009, '角色修改', 101, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:edit', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1010, '角色删除', 101, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:remove', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1011, '角色导出', 101, 5, '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:export', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1012, '菜单查询', 102, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:query', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1013, '菜单新增', 102, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:add', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1014, '菜单修改', 102, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:edit', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1015, '菜单删除', 102, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:remove', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1016, '部门查询', 103, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:query', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1017, '部门新增', 103, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:add', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1018, '部门修改', 103, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:edit', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1019, '部门删除', 103, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:remove', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1020, '岗位查询', 104, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:query', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1021, '岗位新增', 104, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:add', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1022, '岗位修改', 104, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:edit', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1023, '岗位删除', 104, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:remove', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1024, '岗位导出', 104, 5, '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:export', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1025, '字典查询', 105, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:query', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1026, '字典新增', 105, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:add', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1027, '字典修改', 105, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:edit', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1028, '字典删除', 105, 4, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:remove', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1029, '字典导出', 105, 5, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:export', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1030, '参数查询', 106, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:query', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1031, '参数新增', 106, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:add', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1032, '参数修改', 106, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:edit', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1033, '参数删除', 106, 4, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:remove', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1034, '参数导出', 106, 5, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:export', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1035, '公告查询', 107, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:query', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1036, '公告新增', 107, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:add', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1037, '公告修改', 107, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:edit', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1038, '公告删除', 107, 4, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:remove', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1039, '操作查询', 500, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:operlog:query', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1040, '操作删除', 500, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:operlog:remove', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1041, '日志导出', 500, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:operlog:export', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1042, '登录查询', 501, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:logininfor:query', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1043, '登录删除', 501, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:logininfor:remove', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1044, '日志导出', 501, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:logininfor:export', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1045, '账户解锁', 501, 4, '#', '', '', '', 1, 0, 'F', '0', '0', 'system:logininfor:unlock', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1046, '在线查询', 109, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:online:query', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1047, '批量强退', 109, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:online:batchLogout', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1048, '单条强退', 109, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:online:forceLogout', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1049, '任务查询', 110, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:query', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1050, '任务新增', 110, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:add', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1051, '任务修改', 110, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:edit', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1052, '任务删除', 110, 4, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:remove', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1053, '状态修改', 110, 5, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:changeStatus', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1054, '任务导出', 110, 6, '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:export', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1055, '生成查询', 115, 1, '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:query', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1056, '生成修改', 115, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:edit', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1057, '生成删除', 115, 3, '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:remove', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1058, '导入代码', 115, 2, '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:import', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1059, '预览代码', 115, 4, '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:preview', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1060, '生成代码', 115, 5, '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:code', '#', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2000, '设备管理', 0, 4, 'device', 'qs/device/index', NULL, 'device', 1, 0, 'C', '0', '0', '', 'clipboard', 'admin', '2026-03-27 16:12:03', 'admin', '2026-04-27 13:26:53', '');
INSERT INTO `sys_menu` VALUES (2001, '媒体节点', 0, 9, 'mediaServer', 'zlm/mediaServer/index', NULL, 'mediaServer', 1, 0, 'C', '0', '0', '', 'clipboard', 'admin', '2026-04-10 15:42:29', 'admin', '2026-04-10 15:43:14', '');
INSERT INTO `sys_menu` VALUES (2002, '云端录像', 0, 8, 'cloudRecord', 'zlm/cloudRecord/index', NULL, 'cloudRecord', 1, 0, 'C', '0', '0', '', 'date-range', 'admin', '2026-04-10 22:23:15', 'admin', '2026-04-25 13:37:21', '');
INSERT INTO `sys_menu` VALUES (2003, '录像计划', 0, 7, 'recordPlan', 'zlm/recordPlan/index', NULL, 'recordPlan', 1, 0, 'C', '0', '0', '', 'client', 'admin', '2026-04-11 22:36:33', 'admin', '2026-04-25 13:37:38', '');
INSERT INTO `sys_menu` VALUES (2004, '组织结构', 0, 6, 'common', NULL, NULL, '', 1, 0, 'M', '0', '0', NULL, 'build', 'admin', '2026-04-14 14:21:47', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2005, '行政区划', 2004, 1, 'region', 'qs/common/region', NULL, 'region', 1, 0, 'C', '0', '0', NULL, 'code', 'admin', '2026-04-14 14:22:27', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2006, '业务分组', 2004, 2, 'group', 'qs/common/group', NULL, 'group', 1, 0, 'C', '0', '0', NULL, 'dashboard', 'admin', '2026-04-14 14:23:34', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2007, '电子地图', 0, 3, 'map', 'qs/map/index', NULL, 'map', 1, 0, 'C', '0', '0', 'map', 'component', 'admin', '2026-04-15 00:45:20', 'admin', '2026-04-27 13:26:56', '');
INSERT INTO `sys_menu` VALUES (2008, '分屏监控', 0, 1, 'live', 'qs/live/index', NULL, 'live', 1, 0, 'C', '0', '0', NULL, 'button', 'admin', '2026-04-15 14:47:32', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2009, '录像回放', 0, 2, 'recordPlayback', 'zlm/recordPlayback/index', NULL, 'recordPlayback', 1, 0, 'C', '0', '0', NULL, 'education', 'admin', '2026-04-27 13:27:57', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2010, '国标级联', 0, 10, 'platform', 'qs/platform/index', NULL, 'platform', 1, 0, 'C', '0', '0', NULL, 'input', 'admin', '2026-05-13 17:59:33', '', NULL, '');

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`  (
  `notice_id` int(4) NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `notice_title` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '公告标题',
  `notice_type` char(1) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '公告类型（1通知 2公告）',
  `notice_content` longblob NULL COMMENT '公告内容',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '通知公告表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
INSERT INTO `sys_notice` VALUES (1, 'ruoyi-qs-nvr 系统', '2', 0x3C703EE59FBAE4BA8E72756F79692D636C6F7564E5BC80E58F91E79A846E7672E7B3BBE7BB9FEFBC8CE694AFE68C8172747370EFBC8C72746D70EFBC8C6F6E766966EFBC8CE6B5B7E5BAB773646BEFBC8CE6B5B7E5BAB769737570EFBC8CE5A4A7E58D8E73646BEFBC8C67623238313831EFBC8C6A74383038EFBC8C6A7431303738E7AD89E58D8FE8AEAEE68EA5E585A5EFBC8CE69BB4E69C89E69588E7AEA1E79086E79B91E68EA7E8A786E9A291E380823C2F703E, '0', 'admin', '2026-05-14 12:58:04', '', NULL, NULL);

-- ----------------------------
-- Table structure for sys_notice_read
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice_read`;
CREATE TABLE `sys_notice_read`  (
  `read_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '已读主键',
  `notice_id` int(4) NOT NULL COMMENT '公告id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `read_time` datetime NOT NULL COMMENT '阅读时间',
  PRIMARY KEY (`read_id`) USING BTREE,
  UNIQUE INDEX `uk_user_notice`(`user_id`, `notice_id`) USING BTREE COMMENT '同一用户同一公告只记录一次'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '公告已读记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_notice_read
-- ----------------------------

-- ----------------------------
-- Table structure for sys_oper_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_oper_log`;
CREATE TABLE `sys_oper_log`  (
  `oper_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '模块标题',
  `business_type` int(2) NULL DEFAULT 0 COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '请求方式',
  `operator_type` int(1) NULL DEFAULT 0 COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '操作人员',
  `dept_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '部门名称',
  `oper_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '返回参数',
  `status` int(1) NULL DEFAULT 0 COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime NULL DEFAULT NULL COMMENT '操作时间',
  `cost_time` bigint(20) NULL DEFAULT 0 COMMENT '消耗时间',
  PRIMARY KEY (`oper_id`) USING BTREE,
  INDEX `idx_sys_oper_log_bt`(`business_type`) USING BTREE,
  INDEX `idx_sys_oper_log_s`(`status`) USING BTREE,
  INDEX `idx_sys_oper_log_ot`(`oper_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1417 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '操作日志记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post`  (
  `post_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '岗位名称',
  `post_sort` int(4) NOT NULL COMMENT '显示顺序',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`post_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '岗位信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_post
-- ----------------------------
INSERT INTO `sys_post` VALUES (1, 'ceo', '董事长', 1, '0', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_post` VALUES (2, 'se', '项目经理', 2, '0', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_post` VALUES (3, 'hr', '人力资源', 3, '0', 'admin', '2026-03-27 13:52:39', '', NULL, '');
INSERT INTO `sys_post` VALUES (4, 'user', '普通员工', 4, '0', 'admin', '2026-03-27 13:52:39', '', NULL, '');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '角色权限字符串',
  `role_sort` int(4) NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `menu_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '菜单树选择项是否关联显示',
  `dept_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '部门树选择项是否关联显示',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '角色信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'admin', 1, '1', 1, 1, '0', '0', 'admin', '2026-03-27 13:52:39', '', NULL, '超级管理员');
INSERT INTO `sys_role` VALUES (2, '普通角色', 'common', 2, '2', 1, 1, '0', '0', 'admin', '2026-03-27 13:52:39', 'admin', '2026-04-15 00:42:55', '普通角色');

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept`  (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `dept_id` bigint(20) NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`role_id`, `dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '角色和部门关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------
INSERT INTO `sys_role_dept` VALUES (2, 100);
INSERT INTO `sys_role_dept` VALUES (2, 101);
INSERT INTO `sys_role_dept` VALUES (2, 105);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '角色和菜单关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `user_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户昵称',
  `user_type` varchar(2) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '00' COMMENT '用户类型（00系统用户）',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '手机号码',
  `sex` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '密码',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '0' COMMENT '账号状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `pwd_update_date` datetime NULL DEFAULT NULL COMMENT '密码最后更新时间',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '用户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 103, 'admin', '若依', '00', 'ry@163.com', '15888888888', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '192.168.81.1', '2026-05-15 23:36:03', '2026-03-27 13:52:39', 'admin', '2026-03-27 13:52:39', '', NULL, '管理员');
INSERT INTO `sys_user` VALUES (2, 105, 'ry', '若依', '00', 'ry@qq.com', '15666666666', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', '2026-03-27 13:52:39', '2026-03-27 13:52:39', 'admin', '2026-03-27 13:52:39', '', NULL, '测试员');

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `post_id` bigint(20) NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`user_id`, `post_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '用户与岗位关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_post
-- ----------------------------
INSERT INTO `sys_user_post` VALUES (1, 1);
INSERT INTO `sys_user_post` VALUES (2, 2);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '用户和角色关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (2, 2);

-- ----------------------------
-- Table structure for zlm_cloud_record
-- ----------------------------
DROP TABLE IF EXISTS `zlm_cloud_record`;
CREATE TABLE `zlm_cloud_record`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `app` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '应用名',
  `stream` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '流id',
  `call_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '健全ID',
  `start_time` bigint(20) NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` bigint(20) NULL DEFAULT NULL COMMENT '结束时间',
  `media_server_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'ZLM Id',
  `server_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '所属服务ID',
  `file_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '文件名称',
  `folder` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '文件夹',
  `file_path` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '文件路径',
  `collect` tinyint(1) NULL DEFAULT 0 COMMENT '收藏，收藏的文件不移除',
  `file_size` bigint(20) NULL DEFAULT NULL COMMENT '文件大小',
  `time_len` double NULL DEFAULT NULL COMMENT '文件时长',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 693 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '云端录像表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of zlm_cloud_record
-- ----------------------------
INSERT INTO `zlm_cloud_record` VALUES (686, 'rtmp', 'rtmp_2037455934688763904', NULL, 1777266297000, 1777266322634, 'hxkj_zlm', '000000', '2026-04-26-20-04-57-0.mp4', 'C:/Users/29290/Desktop/zlm/zlm-pro/www/record/rtmp/rtmp_2037455934688763904/', 'C:/Users/29290/Desktop/zlm/zlm-pro/www/record/rtmp/rtmp_2037455934688763904/2026-04-26/2026-04-26-20-04-57-0.mp4', 0, 7271564, 25634.000778198242, '2026-04-27 13:05:12', '2026-04-27 13:05:11');
INSERT INTO `zlm_cloud_record` VALUES (687, 'rtmp', 'rtmp_2037455934688763904', NULL, 1777266616000, 1777266625366, 'hxkj_zlm', '000000', '2026-04-26-20-10-16-0.mp4', 'C:/Users/29290/Desktop/zlm/zlm-pro/www/record/rtmp/rtmp_2037455934688763904/', 'C:/Users/29290/Desktop/zlm/zlm-pro/www/record/rtmp/rtmp_2037455934688763904/2026-04-26/2026-04-26-20-10-16-0.mp4', 0, 3056546, 9366.999626159668, '2026-04-27 13:10:21', '2026-04-27 13:10:20');
INSERT INTO `zlm_cloud_record` VALUES (688, 'rtmp', 'rtmp_2037455934688763904', NULL, 1777294892000, 1777294920132, 'hxkj_zlm', '000000', '2026-04-27-04-01-32-0.mp4', 'C:/Users/29290/Desktop/zlm/zlm-pro/www/record/rtmp/rtmp_2037455934688763904/', 'C:/Users/29290/Desktop/zlm/zlm-pro/www/record/rtmp/rtmp_2037455934688763904/2026-04-27/2026-04-27-04-01-32-0.mp4', 0, 9179042, 28132.999420166016, '2026-04-27 21:01:53', '2026-04-27 21:01:53');
INSERT INTO `zlm_cloud_record` VALUES (689, 'rtmp', 'rtmp_2037455934688763904', NULL, 1777299515000, 1777299538232, 'hxkj_zlm', '000000', '2026-04-27-05-18-35-0.mp4', 'C:/Users/29290/Desktop/zlm/zlm-pro/www/record/rtmp/rtmp_2037455934688763904/', 'C:/Users/29290/Desktop/zlm/zlm-pro/www/record/rtmp/rtmp_2037455934688763904/2026-04-27/2026-04-27-05-18-35-0.mp4', 0, 7813312, 23232.999801635742, '2026-04-27 22:18:49', '2026-04-27 22:18:49');
INSERT INTO `zlm_cloud_record` VALUES (690, 'rtmp', 'rtmp_2037455934688763904', NULL, 1777299832000, 1777299843100, 'hxkj_zlm', '000000', '2026-04-27-05-23-52-0.mp4', 'C:/Users/29290/Desktop/zlm/zlm-pro/www/record/rtmp/rtmp_2037455934688763904/', 'C:/Users/29290/Desktop/zlm/zlm-pro/www/record/rtmp/rtmp_2037455934688763904/2026-04-27/2026-04-27-05-23-52-0.mp4', 0, 2338469, 11100.000381469727, '2026-04-27 22:23:57', '2026-04-27 22:23:57');
INSERT INTO `zlm_cloud_record` VALUES (691, 'rtmp', 'rtmp_2037455934688763904', NULL, 1778850090000, 1778850127132, 'hxkj_zlm', '000000', '2026-05-15-21-01-30-0.mp4', 'C:/Users/29290/Desktop/zlm/windows/Release/www/record/rtmp/rtmp_2037455934688763904/', 'C:/Users/29290/Desktop/zlm/windows/Release/www/record/rtmp/rtmp_2037455934688763904/2026-05-15/2026-05-15-21-01-30-0.mp4', 0, 11040421, 37132.999420166016, '2026-05-15 21:01:59', '2026-05-15 21:01:59');
INSERT INTO `zlm_cloud_record` VALUES (692, 'rtmp', 'rtmp_2037455934688763904', NULL, 1778854093000, 1778854311533, 'hxkj_zlm', '000000', '2026-05-15-22-08-13-0.mp4', 'C:/Users/29290/Desktop/zlm/windows/Release/www/record/rtmp/rtmp_2037455934688763904/', 'C:/Users/29290/Desktop/zlm/windows/Release/www/record/rtmp/rtmp_2037455934688763904/2026-05-15/2026-05-15-22-08-13-0.mp4', 0, 62118086, 218533.99658203125, '2026-05-15 22:11:43', '2026-05-15 22:11:43');

-- ----------------------------
-- Table structure for zlm_media_server
-- ----------------------------
DROP TABLE IF EXISTS `zlm_media_server`;
CREATE TABLE `zlm_media_server`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'ID',
  `ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'IP',
  `hook_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'hook使用的IP（zlm访问WVP使用的IP）',
  `sdp_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'SDP IP',
  `stream_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '流IP',
  `http_port` int(11) NULL DEFAULT NULL COMMENT 'HTTP端口',
  `http_ssl_port` int(11) NULL DEFAULT NULL COMMENT 'HTTPS端口',
  `rtmp_port` int(11) NULL DEFAULT NULL COMMENT 'RTMP端口',
  `rtmp_ssl_port` int(11) NULL DEFAULT NULL COMMENT 'RTMPS端口',
  `rtp_proxy_port` int(11) NULL DEFAULT NULL COMMENT 'RTP收流端口（单端口模式有用）',
  `rtsp_port` int(11) NULL DEFAULT NULL COMMENT 'RTSP端口',
  `rtsp_ssl_port` int(11) NULL DEFAULT NULL COMMENT 'RTSPS端口',
  `flv_port` int(11) NULL DEFAULT NULL COMMENT 'flv端口',
  `flv_ssl_port` int(11) NULL DEFAULT NULL COMMENT 'https-flv端口',
  `mp4_port` int(11) NULL DEFAULT NULL COMMENT 'mp4端口',
  `mp4_ssl_port` int(11) NULL DEFAULT NULL COMMENT 'mp4端口',
  `ws_flv_port` int(11) NULL DEFAULT NULL COMMENT 'ws-flv端口',
  `ws_flv_ssl_port` int(11) NULL DEFAULT NULL COMMENT 'wss-flv端口',
  `jtt_proxy_port` int(11) NULL DEFAULT NULL COMMENT '1078收流端口（单端口模式有用）',
  `auto_config` tinyint(1) NULL DEFAULT 0 COMMENT '是否开启自动配置ZLM',
  `secret` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'ZLM鉴权参数',
  `type` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT 'zlm' COMMENT '类型： zlm/abl',
  `rtp_enable` tinyint(1) NULL DEFAULT 0 COMMENT '是否使用多端口模式',
  `rtp_port_range` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '多端口RTP收流端口范围',
  `send_rtp_port_range` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'RTP发流端口范围',
  `record_assist_port` int(11) NULL DEFAULT NULL COMMENT 'assist服务端口',
  `default_server` tinyint(1) NULL DEFAULT 0 COMMENT '是否是默认ZLM',
  `hook_alive_interval` int(11) NULL DEFAULT NULL COMMENT 'keepalive hook触发间隔,单位秒',
  `record_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '录像存储路径',
  `record_day` int(11) NULL DEFAULT 7 COMMENT '录像存储时长',
  `transcode_suffix` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '转码的前缀',
  `server_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '服务Id',
  `status` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT 'ON' COMMENT '状态(OFFLINE=离线,ON=在线)',
  `create_time` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_media_server_unique_ip_http_port`(`ip`, `http_port`, `server_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '流媒体表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of zlm_media_server
-- ----------------------------
INSERT INTO `zlm_media_server` VALUES ('hxkj_zlm', '192.168.1.200', '192.168.1.200', '192.168.1.200', '192.168.1.200', 8092, 443, 1935, 0, 10000, 554, 0, 0, 443, 0, NULL, 0, 443, 0, 1, 'hxkj_zlm', 'zlm', 1, '30500,40500', '30000,30500', 0, 1, 10, '', 3, NULL, '000000', 'ON', '2026-05-15 23:34:51.679', '2026-05-15 23:34:51.679');

-- ----------------------------
-- Table structure for zlm_record_plan
-- ----------------------------
DROP TABLE IF EXISTS `zlm_record_plan`;
CREATE TABLE `zlm_record_plan`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `snap` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '0' COMMENT '计划名称',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '开启定时截图(ENABLE/DEACTIVATE)',
  `status` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '0' COMMENT '启用(ENABLE/DEACTIVATE)',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '录像计划表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of zlm_record_plan
-- ----------------------------
INSERT INTO `zlm_record_plan` VALUES (1, 'ENABLE', '测试计划', 'ENABLE', '2026-04-27 13:14:47', '2026-05-07 09:49:08');

-- ----------------------------
-- Table structure for zlm_record_plan_item
-- ----------------------------
DROP TABLE IF EXISTS `zlm_record_plan_item`;
CREATE TABLE `zlm_record_plan_item`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `start` int(11) NULL DEFAULT NULL,
  `stop` int(11) NULL DEFAULT NULL,
  `week_day` int(11) NULL DEFAULT NULL,
  `plan_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '录像计划管理通道表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of zlm_record_plan_item
-- ----------------------------
INSERT INTO `zlm_record_plan_item` VALUES (2, 0, 0, 1, 1);

SET FOREIGN_KEY_CHECKS = 1;
