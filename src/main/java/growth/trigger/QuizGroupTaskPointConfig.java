package growth.trigger;

import lombok.Data;

import javax.annotation.Nullable;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/2 22:10
 */
@Data
public class QuizGroupTaskPointConfig {
    @Nullable
    private QuizTaskPointConfig<Void> onSingleRoundStart;
    @Nullable
    private QuizTaskPointConfig<QuestionAnswerCorrectContext> onQuestionAnswerCorrect;
    @Nullable
    private QuizTaskPointConfig<Void> onRoomMemberGetReady;
    @Nullable
    private QuizTaskPointConfig<Void> onRoomRoundStartEachMember;
    @Nullable
    private QuizTaskPointConfig<RoomRoundEndContext> onRoomRoundEndEachMember;
}
