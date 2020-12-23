package vn.yotel.jobsearch247.api.employer.web.requestDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDto {
    private String postId;
    private String userName;
    private String content;
}
