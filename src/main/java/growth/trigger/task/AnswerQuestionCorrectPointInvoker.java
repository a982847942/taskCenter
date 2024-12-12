package growth.trigger.task;

import growth.trigger.QuestionAnswerCorrectContext;
import growth.trigger.QuizTaskPointConfig;
import growth.trigger.TaskPointInvoker;
import lombok.Data;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/2 21:48
 */
public class AnswerQuestionCorrectPointInvoker implements TaskPointInvoker<AnswerQuestionCorrectPointInvoker.Config, QuestionAnswerCorrectContext, Void> {

    @Override
    public String getPointName() {
        return "QUESTION_ANSWER_CORRECT";
    }

    @Override
    public Class<Config> getConfigClazz() {
        return Config.class;
    }

    @Override
    public Void trigger(String activityId, String userId, QuizTaskPointConfig<QuestionAnswerCorrectContext> config, Config configParam, QuestionAnswerCorrectContext context) {
        bachServiceClient.sendTaskEvent(activityId, userId, configParam.eventType, null, Map.of(
                "questionId", context.getQuestionId().toString(),
                "quizGroup", context.getGroupName()
        ));
        return null;
    }

    @Data
    public static class Config{
        private String eventType;
    }
}
