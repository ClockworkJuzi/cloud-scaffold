package pers.regulus.cloud.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;
import pers.regulus.cloud.common.constant.CommonConstants;
import pers.regulus.cloud.common.util.StringUtils;

/**
 * JWT Helper
 *
 * @author Regulus
 */
public class JwtHelper {

    private static RsaKeyHelper rsaKeyHelper = new RsaKeyHelper();

    /**
     * 密钥加密 token
     *
     * @param jwtInfo
     * @param privKeyPath
     * @param expire
     * @return
     * @throws Exception
     */
    public static String generateToken(IJwtInfo jwtInfo, String privKeyPath) throws Exception {
        String compactJws = Jwts.builder()
                .setSubject(jwtInfo.getUsername())
                .claim(CommonConstants.JWT_KEY_USER_ID, jwtInfo.getUserId())
                .claim(CommonConstants.JWT_KEY_NAME, jwtInfo.getName())
                .setExpiration(DateTime.now().plusSeconds(CommonConstants.JWT_KEY_TOKEN_EXPIRE).toDate())
                .signWith(SignatureAlgorithm.RS256, rsaKeyHelper.getPrivateKey(privKeyPath))
                .compact();
        return compactJws;
    }

    /**
     * 密钥加密 token
     *
     * @param jwtInfo
     * @param privKey
     * @param expire
     * @return
     * @throws Exception
     */
    public static String generateToken(IJwtInfo jwtInfo, byte[] privKey) throws Exception {
        String compactJws = Jwts.builder()
                .setSubject(jwtInfo.getUsername())
                .claim(CommonConstants.JWT_KEY_USER_ID, jwtInfo.getUserId())
                .claim(CommonConstants.JWT_KEY_NAME, jwtInfo.getName())
                .claim(CommonConstants.JWT_KEY_TOKEN_ID, jwtInfo.getTokenId())
                .setExpiration(DateTime.now().plusSeconds(CommonConstants.JWT_KEY_TOKEN_EXPIRE).toDate())
                .signWith(SignatureAlgorithm.RS256, rsaKeyHelper.getPrivateKey(privKey))
                .compact();
        return compactJws;
    }

    /**
     * 公钥解析 token
     *
     * @param token
     * @param publicKeyPath
     * @return
     * @throws Exception
     */
    public static Jws<Claims> parseToken(String token, String publicKeyPath) throws Exception {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(rsaKeyHelper.getPublicKey(publicKeyPath)).parseClaimsJws(token);
        return claimsJws;
    }

    /**
     * 公钥解析 token
     *
     * @param token
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static Jws<Claims> parseToken(String token, byte[] publicKey) throws Exception {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(rsaKeyHelper.getPublicKey(publicKey)).parseClaimsJws(token);
        return claimsJws;
    }

    /**
     * 获取 token 中的用户信息
     *
     * @param token
     * @param publicKeyPath
     * @return
     * @throws Exception
     */
    public static IJwtInfo getInfoFromToken(String token, String publicKeyPath) throws Exception {
        Jws<Claims> claimsJws = parseToken(token, publicKeyPath);
        Claims body = claimsJws.getBody();
        return new JwtInfo(body.getSubject(), StringUtils.getObjectValue(body.get(CommonConstants.JWT_KEY_USER_ID)), StringUtils.getObjectValue(body.get(CommonConstants.JWT_KEY_NAME)));
    }

    /**
     * 获取 token 中的用户信息
     *
     * @param token
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static IJwtInfo getInfoFromToken(String token, byte[] publicKey) throws Exception {
        if (token.startsWith("Bearer")) {
            token = token.replace(CommonConstants.JWT_KEY_TOKEN_PREFIX, "");
        }
        Jws<Claims> claimsJws = parseToken(token, publicKey);
        Claims body = claimsJws.getBody();
        return new JwtInfo(body.getSubject(), StringUtils.getObjectValue(body.get(CommonConstants.JWT_KEY_USER_ID)), StringUtils.getObjectValue(body.get(CommonConstants.JWT_KEY_NAME)), StringUtils.getObjectValue(body.get(CommonConstants.JWT_KEY_TOKEN_ID)));
    }

}
