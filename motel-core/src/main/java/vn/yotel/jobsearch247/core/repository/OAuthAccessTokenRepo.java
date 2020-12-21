package vn.yotel.jobsearch247.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.yotel.jobsearch247.core.jpa.OAuthAccessToken;

import java.util.List;

@Repository(value = "oAuthAccessTokenRepo")
public interface OAuthAccessTokenRepo extends JpaRepository<OAuthAccessToken, Long> {
    List<OAuthAccessToken> findByUserName(String userName);
}