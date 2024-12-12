//package growth.tech.spring.starter.autoconfigure;
//
//import growth.tech.config.annotation.Config;
//import growth.tech.spring.starter.autoconfigure.properties.GrowthConfigProperties;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.annotation.Resource;
//
///**
// * @author brain
// * @version 1.0
// * @date 2024/10/30 10:58
// */
//@Configuration
//@ConditionalOnClass({Config.class})
//@EnableConfigurationProperties(value = GrowthConfigProperties.class)
//@ConditionalOnProperty(prefix = "growth.tech.config", name = "enabled", havingValue = "true")
//public class GrowthConfigAutoConfiguration {
//
//    @Resource
//    private GrowthConfigProperties properties;
//
//    @Resource
//    private ApplicationContext applicationContext;
//
//    @Bean(initMethod = "init", destroyMethod = "shutdown")
//    @ConditionalOnMissingBean
//    public ConfigEngine growthConfigEngine(ConfigRepository configRepository, List<ConfigProvider> configProviderList) {
//        ConfigEngine configEngine = new ConfigEngine();
//        configEngine.setConfigRepository(configRepository);
//        configEngine.setConfigProviderList(configProviderList);
//        configEngine.setReloadInterval(properties.getReloadInterval());
//        return configEngine;
//    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    public ConfigRepository growthNamedParameterJdbcTemplateConfigRepository() {
//        DataSource dataSource = applicationContext.getBean(properties.getDatasourceBeanName(), DataSource.class);
//        AssertUtils.isNotNull(dataSource, CONFIG_ERROR, "can not get datasource with beanName:"
//                + properties.getDatasourceBeanName());
//        AssertUtils.isNotBlank(properties.getTableName(), CONFIG_ERROR, "tableName is required!");
//        return new NamedParameterJdbcTemplateConfigRepository(properties.getAppName(), properties.getTableName(),
//                dataSource);
//    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    public ConfigProvider growthDatabaseConfigProvider(ConfigRepository configRepository) {
//        return new DatabaseConfigProvider(configRepository);
//    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    public GrowthTechConfigService.Iface growthConfigServerFacade(ConfigRepository configRepository) {
//        return new GrowthConfigServerFacade(configRepository);
//    }
//
//    @Bean
//    public ServiceBuilder<GrowthTechConfigService.Iface> growthConfigServerFacadeBuilder(
//            GrowthTechConfigService.Iface configFacade) {
//        AssertUtils.isTrue(properties.getRpcPort() > 0, CONFIG_ERROR,
//                "growth.tech.config.rpcPort is required!");
//        return ServiceBuilder.fromInstance(GrowthTechConfigService.Iface.class, properties.getRpcPort(), configFacade)
//                .withThreadSize(properties.getThreadSize());
//    }
//
//}
