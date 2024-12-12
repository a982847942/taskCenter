package edu.muying.component.useraction.repository;

import edu.muying.component.useraction.entity.UserEntityDO;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/29 12:14
 */
public class UserEntityRepository {
//    @Resource
//    private UserEntityMapper userEntityMapper;

    /**
     * 根据 活动Id、实体类型、userId，查询一条数据
     */
    public UserEntityDO selectByActivityIdEntityTypeUserId(String activityId, String entityType, String userId) {
//        QueryWrapper<UserEntityDO> qw = new QueryWrapper<>();
//        qw.eq("activity_id", activityId);
//        qw.eq("entity_type", entityType);
//        qw.eq("user_id", userId);
//        qw.eq("delete_flag", false);
//        return userEntityMapper.selectOne(qw);
        return null;
    }

    /**
     * 根据 活动Id、实体类型、userId，锁一条数据
     */
    public UserEntityDO lockByActivityIdEntityTypeUserId(String activityId, String entityType, String userId) {
//        QueryWrapper<UserEntityDO> qw = new QueryWrapper<>();
//        qw.eq("activity_id", activityId);
//        qw.eq("entity_type", entityType);
//        qw.eq("user_id", userId);
//        qw.eq("delete_flag", false);
//        qw.last("FOR UPDATE");
//        return userEntityMapper.selectOne(qw);
        return null;
    }

    /**
     * 插入一条数据
     */
    public void insert(UserEntityDO entityDO) {
//        if (entityDO == null) {
//            return;
//        }
//        userEntityMapper.insert(entityDO);
    }

    /**
     * 修改一条数据
     */
    public void update(UserEntityDO entityDO) {
//        if (entityDO == null) {
//            return;
//        }
//        UpdateWrapper<UserEntityDO> uw = new UpdateWrapper<>();
//        uw.eq("activity_id", entityDO.getActivityId());
//        uw.eq("entity_type", entityDO.getEntityType());
//        uw.eq("user_id", entityDO.getUserId());
//
//        uw.set("content", entityDO.getContent());
//        userEntityMapper.update(entityDO, uw);
    }
}
