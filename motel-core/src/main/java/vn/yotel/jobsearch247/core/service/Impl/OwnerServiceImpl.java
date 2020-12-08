package vn.yotel.jobsearch247.core.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import vn.yotel.commons.bo.impl.GenericBoImpl;
import vn.yotel.jobsearch247.core.jpa.Owner;
import vn.yotel.jobsearch247.core.repository.OwnerRepo;
import vn.yotel.jobsearch247.core.service.OwnerService;

import javax.annotation.Resource;

@Service(value = "ownerService")
@Slf4j
public class OwnerServiceImpl extends GenericBoImpl<Owner, Long> implements OwnerService {

    @Resource
    OwnerRepo ownerRepo;

    @Override
    public <E extends JpaRepository<Owner, Long>> E getDAO() {
        return null;
    }

    @Override
    public Long findByAccountId(Long id) {
        return ownerRepo.findByAccountId(id);
    }


}
