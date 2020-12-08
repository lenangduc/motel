package vn.yotel.jobsearch247.core.jpa;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Data
@Entity
@Table(name = "post")
public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "date_request")
    private Date dateRequest;

    @Column(name = "date_post")
    private Date datePost;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "is_rental")
    private Integer isRental;

    Post() {

    }
}
