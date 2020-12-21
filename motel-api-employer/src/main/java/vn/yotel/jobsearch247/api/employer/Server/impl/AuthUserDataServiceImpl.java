package vn.yotel.jobsearch247.api.employer.Server.impl;

import org.springframework.stereotype.Service;
import vn.yotel.admin.jpa.AuthUser;
import vn.yotel.jobsearch247.api.employer.Server.AuthUserDataService;
import vn.yotel.jobsearch247.api.employer.web.model.AuthUserData;

import java.util.List;

@Service("authServicePackageService")
public class AuthUserDataServiceImpl implements AuthUserDataService {

    @Override
    public AuthUserData getUserData(AuthUser authUser) throws Exception {
        if (authUser == null) {
            return null;
        }
        AuthUserData authUserData = new AuthUserData();
        authUserData.setUserId(authUser.getId());
        return authUserData;
    }
}
