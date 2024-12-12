package growth.trigger.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/2 22:02
 */
@AllArgsConstructor
@Getter
public enum QuizAnswerResult {
    CORRECT(1),
    INCORRECT(2),
    PATIAL_CORRECT(3),
    TIMEOUT(4);
    private int value;
}
