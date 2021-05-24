package com.rfq.controller.href;

import com.rfq.controller.BasicController;
import com.rfq.controller.rfq.RfqController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("href")
public class HrefController extends BasicController {

    private static Logger logger = LogManager.getLogger(HrefController.class);


    @RequestMapping("/MSOA")
    public String href(){
        return "href/MSOA";
    }
}
