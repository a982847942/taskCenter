package growth.bach.instance.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/2 10:54
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class InviteAssistRecordDO {
    private Long id;
    private String activityId;
    /**
     * 发起邀请用户ID
     */
    private String inviterUserId;
    /**
     * 助力用户ID
     */
    private String assistUserId;

    private Long taskInstanceId;
    /**
     * 扩展信息
     */
    private JSONObject extra;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    /**
     * 总的数量，按照assist_user_id或者inviter_user_id或extra中所有的数量信息
     * 在DB的表中不存在此字段信息，只是用来进行计算逻辑
     */
    private Long totalPrizeQuantity;

}
