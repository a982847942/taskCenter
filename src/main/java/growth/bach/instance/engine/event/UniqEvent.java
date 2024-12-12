package growth.bach.instance.engine.event;

import lombok.Data;

/**
 * 统一事件界面
 * @author brain
 * @version 1.0
 * @date 2024/11/1 10:43
 */
@Data
public class UniqEvent<T> {
    /**
     * 事件类型
     */
    private String eventType;

    /**
     * 事件来源
     */
    private String source;

    /**
     * 事件主userId
     */
    private String userId;
    /**
     * 事件内容
     */
    private T content;
}
