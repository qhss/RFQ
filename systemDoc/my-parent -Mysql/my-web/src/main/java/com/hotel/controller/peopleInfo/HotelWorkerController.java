package com.hotel.controller.peopleInfo;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.hotel.config.sapConnection.SAPConnUtils;
import com.hotel.config.sapConnection.SapConn;
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
import com.hotel.entity.peopleInfo.HotelWorker;
import com.hotel.entity.sys.Result;
import com.hotel.service.food.HotelFoodsService;
import com.hotel.service.peopleInfo.HotelWorkerService;

@RequestMapping("worker")
@Controller
public class HotelWorkerController extends BasicController {
	 private static Logger logger = LogManager.getLogger(HotelWorkerController.class);
		
		@Autowired
		HotelWorkerService hotelWorkerService;

		@Resource
	    SAPConnUtils sapConnUtils;

		@RequestMapping("")
		public String Index(HttpServletRequest request) {
			return "peopleInfo/worker";
		}
		
		@RequestMapping("/page")
		@ResponseBody
		public Map<String, Object> queryOnePage(HttpServletRequest request) {
			InitDataTablesParams(request);
			Page<HotelWorker> result =hotelWorkerService.queryOnePage(this.getPageIndex(),this.getPageRow(),this.getSort());
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
			ServiceResult<HotelWorker> data= hotelWorkerService.findById(id);
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
				
				ServiceResult<HotelWorker> data= hotelWorkerService.update(param);
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
				
				ServiceResult<String> data= hotelWorkerService.delete(id);
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
	            ServiceResult<String> data= hotelWorkerService.batchDelete(ids);

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
				
				ServiceResult<String> data= hotelWorkerService.cancel(id,canceled);
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
		@RequestMapping("/test")
		@ResponseBody
		public String  test(){

			SapConn con = new SapConn(
					"192.168.16.238",
					"00",
					"800",
					"HSIT-lucky",
					"lucky987",
					"zh",
					"200",
					"200",
					""
			);
			sapConnUtils.connect(con,"customer");
			return "测试";
		}
		
}
