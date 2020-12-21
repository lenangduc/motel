package vn.yotel.jobsearch247.core.service.Impl;

import org.springframework.stereotype.Service;
import vn.yotel.commons.bo.impl.GenericBoImpl;
import vn.yotel.jobsearch247.core.jpa.OAuthAccessToken;
import vn.yotel.jobsearch247.core.repository.OAuthAccessTokenRepo;
import vn.yotel.jobsearch247.core.service.OAuthAccessTokenService;

import javax.annotation.Resource;
import java.util.List;

@Service("oauthAccessTokenService")
public class OauthAccessTokenServiceImpl extends GenericBoImpl<OAuthAccessToken, Long> implements OAuthAccessTokenService {
    @Resource
    OAuthAccessTokenRepo oAuthAccessTokenRepo;

    @Override
    public OAuthAccessTokenRepo getDAO() {
        return oAuthAccessTokenRepo;
    }

    @Override
    public List<OAuthAccessToken> findByUserName(String userName) {
        return oAuthAccessTokenRepo.findByUserName(userName);
    }
}
