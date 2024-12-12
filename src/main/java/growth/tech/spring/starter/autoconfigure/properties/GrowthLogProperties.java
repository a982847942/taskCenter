package growth.tech.spring.starter.autoconfigure.properties;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/30 10:57
 */

import com.google.common.collect.Lists;
import growth.tech.spring.properties.LogItem;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * SDK的配置参数
 */
@Data
@ConfigurationProperties(prefix = "growth.tech.log")
public class GrowthLogProperties {

    /** 生效开关 */
    private boolean enabled;

    /** 开启日志的 server 配置参数 */
    private List<LogItem> serverLogConfig = Lists.newArrayList();

    /** 开启日志的 server 配置参数 */
    private List<LogItem> clientLogConfig = Lists.newArrayList();

}