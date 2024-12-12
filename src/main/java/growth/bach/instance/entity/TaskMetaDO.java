package growth.bach.instance.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/31 22:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskMetaDO {
    private Long id;
    private String activityId;
    private String taskType;
    private String taskName;
    private Integer taskStatus;
    private Boolean saveSnapshot;
    private String createConditionId;
    private JSONObject taskCreateCondition;
    private String enterConditionId;
    private JSONObject taskEnterCondition;
    private String promoteConditionId;
    private JSONObject taskPromoteCondition;
    private String successConditionId;
    private JSONObject taskSuccessCondition;
    /**
     * 活动失效时间
     */
    private LocalDateTime activityExpireTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private boolean deleteFlag;

}
