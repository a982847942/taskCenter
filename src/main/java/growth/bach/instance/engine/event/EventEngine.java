package growth.bach.instance.engine.event;

import growth.bach.instance.engine.event.reg.DefaultTriggerInfo;
import growth.bach.instance.engine.event.reg.EventRegistrar;
import growth.bach.instance.engine.event.reg.TriggerCondition;
import growth.bach.instance.engine.event.reg.TriggerInfo;
import growth.bach.instance.engine.progress.ProgressEngine;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/1 14:34
 */
public class EventEngine {
    private EventRegistrar eventRegistrar;

    private ProgressEngine progressEngine;

    /**
     * 注册兴趣点
     *
     * @param triggerCondition 被触发的条件
     * @param triggerInfo      触发时回传的信息
     * @param expireTime       超时时间
     */
    public void register(TriggerCondition triggerCondition, DefaultTriggerInfo triggerInfo, LocalDateTime expireTime) {
        eventRegistrar.register(triggerCondition, triggerInfo, expireTime);
    }

    /**
     * 异步触发事件
     * TODO 引入线程池
     *
     * @param uniqEvent
     */
    public void triggerAsync(UniqEvent uniqEvent) {
        for (TriggerInfo info : filter(uniqEvent)) {
            progressEngine.progressByEvent(info,uniqEvent);
        }
    }

    private List<TriggerInfo> filter(UniqEvent event) {
        List<Pair<TriggerCondition, TriggerInfo>> registers = eventRegistrar.getRegisters(event.getSource(), event.getEventType(), event.getUserId());
        return registers.stream().filter(it -> it.getLeft().satisfy(event)).map(Pair::getRight).collect(Collectors.toList());
    }
}
