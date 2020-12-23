package vn.yotel.jobsearch247.cms.Model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
public class PostListModel implements Serializable {

    private Long id;
    private String postId;
    private Timestamp datePost;
    private Timestamp dateExpired;
    private String addressRoom;
    private Integer isAccept;
    private Integer isRental;
    private Integer status;
    private Integer star;
    private Integer amountCare;

    public PostListModel() {

    }
}