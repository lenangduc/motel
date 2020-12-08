package vn.yotel.jobsearch247.cms.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vn.yotel.admin.jpa.AuthUser;
import vn.yotel.admin.service.AuthUserService;
import vn.yotel.jobsearch247.core.jpa.Post;
import vn.yotel.jobsearch247.core.jpa.PostDetail;
import vn.yotel.jobsearch247.core.service.OwnerService;
import vn.yotel.jobsearch247.core.service.PostDetailService;
import vn.yotel.jobsearch247.core.service.PostService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping(value = "/owner")
@Slf4j
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private PostService postService;

    @Autowired
    private AuthUserService authUserService;

    @Autowired
    private PostDetailService postDetailService;

    @RequestMapping(value = "list-post", method = RequestMethod.GET)
    public String listPost(Model model, Principal principal) {
        AuthUser authUser = authUserService.findByUsername(principal.getName());
        Long ownerId = ownerService.findByAccountId( authUser.getId());
        List<Post> postList = postService.findListPostByOwnerId(ownerId);
        model.addAttribute("posts", postList);
        return "Post/List";
    }

    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
    public String detailPost (Model model, @PathVariable(value = "id") Long postId) {
        PostDetail postDetail = postDetailService.findByPostId(postId);
        System.out.println(postDetail.getAddressRoom());
        model.addAttribute("post", postDetail);
        return "PostDetail/Detail";
    }
}
