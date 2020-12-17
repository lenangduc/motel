package vn.yotel.jobsearch247.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.yotel.jobsearch247.core.jpa.PostDetail;

import java.util.List;

@Repository(value = "postDetailRepo")
public interface PostDetailRepo extends JpaRepository<PostDetail, Long> {


    @Query( value = "select p.id, p.post_id, p.date_post, p.date_expired, p.address_room, p.is_accept, p.is_rental, p.status " +
            " from post_detail p " +
            " where (:ownerId is null or p.owner_id = :ownerId) ", nativeQuery = true)
    List<Object[]> findListPostByOwnerId(@Param("ownerId") Long ownerId);

//    @Query( value = "select p from PostDetail p " +
//            " where :isAccept is null or p.isAccept = :isAccept", nativeQuery = false)
//    List<PostDetail> findListByIsAccept(@Param("isAccept") Integer value);

    @Query( value = "select p.id, p.post_id, a.user_name, o.phone_number, o.email, p.address_room, p.address_related, p.room_type, " +
            " p.amount_room, p.price_room, p.with_owner, p.bathroom, p.heater, p.kitchen, p.balcony, p.electricity_price, " +
            " p.water_price, p.other_utility, p.Img_path " +
            " from post_detail p" +
            " join owner o on o.id = p.owner_id " +
            " join auth_user a on a.id = o.account_id " +
            " where p.status = 1", nativeQuery = true)
    List<Object[]> findListPostApi();
}
