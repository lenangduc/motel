package vn.yotel.jobsearch247.cms.requestDto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Data
public class PostDto implements Serializable
{
    private Long id;
    private String addressRoom;
    private Integer locationArea;
    private String addressRelated;
    private Integer roomType;
    private Integer amountRow;
    private Integer priceRoom;
    private Integer withOwner;
    private Integer area;
    private Integer bathroom;
    private Integer heater;
    private Integer kitchen;
    private Integer balcony;
    private Integer electricityPrice;
    private Integer waterPrice;
    private String otherUtility;
    private String locationName;
    private List<MultipartFile> images;

    public PostDto() {

    }

}