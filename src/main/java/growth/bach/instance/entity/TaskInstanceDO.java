package growth.bach.instance.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 任务实例
 * @author brain
 * @version 1.0
 * @date 2024/11/1 11:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskInstanceDO {
    private Long id;
    private Long taskMetaId;
    /**
     * 任务类型 冗余自{@link TaskMetaDO}
     * @see growth.bach.instance.enums.TaskType
     */
    private String taskType;

    private String taskName;

    /**
     * 任务状态 {@link growth.bach.instance.enums.TaskInstanceStatus}
     */
    private Integer taskInstanceStatus;

    /**
     * 活动ID
     */
    private String activityId;
    private String userId;
    /**
     * 计数器 按需使用
     */
    private Double counts;
    /**
     * 总进度 百分制
     */
    private BigDecimal progress;

    /**
     *是否保存配置快照（创建实例时的taskMeta）
     */
    private Boolean saveSnapshot;
    /**
     * 配置快照
     */
    private JSONObject snapShot;
    /**
     * 过期时间
     */
    private LocalDateTime expireTime;
    /**
     * 扩展信息
     */
    private JSONObject extra;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private boolean deleteFlag;

}
