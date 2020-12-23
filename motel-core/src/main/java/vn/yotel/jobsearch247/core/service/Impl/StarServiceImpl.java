package vn.yotel.jobsearch247.core.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import vn.yotel.commons.bo.impl.GenericBoImpl;
import vn.yotel.jobsearch247.core.jpa.Star;
import vn.yotel.jobsearch247.core.repository.StarRepo;
import vn.yotel.jobsearch247.core.service.StarService;

import javax.annotation.Resource;
import java.util.List;

@Service(value = "starService")
@Slf4j
public class StarServiceImpl extends GenericBoImpl<Star, Long> implements StarService {
    @Resource
    private StarRepo starRepo;

    @Override
    public <E extends JpaRepository<Star, Long>> E getDAO() {
        return (E) starRepo;
    }

    @Override
    public List<Star> findByPostId(Long postId) {
        return starRepo.findByPostId(postId);
    }
}
