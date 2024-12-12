package growth.tech.spring.starter.autoconfigure;

import growth.tech.spring.easytest.facade.GrowthEasyTestServerFacade;
import growth.tech.spring.easytest.script.GroovySpringFactory;
import growth.tech.spring.starter.autoconfigure.properties.GrowthEasyTestProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/30 10:58
 */
@Configuration
@ConditionalOnClass({GrowthEasyTestServerFacade.class})
@EnableConfigurationProperties(value = GrowthEasyTestProperties.class)
@ConditionalOnProperty(prefix = "growth.tech.easy-test", name = "enabled", havingValue = "true")
public class GrowthEasyTestAutoConfiguration {

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private GrowthEasyTestProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public GroovySpringFactory growthEasyTestGroovySpringFactory() {
        return new GroovySpringFactory();
    }

    @Bean
    @ConditionalOnMissingBean
    public GrowthTechEasyTestService.Iface growthEasyTestServerFacade() {
        return new GrowthEasyTestServerFacade(applicationContext);
    }

    @Bean
    public ServiceBuilder<GrowthTechEasyTestService.Iface> growthEasyTestServerFacadeBuilder(
            GrowthTechEasyTestService.Iface easyTestFacade) {
        AssertUtils.isTrue(properties.getRpcPort() > 0, CONFIG_ERROR,
                "growth.tech.easy-test.rpcPort is required!");
        return ServiceBuilder.fromInstance(GrowthTechEasyTestService.Iface.class, properties.getRpcPort(),
                        easyTestFacade)
                .withThreadSize(properties.getThreadSize());
    }

}
