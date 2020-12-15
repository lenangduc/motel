//package vn.yotel.jobsearch247.cms.scheduled;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import vn.yotel.jobsearch247.core.jpa.PostDetail;
//import vn.yotel.jobsearch247.core.jpa.Transaction;
//import vn.yotel.jobsearch247.core.service.PostDetailService;
//import vn.yotel.jobsearch247.core.service.TransactionService;
//
//import javax.annotation.Resource;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//
//@Component
//@Slf4j
//public class UpdateIsAcceptPostDetail {
//
//    @Resource
//    private PostDetailService postDetailService;
//
//    @Resource
//    private TransactionService transactionService;
//
//    @Scheduled(cron = "0 00 23 * * ?")
//    public void updateIsAcceptPostDetail() {
//        Date dateNow = new Date();
//
//        List<PostDetail> postDetails = postDetailService.findListByIsAccept(PostDetail.isAccept.ACCEPT.getValue());
//        for ( PostDetail postDetail : postDetails) {
//            Transaction transaction = transactionService.findOne(postDetail.getId());
//            Integer duration = transaction.getDuration();
//            Date datePost = transaction.getDatePost();
//            Calendar calendar = Calendar.getInstance();
//            calendar.add(Calendar.DATE, -duration);
//            Date dateExpried = calendar.getTime();
//            if ( dateExpried == datePost ) {
//                postDetail.setIsAccept(PostDetail.isAccept.EXPIRED.getValue());
//                postDetailService.update(postDetail);
//            }
//        }
//    }
//}
