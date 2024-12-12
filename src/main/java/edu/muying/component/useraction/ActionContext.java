package edu.muying.component.useraction;

import java.util.HashMap;
import java.util.Map;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/29 11:16
 */
public class ActionContext {
    /**
     * 活动Id
     */
    private String activityId;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 行动码  activityId --> actionCode (1 --> n)
     */
    private String actionCode;
    /**
     * 前端传入的参数
     */
    private Map<String, String> paramsMap = new HashMap<>();

    public ActionContext() {
    }

    public ActionContext(String activityId, String userId, String actionCode, Map<String, String> paramsMap) {
        this.activityId = activityId;
        this.userId = userId;
        this.actionCode = actionCode;
        this.paramsMap = paramsMap;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public Map<String, String> getParamsMap() {
        return paramsMap;
    }

    public void setParamsMap(Map<String, String> paramsMap) {
        this.paramsMap = paramsMap;
    }

    @Override
    public String toString() {
        return "ActionContext{" +
                "activityId='" + activityId + '\'' +
                ", userId='" + userId + '\'' +
                ", actionCode='" + actionCode + '\'' +
                ", paramsMap=" + paramsMap +
                '}';
    }
}
