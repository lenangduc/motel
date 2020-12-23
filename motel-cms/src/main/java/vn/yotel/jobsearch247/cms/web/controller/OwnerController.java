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
import vn.yotel.jobsearch247.cms.Helper.OwnerModelHelper;
import vn.yotel.jobsearch247.cms.Helper.PostListHelper;
import vn.yotel.jobsearch247.cms.Model.OwnerModel;
import vn.yotel.jobsearch247.cms.Model.PostListModel;
import vn.yotel.jobsearch247.cms.requestDto.PostDto;
import vn.yotel.jobsearch247.core.jpa.Notification;
import vn.yotel.jobsearch247.core.jpa.Owner;
import vn.yotel.jobsearch247.core.jpa.PostDetail;
import vn.yotel.jobsearch247.core.jpa.Transaction;
import vn.yotel.jobsearch247.core.model.BaseChoice;
import vn.yotel.jobsearch247.core.service.*;

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

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AuthUserBackService authUserBackService;

    Gson gson = new Gson();
    ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping(value = "/show-form/new-account", method = RequestMethod.GET)
    public String showFormNewAccountOwner(Model model) {
        Owner owner = new Owner();
        model.addAttribute("owner", owner);
        return "Owner/New";
    }

    @RequestMapping(value = "/list/account", method = RequestMethod.GET)
    @ResponseBody
    public List<BaseChoice> ListAccount() {
        try{
            List<BaseChoice> baseChoiceList = authUserBackService.getBaseChoiceAccountOwner();
            return baseChoiceList;
        }catch (Exception e){
            log.error("", e);
            throw e;
        }
    }

    @RequestMapping(value = "/new-account", method = RequestMethod.GET)
    public String newAccount( Model model, @ModelAttribute("owner") @Valid Owner owner) {
        try{
            owner.setStatus(1);
            ownerService.create(owner);
            return "redirect:/owner/list";
        }catch (Exception e){
            log.error("", e);
            throw e;
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listOwner ( Model model) {
        List<Object[]> Owners = ownerService.getAll(null);
        List<OwnerModel> ownerList = OwnerModelHelper.parseOwnerLists(Owners);
        model.addAttribute("owners", ownerList);
        return "Owner/List";
    }

    @RequestMapping(value = "/account/show/update/{id}")
    public String showFormUpdateAccount(Model model, @PathVariable(name = "id") Long id) {
        List<Object[]> Owners = ownerService.getAll(id);
        List<OwnerModel> ownerList = OwnerModelHelper.parseOwnerLists(Owners);
        OwnerModel ownerModel = ownerList.get(0);
        model.addAttribute("owner", ownerModel);
        return "Owner/Update";
    }

    @RequestMapping(value = "/account/update", method = RequestMethod.POST)
    public String updateAccount( @ModelAttribute("owner") @Valid OwnerModel ownerModel) {
        Owner owner = ownerService.findOne(ownerModel.getId());
        owner.setStatus(ownerModel.getStatus());
        owner.setAddress(ownerModel.getAddress());
        owner.setCmnd(ownerModel.getCmnd());
        owner.setEmail(ownerModel.getEmail());
        owner.setPhoneNumber(ownerModel.getPhoneNumber());
        ownerService.update(owner);
        return "redirect:/owner/list";
    }

    @RequestMapping(value = "list-post", method = RequestMethod.GET)
    public String listPost(@RequestParam(value = "post_id", required = false) String postId,
                           @RequestParam(value = "isAccept", required = false) Integer isAccept,
                           Model model, Principal principal) {
        AuthUser authUser = authUserService.findByUsername(principal.getName());
        Long ownerId = ownerService.findByAccountId(authUser.getId());
        List<Object[]> postLists = postDetailService.findListPostByOwnerId(ownerId, postId, isAccept);
        List<PostListModel> postListModels = PostListHelper.parsePostLists(postLists);
        for ( PostListModel postListModel : postListModels) {
            Integer status = 1;
            Date dateNow = new Date();
            if ( postListModel.getDateExpired() == null ) {
                // expired
                status = -1;
            } else {
                if ( postListModel.getDateExpired().before(dateNow) ) status = -1;
            }
            postListModel.setStatus(status);
        }
        model.addAttribute("post_id", postId);
        model.addAttribute("isAccept", isAccept);
        model.addAttribute("posts", postListModels);
        return "Post/List";
    }

    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
    public String detailPost(Model model, @PathVariable(value = "id") Long postId) {
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

    @RequestMapping(value = "update-post", method = RequestMethod.POST)
    public String updatePost(Model model, @ModelAttribute(name = "post") @Valid PostDetail postDetailView,
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
        String postId = postDetail.getPostId();
        Date datePost = postDetail.getDatePost();
        Date dateExpired = postDetail.getDateExpired();
        Integer isAccept = postDetailView.getIsAccept();
        Integer status = postDetail.getStatus();
        Integer isPay = postDetail.getIsPay();
        BeanUtils.copyProperties(postDetailView, postDetail);
        postDetail.setPostId(postId);
        postDetail.setAddressRelated(jsonAddressRelated);
        postDetail.setOtherUtility(jsonOtherUtility);
        postDetail.setIsRental(0);
        postDetail.setOwnerId(ownerId);
        postDetail.setImgPath(pathImg);
        postDetail.setDatePost(datePost);
        postDetail.setDateExpired(dateExpired);
        postDetail.setStatus(status);
        postDetail.setIsPay(isPay);
        postDetailService.update(postDetail);
        // nếu mà duyệt thì cập nhập trạng thái thanh toán = 0.
        if ( postDetail.getIsAccept() == 1) {
            postDetail.setIsPay(0);
            postDetailService.update(postDetail);
        }
        Long accountId = ownerService.findOne(postDetail.getOwnerId()).getAccountId();
        Notification notification = new Notification();
        notification.setUserSendId(Long.valueOf(1));
        notification.setUserReceiverId(accountId);
        notification.setStatus(0);
        notification.setTime(new Date());
        if(isAccept == 1) {
            notification.setContent("Admin đã duyệt bài có mã số " + postDetail.getPostId());
        }
        else if (isAccept == -1) {
            notification.setContent("Admin đã từ chối bài có mã số " + postDetail.getPostId());
        }
        notificationService.create(notification);
        return "redirect:/owner/list-post";
    }

    @RequestMapping(value = "/update-is-rental", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String updateIsRental(@RequestParam("status") Integer status,
                                 @RequestParam("id") Long id
    ) {
        PostDetail postDetail = postDetailService.findOne(id);
        Integer oldRental = postDetail.getIsRental();
        if (oldRental != status && oldRental == 0) {
            postDetail.setIsRental(status);
            postDetailService.update(postDetail);
        }
        Long ownerId = postDetail.getOwnerId();
        Long accountId = ownerService.findOne(ownerId).getAccountId();
        String nameSend = authUserService.findOne(accountId).getUserName();
        Notification notification = new Notification();
        notification.setContent(nameSend + " đã cập nhập tình trạng thuê của bài đăng có mã số " + postDetail.getPostId());
        notification.setUserReceiverId(Long.valueOf(1));
        notification.setUserSendId(accountId);
        notification.setTime(new Date());
        notification.setStatus(0);
        notificationService.create(notification);
        return "success";
    }

    @RequestMapping(value = "show-form", method = RequestMethod.GET)
    public String showFormNewPost(Model model) {
        PostDto postDto = new PostDto();
        model.addAttribute("post", postDto);
        return "PostDetail/New";
    }

    @RequestMapping(value = "new-post", method = RequestMethod.POST)
    public String newPost(@ModelAttribute(name = "post") PostDto postDto, Principal principal) {
        AuthUser authUser = authUserService.findByUsername(principal.getName());
        Long ownerId = ownerService.findByAccountId(authUser.getId());

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
        if (ownerId == null ) {
            postDetail.setIsAccept(1);
        } else {
            postDetail.setIsAccept(0);
        }
        postDetail.setIsRental(0);
        postDetail.setOwnerId(ownerId);
        postDetail.setImgPath(" ");
        postDetail.setPostId(" ");
        postDetail.setDateExpired(null);
        postDetail.setDatePost(null);
        postDetailService.create(postDetail);

        List<MultipartFile> files = postDto.getImages();
        List<String> fileNames = new ArrayList<String>();
        String image = "";
        if (null != files && files.size() > 0) {
            for (MultipartFile multipartFile : files) {

                String fileName = multipartFile.getOriginalFilename();
                fileName = String.valueOf(postDetail.getId()) + fileName;
                image += "/upload/" + fileName;
                image += ",";
                fileNames.add(fileName);
                File imageFile = new File("/home/motel/tomcat8-motel/motel-upload/", fileName);
//                File imageFile = new File("F:\\documents\\WEB\\motelUpload\\", fileName);
                try {
                    multipartFile.transferTo(imageFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        postDetail.setImgPath(image);
        if ( postDetail.getOwnerId() == null) {
            postDetail.setPostId(String.valueOf(postDetail.getId()) + "0" );
        } else {
            postDetail.setPostId(String.valueOf(postDetail.getId()) + String.valueOf(postDetail.getOwnerId()));
        }
        postDetailService.update(postDetail);
        return "redirect:/owner/list-post";
    }

    @RequestMapping(value = "/show/extend/{id}", method = RequestMethod.GET)
    public String showExtend(Model model, @PathVariable(value = "id") Long id) {
        PostDetail postDetail = postDetailService.findOne(id);
        Integer duration = 1;
        Date dateNow = new Date();
        if ( postDetail.getDateExpired() == null ) {
            // expired
            duration = -1;
        } else {
            if ( postDetail.getDateExpired().before(dateNow) ) duration = -1;
        }
        List<String> listPathImg = new ArrayList<>();
        String[] arrPathImg = postDetail.getImgPath().split(",");
        for (String eachRow : arrPathImg) {
            listPathImg.add(eachRow.trim());
        }
        model.addAttribute("path", listPathImg);
        model.addAttribute("duration", duration);
        model.addAttribute("posts", postDetail);
        return "PostDetail/Extend";
    }

    @RequestMapping(value = "/extend", method = RequestMethod.GET)
    public String extend ( @RequestParam(name = "duration") Integer duration, @RequestParam(name = "id") Long id) {
        Transaction transaction = new Transaction();
        transaction.setPostId(id);
        transaction.setDuration(duration);
        transaction.setDateRequest(new Date() );
        Integer money = 0;
        if ( duration == 1) money = 10000;
        if ( duration == 4) money = 40000;
        if ( duration == 12) money = 100000;
        if ( duration == 48) money = 300000;
        transaction.setMoney(money);
        transaction.setStatus(0);
        transactionService.create(transaction);
        return "redirect:/owner/list-post";
    }

    @SneakyThrows
    @RequestMapping(value = "/listAddressTags/{id}", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    @ResponseBody
    public List<String> getListAddressTags(@PathVariable(name = "id") Long post_id) {
        try {
            PostDetail postDetail = postDetailService.findOne(post_id);
            if (postDetail.getAddressRelated().isEmpty()) {
                postDetail.setAddressRelated("[\"Gần chợ\"]");
            }
            List<String> listAddressTags = objectMapper.readValue(postDetail.getAddressRelated(), List.class);
            return listAddressTags;
        } catch (Exception e) {
            log.error("", e);
            throw e;
        }

    }

    @SneakyThrows
    @RequestMapping(value = "/listOtherUtility/{id}", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
    @ResponseBody
    public List<String> getOtherUtility(@PathVariable(name = "id") Long post_id) {
        try {
            PostDetail postDetail = postDetailService.findOne(post_id);
            List<String> listOtherUtility = objectMapper.readValue(postDetail.getAddressRelated(), List.class);
            return listOtherUtility;
        } catch (Exception e) {
            log.error("", e);
            throw e;
        }

    }


}
