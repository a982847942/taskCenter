package edu.muying.component.useraction.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import edu.muying.component.loader.ComponentConfigLoader;
import edu.muying.component.useraction.entity.ComponentConfigDO;

import javax.annotation.Resource;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/29 12:35
 */
public class ComponentConfigService {
    @Resource
    private ComponentConfigLoader componentConfigLoader;

    /**
     * 获取构件配置
     *
     * @param activityId    活动id
     * @param componentType 构件类型
     * @param configClass   配置的class
     * @return 构件配置
     * @param <C> 构件配置类型
     */
    public <C> C getConfig(String activityId, ComponentTypeEnum componentType, Class<C> configClass) {
        ComponentConfigDO configDO = componentConfigLoader.getConfigByActivityIdAndComponentType(activityId,
                componentType);
        if (configDO == null) {
            return null;
        }

        return JSON.parseObject(configDO.getConfigJson(), configClass);
    }

    /**
     * 获取构件配置
     *
     * @param activityId    活动id
     * @param componentType 构件类型
     * @param configTypeRef 配置的TypeReference
     * @return 构件配置
     * @param <C> 构件配置类型
     */
    public <C> C getConfig(String activityId, ComponentTypeEnum componentType, TypeReference<C> configTypeRef) {
        ComponentConfigDO configDO = componentConfigLoader.getConfigByActivityIdAndComponentType(activityId,
                componentType);
        if (configDO == null) {
            return null;
        }

        return JSON.parseObject(configDO.getConfigJson(), configTypeRef);
    }
}
