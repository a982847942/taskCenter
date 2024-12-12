package growth.tech.core.common.rpc;

/**
 * RPC执行 回调接口
 * @author brain
 * @version 1.0
 * @date 2024/10/29 15:16
 */
public interface RpcCallback<REQ, RES> {
    /**
     * 方法执行标识符， 用于日志前缀
     * @return
     */
    String identifier();

    /**
     * 参数校验逻辑
     * @param request
     */
    default void checkParameters(REQ request){

    }

    /**
     * 执行业务逻辑
     * @param request 请求参数
     * @return 返回结果
     */
    RES execute(REQ request);
}
