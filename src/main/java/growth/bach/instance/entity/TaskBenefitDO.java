package growth.bach.instance.entity;

import com.alibaba.fastjson.JSONObject;
import growth.bach.common.constant.Period;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/2 18:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskBenefitDO {
    private Long id;
    private Long taskMetaId;
    private Long benefitId;
    /**
     * 场景key
     * 用于将同一个奖品benefitId进行更细粒度划分，以应对复杂的发奖逻辑
     */
    private String sceneKey;
    /**
     * 自定义key
     * 用于将同一个奖品benefitId进行更细粒度划分，以应对复杂的发奖逻辑
     */
    private String customizeKey;
    private Rule rule;
    private LocalDate createTime;
    private LocalDate upddateTime;
    private boolean deleteFlag;

    @Data
    public static class Rule{
        private Period limitPeriod;
        private Integer limit;
        private JSONObject extra;
    }
}
