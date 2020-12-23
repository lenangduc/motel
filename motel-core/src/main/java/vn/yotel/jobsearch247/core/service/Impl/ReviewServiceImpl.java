package vn.yotel.jobsearch247.core.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import vn.yotel.commons.bo.impl.GenericBoImpl;
import vn.yotel.jobsearch247.core.jpa.Report;
import vn.yotel.jobsearch247.core.jpa.Review;
import vn.yotel.jobsearch247.core.repository.ReviewRepo;
import vn.yotel.jobsearch247.core.service.ReportService;
import vn.yotel.jobsearch247.core.service.ReviewService;

import javax.annotation.Resource;
import java.util.List;

@Service(value = "reviewService")
@Slf4j
public class ReviewServiceImpl  extends GenericBoImpl<Review, Long> implements ReviewService {
    @Resource
    private ReviewRepo reviewRepo;

    @Override
    public <E extends JpaRepository<Review, Long>> E getDAO() {
        return (E) reviewRepo;
    }

    @Override
    public List<Object[]> getReviewByPostId(Long postId) {
        return reviewRepo.getReviewByPostId(postId);
    }
}
