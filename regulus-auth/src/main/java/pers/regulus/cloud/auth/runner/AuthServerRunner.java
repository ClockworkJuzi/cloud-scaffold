package pers.regulus.cloud.auth.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import pers.regulus.cloud.auth.config.KeyConfig;
import pers.regulus.cloud.common.jwt.RsaKeyHelper;

import java.util.Map;

/**
 * AuthServer Runner
 *
 * @author Regulus
 */
@Configuration
public class AuthServerRunner implements CommandLineRunner {

    private static final String REDIS_USER_PRIV_KEY = "CLOUD:AUTH:JWT:PRIV";
    private static final String REDIS_USER_PUB_KEY = "CLOUD:AUTH:JWT:PUB";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private KeyConfig keyConfig;

    @Override
    public void run(String... args) throws Exception {
        if (redisTemplate.hasKey(REDIS_USER_PRIV_KEY) && redisTemplate.hasKey(REDIS_USER_PUB_KEY)) {
            keyConfig.setUserPrivKey(RsaKeyHelper.toBytes(redisTemplate.opsForValue().get(REDIS_USER_PRIV_KEY).toString()));
            keyConfig.setUserPubKey(RsaKeyHelper.toBytes(redisTemplate.opsForValue().get(REDIS_USER_PUB_KEY).toString()));
        } else {
            Map<String, byte[]> keyMap = RsaKeyHelper.generateKey(keyConfig.getUserSecret());
            keyConfig.setUserPrivKey(keyMap.get("priv"));
            keyConfig.setUserPubKey(keyMap.get("pub"));

            redisTemplate.opsForValue().set(REDIS_USER_PRIV_KEY, RsaKeyHelper.toHexString(keyMap.get("priv")));
            redisTemplate.opsForValue().set(REDIS_USER_PUB_KEY, RsaKeyHelper.toHexString(keyMap.get("pub")));
        }
    }

}
