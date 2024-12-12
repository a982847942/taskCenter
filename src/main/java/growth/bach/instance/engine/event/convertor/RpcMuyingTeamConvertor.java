package growth.bach.instance.engine.event.convertor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import growth.bach.instance.engine.event.UniqEvent;
import growth.bach.instance.engine.event.hub.UniqueEventConvertor;
import growth.bach.instance.engine.task.muyingdati.event.TeamAnswerResult;
import growth.bach.instance.enums.EventSource;
import growth.bach.instance.enums.EventType;
import growth.bach.instance.req.SendTaskEventReq;
import org.springframework.core.annotation.Order;

/**
 * 母婴双人答题
 * @author brain
 * @version 1.0
 * @date 2024/11/2 14:18
 */
@Order(8)
public class RpcMuyingTeamConvertor implements UniqueEventConvertor<SendTaskEventReq, TeamAnswerResult> {
    @Override
    public boolean support(Object event) {
        if (!(event instanceof SendTaskEventReq)){
            return false;
        }
        EventType eventType = EventType.valueOf(((SendTaskEventReq) event).getEventType());
        return EventType.MUYING_TEAM_ANSWER_CORRECT.equals(eventType);
    }

    @Override
    public UniqEvent<TeamAnswerResult> convert(SendTaskEventReq sendTaskEventReq) {
        UniqEvent<TeamAnswerResult> event = new UniqEvent<>();
        event.setEventType(sendTaskEventReq.getEventType());
        event.setSource(EventSource.FE.name());
        event.setUserId(sendTaskEventReq.getUserId());

        TeamAnswerResult result = new TeamAnswerResult();
        result.setTeamId(sendTaskEventReq.getContent().get("teamId"));
        result.setMembers(JSONObject.parseArray(sendTaskEventReq.getContent().get("members"), String.class));
        result.setAverageCorrectCounts(Double.valueOf(sendTaskEventReq.getContent().get("averageCorrectCounts")));
//        result.setCorrectCounts(JasksonKits.toMap(JSONObject.parseObject(sendTaskEventReq.getContent().get("correctCounts"),Integer.class)));
        return event;
    }
}
