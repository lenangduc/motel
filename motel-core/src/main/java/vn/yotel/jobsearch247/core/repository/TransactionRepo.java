package vn.yotel.jobsearch247.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.yotel.jobsearch247.core.jpa.PostDetail;
import vn.yotel.jobsearch247.core.jpa.Transaction;

import java.util.List;

@Repository(value = "transactionRepo")
public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    @Query ( value = "select t.id, p.post_id, t.date_request, t.duration, t.money, t.status " +
            " from transaction t " +
            " left join post_detail p on p.id = t.post_id" +
            " where (:post is null or :post = '' or p.post_id = :post )" +
            " and (:ownerId is null or p.owner_id = :ownerId)" +
            " order by t.date_request DESC", nativeQuery = true)
    List<Object[]> findByPostId(@Param("post") String post, @Param("ownerId") Long ownerId);
}
