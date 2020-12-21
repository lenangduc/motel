package vn.yotel.jobsearch247.api.employer.Server;

import vn.yotel.admin.jpa.AuthUser;
import vn.yotel.jobsearch247.api.employer.web.model.AuthUserData;

public interface AuthUserDataService {
    AuthUserData getUserData(AuthUser authUser) throws Exception;
}
