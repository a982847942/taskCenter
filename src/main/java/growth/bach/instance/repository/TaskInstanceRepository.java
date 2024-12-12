package growth.bach.instance.repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import growth.bach.instance.dto.TaskMetaDTO;
import growth.bach.instance.entity.TaskInstanceDO;
import growth.bach.instance.entity.TaskMetaDO;
import growth.bach.instance.enums.TaskInstanceStatus;
import growth.bach.instance.req.BatchQueryInstanceReq;
import growth.tech.spring.lock.RedisLock;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/1 11:52
 */
public class TaskInstanceRepository {
    private static final BigDecimal PERCENT100 = new BigDecimal("100.0");

//    @Resource
//    private TaskInstanceMapper taskInstanceMapper;

    @Resource
    private TaskMetaRepository taskMetaRepository;

    public TaskInstanceDO create(TaskMetaDO taskMetaDO, String userId) {
        TaskInstanceDO instanceDO = from(taskMetaDO, userId);
        taskInstanceMapper.insert(instanceDO);
        return instanceDO;
    }

    private TaskInstanceDO from(TaskMetaDO taskMetaDO, String userId) {
        TaskInstanceDO t = new TaskInstanceDO();
        t.setTaskMetaId(taskMetaDO.getId());
        t.setTaskType(taskMetaDO.getTaskType());
        t.setTaskName(taskMetaDO.getTaskName());
        t.setTaskInstanceStatus(TaskInstanceStatus.UNFINISHED.getCode());
        t.setActivityId(taskMetaDO.getActivityId());
        t.setUserId(userId);

        t.setSaveSnapshot(taskMetaDO.getSaveSnapshot());
        if (taskMetaDO.getSaveSnapshot().equals(Boolean.TRUE)) {
            t.setSnapShot((JSONObject) JSON.toJSON(taskMetaDO));
        }

        LocalDateTime expireTime = TaskMetaDTO.from(taskMetaDO).getTaskCreateCondition().getExpireTime();
        t.setExpireTime(expireTime);

        t.setCounts(0D);
        t.setProgress(BigDecimal.ZERO);

        t.setExtra(new JSONObject());

        return t;
    }

    public List<TaskInstanceDO> queryByTaskMetaId(String userId, Long metaId, String activityId) {
//        QueryWrapper<TaskInstanceDO> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("user_id", userId);
//        queryWrapper.eq("task_meta_id", metaId);
//        queryWrapper.eq("activity_id", activityId);
//        queryWrapper.eq("delete_flag", false);
//        return taskInstanceMapper.selectList(queryWrapper);
        return Lists.newArrayList();
    }

    public List<TaskInstanceDO> batchQueryByTaskMetaId(List<Long> metaIds, String userId, String activityId) {
//        if (CollectionUtils.isEmpty(metaIds)) {
//            return Lists.newArrayList();
//        }
//        QueryWrapper<TaskInstanceDO> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("user_id", userId);
//        queryWrapper.in("task_meta_id", metaIds);
//        queryWrapper.eq("activity_id", activityId);
//        queryWrapper.eq("delete_flag", false);
//        return taskInstanceMapper.selectList(queryWrapper);
        return Lists.newArrayList();
    }

    public TaskInstanceDO queryById(Long instanceId, String userId) {
//        QueryWrapper<TaskInstanceDO> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("user_id", userId);
//        queryWrapper.eq("id", instanceId);
//        queryWrapper.eq("delete_flag", false);
//        return taskInstanceMapper.selectOne(queryWrapper);
        return new TaskInstanceDO();
    }

    public List<TaskInstanceDO> batchQuery(BatchQueryInstanceReq req) {
//        QueryWrapper<TaskInstanceDO> qw = new QueryWrapper<>();
//        qw.eq("user_id", req.getUserId());
//        qw.eq("task_meta_id", req.getTaskMetaId());
//        qw.eq("activity_id", req.getActivityId());
//        qw.gt(req.getStart() != null, "create_time", req.getStart());
//        qw.lt(req.getEnd() != null, "create_time", req.getEnd());
//        qw.eq(req.getStatus() != null, "task_instance_status", req.getStatus());
//        qw.eq("delete_flag", false);
//
//        return taskInstanceMapper.selectList(qw);
        return Lists.newArrayList();
    }

    /**
     * 增加任务实例的进度
     * - 如果增加progress之后，总进度超过100，则设为100
     * - 如果总进度 >= 100则将任务状态置为完成
     *
     * @param instanceId 任务实例ID
     * @param userId     用户ID
     * @param progress   要增加的进度，百分制
     * @return 进度增加是否成功
     */
    @RedisLock(group = "incrProgress", value = "#instanceId")
    public boolean incrProgress(Long instanceId, String userId, BigDecimal progress) {
        TaskInstanceDO taskInstanceDO = queryById(instanceId, userId);
//        if (taskInstanceDO == null) {
//            throw new ParamsInvalidException(BachErrorCode.TASK_INSTANCE_NOT_EXIST);
//        }

        // 任务状态不是正在进行的，不推进
        if (!TaskInstanceStatus.isOngoing(TaskInstanceStatus.from(taskInstanceDO.getTaskInstanceStatus()))) {
            return false;
        }

        BigDecimal newProgress = Optional.ofNullable(taskInstanceDO.getProgress()).orElse(BigDecimal.ZERO).add(progress);
        newProgress = newProgress.compareTo(new BigDecimal("100.0")) > 0 ? new BigDecimal("100.0") : newProgress;

//        UpdateWrapper<TaskInstanceDO> uw = new UpdateWrapper<>();
//        uw.eq("id", instanceId);
//        uw.eq("user_id", userId);
//        uw.set("progress", newProgress);
//        if (newProgress.compareTo(PERCENT100) == 0) {
//            uw.set("task_instance_status", TaskInstanceStatus.FINISHED.getCode());
//        }
//        return taskInstanceMapper.update(null, uw) > 0;
        return true;
    }

    public boolean updateTaskInstanceStatus(String userId, Long instanceId, TaskInstanceStatus taskInstanceStatus) {
//        UpdateWrapper<TaskInstanceDO> uw = new UpdateWrapper<>();
//        uw.eq("user_id", userId);
//        uw.eq("id", instanceId);
//        uw.set("task_instance_status", taskInstanceStatus.getCode());
//        if (taskInstanceStatus.equals(TaskInstanceStatus.FINISHED)) {
//            uw.set("progress", new BigDecimal("100.0"));
//        }
//        return taskInstanceMapper.update(null, uw) > 0;
        return true;
    }

    public boolean updateTaskInstanceExtra(Long instanceId, String userId, JSONObject extra) {
//        TaskInstanceDO taskInstanceDO = queryById(instanceId, userId);
//        if (Objects.isNull(taskInstanceDO)) {
//            return false;
//        }
//
//        extra.putAll(taskInstanceDO.getExtra());
//        UpdateWrapper<TaskInstanceDO> uw = new UpdateWrapper<>();
//        uw.eq("user_id", userId);
//        uw.eq("id", instanceId);
//        uw.set("extra", extra.toJSONString());
//        return taskInstanceMapper.update(null, uw) > 0;
        return true;
    }
}
