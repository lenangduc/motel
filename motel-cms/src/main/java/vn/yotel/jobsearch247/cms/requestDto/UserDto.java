package vn.yotel.jobsearch247.cms.requestDto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserDto implements Serializable {

    private String username;

    private String password;

    private String rePassword;

    public UserDto() {

    }
}
