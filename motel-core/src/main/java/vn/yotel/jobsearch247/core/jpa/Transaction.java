package vn.yotel.jobsearch247.core.jpa;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "transaction")
public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "date_request", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRequest;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "money")
    private Integer money;

    @Column(name = "status")
    private Integer status;

    public Transaction() {}

}
