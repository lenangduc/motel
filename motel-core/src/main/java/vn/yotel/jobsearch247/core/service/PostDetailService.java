package vn.yotel.jobsearch247.core.service;

import vn.yotel.commons.bo.GenericBo;
import vn.yotel.jobsearch247.core.jpa.PostDetail;

import java.util.List;

public interface PostDetailService extends GenericBo<PostDetail, Long> {

    List<PostDetail> findListPostByOwnerId(Long ownerId);

    List<PostDetail> findListByIsAccept(Integer value);
}
