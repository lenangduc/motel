package vn.yotel.jobsearch247.api.employer.web.requestDto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ReportDto {
    private String postId;
    private String userName;
    private String comment;
}
