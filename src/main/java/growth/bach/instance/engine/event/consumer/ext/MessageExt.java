package growth.bach.instance.engine.event.consumer.ext;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 小红书内部消息封装中间件(支持kafka、rocketmq)
 * @author brain
 * @version 1.0
 * @date 2024/11/2 13:51
 */
@AllArgsConstructor
@Data
public class MessageExt {
    private String qid;
    private Long offset;
    private String msgId;

    private byte[] body;
    private String topic;
    private String key;
    private String tags;
    private String serviceTag;
    private long hashId;
    private int partitionId;
    private int priority;
    // 还有storeTimeStamp  和 bornTimestamp
    public MessageExt() {
    }

    public MessageExt(String qid, Long offset, String msgId) {
        this.qid = qid;
        this.offset = offset;
        this.msgId = msgId;
    }

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    @Override
    public String toString() {
        return "MessageExt{" +
                "qid='" + qid + '\'' +
                ", offset=" + offset +
                ", msgId='" + msgId + '\'' +
                '}';
    }
}
