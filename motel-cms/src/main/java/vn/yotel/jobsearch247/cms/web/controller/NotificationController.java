package vn.yotel.jobsearch247.cms.web.controller;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import vn.yotel.admin.service.AuthUserService;
import vn.yotel.jobsearch247.core.jpa.Notification;
import vn.yotel.jobsearch247.core.service.NotificationService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping(value = "/notification")
@Slf4j
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AuthUserService authUserService;

    @SneakyThrows
    @RequestMapping(value = "/list", method = { RequestMethod.GET}, produces = "application/json")
    @ResponseBody
    public List<Notification> getOtherUtility(Principal principal) {
        try {
            String userName = principal.getName();
            Long userId = authUserService.findByUsername(userName).getId();
            List<Notification> notifications = notificationService.getNotificationByUserReceiverId(userId);
            return notifications;
        } catch (Exception e) {
            log.error("", e);
            throw e;
        }
    }
}
