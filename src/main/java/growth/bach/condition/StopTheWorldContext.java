package growth.bach.condition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/31 19:47
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class StopTheWorldContext {
    private String activityId;
    private String userId;
}
