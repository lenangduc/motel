package vn.yotel.jobsearch247.core.jpa;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "post_detail")
public class PostDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "address_room")
    private String addressRoom;

    @Column(name = "location_area")
    private Integer locationArea;

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

    @Column( name = "is_rental")
    private Integer isRental;

    @Column( name = "is_accept")
    private Integer isAccept;

    @Temporal(value = TemporalType.DATE)
    @Column( name = "date_request")
    private Date dateRequest;

    @Temporal(value = TemporalType.DATE)
    @Column( name = "date_post")
    private Date datePost;

    @Column( name = "location_name")
    private String locationName;

    @Column( name = "img_path")
    private String imgPath;

    public enum isAccept {
        ACCEPT(1), NOT_ACCEPT(0);

        private final Integer value;

        isAccept(int value) {
            this.value = value;
        }

        public Integer getValue() {
            return this.value;
        }
    }

    public PostDetail() {

    }

}


