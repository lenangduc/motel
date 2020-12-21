package vn.yotel.jobsearch247.core.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import vn.yotel.commons.bo.impl.GenericBoImpl;
import vn.yotel.jobsearch247.core.jpa.PostSave;
import vn.yotel.jobsearch247.core.repository.PostSaveRepo;
import vn.yotel.jobsearch247.core.service.PostSaveService;

import javax.annotation.Resource;
import java.util.List;

@Service(value = "postSaveService")
@Slf4j
public class PostSaveServiceImpl extends GenericBoImpl<PostSave, Long> implements PostSaveService {

    @Resource
    private PostSaveRepo postSaveRepo;

    @Override
    public <E extends JpaRepository<PostSave, Long>> E getDAO() {
        return (E) postSaveRepo;
    }

    @Override
    public List<Object[]> findByUserId(Long userId) {
        return postSaveRepo.findByUserId(userId);
    }

    @Override
    public PostSave checkIsSave(Long userId, Long postId) {
        return postSaveRepo.checkIsSave(userId, postId);
    }
}
