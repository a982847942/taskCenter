package growth.bach.instance.engine.task.muyingdati.condition;

import growth.bach.common.lang.spi.Identity;
import growth.bach.instance.engine.event.UniqEvent;
import growth.bach.instance.engine.event.reg.TriggerCondition;
import growth.bach.instance.engine.task.meta.condition.create.SuccessLimitCreateCondition;
import growth.bach.instance.engine.task.muyingdati.event.TeamAnswerResult;
import growth.bach.instance.enums.EventSource;
import growth.bach.instance.enums.EventType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

/**
 * 组队答题触发条件
 * @author brain
 * @version 1.0
 * @date 2024/11/1 16:18
 */

@Data
@Identity("team_answer_trigger")
public class TeamAnswerTriggerCondition implements TriggerCondition<TeamAnswerResult> {
    private String userId;

    @Override
    public String getSource() {
        return EventSource.FE.name();
    }

    @Override
    public String getEventType() {
        return EventType.MUYING_TEAM_ANSWER_CORRECT.name();
    }


    @Override
    public boolean satisfy(UniqEvent<TeamAnswerResult> event) {
        return event.getUserId().equals(userId);
    }

    @Override
    public LocalDateTime getExpireTime() {
        LocalTime endDay = LocalTime.of(23, 59, 59);
        LocalDate today = LocalDate.now();
        return LocalDateTime.of(today, endDay);
    }
}
