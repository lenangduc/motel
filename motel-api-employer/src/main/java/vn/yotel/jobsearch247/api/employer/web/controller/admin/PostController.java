package vn.yotel.jobsearch247.api.employer.web.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.yotel.admin.service.AuthUserService;
import vn.yotel.jobsearch247.api.employer.web.model.ResponseData;
import vn.yotel.jobsearch247.api.employer.web.requestDto.PostDto;
import vn.yotel.jobsearch247.api.employer.web.requestDto.SavePostDto;
import vn.yotel.jobsearch247.api.employer.web.util.RestResponseBuilder;
import vn.yotel.jobsearch247.core.service.PostDetailService;

import java.util.List;

@RestController
@RequestMapping(value = "/post")
@Slf4j
public class PostController {

    @Autowired
    private PostDetailService postDetailService;

    @Autowired
    private AuthUserService authUserService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<ResponseData> ResponsePostApi() {
        List<Object[]> postApis = postDetailService.findListPostApi();
        return RestResponseBuilder.buildSuccess(postApis);
    }

    @RequestMapping(value = "/filter/list", method = RequestMethod.POST)
    public ResponseEntity<ResponseData> filter(@RequestBody PostDto postDto) {
        Integer fromPrice = 0;
        Integer toPrice = 0;
        if ( postDto.getPriceRoom() == 1 ) {
            fromPrice = 0;
            toPrice = 1000000;
        }
        else if (postDto.getPriceRoom() == 2) {
            fromPrice = 1000000;
            toPrice = 3000000;
        }
        else if (postDto.getPriceRoom() == 3 ) {
            fromPrice = 3000000;
            toPrice = 5000000;
        }
        else if (postDto.getPriceRoom() == 4 ) {
            fromPrice = 5000000;
            toPrice = 10000000;
        }
        else if (postDto.getPriceRoom() == 5 ) {
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



}
