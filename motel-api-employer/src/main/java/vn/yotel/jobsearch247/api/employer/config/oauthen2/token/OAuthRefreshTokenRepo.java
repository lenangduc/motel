package vn.yotel.jobsearch247.api.employer.config.oauthen2.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.yotel.jobsearch247.core.jpa.OAuthRefreshToken;

@Repository
public interface OAuthRefreshTokenRepo extends JpaRepository<OAuthRefreshToken, String> {
    public OAuthRefreshToken findByTokenId(String tokenId);
}
