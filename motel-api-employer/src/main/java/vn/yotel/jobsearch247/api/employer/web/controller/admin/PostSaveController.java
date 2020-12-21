package vn.yotel.jobsearch247.api.employer.web.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.yotel.jobsearch247.api.employer.web.model.ResponseData;
import vn.yotel.jobsearch247.api.employer.web.requestDto.SavePostDto;
import vn.yotel.jobsearch247.api.employer.web.util.RestResponseBuilder;
import vn.yotel.jobsearch247.core.jpa.PostDetail;
import vn.yotel.jobsearch247.core.jpa.PostSave;
import vn.yotel.jobsearch247.core.service.PostDetailService;
import vn.yotel.jobsearch247.core.service.PostSaveService;

import java.util.List;

@RestController
@RequestMapping(value = "/post/save")
@Slf4j
public class PostSaveController {

    @Autowired
    private PostSaveService postSaveService;

    @Autowired
    private PostDetailService postDetailService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String savePost(@RequestBody SavePostDto savePostDto) {
        try {
            PostDetail postDetail = postDetailService.findByPostId(savePostDto.getPostId());
            PostSave postSave = postSaveService.checkIsSave(savePostDto.getUserId(), postDetail.getId());
            if ( postSave == null ) {
                PostSave postSave1 = new PostSave();
                postSave1.setUserId(savePostDto.getUserId());
                postSave1.setPostId(postDetail.getId());
                postSaveService.create(postSave1);
                return "Success";
            } else {
                return "Ban da luu r";
            }
        } catch (Exception ex) {
            log.error("", ex);
            return "500";
        }
    }

    @RequestMapping(value = "/show", method = RequestMethod.POST)
    public ResponseEntity<ResponseData> show (@RequestParam("UserId") Long userId) {
        try {
            List<Object[]> postSave = postSaveService.findByUserId(userId);
            return RestResponseBuilder.buildSuccess(postSave);
        } catch (Exception ex) {
            log.error("", ex);
            return RestResponseBuilder.buildValidError(null);
        }
    }

}
