package vn.yotel.jobsearch247.api.employer.web.controller.admin;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import vn.yotel.admin.jpa.AuthUser;
import vn.yotel.admin.jpa.Role;
import vn.yotel.admin.service.AuthRoleService;
import vn.yotel.admin.service.AuthUserService;
import vn.yotel.jobsearch247.api.employer.ConextFacade.AuthenticationFacade;
import vn.yotel.jobsearch247.api.employer.Server.AuthUserDataService;
import vn.yotel.jobsearch247.api.employer.config.oauthen2.token.OAuthUtils;
import vn.yotel.jobsearch247.api.employer.web.constant.Constant;
import vn.yotel.jobsearch247.api.employer.web.enums.UserRoleEnum;
import vn.yotel.jobsearch247.api.employer.web.enums.UserStatusEnum;
import vn.yotel.jobsearch247.api.employer.web.model.AuthUserData;
import vn.yotel.jobsearch247.api.employer.web.model.ResponseData;
import vn.yotel.jobsearch247.api.employer.web.requestDto.LoginDto;
import vn.yotel.jobsearch247.api.employer.web.requestDto.UserNewPasswordDto;
import vn.yotel.jobsearch247.api.employer.web.requestDto.UserRegisterDto;
import vn.yotel.jobsearch247.api.employer.web.util.RestResponseBuilder;
import vn.yotel.jobsearch247.core.jpa.OAuthAccessToken;
import vn.yotel.jobsearch247.core.service.OAuthAccessTokenService;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;
import java.util.regex.Pattern;

@RestController
@RequestMapping(value = "/account")
@Slf4j
public class UsersController {

    @Autowired
    private AuthUserService authUserService;

    @Autowired
    private AuthRoleService authRoleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DefaultTokenServices defaultTokenServices;

    @Autowired
    private OAuthAccessTokenService oAuthAccessTokenService;

    @Autowired
    private AuthUserDataService authUserDataService;

    @Autowired
    private ConsumerTokenServices tokenServices;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Transactional
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<ResponseData> createUser(@RequestBody UserRegisterDto model) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            String userName = model.getUsername();
            List<Role> roles = new ArrayList<>();
            if (Strings.isNullOrEmpty(userName)) {
                responseMap.put("username", "Username không được để trống");
                return RestResponseBuilder.buildValidError(responseMap);
            }
            Pattern pattern = Pattern.compile(Constant.REGEX_PHONE, Pattern.CASE_INSENSITIVE);
            userName = userName.trim().toLowerCase();

            if (!pattern.matcher(userName).matches()) {
                responseMap.put("username", "Số điện thoại không đúng định dạng ");
                return RestResponseBuilder.buildValidError(responseMap);
            }
            if (!model.getPassword().equals(model.getRePassword())) {
                responseMap.put("password", "Mật khẩu nhập lại chưa đúng");
                return RestResponseBuilder.buildValidError(responseMap);
            }

            if (authUserService.findByUsername(userName) != null) {
                responseMap.put("username", "Username đã tồn tại");
                return RestResponseBuilder.buildValidError(responseMap);
            }

            if (!responseMap.isEmpty()) {
                return RestResponseBuilder.buildValidError(responseMap);
            }

            Role employerRole = authRoleService.findOne(UserRoleEnum.ROLE_RENTER.value());
            System.out.println(employerRole.getId());
            roles.add(employerRole);

