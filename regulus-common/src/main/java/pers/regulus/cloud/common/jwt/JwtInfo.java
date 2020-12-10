package pers.regulus.cloud.common.jwt;

import pers.regulus.cloud.common.util.UUIDUtils;

import java.io.Serializable;

/**
 * Jwt Infos
 *
 * @author Regulus
 */
public class JwtInfo implements Serializable, IJwtInfo {
    private static final long serialVersionUID = -3269332699904359181L;

    private String username;
    private String userId;
    private String name;
    private String tokenId;

    public JwtInfo() {
    }

    public JwtInfo(String username, String userId, String name) {
        this.username = username;
        this.userId = userId;
        this.name = name;
        this.tokenId = UUIDUtils.generateShortUuid();
    }

    public JwtInfo(String username, String userId, String name, String tokenId) {
        this.username = username;
        this.userId = userId;
        this.name = name;
        this.tokenId = tokenId;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getTokenId() {
        return this.tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        JwtInfo jwtInfo = (JwtInfo) o;
        if (this.username != null ? !username.equals(jwtInfo.username) : jwtInfo.username != null) {
            return false;
        }

        return this.userId != null ? this.userId.equals(jwtInfo.userId) : jwtInfo.userId == null;
    }

    @Override
    public int hashCode() {
        int result = this.username != null ? this.username.hashCode() : 0;
        result = 31 * result + (this.userId != null ? this.userId.hashCode() : 0);
        return result;
    }
}
