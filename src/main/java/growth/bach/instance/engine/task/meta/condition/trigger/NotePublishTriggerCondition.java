package growth.bach.instance.engine.task.meta.condition.trigger;

import growth.bach.common.lang.spi.Identity;
import growth.bach.instance.engine.event.UniqEvent;
import growth.bach.instance.engine.event.content.NoteChange;
import growth.bach.instance.engine.event.reg.TriggerCondition;
import growth.bach.instance.enums.EventSource;
import growth.bach.instance.enums.EventType;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * 笔记发布触发条件
 * @author brain
 * @version 1.0
 * @date 2024/11/1 16:01
 */
@Slf4j
@Data
@Identity("note_publish_trigger")
public class NotePublishTriggerCondition implements TriggerCondition<NoteChange> {
    private Set<String> topicSet;
    private String userId;
    @Override
    public String getSource() {
        return EventSource.FE.name();
    }

    @Override
    public String getEventType() {
        return EventType.NOTE_CHANGE.name();
    }

    @Override
    public boolean satisfy(UniqEvent<NoteChange> event) {
        if (event.getContent() == null || CollectionUtils.isEmpty(event.getContent().getTagIdList())){
            return false;
        }
        boolean matchTopic = event.getContent().getTagIdList().stream().anyMatch(it -> topicSet.contains(it));
        return matchTopic && StringUtils.equals(userId, event.getUserId());
    }

    @Override
    public LocalDateTime getExpireTime() {
        return null;
    }
}
