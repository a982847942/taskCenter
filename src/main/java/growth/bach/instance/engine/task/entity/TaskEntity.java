package growth.bach.instance.engine.task.entity;

import growth.bach.instance.engine.event.reg.TriggerCondition;
import growth.bach.instance.engine.progress.ProgressContext;

/**
 * 任务实体。
 * 其实就是一个具体的任务，需要回答任务如何准入(Enter)、推进(Promote)、完成(Success)，
 * 且需要回答任务在这三个阶段的具体动作
 * 任务实体基于一个任务实例构建出来，构建交给{@link growth.bach.instance.engine.task.factory.TaskEntityFactory}
 * @author brain
 * @version 1.0
 * @date 2024/11/1 14:15
 */
public interface TaskEntity extends InitializingEntity, TaskInstanceAware{
    /**
     * 获取事件触发条件
     * @return
     */
    TriggerCondition getTriggerCondition();

    /**
     * 推动任务前进
     * @param context 前进上下文
     * @return 最新进度值，100制
     */
    double goForward(ProgressContext context);

    /**
     * 任务是否已经完成
     * @return
     */
    boolean isFinished();

    /**
     * 发奖
     * @return 是否成功
     */
    boolean deliverPrize();
}
