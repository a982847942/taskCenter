package growth.bach.common.lang.spi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 服务提供者
 * @author brain
 * @version 1.0
 * @date 2024/11/1 10:34
 */
public interface Provider<T> {
    Class<? extends T> type();
    T convert(Object object);
    default T get(String json){
        return JSON.parseObject(json, type());
    }
    default T get(JSONObject jsonObject){
        return jsonObject.toJavaObject(type());
    }

    default T get(Object object){
        return convert(object);
    }
}
