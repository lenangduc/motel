package vn.yotel.jobsearch247.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.yotel.jobsearch247.core.jpa.Report;
import vn.yotel.jobsearch247.core.jpa.Review;

import java.util.List;

@Repository(value = "reviewRepo")
public interface ReviewRepo extends JpaRepository<Review, Long> {
    @Query(value = "select a.user_name, r.content " +
            " from review r" +
            " join auth_user a on a.id = r.user_id" +
            " where r.post_id = :postId", nativeQuery = true)
    List<Object[]> getReviewByPostId(@Param("postId") Long postId);
}
