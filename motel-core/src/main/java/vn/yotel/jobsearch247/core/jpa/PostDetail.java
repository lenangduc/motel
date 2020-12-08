package vn.yotel.jobsearch247.core.jpa;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "post_detail")
public class PostDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "post_id")
    private Long PostId;

    @Column(name = "address_room")
    private String addressRoom;

    @Column ( name = "address_related", columnDefinition = "TEXT")
    private String addressRelated;

    @Column( name = "room_type")
    private Integer roomType;

    @Column( name = "amount_room")
    private Integer amountRow;

    @Column( name = "price_room")
    private Integer priceRoom;

    @Column( name = "with_owner")
    private Integer withOwner;

    @Column( name = "area")
    private Integer area;

    @Column( name = "bathroom")
    private Integer bathroom;

    @Column( name = "heater")
    private Integer heater;

    @Column( name = "kitchen")
    private Integer kitchen;

    @Column( name = "balcony")
    private Integer balcony;

    @Column( name = "electricity_price")
    private Integer electricityPrice;

    @Column( name = "water_price")
    private Integer waterPrice;

    @Column ( name = "other_utility", columnDefinition = "TEXT")
    private String otherUtility;

    @Column( name = "duration")
    private Integer duration;

    PostDetail() {

    }

}


