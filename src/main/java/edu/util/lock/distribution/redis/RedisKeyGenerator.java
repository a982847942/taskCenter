package edu.util.lock.distribution.redis;

/**
 *
 * 约束Redis Key的产生逻辑  形成标准
 * 定义一个标准的redis key的前缀含有四个信息: [bizType}:{domain}:{key的业务含义}:{value的业务含义}:{key的值}
 * 可以通过具体的enum通过实现本接口来获得这些约束
 * @author brain
 * @version 1.0
 * @date 2024/10/26 14:10
 */
public interface RedisKeyGenerator {

    /**
     * 业务码
     *
     * @return
     */
    String getBiz();

    /**
     * 领域
     *
     * @return
     */
    String getDomain();

    /**
     * key的含义， 一定简洁一些
     *
     * @return
     */
    String getKeyMeaning();

    /**
     * value的含义  同样要简洁一些
     *
     * @return
     */
    String getValueMeaning();

    default String generate(String keyVal){
        return String.format("%s:%s:%s:%s:%s", getBiz(), getDomain(), getKeyMeaning(), getValueMeaning(), keyVal);
    }
}
