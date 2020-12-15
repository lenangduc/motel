package vn.yotel.jobsearch247.core.service;

import vn.yotel.commons.bo.GenericBo;
import vn.yotel.jobsearch247.core.jpa.Notification;

import java.util.List;

public interface NotificationService extends GenericBo<Notification, Long> {
    List<Notification> getNotificationByUserReceiverId(Long user_id);
}
