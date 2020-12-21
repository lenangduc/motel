package vn.yotel.jobsearch247.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.yotel.jobsearch247.core.jpa.LocationArea;

import java.util.List;

@Repository(value = "locationAreaRepo")
public interface LocationAreaRepo extends JpaRepository<LocationArea, Long> {
    @Query(value = "select l.id, case l1.name when l1.name is null then concat(l.name, \", \", l1.name) else l.name end " +
            " from location_area l \n " +
            " left join location_area l1 on l.parent_id = l1.id " , nativeQuery = true)
    List<Object[]> findAllByAdministrativeLevel();

}
