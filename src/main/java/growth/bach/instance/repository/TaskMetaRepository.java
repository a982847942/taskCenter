package growth.bach.instance.repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import growth.bach.instance.entity.TaskInstanceDO;
import growth.bach.instance.entity.TaskMetaDO;
import growth.bach.instance.enums.TaskInstanceStatus;
import growth.bach.instance.enums.TaskType;

import java.math.BigDecimal;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/31 22:46
 */
public class TaskMetaRepository {
    public TaskMetaDO getFirstTaskMetaByType(String activityId, TaskType taskType) {
        return new TaskMetaDO();
    }

    public TaskMetaDO getTaskMeta(Long taskMetaId, String activityId) {
        return new TaskMetaDO();
    }

    public TaskInstanceDO create(TaskMetaDO taskMetaDO, String userId) {

        TaskInstanceDO t = new TaskInstanceDO();
        // 如果taskMetaDO.saveSnapshot == true 则
        t.setSnapShot((JSONObject) JSON.toJSON(taskMetaDO));
        return t;
    }

    public Boolean updateTaskInstanceStatus(String userId, Long instanceId, TaskInstanceStatus taskInstanceStatus) {
        if (taskInstanceStatus.equals(TaskInstanceStatus.FINISHED)){
            // uw.set("progress", new BigDecimal("100.0"))
        }
        int updateRes = 0;
        return updateRes > 0;
    }

    public TaskInstanceDO queryById(Long instanceId, String userId) {
        return new TaskInstanceDO();
    }
}
