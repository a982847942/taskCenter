package growth.tech.spring.properties;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/30 10:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogItem {
    /**
     * 打印日志的接口名，不能为null
     */
    private String interfaceName;

    /**
     * 中间件的Context太大，加一个参数控制是否打印该对象
     */
    private boolean printContext = false;

    /**
     * 该interface下跳过日志的方法名
     */
    private List<String> skipMethodNames = Lists.newArrayList();
}
