package vn.yotel.jobsearch247.core.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import vn.yotel.admin.jpa.AuthUser;
import vn.yotel.commons.bo.impl.GenericBoImpl;
import vn.yotel.jobsearch247.core.helper.BaseChoiceHelper;
import vn.yotel.jobsearch247.core.model.BaseChoice;
import vn.yotel.jobsearch247.core.repository.AuthUserBackRepo;
import vn.yotel.jobsearch247.core.service.AuthUserBackService;

import javax.annotation.Resource;
import java.util.List;

@Service(value = "authUserBackService")
@Slf4j
public class AuthUserBackServiceImpl extends GenericBoImpl<AuthUser, Long> implements AuthUserBackService {

    @Resource
    private AuthUserBackRepo authUserBackRepo;

    @Override
    public <E extends JpaRepository<AuthUser, Long>> E getDAO() {
        return (E) authUserBackRepo;
    }

    @Override
    public List<BaseChoice> getBaseChoiceAccountOwner() {
        List<Object[]> objects = authUserBackRepo.getBaseChoiceAccountOwner();
        return BaseChoiceHelper.buildList(objects);
    }
}
