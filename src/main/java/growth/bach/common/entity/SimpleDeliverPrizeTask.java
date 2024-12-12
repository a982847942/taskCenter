package growth.bach.common.entity;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/2 11:41
 */

import growth.bach.common.holder.ApplicationContextHolder;
import growth.bach.instance.TaskBenefitDomainService;
import growth.bach.instance.dto.TaskInstanceDTO;
import growth.bach.instance.entity.TaskBenefitDO;
import growth.bach.instance.entity.TaskMetaDO;
import growth.bach.instance.repository.TaskBenefitRepository;
import growth.bach.instance.repository.TaskMetaRepository;
import growth.bach.instance.req.DeliverPrizeReq;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 简单发奖接口
 * <p>
 * 发出任务对应的所有奖品
 */
public interface SimpleDeliverPrizeTask {
    /**
     * 为任务下配置的所有奖品发放
     *
     * @param instance 任务实例
     * @param quantity 发放数量
     * @return 每个奖品的发放结果
     */
    default List<Pair<Long, Boolean>> deliverPrize(TaskInstanceDTO instance, Integer quantity) {
        List<TaskBenefitDO> taskBenefits = ApplicationContextHolder.getApplicationContext().getBean(TaskBenefitRepository.class).batchQuery(instance.getTaskMeta().getId());
        return taskBenefits.stream().map(tb -> {
            boolean result = deliver(instance.getTaskMeta().getActivityId(), instance.getUserId(), quantity, tb);
            return new ImmutablePair<>(tb.getBenefitId(), result);
        }).collect(Collectors.toList());
    }

    private boolean deliver(String activityId, String userId, Integer quantity, TaskBenefitDO taskBenefitDO) {
        try {
            DeliverPrizeReq req = DeliverPrizeReq.from(taskBenefitDO);
            req.setActivityId(activityId);
            req.setUserId(userId);
            req.setQuantity(quantity);
            req.setIssuanceMethod("REAL_TIME");
            req.setInventoryDecrMethod("DIRECT");

            TaskMetaDO taskMeta = ApplicationContextHolder.getApplicationContext().getBean(TaskMetaRepository.class).getTaskMeta(taskBenefitDO.getTaskMetaId(), activityId);
            req.setTaskName(taskMeta.getTaskName());

            ApplicationContextHolder.getApplicationContext().getBean(TaskBenefitDomainService.class).deliverPrize(req);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
