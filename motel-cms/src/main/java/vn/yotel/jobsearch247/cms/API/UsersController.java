package vn.yotel.jobsearch247.cms.API;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.yotel.admin.jpa.AuthUser;
import vn.yotel.admin.jpa.Role;
import vn.yotel.admin.service.AuthRoleService;
import vn.yotel.admin.service.AuthUserService;
import vn.yotel.jobsearch247.cms.Contants.Constant;
import vn.yotel.jobsearch247.cms.Model.ResponseData;
import vn.yotel.jobsearch247.cms.enums.UserRoleEnum;
import vn.yotel.jobsearch247.cms.enums.UserStatusEnum;
import vn.yotel.jobsearch247.cms.requestDto.UserDto;
import vn.yotel.jobsearch247.cms.util.RestResponseBuilder;

import javax.transaction.Transactional;
import java.util.*;
import java.util.regex.Pattern;

@RestController
@RequestMapping(value = "/api/account")
@Slf4j
public class UsersController {

    @Autowired
    private AuthUserService authUserService;

    @Autowired
    private AuthRoleService authRoleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ResponseEntity<ResponseData> createUser(@RequestBody UserDto model) {
        Map<String, Object> responseMap = new HashMap<>();

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
        authUser.setPassword(encryptPassword);
        authUser.setStatus(UserStatusEnum.ACTIVE.value());
        authUser.setAuthRoles(roles);
        authUserService.create(authUser);
        return RestResponseBuilder.buildSuccess(responseMap);
    }
}
