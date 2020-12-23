package vn.yotel.jobsearch247.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;
import vn.yotel.jobsearch247.core.jpa.PostDetail;

import java.util.Date;
import java.util.List;

@Repository(value = "postDetailRepo")
public interface PostDetailRepo extends JpaRepository<PostDetail, Long> {


    @Query( value = "select p.id, p.post_id, p.date_post, p.date_expired, p.address_room, p.is_accept, p.is_rental, p.status, p.star, p.care " +
            " from post_detail p " +
            " where (:ownerId is null or p.owner_id = :ownerId) " +
            " and (p.post_id = :postId or :postId is null or :postId = '')" +
            " and (p.is_accept = :isAccept or :isAccept is null )" +
            " order by p.date_expired ASC ", nativeQuery = true)
    List<Object[]> findListPostByOwnerId( @Param("ownerId") Long ownerId, @Param("postId") String postId,@Param("isAccept") Integer isAccept);

//    @Query( value = "select p from PostDetail p " +
//            " where :isAccept is null or p.isAccept = :isAccept", nativeQuery = false)
//    List<PostDetail> findListByIsAccept(@Param("isAccept") Integer value);

    @Query( value = "select p.id, p.post_id, a.user_name, o.phone_number, o.email, p.address_room, p.address_related, p.room_type, " +
            " p.amount_room, p.price_room, p.with_owner, p.bathroom, p.heater, p.kitchen, p.balcony, p.electricity_price, " +
            " p.water_price, p.other_utility, p.Img_path, p.area, p.star, p.care " +
            " from post_detail p" +
            " join owner o on o.id = p.owner_id " +
            " join auth_user a on a.id = o.account_id " +
            " where p.status = 1 and p.is_rental = 0 and p.is_accept = 1 ", nativeQuery = true)
    List<Object[]> findListPostApi();

    @Query( value = "select p.id, p.post_id, a.user_name, o.phone_number, o.email, p.address_room, p.address_related, p.room_type, " +
            " p.amount_room, p.price_room, p.with_owner, p.bathroom, p.heater, p.kitchen, p.balcony, p.electricity_price, " +
            " p.water_price, p.other_utility, p.Img_path, p.area , p.star, p.care" +
            " from post_detail p" +
            " join owner o on o.id = p.owner_id " +
            " join auth_user a on a.id = o.account_id " +
            " where ( p.post_id = :postId or :postId is null ) and p.status = 1 and p.is_rental = 0 and p.is_accept = 1 " +
            " and ( p.location_area = :locationId or :locationId is null ) " +
            " and ( p.room_type = :typeRoom or :typeRoom is null ) " +
            " and ( p.area = :area or :area is null ) " +
            " and ( (p.price_room between :fromPrice and :toPrice) or ( :fromPrice = 0 ))" , nativeQuery = true)
    List<Object[]> findByFilter(@Param("postId") String postId,@Param("locationId") Integer locationId, @Param("typeRoom") Integer typeRoom,
                                @Param("area") Integer area, @Param("fromPrice")  Integer fromPrice, @Param("toPrice")  Integer toPrice);

    @Query( value = "select p.id, p.post_id, a.user_name, o.phone_number, o.email, p.address_room, p.address_related, p.room_type, " +
            " p.amount_room, p.price_room, p.with_owner, p.bathroom, p.heater, p.kitchen, p.balcony, p.electricity_price, " +
            " p.water_price, p.other_utility, p.Img_path, p.area, p.care " +
            " from post_detail p" +
            " join owner o on o.id = p.owner_id " +
            " join auth_user a on a.id = o.account_id " +
            " where p.post_id = :postId " , nativeQuery = true)
    Object[] findOneByPostId(@Param("postId") String postId);

    @Query(value = "select p from PostDetail p where p.postId = :postId", nativeQuery = false)
    PostDetail findByPostId(@Param("postId") String postId);
}
