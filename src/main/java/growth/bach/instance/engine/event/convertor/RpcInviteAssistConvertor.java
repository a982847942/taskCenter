package growth.bach.instance.engine.event.convertor;

import growth.bach.instance.engine.event.UniqEvent;
import growth.bach.instance.engine.event.content.InviteAssist;
import growth.bach.instance.engine.event.hub.UniqueEventConvertor;
import growth.bach.instance.enums.EventSource;
import growth.bach.instance.enums.EventType;
import growth.bach.instance.req.SendTaskEventReq;
import growth.bach.sharecode.ShareCodeDTO;
import growth.bach.sharecode.ShareCodeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;

import java.util.Objects;
import java.util.Optional;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/2 14:14
 */
@Slf4j
@Order(7)
public class RpcInviteAssistConvertor implements UniqueEventConvertor<SendTaskEventReq, InviteAssist> {
    private ShareCodeService shareCodeService;
    @Override
    public boolean support(Object req) {
        if (!(req instanceof SendTaskEventReq)){
            return false;
        }
        EventType et = EventType.valueOf(((SendTaskEventReq) req).getEventType());
        return EventType.MUYING_INVITE_ASSIST.equals(et);
    }

    @Override
    public UniqEvent<InviteAssist> convert(SendTaskEventReq req) {
        InviteAssist iva = decodeShareCode(req);

        UniqEvent<InviteAssist> event = new UniqEvent<>();
        event.setEventType(req.getEventType());
        event.setSource(EventSource.FE.name());
        event.setUserId(iva.getInviteUserId());
        event.setContent(iva);

        return event;
    }

    private InviteAssist decodeShareCode(SendTaskEventReq req) {
        String SHARE_CODE = "shareCode";
        if (Objects.isNull(req) || Objects.isNull(req.getContent()) || !req.getContent().containsKey(SHARE_CODE)) {
            log.error("decodeShareCode req error, req: {}", req);
//            throw new ParamsInvalidException(BachErrorCode.SHARE_CODE_DECODE_PARAMS_LACK);
        }

        ShareCodeDTO shDTO = shareCodeService.decodeShareCode(req.getContent().get(SHARE_CODE), req.getActivityId());
        InviteAssist iva = new InviteAssist();
        iva.setAssistUserId(req.getUserId());
        iva.setInviteUserId(shDTO.getInviterId());
        iva.setInstanceId(shDTO.getTaskInstanceId());
        iva.setActivityId(shDTO.getActivityId());
//        iva.setDeviceId(ContextHelper.getContext().getBaggage().get("__app_context:c_device_id"));
        iva.setPrizeQuantity(Long.parseLong(Optional.ofNullable(shDTO.getAttribute())
                .map(it -> String.valueOf(it.getOrDefault("prizeQuantity", "0")))
                .filter(StringUtils::isNotBlank)
                .orElse("0")));
        return iva;
    }
}
