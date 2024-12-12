package growth.bach.startegy;

import growth.bach.instance.dto.InviteAssistRecordDTO;
import growth.bach.instance.entity.InviteAssistRecordDO;
import growth.bach.instance.repository.InviteAssistRecordRepository;
import growth.bach.instance.req.InviteAssistRecordReq;
import growth.bach.instance.resp.InviteAssistInfoResp;
import growth.bach.manager.NotificationManager;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/2 10:38
 */
public class InviteAssistRecord {
    @Resource
    private InviteAssistRecordRepository inviteAssistRecordRepository;

    @Resource
    private NotificationManager notificationManager;

    public List<InviteAssistRecordDTO> getByInviteUserId(String activityId, String inviteUserId) {
        List<InviteAssistRecordDO> inviteAssistRecordDOS = inviteAssistRecordRepository.getByInviteUserId(activityId, inviteUserId);

        return inviteAssistRecordDOS.stream()
                .map(InviteAssistRecordDTO::from)
                .collect(Collectors.toList());
    }


    public List<InviteAssistRecordDTO> getByAssistUserId(String activityId, String assistUserId) {
        List<InviteAssistRecordDO> inviteAssistRecordDOS = inviteAssistRecordRepository.getByAssistUserId(activityId, assistUserId);

        return inviteAssistRecordDOS.stream()
                .map(InviteAssistRecordDTO::from)
                .collect(Collectors.toList());
    }

    public InviteAssistRecordDTO createInviteAssistReord(InviteAssistRecordReq req, int userType, String deviceId) {
        InviteAssistRecordDO iard = inviteAssistRecordRepository.createInviteAssistRecord(req.to());
        notificationManager.sendActivityFissionTopicMsg(req.getActivityId(), req.getInviterUserId(), req.getAssistUserId(), UserType.findByValue(userType).name(), deviceId);
        return InviteAssistRecordDTO.from(iard);
    }

    public InviteAssistInfoResp getByInviteUserIdByPage(String activityId, String inviteUserId, int page, int pageSize) {
        return inviteAssistRecordRepository.getByInviteUserIdByPage(activityId, inviteUserId, page, pageSize);
    }
}
