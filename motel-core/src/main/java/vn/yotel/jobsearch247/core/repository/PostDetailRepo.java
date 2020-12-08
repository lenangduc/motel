package vn.yotel.jobsearch247.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.yotel.jobsearch247.core.jpa.PostDetail;

@Repository(value = "postDetailRepo")
public interface PostDetailRepo extends JpaRepository<PostDetail, Long> {

    @Query ( value = "select p from PostDetail p where p.id = :postId", nativeQuery = false)
    PostDetail findByPostId(@Param("postId") Long postId);
}
