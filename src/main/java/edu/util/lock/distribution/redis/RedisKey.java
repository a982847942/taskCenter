package edu.util.lock.distribution.redis;

/**
 * 一个简单的RedisKey案列
 * @author brain
 * @version 1.0
 * @date 2024/10/26 14:16
 */
public enum RedisKey implements RedisKeyGenerator{

    USER_LANDING_PAGE_CACHE("attibution", "oId_uId", "landingPage")
    ;

    private String bizType; // 实际中需要使用BizType枚举类
    private String domain;
    private String keyMeaning;
    private String valueMeaning;

    RedisKey(String bizType, String domain, String keyMeaning, String valueMeaning) {
        this.bizType = bizType;
        this.domain = domain;
        this.keyMeaning = keyMeaning;
        this.valueMeaning = valueMeaning;
    }

    RedisKey(String domain, String keyMeaning, String valueMeaning) {
        this.domain = domain;
        this.keyMeaning = keyMeaning;
        this.valueMeaning = valueMeaning;
    }

    public RedisKey setBizType(String bizType) {
        this.bizType = bizType;
        return this;
    }

    @Override
    public String getBiz() {
        return bizType;
    }

    @Override
    public String getDomain() {
        return domain;
    }

    @Override
    public String getKeyMeaning() {
        return keyMeaning;
    }

    @Override
    public String getValueMeaning() {
        return valueMeaning;
    }

    public String generate(String keyVal){
        if (bizType == null){
            // bizType = DomainContextHolder.getContext().getBizType();
            if (bizType == null){
                // double check
                throw new IllegalArgumentException("错误使用RedisKey, 没有设置bizType");
            }
        }
        return RedisKeyGenerator.super.generate(keyVal);
    }
}
