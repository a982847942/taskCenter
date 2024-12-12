package growth.tech.spring.client;

import edu.common.exception.AssertUtils;
import edu.common.exception.CommonException;
import edu.common.exception.CommonResultCodeEnum;
import growth.tech.Result;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/30 10:46
 */
public class ClientUtils {

    /**
     * 调下游 RPC时，对返回的Result做基础校验。校验不通过抛异常
     *
     * @param response RPC返回结果
     * @param <T> RPC返回结果类型
     */
    @SneakyThrows
    public static <T> T checkBaseResult(T response) {
        AssertUtils.isNotNull(response, CommonResultCodeEnum.INTEGRATION_ERROR, "call rpc return null!");
        // 获取result字段并校验
        Field resultField = Arrays.stream(response.getClass().getDeclaredFields())
                .filter(field -> Result.class.isAssignableFrom(field.getType()))
                .findAny()
                .orElse(null);
        AssertUtils.isNotNull(resultField, CommonResultCodeEnum.INTEGRATION_ERROR,
                "call rpc result does not include Result field!");
        Result result = (Result) resultField.get(response);
        AssertUtils.isNotNull(result, CommonResultCodeEnum.INTEGRATION_ERROR,
                "call rpc Result return null!");
        // 如果下游返回的result中的success字段为false，则将下游返回的Result里的code、message转换为一个CommonException
        if (!result.isSuccess()) {
            throw new CommonException(result.getCode(), result.getMessage(),
                    CommonResultCodeEnum.INTEGRATION_ERROR.isRetryable(),
                    CommonResultCodeEnum.INTEGRATION_ERROR.getCodeDescription(), null);
        }
        return response;
    }

}
