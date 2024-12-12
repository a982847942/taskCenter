package growth.tech.spring.starter.autoconfigure.properties;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/30 10:57
 */

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "growth.tech.easy-test")
public class GrowthEasyTestProperties {

    /** 生效开关 */
    private boolean enabled;

    /** EasyTestFacade的RedRpcService端口号。需要和服务自己的RPC端口一致 */
    private int rpcPort;

    /** EasyTestFacade的RedRpcService线程数量，一般不用改 */
    private int threadSize = 2;

}
