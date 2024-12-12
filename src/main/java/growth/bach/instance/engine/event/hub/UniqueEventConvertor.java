package growth.bach.instance.engine.event.hub;

import growth.bach.instance.engine.event.UniqEvent;

/**
 * 统一事件转换器
 * @author brain
 * @version 1.0
 * @date 2024/11/2 9:39
 */
public interface UniqueEventConvertor<OriginEvent, T> {
    boolean support(Object evet);
    UniqEvent<T> convert(OriginEvent event);
}
