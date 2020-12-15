package vn.yotel.jobsearch247.cms.Model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PostApi implements Serializable {

    private Long id;
    private String postId;
    private String userName;
    private String phoneNumber;
    private String email;
    private String addressRoom;
    private String addressRelated;
    private Integer roomType;
    private Integer amountRoom;
    private Integer priceRoom;
    private Integer withOwner;
    private Integer bathRoom;
    private Integer heater;
    private Integer kitchen;
    private Integer balcony;
    private Integer electricityPrice;
    private Integer waterPrice;
    private Integer otherUtility;
    private Integer imgPath;

    public PostApi(){

    }

}
