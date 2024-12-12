package growth.bach.instance.req;

import lombok.Data;

import java.util.Map;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/2 11:52
 */
@Data
public class SendTaskEventReq {
    private String userId;
    private String activityId;
    private String eventType;
    private Map<String, String> content;
    private Long taskInstanceId;
}
