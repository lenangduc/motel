package vn.yotel.jobsearch247.core.service;

import vn.yotel.commons.bo.GenericBo;
import vn.yotel.jobsearch247.core.jpa.PostSave;

import java.util.List;

public interface PostSaveService extends GenericBo<PostSave, Long> {
    List<Object[]> findByUserId(Long userId);

    PostSave checkIsSave(Long userId, Long postId);
}
