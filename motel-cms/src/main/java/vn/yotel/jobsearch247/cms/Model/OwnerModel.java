package vn.yotel.jobsearch247.cms.Model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class OwnerModel implements Serializable {
    private Long id;
    private String userName;
    private String cmnd;
    private String address;
    private String phoneNumber;
    private String email;
    private Integer status;
}
