package growth.bach.instance.engine.task.meta;

import com.alibaba.fastjson.JSONObject;
import growth.bach.instance.engine.event.reg.TriggerCondition;
import growth.bach.instance.engine.parser.JsonParser;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/31 23:04
 */
public abstract class BasePromoteCondition {
    @Setter
    private String triggerConditionId;
    @Setter
    private JSONObject triggerCondition;

    public TriggerCondition getTriggerCondition() {
        if (triggerCondition == null || StringUtils.isEmpty(triggerConditionId)){
            return null;
        }
        return JsonParser.TRIGGER_CONDITION.parse(triggerCondition, triggerConditionId);
    }
}
