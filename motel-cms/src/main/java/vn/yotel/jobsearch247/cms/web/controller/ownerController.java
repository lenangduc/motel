package vn.yotel.jobsearch247.cms.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vn.yotel.jobsearch247.core.service.OwnerService;

@Controller
@RequestMapping(value = "/owner")
@Slf4j
public class ownerController {

    @Autowired
    private OwnerService ownerService;

    @RequestMapping( value = "list-post", method = RequestMethod.GET)
    public String listPost (Model model) {

        return "test";
    }
}
