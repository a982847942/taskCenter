package growth.bach.instance.engine.task.entity;

/**
 * 任务实体生命周期钩子
 * 在创建任务实体后调用，允许业务层实现一些创建后的初始化动作
 *
 * @author brain
 * @version 1.0
 * @date 2024/11/1 14:16
 */
public interface InitializingEntity {
    default void afterEntityCreated(){

    }
}
