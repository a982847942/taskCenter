package growth.tech.core.common.rpc;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/29 15:16
 */

import edu.common.exception.AssertUtils;
import edu.common.exception.CommonResultCodeEnum;
import growth.tech.Result;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * SDK模版方法，非切面的方式处理公共逻辑
 *
 * @author lijiawei
 * @date 2024/09/14 15:01 星期六
 */
@Slf4j
public class RpcTemplate {

    /**
     * 执行业务逻辑。异常的message会塞到Result的message返回出去！
     *
     * @param request  请求参数
     * @param callback 业务自定义回调
     * @param <REQ>    返回结果类型
     * @param <RES>    请求参数类型
     * @return 返回结果
     */
    @SneakyThrows
    @SuppressWarnings("unchecked")
    public static <REQ, RES> RES execute(REQ request, RpcCallback<REQ, RES> callback) {
        // 获取返回值类型class
        Type[] genericTypes = ((ParameterizedType) (callback.getClass().getGenericInterfaces()[0]))
                .getActualTypeArguments();
        Class<RES> responseType = (Class<RES>) genericTypes[1];

        String identity = callback.identifier();
        try {
            // 1.参数校验
            callback.checkParameters(request);

            // 2.执行逻辑
            RES response = callback.execute(request);
            fillResult(response, success());
            return response;
        } catch (Throwable e) {
            RES response = responseType.newInstance();
            fillResult(response, fail(e));
            log.error("{} execute error. request:{}", identity, request, e);
            return response;
        }
    }

    /**
     * 对response填充类型为{@link Result}的字段，如果没这个类型的字段会报错
     *
     * @param response 返回结果
     * @param result   result
     * @param <RES>    返回结果类型
     */
    @SneakyThrows
    private static <RES> void fillResult(RES response, Result result) {
        AssertUtils.isNotNull(result, CommonResultCodeEnum.SYSTEM_ERROR, "方法返回值为空");
        Field resultField = Arrays.stream(response.getClass().getDeclaredFields())
                .filter(field -> Result.class.isAssignableFrom(field.getType()))
                .findAny()
                .orElse(null);
        AssertUtils.isNotNull(resultField, CommonResultCodeEnum.SYSTEM_ERROR,
                "返回值中不包含com.xiaohongshu.infra.rpc.base.Result类型的字段");
        resultField.setAccessible(true);
        resultField.set(response, result);
    }

    /**
     * 获取成功Result
     *
     * @return 成功 Result
     */
    private static Result success() {
        Result result = new Result();
        result.setSuccess(true);
        result.setMessage("成功");
        result.setCode(0);
        return result;
    }

    /**
     * 获取失败result
     *
     * @param e 异常
     * @return 失败 Result
     */
    private static Result fail(Throwable e) {
        Result result = new Result();
        result.setSuccess(false);
        // 目前SDK的facade把异常都作为message透传出去
        result.setMessage(ExceptionUtils.getMessage(e));
        result.setCode(-1);
        return result;
    }

}
