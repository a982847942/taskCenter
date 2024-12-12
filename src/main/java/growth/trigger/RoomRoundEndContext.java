package growth.trigger;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/2 21:56
 */
@Data
@AllArgsConstructor
public class RoomRoundEndContext {
    private String roomId;
    private String hostId;
    private List<String> memberIds;
    private Map<String, List<QuizAnswerRecord>> answerRecords;
    private Double averageCorrectCount;
}
