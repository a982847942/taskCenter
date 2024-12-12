package growth.bach.instance.dto;

import com.alibaba.fastjson.JSONObject;
import growth.bach.instance.entity.InviteAssistRecordDO;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/2 11:00
 */
@Data
public class InviteAssistRecordDTO {
    private Long id;
    private String activityId;
    /**
     * 发起邀请用户ID
     */
    private String inviterUserId;
    /**
     * 助力用户ID
     */
    private String assistUserId;

    private Long taskInstanceId;
    /**
     * 扩展信息
     */
    private JSONObject extra;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public static InviteAssistRecordDTO from(InviteAssistRecordDO inviteAssistRecordDO){
        InviteAssistRecordDTO iarDTO = new InviteAssistRecordDTO();
        iarDTO.setId(inviteAssistRecordDO.getId());
        iarDTO.setAssistUserId(inviteAssistRecordDO.getAssistUserId());
        iarDTO.setInviterUserId(inviteAssistRecordDO.getInviterUserId());
        iarDTO.setTaskInstanceId(inviteAssistRecordDO.getTaskInstanceId());
        iarDTO.setActivityId(inviteAssistRecordDO.getActivityId());
        iarDTO.setCreateTime(inviteAssistRecordDO.getCreateTime());
        iarDTO.setUpdateTime(inviteAssistRecordDO.getUpdateTime());
        return iarDTO;
    }
}
