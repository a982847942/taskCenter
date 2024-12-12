package growth.bach.instance.req;

import growth.bach.instance.entity.TaskBenefitDO;
import lombok.Data;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/2 11:23
 */
@Data
public class DeliverPrizeReq {
    private String activityId;
    private Long taskMetaId;
    private String taskName;
    private Long benefitId;
    private String sceneKey;
    private String customizeKey;
    private String userId;
    private Integer quantity;
    private String issuanceMethod;
    private String inventoryDecrMethod;

    public static DeliverPrizeReq from(TaskBenefitDO taskBenefitDO){
        DeliverPrizeReq req = new DeliverPrizeReq();
        req.setTaskMetaId(taskBenefitDO.getTaskMetaId());
        req.setBenefitId(taskBenefitDO.getBenefitId());
        req.setSceneKey(taskBenefitDO.getSceneKey());
        req.setCustomizeKey(taskBenefitDO.getCustomizeKey());
        return req;
    }
}
