package growth.bach.instance;

import edu.common.exception.ParamsInvalidException;
import growth.bach.condition.SwitchService;
import growth.bach.instance.dto.TaskInstanceDTO;
import growth.bach.instance.engine.task.TaskEngine;
import growth.bach.instance.engine.task.entity.TaskEntity;
import growth.bach.instance.entity.TaskInstanceDO;
import growth.bach.instance.entity.TaskMetaDO;
import growth.bach.instance.repository.TaskMetaRepository;
import growth.bach.instance.req.CreateTaskInstanceReq;
import growth.bach.instance.req.UpdateInstanceReq;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/31 22:36
 */
@Slf4j
public class TaskInstanceDomainService {

    @Resource
    private SwitchService switchService;

    private TaskMetaRepository taskMetaRepository;

    private TaskEngine taskEngine;

    public TaskInstanceDTO createInstance(CreateTaskInstanceReq req) {
        switchService.stropTheWorldThrowable(req.getActivityId(), req.getUserId());

        TaskMetaDO taskMetaDO = taskMetaRepository.getFirstTaskMetaByType(req.getActivityId(), req.getTaskType());
        if (Objects.isNull(taskMetaDO)){
            log.error("createInstance getTaskMeta error, req: {}", req);
//            throw new ParamsInvalidException()
        }
        return taskEngine.createTask(taskMetaDO.getId(), req.getUserId(), req.getActivityId()).getTaskInstance();
    }

    /**
     * 只用来更新状态，所有校验逻辑放在business层
     * @param req
     * @return
     */
    public Boolean updateInstanceStatus(UpdateInstanceReq req){
        return taskMetaRepository.updateTaskInstanceStatus(req.getUserId(), req.getInstanceId(), req.getTaskInstanceStatus());
    }

    public TaskInstanceDTO queryInstanceId(Long instanceId, String userId) {
        TaskInstanceDO taskInstanceDO = taskMetaRepository.queryById(instanceId, userId);
        return TaskInstanceDTO.from(taskInstanceDO);
    }
}
