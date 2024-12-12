package growth.bach.instance.req;

import growth.bach.instance.enums.TaskType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/31 22:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskInstanceReq {
    private String activityId;
    private String userId;
    private Long taskMetaId;
    private TaskType taskType;
}
