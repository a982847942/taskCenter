package edu.muying.component.useraction.entity;

import java.time.LocalDateTime;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/29 12:40
 */
//@Data
//@TableName(value = "`melody`.`component_config`", autoResultMap = true)
public class ComponentConfigDO {

    /** PK */
//    @TableId(type = IdType.AUTO)
    private Long id;

    /** 活动id */
//    @TableField(value = "activity_id")
    private String activityId;

    /** 构件类型 */
//    @TableField(value = "component_type")
    private String componentType;

    /** 具体构件配置内容，JSON格式 */
//    @TableField(value = "config_json")
    private String configJson;

    /** 数据创建时间 */
//    @TableField(value = "create_time", insertStrategy = FieldStrategy.NEVER, updateStrategy = FieldStrategy.NEVER)
    private LocalDateTime createTime;

    /** 数据修改时间 */
//    @TableField(value = "update_time", insertStrategy = FieldStrategy.NEVER, updateStrategy = FieldStrategy.NEVER)
    private LocalDateTime updateTime;

    /** 逻辑删除。true为被删除 */
//    @TableField(value = "delete_flag")
    private boolean deleteFlag;

}