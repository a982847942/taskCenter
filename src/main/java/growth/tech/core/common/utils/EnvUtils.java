package growth.tech.core.common.utils;

/**
 *
 * @author brain
 * @version 1.0
 * @date 2024/10/29 15:17
 */

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 环境工具类
 *
 * @author lijiawei
 * @date 2024/07/12 14:30 星期五
 */
@Slf4j
public class EnvUtils {

    /** 当前环境 */
    public static final String ENV;

    // 初始化
    static {
        ENV = getEnv();
        if (StringUtils.isEmpty(ENV)) {
            log.error("can not get env!");
        }
    }

    /**
     * 判断sit
     *
     * @return 是否sit
     */
    public static boolean isSit() {
        return StringUtils.equalsIgnoreCase("sit", ENV);
    }

    /**
     * 判断dev
     *
     * @return 是否dev
     */
    public static boolean isDev() {
        return StringUtils.equalsIgnoreCase("dev", ENV);
    }

    /**
     * 判断beta
     *
     * @return 是否beta
     */
    public static boolean isBeta() {
        return StringUtils.equalsIgnoreCase("beta", ENV) || StringUtils.equalsIgnoreCase("staging", ENV);
    }

    /**
     * 判断线上
     *
     * @return 是否线上
     */
    public static boolean isProd() {
        return StringUtils.equalsIgnoreCase("prod", ENV);
    }

    /**
     * 是否未知，目前是基于hostname判断环境的，有可能出现未知情况，需要处理
     *
     * @return 是否未知
     */
    public static boolean isUnknown() {
        return StringUtils.isEmpty(ENV);
    }

    /**
     * 获取环境标识
     *
     * @return 环境标识
     */
    private static String getEnv() {
        String value = System.getProperty("env");
        if (StringUtils.isNotEmpty(value)) {
            value = value.trim();
            log.info("decide env to [{}] by JVM system property 'env'", value);
            return value;
        }
        value = System.getenv("ENV");
        if (StringUtils.isNotEmpty(value)) {
            value = value.trim();
            log.info("decide env to {} by OS env variable 'ENV'", value);
            return value;
        }
        value = System.getenv("XHS_ENV");
        if (StringUtils.isNotBlank(value)) {
            log.info("decide env to {} by OS env variable 'XHS_ENV'", value);
            return value;
        }
        return null;
    }

}
