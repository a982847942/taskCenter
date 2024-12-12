package growth.bach.instance.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 事件任务注册表
 * @author brain
 * @version 1.0
 * @date 2024/11/1 15:21
 */
@Data
public class EventRegistrarDO {
    private Long id;
    /**
     * 事件类型
     */
    private String eventType;
    /**
     * 事件来源
     */
    private String source;

    /**
     * 事件主题用户ID
     */
    private String userId;
    /**
     * 过期时间
     */
    private LocalDateTime expireTime;
    /**
     * 条件唯一标识
     */
    private String conditionIdentity;
    /**
     * 自定义匹配条件数据
     */
    private JSONObject customConditionData;

    /**
     * 事件对应的任务实例ID
     */
    private Long taskInstanceId;

    /**
     * 自定义回传数据
     */
    private JSONObject triggerData;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Boolean deleteFlag;

}
