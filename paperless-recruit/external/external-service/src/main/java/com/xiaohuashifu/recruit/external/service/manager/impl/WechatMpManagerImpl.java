package com.xiaohuashifu.recruit.external.service.manager.impl;

import com.xiaohuashifu.recruit.common.constant.App;
import com.xiaohuashifu.recruit.external.service.manager.WechatMpManager;
import com.xiaohuashifu.recruit.external.service.manager.impl.constant.WechatMpDetails;
import com.xiaohuashifu.recruit.external.service.pojo.dto.Code2SessionDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/20 15:53
 */
@Component
public class WechatMpManagerImpl implements WechatMpManager {

    /**
     * 请求code2Session的url
     */
    @Value("${wechat.mp.code-2-session-url}")
    private String code2SessionUrl;

    /**
     * 获取access_token的url
     */
    @Value("${wechat.mp.access-token-url}")
    private String accessTokenUrl;

    /**
     * 发送模板消息的url
     */
    @Value("${wechat.mp.template-message-url}")
    private String templateMessageUrl;

    /**
     * 获取DailyVisitTrend的url
     */
    @Value("${wechat.mp.daily-visit-trend-url}")
    private String dailyVisitTrendUrl;

    private final WechatMpDetails wechatMpDetails;

    private final RestTemplate restTemplate;

    private final RedisTemplate<Object, Object> redisTemplate;

    public WechatMpManagerImpl(WechatMpDetails wechatMpDetails, RestTemplate restTemplate,
                               RedisTemplate<Object, Object> redisTemplate) {
        this.wechatMpDetails = wechatMpDetails;
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
    }


    /**
     * 通过code获取封装过的Code2SessionDTO
     * @param code String
     * @param wechatMp 小程序类别
     * @return Code2SessionDTO
     */
    @Override
    public Code2SessionDTO getCode2Session(String code, App wechatMp) {
        String url = MessageFormat.format("{0}?appid={1}&secret={2}&js_code={3}&grant_type=authorization_code",
                code2SessionUrl, wechatMpDetails.getAppId(wechatMp), wechatMpDetails.getSecret(wechatMp), code);
        ResponseEntity<Code2SessionDTO> responseEntity = restTemplate.getForEntity(url, Code2SessionDTO.class);
        return responseEntity.getBody();
    }

    /**
     * 团队管理小程序access-token的redis key名
     */
    private static final String REDIS_KEY = "tm:wechat:mp:access:token";

//    /**
//     * 获取access-token
//     *
//     * @return AccessTokenDTO
//     */
//    @Override
//    public Optional<String> getAccessToken() {
//        String accessToken = redisTemplate.opsForValue().get(REDIS_KEY);
//        if (accessToken == null) {
//            logger.warn("Get access token fail.");
//            return Optional.empty();
//        }
//        return Optional.of(accessToken);
//    }
//
//    /**
//     * 获取新的access-token
//     * 并添加到redis
//     * 并设置过期时间
//     *
//     * @return AccessTokenDTO
//     */
//    @Override
//    public Optional<AccessTokenDTO> getNewAccessToken() {
//        // 获取access-token
//        String url = MessageFormat.format("{0}?grant_type={1}&appid={2}&secret={3}",
//                WeChatMpConsts.ACCESS_TOKEN_URL, WeChatGrantTypeEnum.CLIENT_CREDENTIAL.getValue(),
//                WeChatMp.TM.getAppId(), WeChatMp.TM.getSecret());
//        ResponseEntity<AccessTokenDTO> entity = restTemplate.getForEntity(url, AccessTokenDTO.class);
//        if (Objects.requireNonNull(entity.getBody()).getAccess_token() == null) {
//            logger.warn("Get access token fail.");
//            return Optional.empty();
//        }
//
//        // 添加到redis
//        String status = cacheService.set(REDIS_KEY, entity.getBody().getAccess_token());
//        if (!status.equals(RedisStatus.OK.name())) {
//            logger.warn("Set access token fail.");
//            return Optional.empty();
//        }
//
//        // 设置access-token在redis的过期时间
//        Long expire = cacheService.expire(REDIS_KEY, entity.getBody().getExpires_in());
//        if (expire == 0) {
//            logger.warn("Set redis expire fail. {}", entity.getBody().getAccess_token());
//        }
//
//        return Optional.of(entity.getBody());
//    }
//
//    @Override
//    public Result<Void> sendTemplateMessage(MessageTemplateDTO messageTemplate) {
//        Optional<String> accessToken = getAccessToken();
//        if (!accessToken.isPresent()) {
//            return false;
//        }
//
//        String url = MessageFormat.format(WeChatMpConsts.TEMPLATE_MESSAGE_URL + "?access_token={0}",
//                accessToken.get());
//        ResponseEntity<WeChatMpResponseDTO> responseEntity = restTemplate.postForEntity(url, messageTemplate, WeChatMpResponseDTO.class);
//        System.out.println(responseEntity.getBody());
//        return Objects.requireNonNull(responseEntity.getBody()).getErrcode().equals(0);
//    }
}
