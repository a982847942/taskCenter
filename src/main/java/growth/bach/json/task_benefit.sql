CREATE TABLE `task_benefit`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `task_meta_id`  bigint(20) NOT NULL COMMENT '任务配置ID',
    `benefit_id`    bigint(20) NOT NULL COMMENT '奖品ID',
    `scene_key`     varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '场景Key',
    `customize_key` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '自定义Key',
    `rule`          json                             DEFAULT NULL COMMENT '发奖规则',
    `create_time`   datetime NOT NULL                DEFAULT CURRENT_TIMESTAMP COMMENT '数据创建时间',
    `update_time`   datetime NOT NULL                DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据修改时间',
    `delete_flag`   tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除,0-使用中, 1-被删除',
    PRIMARY KEY (`id`),
    KEY             `idx_aid` (`task_meta_id`,`scene_key`,`customize_key`,`benefit_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1805 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='任务奖品关联表'