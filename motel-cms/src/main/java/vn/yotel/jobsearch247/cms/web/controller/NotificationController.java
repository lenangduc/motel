package vn.yotel.jobsearch247.cms.web.controller;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import vn.yotel.jobsearch247.core.jpa.Notification;
import vn.yotel.jobsearch247.core.service.NotificationService;

import java.util.List;

@Controller
@RequestMapping(value = "/notification")
@Slf4j
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @SneakyThrows
    @RequestMapping(value = "/list/{id}", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    @ResponseBody
    public List<Notification> getOtherUtility(@PathVariable(name = "id") Long user_id) {
        try {
            List<Notification> notifications = notificationService.getNotificationByUserReceiverId(user_id);
            return notifications;
        } catch (Exception e) {
            log.error("", e);
            throw e;
        }
    }
}
