package growth.bach.instance.engine.task.muyingdati.condition;

import growth.bach.common.lang.spi.Identity;
import growth.bach.instance.engine.event.UniqEvent;
import growth.bach.instance.engine.event.reg.TriggerCondition;
import growth.bach.instance.enums.EventSource;
import growth.bach.instance.enums.EventType;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 单人答题任务触发条件
 * @author brain
 * @version 1.0
 * @date 2024/11/1 14:42
 */
@Data
@Identity("single_answer_trigger")
public class SingleAnswerTriggerCondition implements TriggerCondition<Boolean> {
    private String userId;
    @Override
    public String getSource() {
        return EventSource.FE.name();
    }

    @Override
    public String getEventType() {
        return EventType.MUYING_ANSWER_CORRECT.name();
    }

    @Override
    public boolean satisfy(UniqEvent<Boolean> event) {
        return StringUtils.equals(userId, event.getUserId());
    }

    @Override
    public LocalDateTime getExpireTime() {
        LocalTime endDay = LocalTime.of(23, 59, 59);
        LocalDate today = LocalDate.now();
        return LocalDateTime.of(today, endDay);
    }
}
