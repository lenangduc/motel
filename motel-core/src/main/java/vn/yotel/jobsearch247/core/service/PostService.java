package vn.yotel.jobsearch247.core.service;

import vn.yotel.commons.bo.GenericBo;
import vn.yotel.jobsearch247.core.jpa.Post;

import java.util.List;

public interface PostService extends GenericBo<Post, Long> {
    public Post findById(Long id);

    List<Post> findListPostByOwnerId(Long ownerId);
}
