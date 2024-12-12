package growth.bach.instance.enums;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Set;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/1 11:54
 */
@Getter
@AllArgsConstructor
public enum TaskInstanceStatus {
    /**
     * 用户未领取
     */
    UNCLAIMED(0, "UNCLAIMED"),
    /**
     * 未完成
     */
    UNFINISHED(1, "UNFINISHED"),
    /**
     * 完成
     */
    FINISHED(2, "FINISHED"),
    /**
     * 超时未完成
     */
    TIMEOUT(3, "TIMEOUT"),
    /**
     * 过期的
     */
    EXPIRED(4, "EXPIRED");

    private static final Set<TaskInstanceStatus> ONGOING_STATUS_SET = Sets.newHashSet(UNFINISHED);

    private final Integer code;
    private String statusName;

    public static boolean isOngoing(TaskInstanceStatus status) {
        return ONGOING_STATUS_SET.contains(status);
    }

    public static TaskInstanceStatus from(int code) {
        return Arrays.stream(TaskInstanceStatus.values()).filter(it -> it.code == code).findFirst().orElse(UNCLAIMED);
    }

    public static TaskInstanceStatus from(String statusName) {
        return Arrays.stream(TaskInstanceStatus.values()).filter(it -> it.statusName.equals(statusName)).findFirst().orElse(UNCLAIMED);
    }
}
