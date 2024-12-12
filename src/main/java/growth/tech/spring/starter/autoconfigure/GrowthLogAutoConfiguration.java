package growth.tech.spring.starter.autoconfigure;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/30 10:58
 */

import growth.tech.spring.client.GrowthClientProxyPostProcessor;
import growth.tech.spring.server.GrowthServerProxyPostProcessor;
import growth.tech.spring.starter.autoconfigure.properties.GrowthLogProperties;
import growth.tech.spring.utils.SpringIdentity;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author lijiawei
 * @date 2024/07/25 20:00 星期四
 */
@Configuration
@ConditionalOnClass({SpringIdentity.class})
@EnableConfigurationProperties(value = GrowthLogProperties.class)
@ConditionalOnProperty(prefix = "growth.tech.log", name = "enabled", havingValue = "true")
public class GrowthLogAutoConfiguration {

    @Resource
    private GrowthLogProperties properties;

    @Bean
    public GrowthClientProxyPostProcessor growthClientProxyPostProcessor() {
        return new GrowthClientProxyPostProcessor(properties.getClientLogConfig());
    }

    @Bean
    public GrowthServerProxyPostProcessor growthServerProxyPostProcessor() {
        return new GrowthServerProxyPostProcessor(properties.getServerLogConfig());
    }

}
