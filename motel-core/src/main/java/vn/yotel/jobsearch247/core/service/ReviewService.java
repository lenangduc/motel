package vn.yotel.jobsearch247.core.service;

import vn.yotel.commons.bo.GenericBo;
import vn.yotel.jobsearch247.core.jpa.Review;

import java.util.List;

public interface ReviewService extends GenericBo<Review, Long> {
    List<Object[]> getReviewByPostId(Long postId);
}
