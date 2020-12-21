package vn.yotel.jobsearch247.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.yotel.jobsearch247.core.jpa.PostSave;

import java.util.List;

@Repository(value = "postSaveRepo")
public interface PostSaveRepo extends JpaRepository<PostSave, Long> {

    @Query(value = "select p.user_id, pd.post_id " +
            " from post_save p" +
            " left join post_detail pd on pd.id = p.post_id " +
            " where p.user_id = :userId " +
            " order by p.id DESC ", nativeQuery = true)
    List<Object[]> findByUserId(@Param("userId") Long userId);

    @Query(value = "select * " +
            " from post_save p " +
            " where p.user_id = :userId and p.post_id = :postId " +
            " ", nativeQuery = true)
    PostSave checkIsSave(@Param("userId") Long userId, @Param("postId") Long postId);
}
