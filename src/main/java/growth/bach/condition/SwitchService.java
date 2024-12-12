package growth.bach.condition;

import edu.common.exception.SystemLogicException;
import growth.bach.BachErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/31 19:46
 */
@Slf4j
@Component
public class SwitchService {
//    @ApolloJsonValue("${switch.stopTheWorld.condition: []}")
    private List<StopTheWorldCondition> stopTheWorldConditionList;



    /**
     * 关停活动的方便方法
     * @param activityId  活动ID
     * @param userId  用户ID
     */
    public void stropTheWorldThrowable(String activityId, String userId){
        StopTheWorldContext ctx = new StopTheWorldContext(activityId, userId);
        stopTheWorldThrowable(ctx);
    }

    /**
     * 关停活动
     * 如果上下文命中某个配置的条件，则关停活动，通过异常的方式通知前端
     * @param ctx
     */
    private void stopTheWorldThrowable(StopTheWorldContext ctx) {
        if (CollectionUtils.isEmpty(stopTheWorldConditionList)){
            return;
        }
        stopTheWorldConditionList.stream().filter(it -> Check.allMatch(it, ctx)).findFirst().ifPresent(it -> throwException(it, ctx));
    }

    private static void throwException(StopTheWorldCondition condition, StopTheWorldContext ctx){
        log.warn("命中关停活动的规则，将阻断。condition is {}, ctx is {}", condition, ctx);
        if (StringUtils.isNoneBlank(condition.getToast())){
            throw new SystemLogicException(BachErrorCode.SWITCH_STOP_THE_WORLD.getCode(), condition.getToast());
        }
        throw new SystemLogicException(BachErrorCode.SWITCH_STOP_THE_WORLD, condition.getToast());
    }

    private enum Check{
        ACTIVITY_ID(){
            @Override
            public boolean match(StopTheWorldCondition condition, StopTheWorldContext ctx) {
                return StringUtils.equals(condition.getActivityId(), ctx.getActivityId());
            }
        },
        START_TIME(){
            @Override
            public boolean match(StopTheWorldCondition condition, StopTheWorldContext ctx) {
                if (StringUtils.isEmpty(condition.getStartTime())){
                    return true;
                }
                LocalDateTime startTime = LocalDateTime.parse(condition.getStartTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                return LocalDateTime.now().isAfter(startTime);
            }
        },
        END_TIME(){
            @Override
            public boolean match(StopTheWorldCondition condition, StopTheWorldContext ctx) {
                if (StringUtils.isEmpty(condition.getEndTime())){
                    return true;
                }
                LocalDateTime endTime = LocalDateTime.parse(condition.getEndTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                return LocalDateTime.now().isBefore(endTime);
            }
        },
        WHITELIST(){
            @Override
            public boolean match(StopTheWorldCondition condition, StopTheWorldContext ctx) {
                return CollectionUtils.isEmpty(condition.getWhiteListUsers()) || condition.getWhiteListUsers().contains(ctx.getUserId());
            }
        }
        ;
        public boolean match(StopTheWorldCondition condition, StopTheWorldContext ctx){
            throw new RuntimeException("需要实现的方法");
        }
        public static boolean allMatch(StopTheWorldCondition condition, StopTheWorldContext ctx){
            return Arrays.stream(Check.values()).allMatch(it -> it.match(condition, ctx));
        }
    }


}
