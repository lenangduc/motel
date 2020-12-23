package vn.yotel.jobsearch247.api.employer.web.requestDto;

import lombok.Data;

@Data
public class UserNewPasswordDto {
    private String userName;
    private String oldPassword;
    private String newPassword;
}
