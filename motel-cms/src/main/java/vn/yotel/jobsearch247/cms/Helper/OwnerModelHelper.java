package vn.yotel.jobsearch247.cms.Helper;

import org.apache.commons.collections.CollectionUtils;
import vn.yotel.jobsearch247.cms.Model.OwnerModel;
import vn.yotel.jobsearch247.cms.util.TypeConvert;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class OwnerModelHelper {
    public static List<OwnerModel> parseOwnerLists(List<Object[]> objects) {
        if (CollectionUtils.isEmpty(objects)) {
            return new ArrayList<>();
        }

        List<OwnerModel> ownerModels = new ArrayList<>();
        try {
            for (Object[] object : objects) {
                OwnerModel ownerModel = new OwnerModel();

                BigInteger id = TypeConvert.parseObj(object[0], BigInteger.class );
                ownerModel.setId( id == null ? null : id.longValue());
                ownerModel.setUserName(TypeConvert.parseObj(object[1], String.class));
                ownerModel.setCmnd(TypeConvert.parseObj(object[2], String.class));
                ownerModel.setAddress(TypeConvert.parseObj(object[3], String.class));
                ownerModel.setPhoneNumber(TypeConvert.parseObj(object[4], String.class));
                ownerModel.setEmail(TypeConvert.parseObj(object[5], String.class));
                ownerModel.setStatus(TypeConvert.parseObj(object[6], Integer.class));
                ownerModels.add(ownerModel);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ownerModels;
    }
}
