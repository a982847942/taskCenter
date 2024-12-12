package growth.bach.instance.engine.task.muyingdati.condition;

import growth.bach.common.lang.spi.Identity;
import growth.bach.instance.engine.task.meta.BasePromoteCondition;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 单人答题推进条件
 * @author brain
 * @version 1.0
 * @date 2024/11/1 16:16
 */
@Identity("muyingSingleAnswer")
@Data
public class SingleAnswerPromoteCondition extends BasePromoteCondition {
    /**
     * 每次推进步长
     */
    private BigDecimal step;

    /**
     * 每次答对发放奖励数量
     */
    private Integer prizeQuantity;

    /**
     * 给前端展示的积分上限
     */
    private Integer limitShowing;
}
