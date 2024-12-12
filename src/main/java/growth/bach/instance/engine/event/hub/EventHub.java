package growth.bach.instance.engine.event.hub;

import growth.bach.instance.engine.event.EventEngine;
import growth.bach.instance.engine.event.UniqEvent;

import java.util.List;

/**
 *  * 事件总线
 *  * 所有的consumer向总线汇报
 * @author brain
 * @version 1.0
 * @date 2024/11/2 9:38
 */
public class EventHub {
    private List<UniqueEventConvertor> convertors;

    private EventEngine eventEngine;

    public void consumer(Object object){
        UniqEvent uniqEvent = convertors.stream().filter(it -> it.support(object)).findFirst().map(it -> it.convert(object)).orElse(null);
        if (uniqEvent == null){
            // 日志
        }
        eventEngine.triggerAsync(uniqEvent);
    }
}
