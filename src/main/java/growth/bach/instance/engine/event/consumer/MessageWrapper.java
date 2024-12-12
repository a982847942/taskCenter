package growth.bach.instance.engine.event.consumer;

import growth.bach.instance.engine.event.consumer.ext.MessageExt;
import growth.bach.instance.enums.EventSource;
import growth.bach.instance.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/2 13:48
 */
@AllArgsConstructor
@Data
public class MessageWrapper {
    private EventSource source;
    private EventType eventType;
    private MessageExt rawMsg;
}
