package vn.yotel.jobsearch247.api.employer.web.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.yotel.jobsearch247.api.employer.web.model.ResponseData;
import vn.yotel.jobsearch247.api.employer.web.util.RestResponseBuilder;
import vn.yotel.jobsearch247.core.service.LocationAreaService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/api-location")
@Slf4j
public class LocationAreaApiController {

    @Autowired
    private LocationAreaService locationAreaService;

    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public ResponseEntity<ResponseData> locationArea() {
        try {
            List<Object[]> listLocation = locationAreaService.findLocationAreaApi();
            return RestResponseBuilder.buildSuccess(listLocation);
        } catch (Exception e) {
            log.error("", e);
            return RestResponseBuilder.buildRuntimeError(null);
        }
    }
}
