package edu.muying.component.useraction.service;

import com.alibaba.fastjson2.JSON;
import edu.muying.component.useraction.entity.UserEntityDO;
import edu.muying.component.useraction.repository.UserEntityRepository;

import javax.annotation.Resource;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/29 12:09
 */
public class UserEntityService {
    @Resource
    private UserEntityRepository userEntityRepository;

    /**
     * 查询一条 活动用户实体 数据
     *
     * @param activityId  activityId
     * @param userId      userId
     * @return 活动用户实体
     */
    public <E> E find(String activityId, String userId, Class<E> entityClass) {
        UserEntityDO entity = userEntityRepository.selectByActivityIdEntityTypeUserId(activityId,
                entityClass.getSimpleName(), userId);
        if (entity == null) {
            return null;
        }
        return JSON.parseObject(entity.getContent(), entityClass);
    }

    /**
     * 查询一条 活动用户实体 数据
     *
     * @param activityId  activityId
     * @param userId      userId
     * @param entityClass 实体类型class
     * @return 活动用户实体
     */
    public <E> E lock(String activityId, String userId, Class<E> entityClass) {
        UserEntityDO entity = userEntityRepository.lockByActivityIdEntityTypeUserId(activityId,
                entityClass.getSimpleName(), userId);
        if (entity == null) {
            return null;
        }
        return JSON.parseObject(entity.getContent(), entityClass);
    }

    /**
     * 插入 活动用户实体
     *
     * @param activityId activityId
     * @param userId     userId
     * @param entity     实体数据对象
     */
    public void insert(String activityId, String userId, Object entity) {
        String entityType = entity.getClass().getSimpleName();

        UserEntityDO entityDO = new UserEntityDO();
        entityDO.setActivityId(activityId);
        entityDO.setEntityType(entityType);
        entityDO.setUserId(userId);
        entityDO.setContent(JSON.toJSONString(entity));
        entityDO.setDeleteFlag(false);
        userEntityRepository.insert(entityDO);
    }

    /**
     * 更新 活动用户实体
     *
     * @param activityId activityId
     * @param userId     userId
     * @param entity     实体数据对象
     */
    public void update(String activityId, String userId, Object entity) {
        String entityType = entity.getClass().getSimpleName();

        UserEntityDO entityDO = new UserEntityDO();
        entityDO.setActivityId(activityId);
        entityDO.setEntityType(entityType);
        entityDO.setUserId(userId);
        entityDO.setContent(JSON.toJSONString(entity));
        userEntityRepository.update(entityDO);
    }
}



