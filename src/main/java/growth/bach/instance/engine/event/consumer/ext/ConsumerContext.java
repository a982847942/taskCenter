package growth.bach.instance.engine.event.consumer.ext;

import lombok.Data;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/2 14:29
 */
@Data
public class ConsumerContext {
    private String topic;
    private String group;
    private String qid;
}
