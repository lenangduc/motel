package vn.yotel.jobsearch247.api.employer.web.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.yotel.admin.service.AuthUserService;
import vn.yotel.jobsearch247.api.employer.web.model.ResponseData;
import vn.yotel.jobsearch247.api.employer.web.requestDto.PostDto;
import vn.yotel.jobsearch247.api.employer.web.requestDto.ReportDto;
import vn.yotel.jobsearch247.api.employer.web.requestDto.ReviewDto;
import vn.yotel.jobsearch247.api.employer.web.requestDto.StarDto;
import vn.yotel.jobsearch247.api.employer.web.util.RestResponseBuilder;
import vn.yotel.jobsearch247.core.jpa.*;
import vn.yotel.jobsearch247.core.service.*;

import java.util.Date;
import java.util.List;

import static org.apache.poi.hssf.usermodel.HSSFShapeTypes.Star;

@RestController
@RequestMapping(value = "/post")
@Slf4j
public class PostController {

    @Autowired
    private PostDetailService postDetailService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private AuthUserService authUserService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private StarService starService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<ResponseData> ResponsePostApi() {
        List<Object[]> postApis = postDetailService.findListPostApi();
        return RestResponseBuilder.buildSuccess(postApis);
    }

    @RequestMapping(value = "/filter/list", method = RequestMethod.POST)
    public ResponseEntity<ResponseData> filter(@RequestBody PostDto postDto) {
        Integer fromPrice = 0;
        Integer toPrice = 0;
        if (postDto.getPriceRoom() == 1) {
            fromPrice = 0;
            toPrice = 1000000;
        } else if (postDto.getPriceRoom() == 2) {
            fromPrice = 1000000;
            toPrice = 3000000;
        } else if (postDto.getPriceRoom() == 3) {
            fromPrice = 3000000;
            toPrice = 5000000;
        } else if (postDto.getPriceRoom() == 4) {
            fromPrice = 5000000;
            toPrice = 10000000;
        } else if (postDto.getPriceRoom() == 5) {
            fromPrice = 10000000;
            toPrice = 50000000;
        }
        System.out.println(fromPrice);
        List<Object[]> postApis = postDetailService.findByFilter(postDto.getPostId(), postDto.getLocationId(), postDto.getTypeRoom(), postDto.getArea(), fromPrice, toPrice);
        return RestResponseBuilder.buildSuccess(postApis);
    }

    @RequestMapping(value = "/find-one", method = RequestMethod.POST)
    public ResponseEntity<ResponseData> getPostOne(@RequestParam("postId") String postId) {
        Object[] post = postDetailService.findOneByPostId(postId);
        return RestResponseBuilder.buildSuccess(post);
    }

    @RequestMapping(value = "/report", method = RequestMethod.POST)
    public String report(@RequestBody ReportDto reportDto) {
        Long postId = postDetailService.findByPostId(reportDto.getPostId()).getId();
        Long userId = authUserService.findByUsername(reportDto.getUserName()).getId();
        Report report = new Report();
        report.setPostId(postId);
        report.setUserId(userId);
        report.setComment(reportDto.getComment());
        reportService.create(report);

        Notification notification = new Notification();
        notification.setTime(new Date());
        notification.setStatus(0);
        notification.setUserReceiverId(1L);
        notification.setUserSendId(userId);
        notification.setContent(reportDto.getUserName() + " đã report bài đăng số " + postId);
        notificationService.create(notification);
        return "success";
    }

    @RequestMapping(value = "/review", method = RequestMethod.POST)
    public String review(@RequestBody ReviewDto reviewDto) {
        Long postId = postDetailService.findByPostId(reviewDto.getPostId()).getId();
        Long userId = authUserService.findByUsername(reviewDto.getUserName()).getId();
        Review review = new Review();
        review.setUserId(userId);
        review.setContent(reviewDto.getContent());
        review.setPostId(postId);
        reviewService.create(review);
        return "success";
    }

    @RequestMapping(value = "/review/list", method = RequestMethod.GET)
    public ResponseEntity<ResponseData> reviewList(@RequestParam("postId") String postId) {
        Long id = postDetailService.findByPostId(postId).getId();
        List<Object[]> reviewPost = reviewService.getReviewByPostId(id);
        return RestResponseBuilder.buildSuccess(reviewPost);
    }

    @RequestMapping(value = "/star", method = RequestMethod.POST)
    public String star(@RequestBody StarDto starDto) {
        Long postId = postDetailService.findByPostId(starDto.getPostId()).getId();
        Long userId = authUserService.findByUsername(starDto.getUserName()).getId();
        vn.yotel.jobsearch247.core.jpa.Star star = new Star();
        star.setPostId(postId);
        star.setUserId(userId);
        star.setAmount(starDto.getAmount());
        starService.create(star);

        List<Star> stars = starService.findByPostId(star.getPostId());
        Integer sum = 0;
        for (vn.yotel.jobsearch247.core.jpa.Star star1: stars) {
            sum += star1.getAmount();
        }
        Integer starMedium = sum / stars.size();
        PostDetail postDetail = postDetailService.findOne(postId);
        postDetail.setStar(starMedium);
        postDetailService.update(postDetail);
        return "success";
    }



}
