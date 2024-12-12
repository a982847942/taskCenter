package growth.bach.instance.engine.parser;

import com.alibaba.fastjson.JSONObject;
import growth.bach.common.lang.spi.IdentityScanner;
import growth.bach.common.lang.spi.Provider;
import growth.bach.common.lang.spi.ProviderLoader;
import growth.bach.instance.engine.event.reg.TriggerCondition;
import growth.bach.instance.engine.task.meta.BaseCreateCondition;
import growth.bach.instance.engine.task.meta.BaseEnterCondition;
import growth.bach.instance.engine.task.meta.BasePromoteCondition;
import growth.bach.instance.engine.task.meta.BaseSuccessCondition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TriggerCondition翻译器
 *
 * @author brain
 * @version 1.0
 * @date 2024/11/1 10:31
 */
public enum JsonParser {
    TRIGGER_CONDITION(TriggerCondition.class, "com.xiaohongshu.growth.bach"),
    CREATE_CONDITION(BaseCreateCondition.class, "com.xiaohongshu.growth.bach"),
    ENTER_CONDITION(BaseEnterCondition.class, "com.xiaohongshu.growth.bach"),
    PROMOTE_CONDITION(BasePromoteCondition.class, "com.xiaohongshu.growth.bach"),
    SUCCESS_CONDITION(BaseSuccessCondition.class, "com.xiaohongshu.growth.bach"),
    ;

    private final Map<String, Provider<?>> PROVIDER_MAP = new HashMap<>();
    private final Map<String, Class<?>> ID_ENTITY_MAP = new HashMap<>();

    <T> JsonParser(Class<T> type, String packagePath) {
        ID_ENTITY_MAP.putAll(new IdentityScanner<>().scan(type, packagePath));
        List<Provider<? extends T>> loaded = new ProviderLoader<T>().load(type);
        for (Provider<? extends T> provider : loaded) {
            PROVIDER_MAP.put(provider.type().getCanonicalName(), provider);
        }
    }

    @SuppressWarnings("all")
    public <T> T parse(JSONObject jsonObject, String id){
        Class<? extends T> entityClass = (Class<? extends T>) ID_ENTITY_MAP.get(id);
        Provider<? extends T> provider = (Provider<? extends T>) PROVIDER_MAP.get(entityClass.getCanonicalName());
        if (provider == null){
            return jsonObject.toJavaObject(entityClass);
        }
        return provider.get(jsonObject);
    }
}
