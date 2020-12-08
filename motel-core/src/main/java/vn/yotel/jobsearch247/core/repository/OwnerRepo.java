package vn.yotel.jobsearch247.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.yotel.jobsearch247.core.jpa.Owner;

@Repository(value = "ownerRepo")
public interface OwnerRepo extends JpaRepository<Owner, Long> {

    @Query( value = "select o.id from Owner o where o.accountId = :id ", nativeQuery = false)
    Long findByAccountId( @Param("id") Long id);

}
