package growth.trigger;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * 答题调用任务服务的调用点配置
 * @param <Context> yongyu trigger时，在编译器检查对应的上下文参数的类型，不要删除！
 * @author brain
 * @version 1.0
 * @date 2024/11/2 21:46
 */
@Data
public class QuizTaskPointConfig<Context> {
    private String pointName;
    private JSONObject parameter;
}
