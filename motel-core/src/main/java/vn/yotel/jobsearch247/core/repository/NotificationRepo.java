package vn.yotel.jobsearch247.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.yotel.jobsearch247.core.jpa.Notification;

import java.util.List;

@Repository(value = "notificationRepo")
public interface NotificationRepo extends JpaRepository<Notification, Long> {

    @Query ( value = " select * from notification n " +
            " where n.user_receiver_id = :userId " +
            " order by n.time desc " +
            " ", nativeQuery = true)
    List<Notification> getNotificationByUserReceiverId(@Param("userId") Long user_id);
}
