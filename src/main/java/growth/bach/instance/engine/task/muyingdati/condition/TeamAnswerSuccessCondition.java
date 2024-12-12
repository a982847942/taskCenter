package growth.bach.instance.engine.task.muyingdati.condition;

import growth.bach.common.lang.spi.Identity;
import growth.bach.instance.engine.task.meta.BaseSuccessCondition;
import lombok.Data;

/**
 * 组队答题成功条件
 * @author brain
 * @version 1.0
 * @date 2024/11/1 16:17
 */
@Data
@Identity("muyingTeamAnswer")
public class TeamAnswerSuccessCondition extends BaseSuccessCondition {
    /**
     * 最小平均分
     */
    private Double minAverageSource;
    /**
     * 每人发放值
     */
    private Integer quantity;
    /**
     * 每题分值
     */
    private Double unitScore;
}
