package growth.bach.instance.resp;

import growth.bach.instance.dto.InviteAssistRecordDTO;
import lombok.Data;

import java.util.List;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/2 11:08
 */
@Data
public class InviteAssistInfoResp {
    private List<InviteAssistRecordDTO> inviteAssistRecordDTOList;
    private Integer page;
    private Integer pageSize;
    private Long totalNum;
    private Long totalPrizeQuantity;
}
