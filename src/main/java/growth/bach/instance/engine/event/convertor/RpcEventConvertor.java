package growth.bach.instance.engine.event.convertor;

import growth.bach.instance.engine.event.UniqEvent;
import growth.bach.instance.engine.event.hub.UniqueEventConvertor;
import growth.bach.instance.enums.EventSource;
import growth.bach.instance.req.SendTaskEventReq;
import org.springframework.core.annotation.Order;

import java.util.Map;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/2 14:11
 */
@Order(10)
public class RpcEventConvertor implements UniqueEventConvertor<SendTaskEventReq, Map<String, String>> {
    @Override
    public boolean support(Object req) {
        return req instanceof SendTaskEventReq;
    }

    @Override
    public UniqEvent<Map<String, String>> convert(SendTaskEventReq req) {
        UniqEvent<Map<String, String>> event = new UniqEvent<>();
        event.setEventType(req.getEventType());
        event.setSource(EventSource.FE.name());
        event.setUserId(req.getUserId());
        event.setContent(req.getContent());
        return event;
    }
}
