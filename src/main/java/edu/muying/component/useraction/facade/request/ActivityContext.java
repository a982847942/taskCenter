package edu.muying.component.useraction.facade.request;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/29 11:46
 */
public class ActivityContext {
    private String activityId;
    private String userId;

    public ActivityContext() {
    }

    public ActivityContext(String activityId, String userId) {
        this.activityId = activityId;
        this.userId = userId;
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

    @Override
    public String toString() {
        return "ActivityContext{" +
                "activityId='" + activityId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
