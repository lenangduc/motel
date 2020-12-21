package vn.yotel.jobsearch247.core.service;

import vn.yotel.admin.jpa.AuthUser;
import vn.yotel.commons.bo.GenericBo;
import vn.yotel.jobsearch247.core.model.BaseChoice;

import java.util.List;

public interface AuthUserBackService extends GenericBo<AuthUser, Long> {
    List<BaseChoice> getBaseChoiceAccountOwner();
}
