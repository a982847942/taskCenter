package edu.muying.component.useraction.facade;

import com.alibaba.fastjson2.JSON;
import edu.muying.component.useraction.ActionContext;
import edu.muying.component.useraction.dispatcher.UserActionDispatcher;
import edu.muying.component.useraction.facade.request.ActivityContext;
import edu.muying.component.useraction.facade.request.DoActivityActionRequest;
import edu.muying.component.useraction.facade.response.DoActivityActionResponse;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/29 11:44
 */
public class UserActionFacade {
    private UserActionDispatcher dispatcher;

    public DoActivityActionResponse doAction(DoActivityActionRequest request){
        ActivityContext actionContext = request.getActionContext();
        RpcUtils.checkActivityContext(actionContext);
        Object actionData = dispatcher.doAction(actionContext.getActivityId(), actionContext.getUserId(), request.getActionCode(), request.getParamMap());
        DoActivityActionResponse response = new DoActivityActionResponse();
        response.setActionData(JSON.toJSONString(actionData));
        return response;
    }
}
