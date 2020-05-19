/*
Navicat MySQL Data Transfer

Source Server         : 192.168.16.11
Source Server Version : 50505
Source Host           : 192.168.16.11:3306
Source Database       : frameworkv3

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2018-10-22 15:20:58
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_sys_quartz_job
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_quartz_job`;
CREATE TABLE `t_sys_quartz_job` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `job_name` varchar(200) NOT NULL COMMENT '任务名',
  `job_group` varchar(200) NOT NULL COMMENT '任务组',
  `interval_time` int(11) DEFAULT NULL COMMENT '间隔时间，单位S',
  `job_status` int(1) DEFAULT NULL COMMENT '任务状态 0:停用，1:启用',
  `trigger_type` char(1) DEFAULT '' COMMENT '0:按时间间隔触发, 1:按指定时间调度',
  `cron_expression` varchar(250) DEFAULT NULL COMMENT 'cron表达式',
  `job_class_name` varchar(250) DEFAULT NULL COMMENT '任务全路径',
  `job_description` varchar(250) DEFAULT NULL COMMENT '任务描述',
  `executing_host_name` varchar(200) DEFAULT NULL COMMENT 'job运行主机',
  `job_data` blob DEFAULT NULL COMMENT '一个blob字段，存放持久化job对象 ',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(36) DEFAULT '' COMMENT '创建人',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user` varchar(36) DEFAULT '' COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DELETE from `t_dictionary_type` WHERE DICT_TYPE = 'S002';
DELETE from `t_dictionary_type` WHERE DICT_TYPE = 'S003';

DELETE from `t_dictionary` WHERE DICT_TYPE = 'S002';
DELETE from `t_dictionary` WHERE DICT_TYPE = 'S003';

INSERT INTO `t_dictionary_type` (`id`, `dict_type`, `parent_type`, `dict_flag`, `ch_desc`, `en_desc`, `status`, `remark`, `create_user`, `create_date`, `update_user`, `update_date`) VALUES ('d5b1ad1a-af66-44d5-8b8e-c74691fe0e71', 'S003', NULL, '0', '触发类型', NULL, '0', NULL, 'admin', '2018-10-22 15:17:48', 'admin', '2018-10-22 15:17:57');
INSERT INTO `t_dictionary` (`id`, `dict_type`, `dict_key`, `dict_value`, `dict_en_value`, `dict_desc`, `del_flag`, `locked_flag`, `create_user`, `create_date`, `update_user`, `update_date`, `dict_order`) VALUES ('fbf9c5e08c284dce99c2f3dc4916178f', 'S003', '0', '按时间间隔触发', NULL, '按时间间隔触发', '0', '0', 'admin', '2018-10-22 15:25:47', 'admin', '2018-10-22 15:25:54', '1');
INSERT INTO  `t_dictionary` (`id`, `dict_type`, `dict_key`, `dict_value`, `dict_en_value`, `dict_desc`, `del_flag`, `locked_flag`, `create_user`, `create_date`, `update_user`, `update_date`, `dict_order`) VALUES ('fbf9c5e08c284dce99c2f3dc4916179f', 'S003', '1', '按指定时间调度', NULL, '按指定时间调度', '0', '0', 'admin', '2018-10-22 15:26:47', 'admin', '2018-10-22 15:26:55', '2');

INSERT INTO  `t_dictionary_type` (`id`, `dict_type`, `parent_type`, `dict_flag`, `ch_desc`, `en_desc`, `status`, `remark`, `create_user`, `create_date`, `update_user`, `update_date`) VALUES ('d5b1ad1a-af66-44d5-8b8e-c74691fe0e8c', 'S002', NULL, '0', '任务状态', NULL, '0', '任务状态', 'admin', '2018-10-22 15:29:44', 'admin', '2018-10-22 15:29:52');
INSERT INTO  `t_dictionary` (`id`, `dict_type`, `dict_key`, `dict_value`, `dict_en_value`, `dict_desc`, `del_flag`, `locked_flag`, `create_user`, `create_date`, `update_user`, `update_date`, `dict_order`) VALUES ('fbf8c5e08c284dce99c2f3dc4916179f', 'S002', '0', '停用', NULL, '停用', '0', '0', 'admin', '2018-10-22 15:32:08', 'admin', '2018-10-22 15:32:13', '1');
INSERT INTO  `t_dictionary` (`id`, `dict_type`, `dict_key`, `dict_value`, `dict_en_value`, `dict_desc`, `del_flag`, `locked_flag`, `create_user`, `create_date`, `update_user`, `update_date`, `dict_order`) VALUES ('fbf8c5e08c284dce99c2f3dc4998179f', 'S002', '1', '启用', NULL, '启用', '0', '0', 'admin', '2018-10-22 15:32:55', 'admin', '2018-10-22 15:33:00', '2');


