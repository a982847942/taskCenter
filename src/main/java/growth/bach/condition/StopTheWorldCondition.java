package growth.bach.condition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/31 19:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StopTheWorldCondition {
    private String activityId;
    private String startTime;
    private String endTime;
    private List<String> whiteListUsers;
    /**
     * 对外提示的文案
     */
    private String toast;
}
