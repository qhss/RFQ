package com.rfq.controller.rfq;

import com.rfq.controller.BasicController;
import com.rfq.entity.ServiceResult;
import com.rfq.entity.customer.Customer;
import com.rfq.entity.pop.PopApplyZF;
import com.rfq.entity.rfq.RFQ;
import com.rfq.entity.rfq.RfqCheckList;
import com.rfq.entity.sys.Result;
import com.rfq.service.customer.CustomerService;
import com.rfq.service.pop.PopApplyZFService;
import com.rfq.service.rfq.RfqCheckListService;
import com.rfq.service.rfq.RfqItemService;
import com.rfq.service.rfq.RfqService;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("rfq")
public class RfqController extends BasicController {
    private static Logger logger = LogManager.getLogger(RfqController.class);

    @Autowired
    RfqService rfqService;

    @Autowired
    RfqItemService rfqItemService;

    @Autowired
    RfqCheckListService rfqCheckListService;


    @Autowired
    CustomerService customerService;

    @Autowired
    PopApplyZFService popApplyZFService;
    /*
     * 我的RFQ页面入口
     */
    @RequestMapping("/list")
    public String list(){
        return "rfq/list";
    }

    /**
     * 审批记录页面入口
      * @return
     */
    @RequestMapping("/record")
    public String checkRecord(){
        return "rfq/record";
    }

    /**
     * 添加报价信息入口
     * @return
     */
    @RequestMapping("")
    public String add(){
        return "rfq/add";
    }


    /**
     * 我的RFQ分页查询
     */
    @RequestMapping("/page")
    @ResponseBody
    public Map<String, Object> queryOnePage(HttpServletRequest request,Integer kId,String kCardCode,String kItemcode) {

        InitDataTablesParams(request);
        Page<RFQ> result =rfqService.queryOnePage(kId,kCardCode,kItemcode,this.getPageIndex(),this.getPageRow(),this.getSort());
        Map<String, Object> mapResult=getResultMap(result);
        return mapResult;
    }

    /**
     *审批记录
     * 查checkList，只含有审批通过的记录（目前采用这种方式）
     * * rfqItems,审批通过不通过都有记录
     */
    @RequestMapping("/record/page")
    @ResponseBody
    public Map<String, Object>  checkRecordPage(HttpServletRequest request,Integer kRfqId,String kCardCode,String kItemcode){
        InitDataTablesParams(request);
        Page<Map<String,Object>> result =rfqCheckListService.queryOnePage(kRfqId,kCardCode,kItemcode,this.getPageIndex(),this.getPageRow(),this.getSort());
//        Page<Map<String,Object>> result =rfqItemService.queryOnePage(kRfqId,kCardCode,kItemcode,this.getPageIndex(),this.getPageRow(),this.getSort());
        Map<String, Object> mapResult=getResultMap(result);
        return mapResult;
    }

    /**
     * 根据公司和客户代码查询客户信息
     *
     */
    @RequestMapping("/checkCustomer")
    @ResponseBody
    public Result customerSearch(@RequestParam Map<String, Object> param){
        Result result = new Result();
        try {
            ServiceResult<Customer> data=customerService.checkCustomer(param);
            if (data.isSuccess()){
                result.setRightResult(data.getData());
                return result;
            } else {
                result.setErrorResult("", data.getErrorMessage());
                return result;
            }
        }catch (Exception e) {
            logger.error(e);
            result.setErrorResult(e);
            return result;
        }
    }
    /**
     * 查询spaName(用于选择)
     * @return
     */
    @RequestMapping("/allApplys")
    @ResponseBody
    public Result query() {
        List<PopApplyZF> list =popApplyZFService.findAll();
        Result result=new Result();
        result.setRightResult(list);
        return result;
    }
}
