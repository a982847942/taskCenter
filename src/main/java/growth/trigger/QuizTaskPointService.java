package growth.trigger;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/2 22:03
 */
@Slf4j
public class QuizTaskPointService {
    @Resource
    private List<TaskPointInvoker<?,?,?>> taskPointInvokers;

    private final Map<String, TaskPointInvoker<?,?,?>> taskPointInvokerMap = new HashMap<>();

    public <C,A,R>  R trigger(String activityId, String userId, @Nullable QuizTaskPointConfig<A> config, A argument){
        if (config == null){
            return null;
        }
        TaskPointInvoker<?, ?, ?> invoker = (TaskPointInvoker<?, ?, ?>) taskPointInvokerMap.get(config.getPointName());
        if (invoker == null){
            // 日志 异常
        }
        Object configParameter = JSON.toJavaObject(config.getParameter(), invoker.getConfigClazz());
        return invoker.trigger(activityId, userId, config, configParameter, argument);
    }

    @PostConstruct
    public void init(){
        taskPointInvokers.forEach(invoker -> taskPointInvokerMap.put(invoker.getPointName(), invoker));
    }
}
