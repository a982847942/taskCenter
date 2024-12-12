package growth.bach.instance.engine.task.meta;

import growth.bach.common.constant.Period;
import growth.bach.instance.dto.TaskMetaDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/31 23:04
 */
@Slf4j
public abstract class BaseCreateCondition {
    @Getter
    @Setter
    public Period expirePeriod;

    @Getter(AccessLevel.NONE)
    @Setter
    protected TaskMetaDTO taskMetaDTO;

    @Getter
    @Setter
    private boolean checkSpam;

    /**
     * 满足创建条件
     * @param userId
     * @return
     */
    public boolean satisfy(String userId){
        if (checkSpam && !spamPassed(userId)){
            // 日志 异常
        }
        return LocalDateTime.now().isBefore(taskMetaDTO.getActivityExpireTime());
    }

    /**
     * 风控校验
     * @param userId
     * @return
     */
    private boolean spamPassed(String userId) {
        return true;
    }

    public LocalDateTime getExpireTime(){
        if (expirePeriod == null || expirePeriod.equals(Period.ACTIVITY)){
            return taskMetaDTO.getActivityExpireTime();
        }
        return expirePeriod.getDuration().getRight();
    }

    /**
     * 公共的风控参数
     * 如有需要可以子类自定义
     * @param userId
     * @return
     */
    public Map<String, String> getSpamParams(String userId){
        return null;
    }
}
