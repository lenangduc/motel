package vn.yotel.jobsearch247.core.jpa;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "notification")
public class Notification implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( name = "content")
    private String content;

    @Column( name = "user_send_id")
    private Long userSendId;

    @Column( name = "user_receiver_id")
    private Long userReceiverId;

    @Column(name = "time", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    @Column( name = "status")
    private Integer status;

    public Notification() {

    }

}
