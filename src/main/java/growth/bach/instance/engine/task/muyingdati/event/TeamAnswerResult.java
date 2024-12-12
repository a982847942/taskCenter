package growth.bach.instance.engine.task.muyingdati.event;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/1 16:26
 */
@Data
public class TeamAnswerResult {
    private String teamId;
    private List<String> members;
    private Double averageCorrectCounts;
    private Map<String, Integer> correctCounts;
}
