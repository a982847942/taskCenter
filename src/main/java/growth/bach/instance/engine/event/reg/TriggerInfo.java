package growth.bach.instance.engine.event.reg;

/**
 * 事件触发回传信息
 * @author brain
 * @version 1.0
 * @date 2024/11/1 14:36
 */
public interface TriggerInfo {
    /**
     * 获取任务实例ID
     * @return
     */
    Long getTaskInstanceId();
}
