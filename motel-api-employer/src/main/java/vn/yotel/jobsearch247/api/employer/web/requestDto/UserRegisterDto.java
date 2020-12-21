package vn.yotel.jobsearch247.api.employer.web.requestDto;

import lombok.Data;

@Data
public class UserRegisterDto {

	private String username;

	private String password;
	
	private String rePassword;
}
