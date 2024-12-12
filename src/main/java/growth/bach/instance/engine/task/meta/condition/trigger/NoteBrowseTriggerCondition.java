package growth.bach.instance.engine.task.meta.condition.trigger;

import growth.bach.common.lang.spi.Identity;
import growth.bach.instance.engine.event.UniqEvent;
import growth.bach.instance.engine.event.reg.TriggerCondition;
import growth.bach.instance.enums.EventSource;
import growth.bach.instance.enums.EventType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

/**
 * 笔记浏览触发条件
 * @author brain
 * @version 1.0
 * @date 2024/11/1 16:01
 */
@Data
@Identity("note_browse_trigger")
public class NoteBrowseTriggerCondition implements TriggerCondition<Map<String, String>> {
    private String userId;
    private Set<String> pageIdSet;
    @Override
    public String getSource() {
        return EventSource.FE.name();
    }

    @Override
    public String getEventType() {
        return EventType.NOTE_BROWSE.name();
    }

    @Override
    public boolean satisfy(UniqEvent<Map<String, String>> event) {
        return true;
    }

    @Override
    public LocalDateTime getExpireTime() {
        return null;
    }
}
