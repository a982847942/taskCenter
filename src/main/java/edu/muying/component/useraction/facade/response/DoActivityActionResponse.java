package edu.muying.component.useraction.facade.response;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/29 11:45
 */
public class DoActivityActionResponse {
    // Result

    private String actionData;

    public DoActivityActionResponse() {
    }

    public DoActivityActionResponse(String actionData) {
        this.actionData = actionData;
    }

    public String getActionData() {
        return actionData;
    }

    public void setActionData(String actionData) {
        this.actionData = actionData;
    }

    @Override
    public String toString() {
        return "DoActivityActionResponse{" +
                "actionData='" + actionData + '\'' +
                '}';
    }
}
