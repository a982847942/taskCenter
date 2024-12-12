package growth.bach.instance.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/31 22:38
 */
@Getter
@AllArgsConstructor
public enum TaskType {
    UNKNOWN(-1, "UNKNOWN"),
    TOPIC_NOTE_PUBLISH(1, "TOPIC_NOTE_PUBLISH"), //话题笔记发布
    INVITE_ASSISTANCE(2, "INVITE_ASSISTANCE"), // 邀请助力
    TOPIC_NOTE_BROWSE(3, "TOPIC_NOTE_BROWSE"), // 话题笔记浏览
    MUYINGDATI_DANREN(100, "MUYINGDATI_DANREN"),// 母婴单体单人
    MUYINGDATI_ZUDUI(101, "MUYINGDATI_ZUDUI") // 母婴单体组队
    ;
    private int code;
    private String status;

    public static TaskType from(int code){
        return Arrays.stream(TaskType.values()).filter(it -> it.getCode() == code).findFirst().orElse(UNKNOWN);
    }

    public static TaskType from(String status){
        return Arrays.stream(TaskType.values()).filter(it -> it.getStatus().equals(status)).findFirst().orElse(UNKNOWN);
    }
}
