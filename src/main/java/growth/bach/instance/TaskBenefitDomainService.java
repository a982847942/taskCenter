package growth.bach.instance;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/2 18:56
 */

import growth.bach.condition.SwitchService;
import growth.bach.instance.entity.TaskBenefitDO;
import growth.bach.instance.repository.TaskBenefitRepository;
import growth.bach.instance.req.DeliverPrizeReq;
import growth.bach.manager.ChopinManager;
import growth.tech.spring.lock.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Optional;

/**
 * 任务奖励服务
 *
 * @author yuzhun
 * @created 2024-07-09 12:13
 */
@Slf4j
@Component
public class TaskBenefitDomainService {
    @Resource
    private TaskBenefitRepository taskBenefitRepository;

    @Resource
    private ChopinManager chopinManager;

    @Resource
    private SwitchService switchService;

    /**
     * 为某个任务执行发奖动作
     * <p>
     * 立刻执行
     * <p>
     * <strong>注意:</strong> 不检查任务状态，由外部决定,本方法只关注发奖限制规则
     * <p>
     *
     * @param req request
     */
    @RedisLock(group = "deliverPrize", value = "#req.userId")
    public void deliverPrize(DeliverPrizeReq req) {
        TaskBenefitDO benefitDO = taskBenefitRepository.query(req.getTaskMetaId(), req.getBenefitId(), req.getSceneKey(), req.getCustomizeKey());
        if (benefitDO == null) {
            log.error("未配置任务的奖品信息，req={}", req);
//            throw new SystemLogicException(BachErrorCode.TASK_BENEFIT_CONFIG_ERROR);
        }

        checkRules(benefitDO, req);
        chopinManager.issueBenefit(req.getActivityId(), req.getUserId(), req.getUserId(), req.to());
    }

    private void checkRules(TaskBenefitDO benefitDO, DeliverPrizeReq req) {
        switchService.stropTheWorldThrowable(req.getActivityId(), req.getUserId());

        TaskBenefitDO.Rule rule = benefitDO.getRule();
        if (rule == null) {
            return;
        }

        if (rule.getLimitPeriod() != null && rule.getLimit() != null) {
            LocalDate start = Optional.ofNullable(rule.getLimitPeriod().getDuration()).map(it -> it.getLeft().toLocalDate()).orElse(null);
            LocalDate end = Optional.ofNullable(rule.getLimitPeriod().getDuration()).map(it -> it.getRight().toLocalDate()).orElse(null);
            int quantity = chopinManager.queryBenefitQuantity(req.getActivityId(), benefitDO.getBenefitId(), benefitDO.getTaskMetaId(), req.getUserId(), start, end);
            // fixme: 为了不超发，严格限制了最后一次发放
            if (quantity + req.getQuantity() > rule.getLimit()) {
                log.info("发奖已达今日上限, req={}, rule: {}", req, rule);
//                throw new SystemLogicException(BachErrorCode.TASK_BENEFIT_OVER_LIMIT);
            }
        }
    }
}
