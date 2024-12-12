package edu.util.kits;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/26 14:25
 */
public class LangKits {

    public static Assert anAssert(){
        return new Assert();
    }

    public static class Assert {
        private boolean passed = true;

        private final Map<String, String> errorMsg = new HashMap<>();

        public Assert notNull(Object obj, String col, String msg) {
            if (obj == null) {
                passed = false;
                if(msg == null){
                    msg = String.format("%s should not be null", col);
                }
                errorMsg.put(col, msg);
            }
            return this;
        }

        public Assert notBlank(Object obj, String col, String msg) {
            if (ObjectUtils.isEmpty(obj)) {
                passed = false;
                if(msg == null){
                    msg = String.format("%s should not be blank", col);
                }
                errorMsg.put(col, msg);
            }
            return this;
        }
        // 可以增加 集合 数组等的判断 总体来说适合做一个简单的工具类，不适合做需要大量安全检查的场景
    }
}
