package vn.yotel.jobsearch247.core.service;

import vn.yotel.commons.bo.GenericBo;
import vn.yotel.jobsearch247.core.jpa.PostDetail;

import java.util.Date;
import java.util.List;

public interface PostDetailService extends GenericBo<PostDetail, Long> {

    List<Object[]> findListPostByOwnerId( Long ownerId, String postId, Integer isAccept);

//    List<PostDetail> findListByIsAccept(Integer value);

    List<Object[]> findListPostApi();

    List<Object[]> findByFilter(String postId, Integer locationId, Integer typeRoom, Integer area, Integer fromPrice, Integer toPrice);

    Object[] findOneByPostId(String postId);

    PostDetail findByPostId(String postId);
}
