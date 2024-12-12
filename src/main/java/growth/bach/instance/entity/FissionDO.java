package growth.bach.instance.entity;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * rny.fission
 * @author brain
 * @version 1.0
 * @date 2024/11/2 11:11
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FissionDO {
    private Long id;
    private String activityId;
    private String inviterId;
    private String helperId;
    private String aim;
    private Boolean isNew;
    private LocalDate date;
    private String status;
    private Timestamp createTime;
    private Timestamp updateTime;
    private JSON type;
}
