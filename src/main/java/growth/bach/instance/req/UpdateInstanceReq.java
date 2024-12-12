package growth.bach.instance.req;

import com.alibaba.fastjson.JSONObject;
import growth.bach.instance.enums.TaskInstanceStatus;
import lombok.Data;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/2 10:10
 */
@Data
public class UpdateInstanceReq {
    private String userId;
    Long instanceId;
    TaskInstanceStatus taskInstanceStatus;
    JSONObject extra;
}
