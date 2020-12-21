package vn.yotel.jobsearch247.cms.web.controller;

import com.google.common.base.Strings;
import org.apache.commons.collections4.map.HashedMap;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.yotel.admin.jpa.AuthUser;
import vn.yotel.admin.jpa.Role;
import vn.yotel.admin.model.AuthUserModel;
import vn.yotel.admin.model.UserAccountSearchingForm;
import vn.yotel.admin.service.AuthPermissionService;
import vn.yotel.admin.service.AuthRoleService;
import vn.yotel.admin.service.AuthUserService;
import vn.yotel.admin.validation.UserFormValidator;
import vn.yotel.admin.web.controller.AuthUserController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping({"/account"})
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(AuthUserController.class);
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthUserService authUserService;
    @Resource
    private AuthRoleService authRoleService;
    @Resource
    private AuthPermissionService authPermissionService;
    @Autowired
    private UserFormValidator userFormValidator;

    public UserController() {
    }

    @RequestMapping(value = {"/login-error.html"}, method = {RequestMethod.GET, RequestMethod.POST}
    )
    public String loginError(Locale locale, Model model) {
        model.addAttribute("error", true);
        return "login";
    }

    @RequestMapping(value = {"/change_password.html"}, method = {RequestMethod.GET})
    public String changePassword(Locale locale, Model model) {
        LOG.info("Going to changepassword-page");
        AuthUser principal = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("username", principal.getUserName());
        model.addAttribute("old_password", "");
        model.addAttribute("new_password", "");
        model.addAttribute("verify_password", "");
        return "change_password";
    }

    @RequestMapping(value = {"/change_password.html"}, method = {RequestMethod.POST})
    @CacheEvict(value = {"authUsers"}, allEntries = true
    )
    public String changePassword(Locale locale, Model model, @RequestParam String username, @RequestParam String old_password, @RequestParam String new_password, @RequestParam String verify_password) {
        LOG.info("Process changing password");

        try {
            AuthUser _user = this.authUserService.findByUsername(username);
            if (this.passwordEncoder.matches(old_password, _user.getPassword()) && new_password.equals(verify_password)) {
                String newEncryptedPassword = this.passwordEncoder.encode(new_password);
                _user.setPassword(newEncryptedPassword);
                this.authUserService.update(_user);
                return "redirect:/logout.html";
            } else {
                model.addAttribute("username", username);
                model.addAttribute("old_password", old_password);
                model.addAttribute("new_password", new_password);
                model.addAttribute("verify_password", verify_password);
                return "change_password";
            }
        } catch (Exception var9) {
            LOG.error("", var9);
            return "login";
        }
    }

    @RequestMapping({"/user_list.html"})
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public String listUserAccount(Model model, HttpSession session, HttpServletRequest request, Pageable pageable,
                                  @RequestParam(value = "user_list_ss",defaultValue = "") String user_list_ss,
                                  @ModelAttribute("userSearchingForm") @Valid UserAccountSearchingForm userSearchingForm,
                                  BindingResult bindingResult) {
        LOG.info("listing users ...");
        String not_found_message = "";
        model.addAttribute("userSearchingForm", userSearchingForm);
        if (bindingResult.hasErrors()) {
            Page<AuthUserModel> pageTop = new PageImpl(new ArrayList());
            model.addAttribute("page", pageTop);
            return "user/user_list";
        } else {
            String userName;
            if (request.getMethod().equalsIgnoreCase("GET")) {
                userName = (String)session.getAttribute("user_list_ss");
                userSearchingForm = (UserAccountSearchingForm)session.getAttribute(userName);
                if (userSearchingForm == null) {
                    userSearchingForm = new UserAccountSearchingForm();
                }
            }

            userName = userSearchingForm.getUserName();
            String email = userSearchingForm.getEmail();
            Sort sort = new Sort(new Sort.Order[]{new Sort.Order(Sort.Direction.ASC, "userName")});
            Pageable _pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sort);
            Page<AuthUserModel> pageTop = this.authUserService.listUsers(userName, email, _pageable);
            if (request.getMethod().equalsIgnoreCase("POST") && pageTop.getContent().size() == 0) {
                not_found_message = "Không tìm thấy dữ liệu.";
            }

            Map<Byte, String> user_status = new HashedMap();
            user_status.put((byte) 0, "ACTIVE");
            user_status.put((byte) 1, "INACTIVE");
            user_status.put((byte) 2, "BLOCK");
            Map<Byte, String> user_types = new HashedMap();
            user_types.put((byte) 1, "Org");
            user_types.put((byte) 2, "Partner");
            session.removeAttribute(user_list_ss);
            user_list_ss = UUID.randomUUID().toString();
            session.setAttribute("user_list_ss", user_list_ss);
            session.setAttribute(user_list_ss, userSearchingForm);
            request.getSession().setAttribute("pageIndex", pageable.getPageNumber());
            model.addAttribute("userSearchingForm", userSearchingForm);
            model.addAttribute("user_list_ss", user_list_ss);
            model.addAttribute("page", pageTop);
            model.addAttribute("user_status", user_status);
            model.addAttribute("user_types", user_types);
            model.addAttribute("not_found_message", not_found_message);
            return "user/user_list";
        }
    }

    @RequestMapping(value = {"/user_add.html"}, method = {RequestMethod.GET})
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public String addUser(Model model, HttpSession session) {
        List<Role> allRoles = this.authRoleService.findAll();
        AuthUserModel user = new AuthUserModel();
        user.setPassword("123456a@");
        user.setVerifyPassword("123456a@");
        model.addAttribute("user", user);
        model.addAttribute("allRoles", allRoles);
        return "user/user_add";
    }

    @RequestMapping(value = {"/user_add.html"}, method = {RequestMethod.POST})
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public String addUser(Model model, HttpSession session, @ModelAttribute("user") @Valid AuthUserModel user, BindingResult result) {
        this.userFormValidator.validateUserForm(user, result);
        List roleIds;
        if (result.hasErrors()) {
            roleIds = this.authRoleService.findAll();
            model.addAttribute("user", user);
            model.addAttribute("allRoles", roleIds);
            return "user/user_add";
        } else {
            roleIds = user.getRoles();
            List<Role> roles = new ArrayList();
            if (roleIds != null) {
                Iterator var7 = roleIds.iterator();

                while(var7.hasNext()) {
                    Long roleId = (Long)var7.next();
                    Role eachRole = (Role)this.authRoleService.findOne(roleId);
                    roles.add(eachRole);
                }
            }

            Date createdDate = (new DateTime()).toDate();
            String password = user.getPassword();
            String salt = "5876695f8e4e1811";
            String encryptPassword = "";
            encryptPassword = this.passwordEncoder.encode(password);
            AuthUser authUser = new AuthUser();
            authUser.setCreatedDate(createdDate);
            authUser.setEmail(user.getEmail());
            authUser.setFullName(user.getFullName());
            authUser.setGender(user.getGender());
            authUser.setIsVerified(user.getIsVerified());
            authUser.setModifiedDate((Date)null);
            authUser.setUserName(user.getUserName());
            authUser.setSalt(salt);
            authUser.setPassword(encryptPassword);
            authUser.setStatus(user.getStatus());
            authUser.setUserType(user.getUserType());
            authUser.setAuthRoles(roles);
            this.authUserService.create(authUser);
            return "redirect:/account/user_list.html";
        }
    }

    @RequestMapping(value = {"/update/{id}/user_update.html"}, method = {RequestMethod.GET})
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public String updateUser(Model model, @PathVariable Long id) {
        AuthUser authUser = (AuthUser)this.authUserService.findOne(id);
        if (authUser == null) {
            return "400";
        } else {
            List<Long> userRoleIds = this.authUserService.findRolesByUserId(id);
            AuthUserModel user = new AuthUserModel();
            user.setId(id);
            user.setFullName(authUser.getFullName());
            user.setGender(authUser.getGender());
            user.setEmail(authUser.getEmail());
            user.setUserName(authUser.getUserName());
            user.setIsVerified(authUser.getIsVerified());
            user.setStatus(authUser.getStatus());
            user.setUserType(authUser.getUserType());
            user.setRoles(userRoleIds);
            List<Role> allRoles = this.authRoleService.findAll();
            model.addAttribute("user", user);
            model.addAttribute("allRoles", allRoles);
            return "user/user_update";
        }
    }

    @RequestMapping(value = {"/update/{id}/user_update.html"}, method = {RequestMethod.POST})
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public String updateUser(Model model, @PathVariable Long id, HttpSession session, @ModelAttribute("user") @Valid AuthUserModel user, BindingResult result) {
        AuthUser authUser = (AuthUser)this.authUserService.findOne(id);
        if (authUser == null) {
            return "400";
        } else {
            user.setId(id);
            this.userFormValidator.validateUserForm(user, result);
            List roleIds;
            if (result.hasErrors()) {
                roleIds = this.authRoleService.findAll();
                model.addAttribute("user", user);
                model.addAttribute("allRoles", roleIds);
                return "user/user_update";
            } else {
                roleIds = user.getRoles();
                List<Role> roles = new ArrayList();
                if (roleIds != null) {
                    Iterator var9 = roleIds.iterator();

                    while(var9.hasNext()) {
                        Long roleId = (Long)var9.next();
                        Role eachRole = (Role)this.authRoleService.findOne(roleId);
                        roles.add(eachRole);
                    }
                }

                Date createdDate = (new DateTime()).toDate();
                authUser.setCreatedDate(createdDate);
                authUser.setEmail(user.getEmail());
                authUser.setFullName(user.getFullName());
                authUser.setGender(user.getGender());
                authUser.setIsVerified(user.getIsVerified());
                authUser.setModifiedDate((Date)null);
                authUser.setUserName(user.getUserName());
                authUser.setStatus(user.getStatus());
                authUser.setUserType(user.getUserType());
                authUser.setAuthRoles(roles);
                this.authUserService.update(authUser);
                return "redirect:/account/user_list.html";
            }
        }
    }

    @RequestMapping({"/delete/{userName}/user_list.html"})
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public String deleteUser(Model model, @PathVariable String userName) {
        AuthUser authUser = this.authUserService.findByUsername(userName);
        if (authUser == null) {
            return "400";
        } else {
            this.authUserService.delete(authUser);
            return "redirect:/account/user_list.html";
        }
    }

    @RequestMapping({"/reset/{userName}/user_list.html"})
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public String resetPasswordUserAccount(Model model, @PathVariable String userName) {
        AuthUser authUser = this.authUserService.findByUsername(userName);
        if (authUser == null) {
            return "400";
        } else {
            String password = "123456a@";
            String encryptPassword = "";
            if (!Strings.isNullOrEmpty(password)) {
                encryptPassword = this.passwordEncoder.encode(password);
            }

            authUser.setPassword(encryptPassword);
            this.authUserService.update(authUser);
            return "redirect:/account/user_list.html";
        }
    }
}
