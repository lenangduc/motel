package vn.yotel.jobsearch247.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.yotel.jobsearch247.core.jpa.Review;
import vn.yotel.jobsearch247.core.jpa.Star;

import java.util.List;

@Repository(value = "starRepo")
public interface StarRepo extends JpaRepository<Star, Long> {
    @Query( value = "select s from Star s where s.postId = :postId", nativeQuery = false)
    List<Star> findByPostId(@Param("postId") Long postId);
}
