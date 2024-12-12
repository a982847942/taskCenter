package growth.bach.instance.engine.task.meta.condition.create;

import growth.bach.common.constant.Period;
import growth.bach.common.lang.spi.Identity;
import growth.bach.instance.engine.task.meta.BaseCreateCondition;
import growth.bach.instance.enums.TaskInstanceStatus;
import growth.bach.instance.repository.TaskInstanceRepository;
import lombok.Setter;

/**
 * 成功次数限制类创建条件
 * @author brain
 * @version 1.0
 * @date 2024/11/1 15:46
 */
@Setter
@Identity("successLimit")
public class SuccessLimitCreateCondition extends BaseCreateCondition {
    /**
     * 统计周期
     */
    private Period checkPeriod;

    private Integer limit;

    // 注意 这个类不会加Spring注解，因此需要手动（提供一个工具类获取AC）使用ApplicationContext进行获取taskInstanceRepository
    private TaskInstanceRepository taskInstanceRepository;


    @Override
    public boolean satisfy(String userId) {
        if (!super.satisfy(userId)){
            return false;
        }
        Integer i = querySuccessTaskCounts(taskMetaDTO.getId(), userId, taskMetaDTO.getActivityId());
        return i < limit;
    }

    private Integer querySuccessTaskCounts(Long taskMetaId, String userId, String activityId) {
        /**
         *
         *  BatchQueryInstanceQuery
         *  设置 startTime, endTime, taskMetaId, userId, activityId  TaskInstanceStatus.FINISHED
         */
//        taskInstanceRepository.batchQuery();
        return 0;
    }

}
