package growth.bach.instance.repository;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import growth.bach.instance.dto.InviteAssistRecordDTO;
import growth.bach.instance.entity.FissionDO;
import growth.bach.instance.entity.InviteAssistRecordDO;
import growth.bach.instance.resp.InviteAssistInfoResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/2 11:04
 */
@Slf4j
@Component
public class InviteAssistRecordRepository {
//    @Resource
//    private InviteAssistRecordMapper inviteAssistRecordMapper;

    @Resource
//    private FissionMapper fissionMapper;

    public List<InviteAssistRecordDO> getByInviteUserId(String activityId, String inviteUserId) {
//        QueryWrapper<InviteAssistRecordDO> qw = new QueryWrapper<>();
//        qw.eq("activity_id", activityId);
//        qw.eq("invite_user_id", inviteUserId);
//
//        return inviteAssistRecordMapper.selectList(qw);
        return Lists.newArrayList();
    }

    public List<InviteAssistRecordDO> getByAssistUserId(String activityId, String assistUserId) {
//        QueryWrapper<InviteAssistRecordDO> qw = new QueryWrapper<>();
//        qw.eq("activity_id", activityId);
//        qw.eq("assist_user_id", assistUserId);
//
//        return inviteAssistRecordMapper.selectList(qw);
        return Lists.newArrayList();
    }

    public InviteAssistRecordDO createInviteAssistRecord(InviteAssistRecordDO inviteAssistRecordDO) {
//        int effectRow = inviteAssistRecordMapper.insert(inviteAssistRecordDO);
//        try {
//            FissionDO fissionDO = buildFission(inviteAssistRecordDO);
//            fissionMapper.insert(fissionDO);
//        } catch (Exception e) {
//            log.error("createInviteAssistRecord buildFission error, inviteAssistRecordDO: {}", inviteAssistRecordDO, e);
//        }
//        if (effectRow == 1) {
//            return inviteAssistRecordDO;
//        }

        return null;
    }

    public InviteAssistInfoResp getByInviteUserIdByPage(String activityId, String inviteUserId, int page, int pageSize) {
        InviteAssistInfoResp resp = new InviteAssistInfoResp();
        page = page <= 0 ? 1 : page;
        pageSize = pageSize <= 0 ? 10 : pageSize;

//        Page<InviteAssistRecordDO> pageObj = new Page<>(page, pageSize);
//        QueryWrapper<InviteAssistRecordDO> qw = new QueryWrapper<>();
//        qw.eq("activity_id", activityId);
//        qw.eq("inviter_user_id", inviteUserId);
//        qw.orderByDesc("id");
//
//        IPage<InviteAssistRecordDO> res = inviteAssistRecordMapper.selectPage(pageObj, qw);
//        resp.setTotalNum(res.getTotal());
//        resp.setPage((int)res.getCurrent());
//        resp.setPageSize((int) res.getPages());

//        List<InviteAssistRecordDTO> dtoList = new ArrayList<>();
//        resp.setInviteAssistRecordDTOList(dtoList);
//        for (InviteAssistRecordDO record : res.getRecords()) {
//            InviteAssistRecordDTO im = InviteAssistRecordDTO.from(record);
//            resp.getInviteAssistRecordDTOList().add(im);
//        }
//
//        String sql = "SUM(IFNULL(CAST(JSON_UNQUOTE(JSON_EXTRACT(extra, '$.prizeQuantity')) AS UNSIGNED), 0)) AS total_prize_quantity";
//        qw.select(sql);
//        InviteAssistRecordDO qs = inviteAssistRecordMapper.selectOne(qw);
//        resp.setTotalPrizeQuantity((Objects.isNull(qs) || Objects.isNull(qs.getTotalPrizeQuantity())) ? 0 : qs.getTotalPrizeQuantity());

        return resp;
    }

    private FissionDO buildFission(InviteAssistRecordDO it) {
        FissionDO fission = FissionDO.builder()
                .helperId(it.getAssistUserId())
                .inviterId(it.getInviterUserId())
                .build();
        fission.setActivityId(it.getActivityId());
        fission.setAim("FISSION");
        fission.setDate(LocalDate.now());
        fission.setStatus("SUCCESS");
        JSONObject extra = it.getExtra();
        Integer userType = Optional.ofNullable(extra).map(f -> f.getInteger("userType")).orElse(0);
        fission.setIsNew(Integer.valueOf(1).equals(userType));
        fission.setType(it.getExtra());
        return fission;
    }
}
