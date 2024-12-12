package growth.bach.instance.engine.event.consumer;

import growth.bach.instance.engine.event.consumer.ext.ConsumerContext;
import growth.bach.instance.engine.event.consumer.ext.ConsumerStatus;
import growth.bach.instance.engine.event.consumer.ext.MessageExt;
import growth.bach.instance.engine.event.hub.EventHub;
import growth.bach.instance.enums.EventSource;
import growth.bach.instance.enums.EventType;
import org.apache.logging.log4j.message.Message;

/**
 * 笔记发布监听器
 * 笔记发布会上报到一个topic集群，接入小红书messageProcessor监听topic变化
 * @author brain
 * @version 1.0
 * @date 2024/11/2 14:25
 */
public class TopicNoteEventConsumer {
    private EventHub eventHub;
    private ConsumerStatus process(MessageExt messageExt, ConsumerContext consumerContext){
        try {
            eventHub.consumer(new MessageWrapper(EventSource.SHEQU, EventType.NOTE_CHANGE, messageExt));
        }catch (Exception e){
            return ConsumerStatus.FAIL;
        }
        return ConsumerStatus.SUCCESS;
    }
}
