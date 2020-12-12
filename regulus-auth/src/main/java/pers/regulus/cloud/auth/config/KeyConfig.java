package pers.regulus.cloud.auth.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Key Configuration
 *
 * @author Regulus
 */
@Configuration
@Data
public class KeyConfig {

    @Value("jwt.rsa-secret")
    private String userSecret;

    private byte[] userPubKey;

    private byte[] userPrivKey;

}
