package vn.yotel.jobsearch247.cms.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import vn.yotel.jobsearch247.core.model.BaseChoice;
import vn.yotel.jobsearch247.core.service.LocationAreaService;

import java.util.List;

@Controller
@RequestMapping(value = "/location-area")
@Slf4j
public class LocationAreaController {

    @Autowired
    private LocationAreaService locationAreaService;

    @RequestMapping(value = "/list/location", method = RequestMethod.GET)
    @ResponseBody
    public List<BaseChoice> ListLocation() {
        try{
            List<BaseChoice> listLocation = locationAreaService.findAllByAdministrativeLevel();
            return listLocation;
        }catch (Exception e){
            log.error("", e);
            throw e;
        }

    }

}
