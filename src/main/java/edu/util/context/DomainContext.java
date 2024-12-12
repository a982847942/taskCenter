package edu.util.context;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/26 14:40
 */
public class DomainContext {
    private BizType bizType;

    public DomainContext(BizType bizType) {
        this.bizType = bizType;
    }

    public DomainContext() {
    }

    public BizType getBizType() {
        return bizType;
    }

    public void setBizType(BizType bizType) {
        this.bizType = bizType;
    }

    @Override
    public String toString() {
        return "DomainContext{" +
                "bizType=" + bizType +
                '}';
    }
}
