package vn.yotel.jobsearch247.cms.API;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.yotel.jobsearch247.cms.Model.ResponseData;
import vn.yotel.jobsearch247.cms.util.RestResponseBuilder;
import vn.yotel.jobsearch247.core.service.PostDetailService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/post")
@Slf4j
public class PostController {

    @Autowired
    private PostDetailService postDetailService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseEntity<ResponseData> ResponsePostApi() {
        List<Object[]> postApis = postDetailService.findListPostApi();
        return RestResponseBuilder.buildSuccess(postApis);
    }
}
