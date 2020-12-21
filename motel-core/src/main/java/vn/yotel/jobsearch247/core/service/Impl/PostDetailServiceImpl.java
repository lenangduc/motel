package vn.yotel.jobsearch247.core.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import vn.yotel.commons.bo.impl.GenericBoImpl;
import vn.yotel.jobsearch247.core.jpa.PostDetail;
import vn.yotel.jobsearch247.core.repository.PostDetailRepo;
import vn.yotel.jobsearch247.core.service.PostDetailService;

import javax.annotation.Resource;
import java.util.Date;
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
    public List<Object[]> findListPostByOwnerId( Long ownerId, String postId, Integer isAccept) {
        return postDetailRepo.findListPostByOwnerId(ownerId, postId, isAccept);
    }

    @Override
    public List<Object[]> findByFilter(String postId, Integer locationId, Integer typeRoom, Integer area, Integer fromPrice, Integer toPrice) {
        return postDetailRepo.findByFilter(postId, locationId, typeRoom, area, fromPrice, toPrice);
    }

    @Override
    public List<Object[]> findListPostApi() {
        return postDetailRepo.findListPostApi();
    }

    @Override
    public Object[] findOneByPostId(String postId) {
        return postDetailRepo.findOneByPostId(postId);
    }

    @Override
    public PostDetail findByPostId(String postId) {
        return postDetailRepo.findByPostId(postId);
    }
}
