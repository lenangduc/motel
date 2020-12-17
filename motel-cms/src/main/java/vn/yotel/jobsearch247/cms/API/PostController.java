package vn.yotel.jobsearch247.cms.API;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.yotel.admin.jpa.AuthUser;
import vn.yotel.admin.jpa.Role;
import vn.yotel.jobsearch247.cms.Model.ResponseData;
import vn.yotel.jobsearch247.cms.requestDto.UserDto;
import vn.yotel.jobsearch247.cms.util.RestResponseBuilder;
import vn.yotel.jobsearch247.core.service.PostDetailService;

import javax.transaction.Transactional;
import java.util.*;
import java.util.regex.Pattern;

@RestController
@RequestMapping(value = "/api/post")
@Slf4j
public class PostController {

    @Autowired
    private PostDetailService postDetailService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<ResponseData> ResponsePostApi() {
        List<Object[]> postApis = postDetailService.findListPostApi();
        return RestResponseBuilder.buildSuccess(postApis);
    }


}
