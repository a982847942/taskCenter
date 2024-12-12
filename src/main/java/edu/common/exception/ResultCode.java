package edu.common.exception;

/**
 * 统一错误接口
 * @author brain
 * @version 1.0
 * @date 2024/10/27 13:33
 */
public interface ResultCode {
    /**
     * 结果码
     * @return
     */
    int getCode();

    /**
     * 结果信息
     * @return
     */
    String getMessage();

    /**
     * 是否可重试
     * @return
     */
    boolean isRetryable();

    /**
     * 获取code的描述(这个字段的存在是因为code是int 类型， 根据int数字判断是什么
     * 错误还需要查询代码文档，比较费劲)
     * @return
     */
    default String getCodeDescription(){
        return String.valueOf(getCode());
    }
}
