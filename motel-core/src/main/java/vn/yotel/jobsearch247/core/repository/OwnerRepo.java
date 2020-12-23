package vn.yotel.jobsearch247.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.yotel.jobsearch247.core.jpa.Owner;

import java.util.List;

@Repository(value = "ownerRepo")
public interface OwnerRepo extends JpaRepository<Owner, Long> {

    @Query( value = "select o.id from Owner o where o.accountId = :id ", nativeQuery = false)
    Long findByAccountId( @Param("id") Long id);

    @Query( value = "select o.id, a.user_name, o.cmnd, o.address, o.phone_number, o.email, o.status" +
            " from owner o " +
            " join auth_user a on a.id = o.account_id " +
            " where o.status = 1 and ( :id is null or  o.id = :id )", nativeQuery = true)
    List<Object[]> getAll(@Param("id") Long id);
}
