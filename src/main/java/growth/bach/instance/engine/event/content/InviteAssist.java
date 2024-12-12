package growth.bach.instance.engine.event.content;

import lombok.Data;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/1 16:07
 */
@Data
public class InviteAssist {
    private String activityId;
    private String inviteUserId;
    private String assistUserId;
    private String deviceId;
    private Long instanceId;
    private Long prizeQuantity;
}
