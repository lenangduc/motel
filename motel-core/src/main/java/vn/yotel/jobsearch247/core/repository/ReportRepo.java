package vn.yotel.jobsearch247.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.yotel.jobsearch247.core.jpa.Report;

@Repository(value = "reportRepo")
public interface ReportRepo extends JpaRepository<Report, Long> {
}
