package growth.tech.spring.starter.autoconfigure.properties;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/30 10:57
 */

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * config 的配置参数
 *
 * @author lijiawei
 * @date 2024/08/08 19:22 星期四
 */
@Data
@ConfigurationProperties(prefix = "growth.tech.config")
public class GrowthConfigProperties {

    /** 生效开关 */
    private boolean enabled;

    /** 应用名。需要和配置表的appName字段一致 */
    private String appName;

    /** 配置表名 */
    private String tableName;

    /** 配置所属datasource的beanName */
    private String datasourceBeanName;

    /** 重新加载间隔(单位毫秒)，因为必须要加载配置才能使用，所以该字段必须大于0 */
    private long reloadInterval = 10000;

    /** ConfigFacade的RedRpcService端口号。需要和服务自己的RPC端口一致 */
    private int rpcPort;

    /** ConfigFacade的RedRpcService线程数量，一般不用改 */
    private int threadSize = 5;

}
