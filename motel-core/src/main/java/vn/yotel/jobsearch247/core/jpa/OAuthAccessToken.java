package vn.yotel.jobsearch247.core.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name="oauth_access_token")
public class OAuthAccessToken  implements Serializable {
    public static final String PRO_CREATED_TIME = "createdTime";

    /**
     * access_token
     */
    @Column(name="token_id", length= 256, nullable = true)
    @Id
    private String tokenId;


//	@Lob
//	@Basic(fetch=FetchType.LAZY)
//	@Column(name="token", columnDefinition="BLOB NOT NULL")
//	private OAuth2AccessToken token;

    @Column(name = "authentication_id",length=256,nullable = true)
    private String authenticationId;

    @Column(name = "user_name",length=256,nullable = true)
    private String userName;

    @Column(name = "client_id",length=256,nullable = true)
    private String clientId;

//	@Lob
//	@Basic(fetch=FetchType.LAZY)
//	@Column(name="authentication", columnDefinition="BLOB NOT NULL")
//	private OAuth2Authentication authentication;

    @Column(name = "refresh_token",length=256,nullable = true)
    private String refreshToken;

    @Column(name="created_time",nullable=true)
    private Timestamp createdTime;


    public OAuthAccessToken() {
    }

//	public OAuthAccessToken(
//			final OAuth2AccessToken oAuth2AccessToken,
//			final OAuth2Authentication authentication,
//			final String authenticationId) {
//		this.tokenId = oAuth2AccessToken.getValue();
//		this.token = oAuth2AccessToken;
//		this.authenticationId = authenticationId;
//		this.userName = authentication.getName();
//		this.clientId = authentication.getOAuth2Request().getClientId();
//		this.authentication = authentication;
//		this.refreshToken = oAuth2AccessToken.getRefreshToken() == null ? null: oAuth2AccessToken.getRefreshToken().getValue();
//	}

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

//	public OAuth2AccessToken getToken() {
//		return token;
//	}
//
//	public void setToken(OAuth2AccessToken oAuth2AccessToken) {
//		this.token = oAuth2AccessToken;
//	}

    public String getAuthenticationId() {
        return authenticationId;
    }

    public void setAuthenticationId(String authenticationId) {
        this.authenticationId = authenticationId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

//	public OAuth2Authentication getAuthentication() {
//		return authentication;
//	}
//
//	public void setAuthentication(OAuth2Authentication authentication) {
//		this.authentication = authentication;
//	}


    public String getRefreshToken() {
        return refreshToken;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "OAuthAccessToken [tokenId=" + tokenId + ", authenticationId="
                + authenticationId + ", userName=" + userName + ", clientId="
                + clientId + ", refreshToken=" + refreshToken + "]";
    }
}
