package edu.muying.component.useraction.facade;

import edu.common.exception.AssertUtils;
import edu.common.exception.CommonResultCodeEnum;
import edu.muying.component.useraction.facade.request.ActivityContext;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/29 11:48
 */
public class RpcUtils {
    public static void checkActivityContext(ActivityContext activityContext){
        AssertUtils.isNotNull(activityContext, CommonResultCodeEnum.PARAMETER_ILLEGAL,"context 为 null");
        AssertUtils.isNotNull(activityContext.getActivityId(), CommonResultCodeEnum.PARAMETER_ILLEGAL,"context.activityId 为 null");
        AssertUtils.isNotNull(activityContext.getUserId(), CommonResultCodeEnum.PARAMETER_ILLEGAL,"context.userId 为 null");
    }
}
