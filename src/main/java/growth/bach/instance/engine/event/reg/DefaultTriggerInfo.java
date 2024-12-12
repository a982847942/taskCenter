package growth.bach.instance.engine.event.reg;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/1 14:36
 */
@AllArgsConstructor
@NoArgsConstructor
public class DefaultTriggerInfo implements TriggerInfo {
    private Long taskInstanceId;

    @Override
    public Long getTaskInstanceId() {
        return taskInstanceId;
    }

}
