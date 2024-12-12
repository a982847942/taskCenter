package edu.util.login;

import edu.util.context.BizType;
import edu.util.login.handle.Handler;
import edu.util.login.handle.ReqHandler;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

/**
 * 请求处理分发器
 * @author brain
 * @version 1.0
 * @date 2024/10/26 19:29
 */
public class Dispatcher {
    @Resource
    private List<Handler<?, ?>> handlers;
    private Map<BizType, Map<Class, Handler>> register = new HashMap<>();

    @PostConstruct
    public void init() {
        for (Handler<?, ?> handler : handlers) {
            ReqHandler anno = extractAnnotation(handler);
            if (anno == null) {
                continue;
            }
            for (BizType bizType : anno.bizType()) {
                register.computeIfAbsent(bizType, b -> new HashMap<>()).put(anno.support(), handler);
            }
        }
    }

    private ReqHandler extractAnnotation(Handler<?, ?> handler) {
        return handler.getClass().getAnnotation(ReqHandler.class);
    }

    public <Req, Resp> Resp dispatch(BizType bizType, Req req) {
        Map<Class, Handler> candidates = register.getOrDefault(bizType, new HashMap<>());
        Handler handler = Optional.ofNullable(candidates.get(req.getClass())).orElseGet(
                () ->
                        candidates.entrySet().stream().filter(entry -> {
                            return entry.getKey().isAssignableFrom(req.getClass());
                        }).findAny().map(entry -> entry.getValue()).orElse(null));
        if (handler == null) {
            //记录日志
            throw new RuntimeException("没有能够处理这种业务类型的处理器");
        }
        return (Resp) handler.handle(req);
    }
}
