package vn.yotel.jobsearch247.core.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import vn.yotel.commons.bo.impl.GenericBoImpl;
import vn.yotel.jobsearch247.core.jpa.Notification;
import vn.yotel.jobsearch247.core.jpa.PostDetail;
import vn.yotel.jobsearch247.core.repository.NotificationRepo;
import vn.yotel.jobsearch247.core.service.NotificationService;
import vn.yotel.jobsearch247.core.service.PostDetailService;

import javax.annotation.Resource;
import java.util.List;

@Service(value = "notificationService")
@Slf4j
public class NotificationServiceImpl extends GenericBoImpl<Notification, Long> implements NotificationService {

    @Resource
    private NotificationRepo notificationRepo;

    @Override
    public <E extends JpaRepository<Notification, Long>> E getDAO() {
        return (E) notificationRepo;
    }

    @Override
    public List<Notification> getNotificationByUserReceiverId(Long user_id) {
        return notificationRepo.getNotificationByUserReceiverId(user_id);
    }
}
