package growth.bach.instance.engine.event.reg;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import growth.bach.common.lang.spi.IdentityKits;
import growth.bach.instance.engine.parser.JsonParser;
import growth.bach.instance.entity.EventRegistrarDO;
import growth.bach.instance.repository.EventTaskRegisterRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/1 15:18
 */
public class EventRegistrar {
    private EventTaskRegisterRepository eventTaskRegisterRepository;
    public void register(TriggerCondition triggerCondition, DefaultTriggerInfo triggerInfo, LocalDateTime expireTime) {
        EventRegistrarDO entity = new EventRegistrarDO();
        entity.setSource(triggerCondition.getSource());
        entity.setEventType(triggerCondition.getEventType());
        entity.setUserId(triggerCondition.getUserId());
        entity.setExpireTime(expireTime);
        entity.setConditionIdentity(IdentityKits.getIdentity(triggerCondition));
        entity.setCustomConditionData((JSONObject) JSON.toJSON(triggerCondition));
        entity.setTaskInstanceId(triggerInfo.getTaskInstanceId());
        entity.setTriggerData((JSONObject) JSONObject.toJSON(triggerCondition));

        eventTaskRegisterRepository.createEventTaskRegister(entity);
    }

    /**
     * 获取关注的注册事件
     * @param source
     * @param eventType
     * @param userId
     * @return
     */

    public List<Pair<TriggerCondition, TriggerInfo>> getRegisters(String source, String eventType, String userId) {
        List<EventRegistrarDO> candidates = eventTaskRegisterRepository.getCandidates(source, eventType, userId);
        return candidates.stream().map(this::convertModel).collect(Collectors.toList());
    }

    private Pair<TriggerCondition, TriggerInfo> convertModel(EventRegistrarDO entity) {
        TriggerCondition triggerCondition = JsonParser.TRIGGER_CONDITION.parse(entity.getCustomConditionData(), entity.getConditionIdentity());
        return new ImmutablePair<>(triggerCondition, new DefaultTriggerInfo(entity.getTaskInstanceId()));
    }
}
