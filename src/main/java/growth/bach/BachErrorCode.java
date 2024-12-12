package growth.bach;

import edu.common.exception.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/31 20:10
 */
@Getter
@AllArgsConstructor
public enum BachErrorCode implements ResultCode {
    // 业务开关
    SWITCH_STOP_THE_WORLD(400100, "本期活动已经停止，无法继续任务", false, "强制关停活动");

    private final int code;
    private final String message;

    private final boolean retryable;

    private final String codeDescription;
}
