package vn.yotel.jobsearch247.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.yotel.admin.jpa.AuthUser;
import vn.yotel.jobsearch247.core.model.BaseChoice;

import java.util.List;

@Repository(value = "authUserBackRepo")
public interface AuthUserBackRepo extends JpaRepository<AuthUser, Long> {

   @Query(value = "select a.id, a.user_name" +
           " from auth_user a " +
           " join auth_user_role aur on aur.user_id = a.id " +
           " where aur.role_id = 2", nativeQuery = true)
    List<Object[]> getBaseChoiceAccountOwner();
}
