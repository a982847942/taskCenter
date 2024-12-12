package growth.bach.instance.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/2 19:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityReqContext {
    private String accountId;
    private String operator;
    private String activityId;
    private String transactionId;
}
