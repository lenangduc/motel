package vn.yotel.jobsearch247.core.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import vn.yotel.commons.bo.impl.GenericBoImpl;
import vn.yotel.jobsearch247.core.jpa.Post;
import vn.yotel.jobsearch247.core.repository.PostRepo;
import vn.yotel.jobsearch247.core.service.PostService;

import javax.annotation.Resource;
import java.util.List;

@Service(value = "postService")
@Slf4j
public class PostServiceImpl extends GenericBoImpl<Post, Long> implements PostService {

    @Resource
    PostRepo postRepo;

    @Override
    public <E extends JpaRepository<Post, Long>> E getDAO() {
        return (E)postRepo;
    }

    @Override
    public Post findById(Long id) {
        return postRepo.findById(id);
    }

    @Override
    public List<Post> findListPostByOwnerId(Long ownerId) {
        return postRepo.findListPostByOwnerId(ownerId);
    }
}
