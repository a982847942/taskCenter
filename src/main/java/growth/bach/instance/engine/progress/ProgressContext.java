package growth.bach.instance.engine.progress;

import growth.bach.instance.engine.event.UniqEvent;
import lombok.Data;

/**
 * 事件推动上下文
 * @author brain
 * @version 1.0
 * @date 2024/11/1 14:28
 */
@Data
public class ProgressContext {
    private UniqEvent event;
}
