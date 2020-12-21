package vn.yotel.jobsearch247.core.service;

import vn.yotel.commons.bo.GenericBo;
import vn.yotel.jobsearch247.core.jpa.OAuthAccessToken;

import java.util.List;

public interface OAuthAccessTokenService extends GenericBo<OAuthAccessToken, Long> {
    List<OAuthAccessToken> findByUserName(String userName);
}
