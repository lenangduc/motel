package vn.yotel.jobsearch247.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.yotel.jobsearch247.core.jpa.owner;

@Repository(value = "ownerRepo")
public interface OwnerRepo extends JpaRepository<owner, Long> {
}
