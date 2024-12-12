package growth.bach.instance.engine.task.meta.condition.success;

import growth.bach.common.lang.spi.Identity;
import growth.bach.instance.engine.task.meta.BaseSuccessCondition;
import lombok.Data;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/1 15:58
 */
@Data
@Identity("default")
public class DefaultSuccessCondition extends BaseSuccessCondition {
    /**
     * 成功后发奖的数量
     * 用于一些简单的成功后发奖的任务，更复杂的发奖规则需要额外字段的，请实现新的子类
     */
    private Integer prizeQuantity;
}
