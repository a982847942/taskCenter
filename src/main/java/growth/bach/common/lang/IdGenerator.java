package growth.bach.common.lang;

import cn.hutool.core.util.IdUtil;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/2 19:07
 */
public class IdGenerator {
    public static String objectId(){
        return IdUtil.randomUUID();
    }
}
