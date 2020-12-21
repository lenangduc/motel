package vn.yotel.jobsearch247.api.employer.config.oauthen2.token;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.yotel.jobsearch247.core.jpa.OAuthAccessToken;

import java.util.List;


@Repository(value = "oauthAccessTokenRepo")
public interface OAuthAccessTokenRepo extends JpaRepository<OAuthAccessToken, String> {

    public OAuthAccessToken findByTokenId(String tokenId);

    public OAuthAccessToken findByRefreshToken(String refreshToken);

    public OAuthAccessToken findByAuthenticationId(String authenticationId);

    public List<OAuthAccessToken> findByClientIdAndUserName(String clientId, String userName);

    public List<OAuthAccessToken> findByClientId(String clientId);

    public List<OAuthAccessToken> findByUserName(String userName, Sort sort);

    public long deleteByRefreshToken(String refreshToken);
}
