package growth.bach.sharecode;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import growth.bach.sharecode.repository.InternalRedisKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.simple.RandomSource;
import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;

/**
 * @author brain
 * @version 1.0
 * @date 2024/11/2 13:34
 */
@Slf4j
public class ShareCodeService {
    @Resource
    private Jedis smallDataRedis;

    @Resource
    private RedUserServiceManager redUserServiceManager;

    private final String SHARE_CODE_FORMAT = "string:%s:%s";

    public String generateWithExpire(ShareCodeDTO shareCodeDTO, String activityId, int expireSecond) {
        // sharecodeDTO 转化为String
        String shareCodeString = null;
        try {
            shareCodeString = JacksonKits.getObjectMapper().writeValueAsString(shareCodeDTO);
        } catch (JsonProcessingException e) {
            log.error("share code param encode error, shareCodeDTP: {}", shareCodeDTO, e);
//            throw new ParamsInvalidException(BachErrorCode.SHARE_CODE_PARAMS_FORMAT_ERROR);
        }


        int retry = 3;
        String redisRes = null;
        String code;
        String redisKey;
        do {
            code = fixedLengthRanStr(15);
            redisKey = InternalRedisKey.ASSIST_INVITE_EXPIRE_SHARE_CODE.generate(String.format(SHARE_CODE_FORMAT, activityId, code));
            redisRes = smallDataRedis.get(redisKey);
        } while (!StringUtils.isEmpty(redisRes) && retry-- > 0);

        if (!StringUtils.isEmpty(redisRes)) {
//            throw new SystemLogicException(BachErrorCode.SHARE_CODE_REPEATED_ERROR);
        }

        smallDataRedis.setex(redisKey, expireSecond, shareCodeString);

        return code;
    }

    private String fixedLengthRanStr(int fixedLength) {
        UniformRandomProvider rng = RandomSource.create(RandomSource.MT);
        return new RandomStringGenerator.Builder().withinRange('0', 'z')
                .filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS)
                .usingRandom(rng::nextInt).build().generate(fixedLength);
    }

    public ShareCodeDTO decodeShareCode(String code, String activityId) {
        if (StringUtils.isEmpty(code)) {
//            throw new ParamsInvalidException(BachErrorCode.SHARE_CODE_DECODE_PARAMS_LACK);
        }
        String shCode = smallDataRedis.get(InternalRedisKey.ASSIST_INVITE_EXPIRE_SHARE_CODE.generate(String.format(SHARE_CODE_FORMAT, activityId, code)));

        if (StringUtils.isEmpty(shCode)) {
            log.error("shareCode has expire, activityId: {}, shareCode: {}", activityId, code);
//            throw new SystemLogicException(BachErrorCode.SHARE_CODE_EXPIRE);
        }
        ShareCodeDTO shDTO = JSON.parseObject(shCode, ShareCodeDTO.class);
        return shDTO;
    }

    public UserBriefInfo decodeUserInfoByShareCode(String sc, String activityId) {
        ShareCodeDTO scd = decodeShareCode(sc, activityId);
        UserInfoDTO ui = redUserServiceManager.getUserInfo(scd.getInviterId());
        UserBriefInfo ubi = new UserBriefInfo();
        ubi.setUserName(userNameCap(ui.getNickName()));
        ubi.setAvatarUrl(ui.getAvatarUrl());

        return ubi;
    }

    public String userNameCap(String name) {
        return name.length() > 2 ?
                name.charAt(0) + "***" + name.charAt(name.length() - 1) :
                (name.isEmpty() ? "***" : name.charAt(0) + "***");
    }
}
