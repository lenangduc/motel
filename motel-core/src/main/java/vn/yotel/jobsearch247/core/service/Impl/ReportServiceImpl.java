package vn.yotel.jobsearch247.core.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import vn.yotel.commons.bo.impl.GenericBoImpl;
import vn.yotel.jobsearch247.core.jpa.Report;
import vn.yotel.jobsearch247.core.repository.ReportRepo;
import vn.yotel.jobsearch247.core.service.ReportService;

import javax.annotation.Resource;

@Service(value = "reportService")
@Slf4j
public class ReportServiceImpl extends GenericBoImpl<Report, Long> implements ReportService {

    @Resource
    private ReportRepo reportRepo;

    @Override
    public <E extends JpaRepository<Report, Long>> E getDAO() {
        return (E) reportRepo;
    }
}
