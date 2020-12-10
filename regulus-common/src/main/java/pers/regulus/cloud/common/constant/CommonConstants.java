package pers.regulus.cloud.common.constant;

/**
 * Common Constants
 *
 * @author Regulus
 */
public class CommonConstants {

    /**
     * 字符集
     */
    public static final String UTF8 = "UTF-8";
    public static final String GBK = "GBK";

    /**
     * JWT
     */
    public static final String JWT_KEY_USER_ID = "userId";
    public static final String JWT_KEY_NAME = "name";
    public static final String JWT_KEY_TOKEN_ID = "tokenId";
    public static final String JWT_KEY_TOKEN_HEADER = "Authorization";
    public static final String JWT_KEY_TOKEN_PREFIX = "Bearer ";
    public static final int JWT_KEY_TOKEN_EXPIRE = 4 * 60 * 60;

}
