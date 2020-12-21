package vn.yotel.jobsearch247.core.jpa;

import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name="oauth_refresh_token")
public class OAuthRefreshToken implements Serializable {


    public static final String PRO_CREATED_TIME = "createdTime";


    /**
     * refresh_token
     */
    @Column(name="token_id", length= 256, nullable = true)
    @Id
    private String tokenId;

    @Lob
    @Basic(fetch=FetchType.LAZY)
    @Column(name="token", columnDefinition="BLOB NOT NULL")
    private OAuth2RefreshToken token;


    @Lob
    @Basic(fetch=FetchType.LAZY)
    @Column(name="authentication", columnDefinition="BLOB NOT NULL")
    private OAuth2Authentication authentication;


    @Column(name="created_time",nullable=true)
    private Timestamp createdTime;



    public OAuthRefreshToken(
            OAuth2RefreshToken oAuth2RefreshToken,
            OAuth2Authentication authentication) {
        this.token = oAuth2RefreshToken;
        this.authentication = authentication;
        this.tokenId = oAuth2RefreshToken.getValue();
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }


    public OAuth2Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(OAuth2Authentication authentication) {
        this.authentication = authentication;
    }


    public OAuth2RefreshToken getToken() {
        return token;
    }

    public void setToken(OAuth2RefreshToken token) {
        this.token = token;
    }


    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "OAuthRefreshToken [tokenId=" + tokenId + "]";
    }
}
