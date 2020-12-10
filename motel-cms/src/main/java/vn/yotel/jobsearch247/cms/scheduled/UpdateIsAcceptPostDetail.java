package vn.yotel.jobsearch247.cms.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vn.yotel.jobsearch247.core.jpa.PostDetail;
import vn.yotel.jobsearch247.core.service.PostDetailService;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class UpdateIsAcceptPostDetail {

    @Resource
    private PostDetailService postDetailService;

    @Scheduled(cron = "0 00 23 * * ?")
    public void updateIsAcceptPostDetail() {
        Date dateNow = new Date();

        List<PostDetail> postDetails = postDetailService.findListByIsAccept(PostDetail.isAccept.ACCEPT.getValue());
        for ( PostDetail postDetail : postDetails) {
            Integer duration = postDetail.getDuration();
            Date datePost = postDetail.getDatePost();
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -duration);
            Date dateExpried = calendar.getTime();
            if ( dateExpried == datePost ) {
                postDetail.setIsAccept(PostDetail.isAccept.NOT_ACCEPT.getValue());
                postDetailService.update(postDetail);
            }
        }
    }
}
