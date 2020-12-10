package vn.yotel.jobsearch247.cms.web.controller;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.yotel.admin.jpa.AuthUser;
import vn.yotel.admin.service.AuthUserService;
import vn.yotel.jobsearch247.cms.requestDto.PostDto;
import vn.yotel.jobsearch247.core.jpa.Owner;
import vn.yotel.jobsearch247.core.jpa.PostDetail;
import vn.yotel.jobsearch247.core.service.OwnerService;
import vn.yotel.jobsearch247.core.service.PostDetailService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/owner")
@Slf4j
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private AuthUserService authUserService;

    @Autowired
    private PostDetailService postDetailService;

    Gson gson = new Gson();
    ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping(value = "list-post", method = RequestMethod.GET)
    public String listPost(Model model, Principal principal) {
        AuthUser authUser = authUserService.findByUsername(principal.getName());
        Long ownerId = ownerService.findByAccountId( authUser.getId());
        List<PostDetail> postList = postDetailService.findListPostByOwnerId(ownerId);
        model.addAttribute("posts", postList);
        return "Post/List";
    }

    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
    public String detailPost (Model model, @PathVariable(value = "id") Long postId) {
        PostDetail postDetail = postDetailService.findOne(postId);
        List<String> listPathImg = new ArrayList<>();
        String[] arrPathImg = postDetail.getImgPath().split(",");
        for (String eachRow : arrPathImg) {
            listPathImg.add(eachRow.trim());
        }
        model.addAttribute("path", listPathImg);
        model.addAttribute("post", postDetail);
        return "PostDetail/Detail";
    }

    @RequestMapping( value = "update-post", method = RequestMethod.POST)
    public String updatePost ( Model model, @ModelAttribute( name = "post") @Valid PostDetail postDetailView,
                               @RequestParam("addressRelated") String addressRelated,
                               @RequestParam("listOtherUtility") String otherUtility) {
        List<String> listAddressRelated = new ArrayList<>();
        String[] arrAddressTags = addressRelated.split(",");
        for (String eachRow : arrAddressTags) {
            listAddressRelated.add(eachRow.trim());
        }
        String jsonAddressRelated = gson.toJson(listAddressRelated);
        List<String> listOtherUtility = new ArrayList<>();
        String[] arrOtherUtilityTags = otherUtility.split(",");
        for (String eachRow : arrOtherUtilityTags) {
            listOtherUtility.add(eachRow.trim());
        }
        String jsonOtherUtility = gson.toJson(listOtherUtility);
        PostDetail postDetail = postDetailService.findOne(postDetailView.getId());
        Long ownerId = postDetail.getOwnerId();
        String pathImg = postDetail.getImgPath();
        BeanUtils.copyProperties(postDetailView, postDetail);
        postDetail.setAddressRelated(jsonAddressRelated);
        postDetail.setOtherUtility(jsonOtherUtility);
        postDetail.setIsRental(0);
        postDetail.setOwnerId(ownerId);
        postDetail.setDateRequest(new Date());
        postDetail.setDatePost(null);
        postDetail.setImgPath(pathImg);
        postDetailService.update(postDetail);
        return "redirect:/owner/list-post";
    }

    @RequestMapping(value = "/update-is-rental", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String updateIsRental(@RequestParam("status") Integer status,
                                @RequestParam("id") Long id
    ) {
        PostDetail postDetail = postDetailService.findOne(id);
        Integer oldRental = postDetail.getIsRental();
        if (oldRental != status) {
            postDetail.setIsRental(status);
            postDetailService.update(postDetail);
        }
        return "success";
    }

    @RequestMapping(value = "/update-is-accept", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String updateIsAccept(@RequestParam("status") Integer status,
                                @RequestParam("id") Long id
    ) {
        PostDetail postDetail = postDetailService.findOne(id);
        Integer isAccept = postDetail.getIsAccept();
        if (isAccept != status && status == 1 ) {
            postDetail.setIsAccept(status);
            postDetail.setDatePost(new Date());
            postDetailService.update(postDetail);
        }
        return "success";
    }

    @RequestMapping( value = "show-form", method = RequestMethod.GET)
    public String showFormNewPost( Model model) {
        PostDto postDto = new PostDto();
        model.addAttribute("post", postDto);
        return "PostDetail/New";
    }

    @RequestMapping(value = "new-post", method = RequestMethod.POST)
    public String newPost ( @ModelAttribute(name = "post") PostDto postDto,  Principal principal) {
        AuthUser authUser = authUserService.findByUsername(principal.getName());
        Long ownerId = ownerService.findByAccountId( authUser.getId());

        List<String> listAddressRelated = new ArrayList<>();
        String[] arrAddressTags = postDto.getAddressRelated().split(",");
        for (String eachRow : arrAddressTags) {
            listAddressRelated.add(eachRow.trim());
        }
        String jsonAddressRelated = gson.toJson(listAddressRelated);
        List<String> listOtherUtility = new ArrayList<>();
        String[] arrOtherUtilityTags = postDto.getOtherUtility().split(",");
        for (String eachRow : arrOtherUtilityTags) {
            listOtherUtility.add(eachRow.trim());
        }
        String jsonOtherUtility = gson.toJson(listOtherUtility);

        PostDetail postDetail = new PostDetail();
        BeanUtils.copyProperties(postDto, postDetail);
        postDetail.setAddressRelated(jsonAddressRelated);
        postDetail.setOtherUtility(jsonOtherUtility);
        if ( ownerId != null ) {
            postDetail.setIsAccept(0);
            postDetail.setDatePost(null);
        } else {
            postDetail.setIsAccept(1);
            postDetail.setDatePost(new Date());
        }
        postDetail.setIsRental(0);
        postDetail.setDateRequest(new Date());
        postDetail.setOwnerId(ownerId);
        postDetail.setImgPath(" ");
        postDetailService.create(postDetail);


        List<MultipartFile> files = postDto.getImages();
        List<String> fileNames = new ArrayList<String>();
        String image = "";
        if (null != files && files.size() > 0)
        {
            for (MultipartFile multipartFile : files) {

                String fileName = multipartFile.getOriginalFilename();
                fileName = String.valueOf(postDetail.getId()) + fileName;
                image += "/upload/" + fileName;
                image += ",";
                fileNames.add(fileName);
                File imageFile = new File("F:\\documents\\WEB\\motelUpload\\", fileName);
                try
                {
                    multipartFile.transferTo(imageFile);
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        postDetail.setImgPath(image);
        postDetailService.update(postDetail);
        return "redirect:/owner/list-post";
    }

    @SneakyThrows
    @RequestMapping(value = "/listAddressTags/{id}", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    @ResponseBody
    public List<String> getListAddressTags(@PathVariable(name = "id") Long post_id) {
        try{
            PostDetail postDetail = postDetailService.findOne(post_id);
            if ( postDetail.getAddressRelated().isEmpty()) {
                postDetail.setAddressRelated("[\"Gần chợ\"]");
            }
            List<String> listAddressTags = objectMapper.readValue(postDetail.getAddressRelated(), List.class);
            return listAddressTags;
        }catch (Exception e){
            log.error("", e);
            throw e;
        }

    }

    @SneakyThrows
    @RequestMapping(value = "/listOtherUtility/{id}", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    @ResponseBody
    public List<String> getOtherUtility(@PathVariable(name = "id") Long post_id) {
        try{
            PostDetail postDetail = postDetailService.findOne(post_id);
            List<String> listOtherUtility = objectMapper.readValue(postDetail.getAddressRelated(), List.class);
            return listOtherUtility;
        }catch (Exception e){
            log.error("", e);
            throw e;
        }

    }


}
