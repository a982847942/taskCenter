CREATE TABLE `event_task_register_00`
(
    `id`                    bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `event_type`            varchar(128) COLLATE utf8mb4_bin NOT NULL COMMENT '事件类型',
    `source`                varchar(128) COLLATE utf8mb4_bin NOT NULL COMMENT '事件来源',
    `user_id`               varchar(64) COLLATE utf8mb4_bin  NOT NULL COMMENT '用户id',
    `expire_time`           datetime                                  DEFAULT NULL COMMENT '过期时间',
    `condition_identity`    varchar(128) COLLATE utf8mb4_bin NOT NULL COMMENT '条件唯一标识',
    `custom_condition_data` json                                      DEFAULT NULL COMMENT '自定义匹配条件数据',
    `task_instance_id`      bigint(20) NOT NULL COMMENT '事件对应的任务实例ID',
    `trigger_data`          json                                      DEFAULT NULL COMMENT '自定义回传数据',
    `delete_flag`           tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除',
    `create_time`           datetime                         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据创建时间',
    `update_time`           datetime                         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据修改时间',
    PRIMARY KEY (`id`),
    KEY                     `idx_user_event` (`user_id`,`event_type`,`source`),
    KEY                     `idx_update_time` (`update_time`)
) ENGINE=InnoDB AUTO_INCREMENT=2583 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='任务事件注册表'