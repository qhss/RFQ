package com.rfq.controller.customer;

import com.rfq.controller.BasicController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("customer")
public class CustomerController extends BasicController {
    private static Logger logger = LogManager.getLogger(CustomerController.class);
}
