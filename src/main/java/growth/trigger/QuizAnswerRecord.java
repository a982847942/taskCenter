package growth.trigger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sun.util.resources.LocaleData;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/2 21:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizAnswerRecord {
    private Long id;
    private String activityId;
    private String userId;
    private String groupName;
    private String round;
    private Long questionId;
    private List<String> chosenOptions;
    private String result;
    private LocalDateTime createTime;
}
