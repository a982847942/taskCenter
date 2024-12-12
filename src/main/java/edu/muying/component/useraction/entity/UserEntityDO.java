package edu.muying.component.useraction.entity;

import java.time.LocalDateTime;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/29 12:13
 */
//@Data
//@TableName(value = "`melody`.`user_entity`", autoResultMap = true)
public class UserEntityDO {

    /** PK */
//    @TableId(type = IdType.AUTO)
    private String id;

    /** 活动id */
//    @TableField(value = "activity_id")
    private String activityId;

    /** 用户id */
//    @TableField(value = "user_id")
    private String userId;

    /** 实体类型。对应Java POJO类名，一个用户在一个活动下可能多个实体 */
//    @TableField(value = "entity_type")
    private String entityType;

    /** 用户活动信息。JSON格式 */
//    @TableField(value = "content")
    private String content;

    /** 数据创建时间 */
//    @TableField(value = "create_time", insertStrategy = FieldStrategy.NEVER, updateStrategy = FieldStrategy.NEVER)
    private LocalDateTime createTime;

    /** 数据修改时间 */
//    @TableField(value = "update_time", insertStrategy = FieldStrategy.NEVER, updateStrategy = FieldStrategy.NEVER)
    private LocalDateTime updateTime;

    /** 逻辑删除。true为被删除 */
//    @TableField(value = "delete_flag")
    private boolean deleteFlag;

    public UserEntityDO() {
    }

    public UserEntityDO(String id, String activityId, String userId, String entityType, String content, LocalDateTime createTime, LocalDateTime updateTime, boolean deleteFlag) {
        this.id = id;
        this.activityId = activityId;
        this.userId = userId;
        this.entityType = entityType;
        this.content = content;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.deleteFlag = deleteFlag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    @Override
    public String toString() {
        return "UserEntityDO{" +
                "id='" + id + '\'' +
                ", activityId='" + activityId + '\'' +
                ", userId='" + userId + '\'' +
                ", entityType='" + entityType + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", deleteFlag=" + deleteFlag +
                '}';
    }
}
