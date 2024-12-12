package growth.bach.startegy.muying;

import com.alibaba.fastjson.JSONObject;
import growth.bach.instance.dto.InviteAssistRecordDTO;
import growth.bach.instance.engine.event.content.InviteAssist;
import growth.bach.instance.req.InviteAssistRecordReq;
import growth.bach.startegy.BaseStrategy;
import growth.bach.startegy.InviteAssistRecord;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/2 10:33
 */
public class MuYingStrategy extends BaseStrategy<InviteAssist> {

    // @ApolloJsonValue("${activity_muyinganswer:{}}")
    private Map<String, Map<String, Integer>> activityMuyinganswer;

    private InviteAssistRecord inviteAssistRecord;

    @Override
    public InviteStrategyRes satifyAssistCondition(InviteAssist iva) {
        String assistUserId = iva.getAssistUserId();
        String inviteUserId = iva.getInviteUserId();
        String activityId = iva.getActivityId();
        String deviceId = iva.getDeviceId();
        // 自己不能给自己助力
        if (assistUserId.equals(inviteUserId)) {
            //异常
        }
        // inviteAssistRecord表记录助力
        List<InviteAssistRecordDTO> ivList = inviteAssistRecord.getByAssistUserId(activityId, assistUserId);
        if (!ivList.isEmpty()) {
            // 日志 异常
        }
        // 归因判断用户类型
        int userType = 0;
        // 调用风控，如有必要，重写基类的风控方法
        InviteStrategyRes spamRes = inviteAssistTaskSpamPass(iva, userType);
        createInviteAssistRecord(iva, userType, spamRes);
        InviteStrategyRes res = new InviteStrategyRes();
        res.setDecay(spamRes.getDecay());
        res.setPassed(true);
        res.setQuality(spamRes.getQuality());
        return res;
    }

    /**
     * 插入助力记录
     * @param iva
     * @param userType
     * @param spamRes
     */
    private void createInviteAssistRecord(InviteAssist iva, int userType, InviteStrategyRes spamRes) {
        InviteAssistRecordReq req = new InviteAssistRecordReq();
        req.setActivityId(iva.getActivityId());
        req.setTaskInstanceId(iva.getInstanceId());
        req.setAssistUserId(iva.getAssistUserId());
        req.setInviterUserId(iva.getInviteUserId());
        JSONObject ext = new JSONObject();
        ext.put("userType", String.valueOf(userType));
        Long quality = Math.round(iva.getPrizeQuantity() * spamRes.getDecay());
        ext.put("prizeQuantity", String.valueOf(quality));
        req.setExtra(ext);
        InviteAssistRecordDTO iarDTO = inviteAssistRecord.createInviteAssistReord(req, userType, iva.getDeviceId());
        if (Objects.isNull(iarDTO)) {
//            log.error("satisfyAssistCondition createInviteAssistReword error, req: {}", req);
//            throw new SystemLogicException(BachErrorCode.DB_INSERT_ERROR);
        }
        spamRes.setQuality(quality);
    }

    private InviteStrategyRes inviteAssistTaskSpamPass(InviteAssist iva, int userType) {
        // 包装参数 调用风控
        // 风控不通过 抛出异常

        // 风控通过
        InviteStrategyRes isr = new InviteStrategyRes();
        isr.setPassed(true);
        // 根据风控返回结果摄者decay,如果风控结果为null设置默认值1.0
        isr.setDecay(1.0);
        return isr;
    }
}
