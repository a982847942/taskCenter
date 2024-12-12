package growth.bach.startegy;

import com.google.common.collect.Maps;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/2 10:29
 */
public abstract class BaseStrategy<T> {
    @Data
    public static class InviteStrategyRes{
        boolean passed;
        double decay;
        int userType;
        Long quality;
    }
    public InviteStrategyRes satifyAssistCondition(T t){
        InviteStrategyRes res = new InviteStrategyRes();
        res.setPassed(false);
        res.setDecay(1.0);
        return res;
    }
    public Map<String, String> getSpamParams(){
        // RPC工具类 获取Context上下文控参数
        return Maps.newHashMap();
    }
}
