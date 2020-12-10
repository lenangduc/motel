package vn.yotel.jobsearch247.core.service;

import vn.yotel.commons.bo.GenericBo;
import vn.yotel.jobsearch247.core.jpa.LocationArea;
import vn.yotel.jobsearch247.core.model.BaseChoice;

import java.util.List;

public interface LocationAreaService extends GenericBo<LocationArea, Long> {
    List<BaseChoice> findAllByAdministrativeLevel();
}
