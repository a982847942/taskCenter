package growth.bach.instance.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/31 23:00
 */
@Getter
@AllArgsConstructor
public enum TaskStatus {
    UNKNOWN(-1, "UNKNOWN"),
    ONLINE(0, "ONLINE"),
    OFFLINE(1, "OFFLINE"),
    EDIT(2, "EDIT")
    ;
    private Integer code;
    private String status;

    public static TaskStatus from(int code){
        return Arrays.stream(TaskStatus.values()).filter(it -> it.getCode() == code).findFirst().orElse(UNKNOWN);
    }

    public static TaskStatus from(String status){
        return Arrays.stream(TaskStatus.values()).filter(it -> it.getStatus().equals(status)).findFirst().orElse(UNKNOWN);
    }
}
