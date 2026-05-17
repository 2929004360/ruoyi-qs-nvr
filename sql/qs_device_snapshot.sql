
-- ----------------------------
-- 设备抓图表
-- ----------------------------
DROP TABLE IF EXISTS `qs_device_snapshot`;
CREATE TABLE `qs_device_snapshot` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `device_id` bigint(20) NOT NULL COMMENT '关联设备ID',
  `device_code` varchar(128) DEFAULT NULL COMMENT '设备编号',
  `device_name` varchar(256) DEFAULT NULL COMMENT '设备名称',
  `file_url` varchar(1024) NOT NULL COMMENT '图片访问地址',
  `file_path` varchar(1024) DEFAULT NULL COMMENT '图片存储路径',
  `file_size` bigint(20) DEFAULT NULL COMMENT '文件大小(字节)',
  `file_name` varchar(256) DEFAULT NULL COMMENT '文件名称',
  `file_type` varchar(64) DEFAULT NULL COMMENT '文件类型(jpg/png等)',
  `snapshot_type` varchar(64) DEFAULT NULL COMMENT '抓图类型(MANUAL-手动, ALARM-报警, SCHEDULE-定时, PREVIEW-预览)',
  `sdk_type` varchar(64) DEFAULT NULL COMMENT 'SDK类型(HIK-海康,DAHUA-大华,UNIVIEW-宇视,TIANDY-天地伟业,GB28181-国标,OTHER-其他)',
  `channel` int(11) DEFAULT NULL COMMENT '通道号',
  `capture_time` datetime DEFAULT NULL COMMENT '抓图时间',
  `width` int(11) DEFAULT NULL COMMENT '图片宽度',
  `height` int(11) DEFAULT NULL COMMENT '图片高度',
  `latitude` decimal(10,6) DEFAULT NULL COMMENT '纬度',
  `longitude` decimal(10,6) DEFAULT NULL COMMENT '经度',
  `remark` varchar(512) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_device_id` (`device_id`),
  KEY `idx_device_code` (`device_code`),
  KEY `idx_capture_time` (`capture_time`),
  KEY `idx_sdk_type` (`sdk_type`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='设备抓图表';

