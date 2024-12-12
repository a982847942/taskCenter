package growth.bach.manager;

import growth.bach.common.lang.IdGenerator;
import growth.bach.instance.req.ActivityReqContext;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/2 19:03
 */
@Slf4j
@Component
public class ChopinManager {

    @Resource
    private ChopinService.Iface chopinClient;

    /**
     * 权益中心奖励发放接口 该方法已做过三次重试 请勿在调用时再做循环重试
     *
     * @param activityId 活动ID
     * @param accountId  账号ID可为用户ID
     * @param operator   操作者当前用户ID
     * @param req        发奖亲求参数
     */
    @SneakyThrows
    public void issueBenefit(String activityId, String accountId, String operator, IssueBenefitReq req) {
        ActivityReqContext reqContext = new ActivityReqContext();
        reqContext.setAccountId(accountId);
        reqContext.setOperator(operator);
        reqContext.setActivityId(activityId);
        reqContext.setTransactionId(IdGenerator.objectId());
        IssueBenefitRequest request = req.to();

        int tryCount = 1;
        while (tryCount <= 3) {
            try {
                IssueBenefitResponse response = chopinClient.issueBenefit(ContextHelper.getContext(), reqContext, request);
                log.info("issue benefit call chopin issueBenefit method requestContext:{} request:{} response:{}", reqContext, request, response);
                boolean success = response.getResult().isSuccess();
                if (!success) {
//                    throw new DownstreamException(BachErrorCode.CHOPIN_ISSUE_BENEFIT_DATA_ABNORMAL);
                }
                return;
            } catch (Exception e) {
                ++tryCount;
                log.info("call chopin issueBenefit failed activityRequest:{}, bizReq:{} context:{}", request, request, ContextHelper.getContext(), e);
                Thread.sleep(50);
            }
        }

        throw new DownstreamException(BachErrorCode.CHOPIN_ISSUE_BENEFIT_ERROR);
    }

    /**
     * 查询奖品已经发放数量
     *
     * @param benefitId  奖品ID
     * @param taskMetaId 任务配置ID
     * @param start      开始时间，include
     * @param end        结束时间，include
     * @return 数量
     */
    public int queryBenefitQuantity(String activityId, Long benefitId, Long taskMetaId, String userId, LocalDate start, LocalDate end) {
        ActivityReqContext reqContext = new ActivityReqContext();
        reqContext.setAccountId(userId);
        reqContext.setOperator(userId);
        reqContext.setActivityId(activityId);
        reqContext.setTransactionId(IdGenerator.objectId());

        ObtainedBenefitRequest req = new ObtainedBenefitRequest();
        req.setBenefitIds(Lists.newArrayList(benefitId));
        req.setSource(taskMetaId.toString());
        if (start != null) {
            req.setFromDtm(start.toString());
        }
        if (end != null) {
            req.setEndDtm(end.toString());
        }

        try {
            ObtainedBenefitResponse resp = chopinClient.getObtainedRecord(ContextHelper.getContext(), reqContext, req);
            if (!resp.getResult().isSuccess()) {
                log.error("查询奖品发放情况异常, req={}, resp={}", req, resp);
                throw new DownstreamException(BachErrorCode.CHOPIN_GET_OBTAINED_DATA_ABNORMAL);
            }
            return resp.records.stream().mapToInt(ObtainedRecord::getQuantity).sum();
        } catch (TException e) {
            log.error("查询奖品发放情况异常, req={}", req, e);
            throw new DownstreamException(BachErrorCode.CHOPIN_GET_OBTAINED_ERROR, BachErrorCode.CHOPIN_GET_OBTAINED_ERROR.getMessage(), e);
        }
    }

    /**
     * 查询对应任务今天已发放的积分
     *
     * @param taskMetaId: 活动配置id
     * @param userId:     用户userId
     * @param activityId: 活动id
     * @return int: 今天获得的积分数
     */
    public int getPointToday(Long taskMetaId, String userId, String activityId) {
        ActivityReqContext reqContext = new ActivityReqContext();
        reqContext.setAccountId(userId);
        reqContext.setOperator(userId);
        reqContext.setActivityId(activityId);
        reqContext.setTransactionId(IdGenerator.objectId());

        TargetResourceRequest req = new TargetResourceRequest();
        req.setType(ResouceType.POINT);
        req.setSource(String.valueOf(taskMetaId));

        try {
            ResourceResponse resp = chopinClient.getResourceToday(ContextHelper.getContext(), reqContext, req);
            if (!resp.getResult().isSuccess()) {
                log.error("查询积分发放情况异常, req={}, resp={}", req, resp);
                throw new DownstreamException(BachErrorCode.CHOPIN_GET_RESOURCE_DATA_ABNORMAL);
            }
            return (int) Double.parseDouble(resp.resource.accumulatedVal);
        } catch (TException e) {
            log.error("查询积分发放情况异常, req={}", req, e);
            throw new DownstreamException(BachErrorCode.CHOPIN_GET_RESOURCE_ERROR, BachErrorCode.CHOPIN_GET_RESOURCE_ERROR.getMessage(), e);
        }
    }
}
