package com.rfq.controller.rfq;

import com.rfq.controller.BasicController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("rfqItem")
public class RfqItemController extends BasicController {
    private static Logger logger = LogManager.getLogger(RfqItemController.class);
}
