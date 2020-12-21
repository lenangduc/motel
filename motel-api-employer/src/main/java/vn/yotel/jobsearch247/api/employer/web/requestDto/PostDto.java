package vn.yotel.jobsearch247.api.employer.web.requestDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDto {
    private String postId;
    private Integer typeRoom;
    private Integer locationId;
    private Integer priceRoom;
    private Integer area;
}
