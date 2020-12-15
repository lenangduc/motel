package vn.yotel.jobsearch247.cms.Model;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Data
public class TransactionModel implements Serializable {

    private Long id;
    private String postId;
    private Timestamp dateRequest;
    private Integer duration;
    private Integer money;
    private Integer status;

    public TransactionModel() {

    }

}
