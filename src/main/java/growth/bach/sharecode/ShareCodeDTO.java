package growth.bach.sharecode;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/2 13:34
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShareCodeDTO {
    private String activityId;
    private String inviterId;
    private Long taskInstanceId;
    @Builder.Default
    private Map<Object, Object> attribute = Maps.newHashMap();
}
