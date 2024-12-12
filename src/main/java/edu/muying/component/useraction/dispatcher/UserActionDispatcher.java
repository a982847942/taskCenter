package edu.muying.component.useraction.dispatcher;

import edu.common.exception.CommonResultCodeEnum;
import edu.common.exception.ParamsInvalidException;
import edu.muying.component.useraction.ActionContext;
import edu.muying.component.useraction.Activity;
import edu.muying.component.useraction.UserAction;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/29 11:26
 */
public class UserActionDispatcher implements ApplicationContextAware {
    /**
     * activityId --> actionCode --> userAction
     */
    private Map<String, Map<String, UserAction>> userActionBeanMap = new HashMap<>();

    public Object doAction(String activityId, String userId, String actionCode, Map<String, String> paramsMap) {
        Map<String, UserAction> actionMap = userActionBeanMap.get(activityId);
        if (actionMap == null) {
            // 日志
            //异常
            throw new ParamsInvalidException(CommonResultCodeEnum.PARAMETER_ILLEGAL, "传入的activityId没有找到对应的处理器");
        }

        UserAction userAction = actionMap.get(actionCode);
        if (userAction == null) {
            //日志
            //异常
            throw new ParamsInvalidException(CommonResultCodeEnum.PARAMETER_ILLEGAL, "传入的actionCode没有找到对应的处理器");
        }
        ActionContext actionContext = buildActivityContext(activityId, userId, actionCode, paramsMap);
        try {
            Object result = userAction.doAction(actionContext);
            reportCat(actionContext, true);
            return result;
        }catch (Throwable e){
            reportCat(actionContext, false);
            throw e;
        }
    }

    private void reportCat(ActionContext actionContext, boolean success) {
        // Cat打点上报
//        Cat.counter("UserAction", "用户通用行动点")
//                .addTag("activityId", actionContext.getActivityId())
//                .addTag("actionCode", actionContext.getActionCode())
//                .addTag("status", success)
//                .increment();
    }

    private ActionContext buildActivityContext(String activityId, String userId, String actionCode, Map<String, String> paramsMap) {
        ActionContext actionContext = new ActionContext();
        actionContext.setActionCode(actionCode);
        actionContext.setActivityId(activityId);
        actionContext.setUserId(userId);
        actionContext.setParamsMap(paramsMap == null ? new HashMap<>() : paramsMap);
        return actionContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext.getBeansOfType(UserAction.class).values().forEach(it ->
                {
                    Activity annotation = it.getClass().getAnnotation(Activity.class);
                    if (annotation != null) {
                        userActionBeanMap.computeIfAbsent(annotation.activityId(), k -> new HashMap<>())
                                .put(it.getClass().getSimpleName(), it);
                    }
                }
        );
    }
}