            Date createdDate = new DateTime().toDate();
            String password = model.getPassword();
            String salt = "5876695f8e4e1811";
            String encryptPassword = passwordEncoder.encode(password);
            AuthUser authUser = new AuthUser();
            authUser.setCreatedDate(createdDate);
            authUser.setModifiedDate(null);
            authUser.setUserName(model.getUsername());
            authUser.setSalt(salt);
            authUser.setIsVerified(Byte.valueOf("1"));
            authUser.setPassword(encryptPassword);
            authUser.setStatus(UserStatusEnum.ACTIVE.value());
            authUser.setAuthRoles(roles);
            authUserService.create(authUser);
//            authUser.setAuthRoles(roles);
            return RestResponseBuilder.buildSuccess(responseMap);

        } catch (Exception ex) {
            log.error("", ex);
            ex.printStackTrace();
            return RestResponseBuilder.buildRuntimeError(null);
        }
    }

    @Transactional
    @RequestMapping(value = "/login", method = {RequestMethod.POST, RequestMethod.OPTIONS})
    public ResponseEntity<ResponseData> Login(@RequestBody LoginDto login) {
        Map<String, Object> responseMap = new HashMap<>();
        List<GrantedAuthority> lsAuthorities = null;
        try {
            if (Strings.isNullOrEmpty(login.getUsername())) {
                responseMap.put("username", " ");
                return RestResponseBuilder.buildValidError(responseMap);
            }
            AuthUser user = authUserService.findByUsername(login.getUsername());
            if (user != null) {
                Hibernate.initialize(user.getAuthRoles());
                user.getAuthRoles();
                lsAuthorities = (List<GrantedAuthority>) user.getAuthorities();
            }
            if (user == null || !passwordEncoder.matches(login.getPassword(), user.getPassword())) {
                responseMap.put("", "Sai tài khoản hoặc mật khẩu");
                return RestResponseBuilder.buildValidError(responseMap);
            }
            if (user.getStatus() == UserStatusEnum.INACTIVE.value()) {
                responseMap.put("", "Tài khoản đã bị khóa. Xin vui lòng liên hệ Admin để được hướng dẫn.");
                return RestResponseBuilder.buildSuccess(responseMap);
            }

            List<OAuthAccessToken> oAuthAccessTokens = oAuthAccessTokenService.findByUserName(login.getUsername());
            for(OAuthAccessToken oAuthAccessToken : oAuthAccessTokens) {
                oAuthAccessTokenService.delete(oAuthAccessToken);
            }
            HashMap<String, String> requestParameters = new HashMap<>();
            OAuth2AccessToken accessToken = OAuthUtils.createAccessToken(user.getUsername(), login.getPassword(), lsAuthorities,
                    requestParameters, defaultTokenServices);
            responseMap.put(OAuth2AccessToken.ACCESS_TOKEN, accessToken.getValue());
            if (accessToken.getExpiresIn() > 0) {
                responseMap.put(OAuth2AccessToken.EXPIRES_IN, accessToken.getExpiresIn());
            }
            responseMap.put(OAuth2AccessToken.TOKEN_TYPE, accessToken.getTokenType());
            if (accessToken.getRefreshToken() != null) {
                responseMap.put(OAuth2AccessToken.REFRESH_TOKEN, accessToken.getRefreshToken().getValue());
            }
            AuthUserData authUserData = authUserDataService.getUserData(user);
            responseMap.put("authUserData", authUserData);
            responseMap.put("authRoles", lsAuthorities);
            return RestResponseBuilder.buildSuccess(responseMap);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
            return RestResponseBuilder.buildRuntimeError(null);
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_RENTER')")
    @RequestMapping(value = "/logout", method = {RequestMethod.POST, RequestMethod.OPTIONS})
    public ResponseEntity<ResponseData> logout(HttpServletRequest req) {

        try {
            String authorizationValue = req.getHeader("Authorization");
            String tokenId = null;
            if (!StringUtils.isEmpty(authorizationValue)) {
                tokenId = authorizationValue.replace("Bearer", "");
            }
            if (StringUtils.isEmpty(tokenId)) {
                return RestResponseBuilder.buildAuthDeny("");
            }
            tokenServices.revokeToken(tokenId);
            return RestResponseBuilder.buildSuccess(null);
        } catch (Exception e) {
            log.error("", e);
            return RestResponseBuilder.buildRuntimeError(null);
        }
    }

    @Transactional
    @RequestMapping(value = "/new-password", method = RequestMethod.POST)
    public ResponseEntity<ResponseData> newPassword(
            @RequestBody UserNewPasswordDto userNewPasswordDto) {

        Map<String, Object> responseMap = new HashMap<>();
        try {
            String userName = userNewPasswordDto.getUserName();
            String oldPassword = userNewPasswordDto.getOldPassword();
            String newPassword = userNewPasswordDto.getNewPassword();
            if (StringUtils.isEmpty(oldPassword) || StringUtils.isEmpty(newPassword) ||
                    oldPassword.equals(newPassword)) {
                responseMap.put("valid", "Valid dữ liệu thất bại");
                return RestResponseBuilder.buildValidError(responseMap);
            }
            AuthUser authUser = authUserService.findByUsername(userName);
            if (!passwordEncoder.matches(oldPassword, authUser.getPassword())) {
                responseMap.put("oldPassword", "Sai password");
                return RestResponseBuilder.buildValidError(responseMap);
            }
            String newEncodedPassword = passwordEncoder.encode(newPassword);
            authUser.setPassword(newEncodedPassword);
            authUserService.update(authUser);
            return RestResponseBuilder.buildSuccess(null);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("", ex);
            return RestResponseBuilder.buildRuntimeError(null);
        }
    }

}
