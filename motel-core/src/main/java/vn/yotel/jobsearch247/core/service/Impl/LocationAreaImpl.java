package vn.yotel.jobsearch247.core.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import vn.yotel.commons.bo.impl.GenericBoImpl;
import vn.yotel.jobsearch247.core.helper.BaseChoiceHelper;
import vn.yotel.jobsearch247.core.jpa.LocationArea;
import vn.yotel.jobsearch247.core.model.BaseChoice;
import vn.yotel.jobsearch247.core.repository.LocationAreaRepo;
import vn.yotel.jobsearch247.core.service.LocationAreaService;

import javax.annotation.Resource;
import java.util.List;

@Service(value = "locationAreaService")
@Slf4j
public class LocationAreaImpl extends GenericBoImpl<LocationArea, Long> implements LocationAreaService {

    @Resource
    LocationAreaRepo locationAreaRepo;


    @Override
    public <E extends JpaRepository<LocationArea, Long>> E getDAO() {
        return (E)locationAreaRepo;
    }

    @Override
    public List<BaseChoice> findAllByAdministrativeLevel() {
        List<Object[]> objects = locationAreaRepo.findAllByAdministrativeLevel();
        return BaseChoiceHelper.buildList(objects);
    }
}
