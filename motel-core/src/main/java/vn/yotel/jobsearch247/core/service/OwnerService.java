package vn.yotel.jobsearch247.core.service;

import vn.yotel.commons.bo.GenericBo;
import vn.yotel.jobsearch247.core.jpa.Owner;

public interface OwnerService extends GenericBo<Owner, Long> {

    Long findByAccountId(Long id);
}
