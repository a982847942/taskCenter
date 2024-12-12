package growth.tech.notification.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import growth.tech.notification.model.ActivityFissionAssistantEventDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/29 20:10
 */
@Component
@Lazy
@Slf4j
public class ActivityFissionAssistantEventProducer {
    // Event事件总线
    private EventsProducer eventsProducer;
    @Value("${activity.event.fission.assistant.topic:online_activity_fission}")
    private String topic;

    public ActivityFissionAssistantEventProducer() {
    }

    public void produceEvent(ActivityFissionAssistantEventDTO eventMessage) {
        try {
            SendResult result = eventsProducer.send(topic, JacksonKits.getObjectMapper().writeValueAsString(eventMessage));
            log.info("send activity fission assistant event to topic online_activity_fission data:{} result:{} msg key:{}", eventMessage, result.getSendStatus(), result.getMsgKey());
        } catch (JsonProcessingException e) {
            log.error("send activity fission assistant event to topic online_activity_fission error of dto {}", eventMessage, e);
        }
    }

    @PostConstruct
    public void init() {
        this.eventsProducer = new EventsProducer(topic);
        eventsProducer.start();
    }
}
