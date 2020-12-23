package vn.yotel.jobsearch247.cms.Scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vn.yotel.jobsearch247.core.jpa.PostDetail;
import vn.yotel.jobsearch247.core.service.PostDetailService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class updateStatusOfPost {
    @Resource
    private PostDetailService postDetailService;

    @Scheduled(cron = "0 59 23 * * ?")
    public void divisionWorkPartnerScheduled() {
        Date nowDate = new Date();
        List<PostDetail> postDetails = postDetailService.findAll();
        for (PostDetail postDetail : postDetails) {
            if (postDetail.getDateExpired() == null ) {
            } else {
                if ( postDetail.getDateExpired().compareTo(nowDate) <= 0 ){
                    postDetail.setStatus(0);
                    postDetailService.update(postDetail);
                }
            }
        }
    }
}
