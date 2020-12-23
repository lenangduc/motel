package vn.yotel.jobsearch247.core.service;

import vn.yotel.commons.bo.GenericBo;
import vn.yotel.jobsearch247.core.jpa.Star;

import java.util.List;

public interface StarService extends GenericBo<Star, Long> {
    List<Star> findByPostId(Long postId);
}
