package edu.muying.component.useraction.facade.request;

import edu.muying.component.useraction.ActionContext;

import java.util.Map;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/29 11:45
 */
public class DoActivityActionRequest {
    private ActivityContext actionContext;

    private String actionCode;

    private Map<String, String> paramMap;

    public DoActivityActionRequest() {
    }

    public DoActivityActionRequest(ActivityContext actionContext, String actionCode, Map<String, String> paramMap) {
        this.actionContext = actionContext;
        this.actionCode = actionCode;
        this.paramMap = paramMap;
    }

    public ActivityContext getActionContext() {
        return actionContext;
    }

    public void setActionContext(ActivityContext actionContext) {
        this.actionContext = actionContext;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public Map<String, String> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, String> paramMap) {
        this.paramMap = paramMap;
    }

    @Override
    public String toString() {
        return "DoActivityActionRequest{" +
                "actionContext=" + actionContext +
                ", actionCode='" + actionCode + '\'' +
                ", paramMap=" + paramMap +
                '}';
    }
}
