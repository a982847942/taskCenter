package growth.trigger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/2 21:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionAnswerCorrectContext {
    private Long questionId;
    private String groupName;
}
