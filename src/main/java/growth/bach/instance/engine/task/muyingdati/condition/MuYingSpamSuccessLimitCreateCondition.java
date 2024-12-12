package growth.bach.instance.engine.task.muyingdati.condition;

import growth.bach.common.lang.spi.Identity;
import growth.bach.instance.engine.task.meta.condition.create.SuccessLimitCreateCondition;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/1 16:17
 */
@Slf4j
@Identity("muYingSpamSuccessLimit")
public class MuYingSpamSuccessLimitCreateCondition extends SuccessLimitCreateCondition {
    /**
     * 任务最多能获得的奖励数
     */
    @Getter
    @Setter
    private String taskMaxBenefitQuantity;

    @Override
    public Map<String, String> getSpamParams(String userId) {
        // 子类风控需要额外的参数
        return super.getSpamParams(userId);
    }
}
