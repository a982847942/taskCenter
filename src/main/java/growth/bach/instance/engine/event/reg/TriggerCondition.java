package growth.bach.instance.engine.event.reg;

import growth.bach.instance.engine.event.UniqEvent;

import java.time.LocalDateTime;

/**
 * 事件命中条件（任务由对应的事件推进）
 * @author brain
 * @version 1.0
 * @date 2024/11/1 10:38
 */
public interface TriggerCondition<S> {
    /**
     * 事件来源
     * @return
     */
    String getSource();

    /**
     * 限制一次注册只关心一种事件
     * @return
     */
    String getEventType();

    /**
     * 事件的主体用户ID
     * @return
     */
    String getUserId();

    void setUserId(String userId);

    /**
     * 判断事件内容是否命中条件
     * @param event
     * @return
     */
    boolean satisfy(UniqEvent<S> event);

    /**
     * 获取失效时间
     * @return
     */
    LocalDateTime getExpireTime();
}
