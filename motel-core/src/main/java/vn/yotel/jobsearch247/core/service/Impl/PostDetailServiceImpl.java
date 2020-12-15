package vn.yotel.jobsearch247.core.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import vn.yotel.commons.bo.impl.GenericBoImpl;
import vn.yotel.jobsearch247.core.jpa.PostDetail;
import vn.yotel.jobsearch247.core.repository.PostDetailRepo;
import vn.yotel.jobsearch247.core.service.PostDetailService;

import javax.annotation.Resource;
import java.util.List;

@Service(value = "postDetailService")
@Slf4j
public class PostDetailServiceImpl extends GenericBoImpl<PostDetail, Long> implements PostDetailService {

    @Resource
    PostDetailRepo postDetailRepo;

    @Override
    public <E extends JpaRepository<PostDetail, Long>> E getDAO() {
        return (E) postDetailRepo;
    }

    @Override
    public List<Object[]> findListPostByOwnerId(Long ownerId) {
        return postDetailRepo.findListPostByOwnerId(ownerId);
    }

    @Override
    public List<PostDetail> findListByIsAccept(Integer value) {
        return postDetailRepo.findListByIsAccept(value);
    }

    @Override
    public List<Object[]> findListPostApi() {
        return postDetailRepo.findListPostApi();
    }
}
