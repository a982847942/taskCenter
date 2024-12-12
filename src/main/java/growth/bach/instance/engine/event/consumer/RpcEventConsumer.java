package growth.bach.instance.engine.event.consumer;

import growth.bach.instance.engine.event.hub.EventHub;
import growth.bach.instance.req.SendTaskEventReq;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/2 9:37
 */
public class RpcEventConsumer {
    private EventHub eventHub;
    public void sendTaskEvent(SendTaskEventReq req){
        eventHub.consumer(req);
    }

}
