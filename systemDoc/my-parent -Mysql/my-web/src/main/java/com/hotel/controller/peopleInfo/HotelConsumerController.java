package com.hotel.controller.peopleInfo;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotel.controller.BasicController;
import com.hotel.controller.food.HotelFoodsController;
import com.hotel.entity.ServiceResult;
import com.hotel.entity.foods.HotelFoods;
import com.hotel.entity.peopleInfo.HotelConsumer;
import com.hotel.entity.sys.Result;
import com.hotel.service.food.HotelFoodsService;
import com.hotel.service.peopleInfo.HotelConsumerService;

@RequestMapping("customer")
@Controller
public class HotelConsumerController  extends BasicController {
	  private static Logger logger = LogManager.getLogger(HotelConsumerController.class);
		
		@Autowired
		HotelConsumerService hotelConsumerService;
		@RequestMapping("")
		public String Index(HttpServletRequest request) {
			return "peopleInfo/consumer";
		}
		
		@RequestMapping("/page")
		@ResponseBody
		public Map<String, Object> queryOnePage(HttpServletRequest request) {
			InitDataTablesParams(request);
			Page<HotelConsumer> result =hotelConsumerService.queryOnePage(this.getPageIndex(),this.getPageRow(),this.getSort());
			Map<String, Object> mapresult=getResultMap(result);
			
			return mapresult;
		}
		
//		/**
//		 * 
//		 * @return
//		 */
//		@RequestMapping("/all")
//		@ResponseBody
//		public Result query() {
//			ServiceResult<List<HotelArround>> list =hotelArroundService.findAll();
//			Result result=new Result();
//			if(list.isSuccess())
//				result.setRightResult(list.getData());
//			else
//				result.setErrorResult("", list.getErrorMessage());
//			return result;
//		}

		@RequestMapping("/one")
		@ResponseBody
		public Result findById(String id) {
			ServiceResult<HotelConsumer> data= hotelConsumerService.findById(id);
			Result result=new Result();
			if(data.isSuccess()) {
				result.setRightResult(data.getData());
				return result;
			}else
			{
				result.setErrorResult("", data.getErrorMessage());
				return result;
			}
		}
		
		@RequestMapping("/save")
		@ResponseBody
		public Result update(@RequestParam Map<String, Object> param) {
			Result result=new Result();
			try {
				
				ServiceResult<HotelConsumer> data= hotelConsumerService.update(param);
				if(data.isSuccess()) {
					result.setRightResult(data.getData().getId());
					return result;
				}else
				{
					result.setErrorResult("", data.getErrorMessage());
					return result;
				}
			}catch (Exception e) {
				logger.error(e);
				result.setErrorResult(e);
				return result;
			}
		}
		@RequestMapping("/del")
		@ResponseBody
		public Result del(String id) {
			Result result=new Result();
			try {
				
				ServiceResult<String> data= hotelConsumerService.delete(id);
				if(data.isSuccess()) {
					result.setRightResult("success");
					return result;
				}else
				{
					result.setErrorResult("", data.getErrorMessage());
					return result;
				}
			}catch (Exception e) {
				logger.error(e);
				result.setErrorResult(e);
				return result;
			}
		}
		
		@RequestMapping("/batchdel")
	    @ResponseBody
	    public Result batchDelete(@RequestParam(value = "ids[]") String[] ids) {
	        Result result=new Result();
	        try {
	            ServiceResult<String> data= hotelConsumerService.batchDelete(ids);

	            if(data.isSuccess())
	                result.setRightResult(data.getData());
	            else
	                result.setErrorResult("", data.getErrorMessage());

	            return result;

	        }catch (Exception e) {
	            logger.error("批量删除数据出错",e);
	            result.setErrorResult("", e.getMessage());
	            return result;
	        }
	    }
		
		@RequestMapping("/cancel")
		@ResponseBody
		public Result cancel(String id,Integer canceled) {
			Result result=new Result();
			try {
				
				ServiceResult<String> data= hotelConsumerService.cancel(id,canceled);
				if(data.isSuccess()) {
					result.setRightResult("success");
					return result;
				}else
				{
					result.setErrorResult("", data.getErrorMessage());
					return result;
				}
			}catch (Exception e) {
				logger.error(e);
				result.setErrorResult(e);
				return result;
			}
		}
		
		
}
