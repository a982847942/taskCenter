package growth.bach.instance.req;

import com.alibaba.fastjson.JSONObject;
import growth.bach.instance.entity.InviteAssistRecordDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/2 10:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InviteAssistRecordReq {
    private String activityId;
    private String inviterUserId;
    private String assistUserId;
    private Long taskInstanceId;
    private JSONObject extra;

    public InviteAssistRecordDO to() {
        InviteAssistRecordDO iarDO = InviteAssistRecordDO.builder()
                .activityId(activityId)
                .inviterUserId(inviterUserId)
                .assistUserId(assistUserId)
                .taskInstanceId(taskInstanceId)
                .extra(extra)
                .build();
        return iarDO;
    }
}
