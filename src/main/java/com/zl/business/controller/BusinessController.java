package com.zl.business.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping(value = "/business")
public class BusinessController {

    @RequestMapping(value = "/toCustomerManager")
    public String toCustomerManager(){
        return "business/customer/customerManager";
    }
}
