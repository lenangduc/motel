package vn.yotel.jobsearch247.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.yotel.jobsearch247.core.jpa.PostDetail;

import java.util.List;

@Repository(value = "postDetailRepo")
public interface PostDetailRepo extends JpaRepository<PostDetail, Long> {

    @Query( value = "select p from PostDetail p " +
            " where :ownerId is null or p.ownerId = :ownerId", nativeQuery = false)
    List<PostDetail> findListPostByOwnerId(@Param("ownerId") Long ownerId);

    @Query( value = "select p from PostDetail p " +
            " where :isAccept is null or p.isAccept = :isAccept", nativeQuery = false)
    List<PostDetail> findListByIsAccept(@Param("isAccept") Integer value);
}
