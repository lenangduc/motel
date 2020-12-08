package vn.yotel.jobsearch247.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.yotel.jobsearch247.core.jpa.Post;

import java.util.List;

@Repository(value = "postRepo")
public interface PostRepo extends JpaRepository<Post, Long> {

    @Query(value = "select p from Post p where id = :id", nativeQuery = false)
    public Post findById(@Param("id") Long id);

    @Query( value = "select p from Post p " +
            " where ( p.ownerId = :ownerId or :ownerId is null ) " +
            " order by p.datePost DESC",
             nativeQuery = false)
    List<Post> findListPostByOwnerId(@Param("ownerId") Long ownerId);
}
