package pers.regulus.cloud.common.jwt;

public interface IJwtInfo {

    /**
     * 获取用户名
     *
     * @return
     */
    String getUsername();

    /**
     * 获取用户Id
     *
     * @return
     */
    String getUserId();

    /**
     * 获取用户名称
     *
     * @return
     */
    String getName();

    /**
     * 获取TokenId
     *
     * @return
     */
    String getTokenId();
}
