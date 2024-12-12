package edu.muying.component.loader;

import edu.common.exception.SystemLogicException;
import edu.muying.component.useraction.entity.ComponentConfigDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/29 12:32
 */
@Slf4j
public class ComponentConfigLoader implements InitializingBean {
    /** 缓存时间 */
    // todo 上线前改成 300
    private static final int CACHE_SECONDS = 10;

    /** 下一次捞取DB的时间 */
    private LocalDateTime nextUpdateTime = LocalDateTime.now();

    /** 用原子boolean控制状态 */
    private final AtomicBoolean loading = new AtomicBoolean(false);

    /**
     * 构件配置map
     * key => activityId
     */
    private Map<String, List<ComponentConfigDO>> componentConfigMap = new HashMap<>();

    @Resource
    private ComponentConfigRepository componentConfigRepository;

    @Override
    public void afterPropertiesSet() {
        load();
    }

    /**
     * 加载缓存
     */
    private void load() {
        try {
            log.info("component_config load start >>>>>");
            nextUpdateTime = LocalDateTime.now().plusSeconds(CACHE_SECONDS);

            // 捞取全部配置
            List<ComponentConfigDO> allConfig = componentConfigRepository.selectAll();
            if (CollectionUtils.isEmpty(allConfig)) {
                log.info("component_config list empty");
                return;
            }

            componentConfigMap = allConfig.stream().collect(Collectors.groupingBy(ComponentConfigDO::getActivityId));
            log.info("component_config load finish, allConfig size:{}, map size:{}, nextUpdateTime:{}",
                    allConfig.size(), componentConfigMap.size(), nextUpdateTime);
        } catch (Throwable e) {
            log.error("component_config load error.", e);
            throw new SystemLogicException("component_config load error.");
        } finally {
            // 释放状态
            loading.set(false);
        }
    }

    /**
     * 缓存刷新重新加载
     */
    private void reload() {
        // 防止缓存击穿
        if (loading.compareAndSet(false, true)) {
            try {
                load();
            } catch (Throwable e) {
                // 线程启动异常 需要释放锁
                loading.set(false);
                log.error("component_config reload error.", e);
            }
        }
    }

    /**
     * 获取 某个活动下所有构件配置
     */
    public List<ComponentConfigDO> getConfigListByActivityId(String activityId) {
        if (LocalDateTime.now().isAfter(nextUpdateTime)) {
            reload();
        }
        if (MapUtils.isEmpty(componentConfigMap)){
            return null;
        }

        return componentConfigMap.get(activityId);
    }

    /**
     * 根据 活动id + 构件类型 获取构件配置
     */
    public ComponentConfigDO getConfigByActivityIdAndComponentType(String activityId, ComponentTypeEnum componentType) {
        List<ComponentConfigDO> configList = getConfigListByActivityId(activityId);
        if (CollectionUtils.isEmpty(configList)) {
            return null;
        }
        return configList.stream()
                .filter(config -> StringUtils.equals(config.getComponentType(), componentType.name()))
                .findFirst()
                .orElse(null);
    }
}
