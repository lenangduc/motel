package vn.yotel.jobsearch247.cms.Helper;

import org.apache.commons.collections.CollectionUtils;
import vn.yotel.jobsearch247.cms.Model.PostListModel;
import vn.yotel.jobsearch247.cms.util.TypeConvert;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostListHelper {
    public static List<PostListModel> parsePostLists(List<Object[]> objects) {
        if (CollectionUtils.isEmpty(objects)) {
            return new ArrayList<>();
        }

        List<PostListModel> PostLists = new ArrayList<>();
        try {
            for (Object[] object : objects) {
                PostListModel postListModel = new PostListModel();

                BigInteger id = TypeConvert.parseObj(object[0], BigInteger.class );
                postListModel.setId( id == null ? null : id.longValue());
//                postListModel.setId(TypeConvert.parseObj(object[0], Long.class));
                postListModel.setPostId(TypeConvert.parseObj(object[1], String.class));
                postListModel.setDatePost(TypeConvert.parseObj(object[2], Timestamp.class));
                postListModel.setDateExpired(TypeConvert.parseObj(object[3], Timestamp.class));
                postListModel.setAddressRoom(TypeConvert.parseObj(object[4], String.class));
                postListModel.setIsAccept(TypeConvert.parseObj(object[5], Integer.class));
                postListModel.setIsRental(TypeConvert.parseObj(object[6], Integer.class));
                postListModel.setStatus(TypeConvert.parseObj(object[7], Integer.class));
                PostLists.add(postListModel);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return PostLists;
    }
}

