package vn.yotel.jobsearch247.core.service;

import vn.yotel.commons.bo.GenericBo;
import vn.yotel.jobsearch247.core.jpa.PostDetail;

public interface PostDetailService extends GenericBo<PostDetail, Long> {
    PostDetail findByPostId(Long postId);
}
