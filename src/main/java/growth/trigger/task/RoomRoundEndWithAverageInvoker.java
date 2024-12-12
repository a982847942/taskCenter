package growth.trigger.task;

import com.alibaba.fastjson.JSON;
import growth.trigger.QuizTaskPointConfig;
import growth.trigger.RoomRoundEndContext;
import growth.trigger.TaskPointInvoker;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/2 21:55
 */
public class RoomRoundEndWithAverageInvoker implements TaskPointInvoker<RoomRoundEndWithAverageInvoker.Config, RoomRoundEndContext, Void> {


    @Override
    public String getPointName() {
        return "ROOM_ROUND_WITH_AVERAGE";
    }

    @Override
    public Class<Config> getConfigClazz() {
        return Config.class;
    }

    @Override
    public Void trigger(String activityId, String userId, QuizTaskPointConfig<RoomRoundEndContext> config, Config configParam, RoomRoundEndContext context) {
        var correctCounts = context.getAnswerRecords().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, it -> it.getValue().stream().filter(record -> QuizAnswerResult.CORRECT.name().equals(record.getResult())).count()));
        bachServiceClient.sendTaskEvent(activityId, userId, configParam.eventType, null, Map.of(
                "teamId", context.getRoomId(),
                "members", JSON.toJSONString(context.getMemberIds()),
                "averageCorrectCounts", context.getAverageCorrectCount().toString(),
                "correctCounts", JSON.toJSONString(correctCounts)
        ));
        return null;
    }

    public static class Config{
        private String eventType;
    }
}
