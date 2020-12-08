package vn.yotel.jobsearch247.core.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import vn.yotel.commons.bo.impl.GenericBoImpl;
import vn.yotel.jobsearch247.core.jpa.Post;
import vn.yotel.jobsearch247.core.jpa.PostDetail;
import vn.yotel.jobsearch247.core.repository.PostDetailRepo;
import vn.yotel.jobsearch247.core.repository.PostRepo;
import vn.yotel.jobsearch247.core.service.PostDetailService;
import vn.yotel.jobsearch247.core.service.PostService;

import javax.annotation.Resource;

@Service(value = "postDetailService")
@Slf4j
public class PostDetailServiceImpl extends GenericBoImpl<PostDetail, Long> implements PostDetailService {

    @Resource
    PostDetailRepo postDetailRepo;

    @Override
    public <E extends JpaRepository<PostDetail, Long>> E getDAO() {
        return (E)postDetailRepo;
    }

    @Override
    public PostDetail findByPostId(Long postId) {
        return postDetailRepo.findByPostId(postId);
    }
}
