package com.hotel.controller.room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotel.controller.BasicController;
import com.hotel.entity.ServiceResult;
import com.hotel.entity.room.HotelPlan;
import com.hotel.entity.room.HotelRoom;
import com.hotel.entity.sys.Result;
import com.hotel.service.room.HotelPlanService;
import com.hotel.service.room.HotelRoomPhotoService;
import com.hotel.service.room.HotelRoomService;



@RequestMapping("price")
@Controller
public class HotelPriceController extends BasicController {
	
	private static Logger logger = LogManager.getLogger(HotelPriceController.class);
	
	@Autowired
	HotelRoomService hotelRoomService;
	
	@Autowired
	HotelRoomPhotoService hotelRoomPhotoService;
	
	@Autowired
	HotelPlanService hotelPlanService;
	
	@RequestMapping("")
	public String Index(HttpServletRequest request) {
		return "prod/price";
	}
	
	/**
	 * 加载常规价格
	 * @param param
	 * @return
	 */
	@RequestMapping("/loadnormal")
	@ResponseBody
	public Result LoadNormalPrice(@RequestParam Map<String, Object> param) {
		
		Result result=new Result();
		try {
			String roomid=MapUtils.getString(param, "id","");
			
			ServiceResult<HotelRoom> data= hotelRoomService.findById(roomid);
			if(data.isSuccess()) {
				Map<String,Object> resumtMap=new HashMap<>();
				resumtMap.put("id", data.getData().getId());
				resumtMap.put("planCount", data.getData().getPlanCount());
				resumtMap.put("price", data.getData().getPrice());
				resumtMap.put("memberPrice", data.getData().getMemberPrice());
				
				result.setRightResult(resumtMap);
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
	
	/**
	 * 保存房型默认价格
	 * @param param
	 * @return
	 */
	@RequestMapping("/savenormal")
	@ResponseBody
	public Result update(@RequestParam Map<String, Object> param) {
		
		Result result=new Result();
		try {
			ServiceResult<String> data= hotelRoomService.savePrice(param);
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
	
	
	/**
	 * 查询所有房间类型(用于选择)
	 * @return
	 */
	@RequestMapping("/allroom")
	@ResponseBody
	public Result query() {
		List<HotelRoom> list =hotelRoomService.findAll();
		List<Map<String, Object>> mapList=new ArrayList<>();
		for (HotelRoom hotelRoom : list) {
			Map<String, Object> map=new HashMap<>();
			map.put("name", hotelRoom.getTypeName());
			map.put("id", hotelRoom.getId());
			
			mapList.add(map);
		}
		Result result=new Result();
		result.setRightResult(mapList);
		return result;
	}
	
	
	
	//================================//
	
	/**
	 * 分页方式查询所有房型
	 * @param request
	 * @return
	 */
	@RequestMapping("/page")
	@ResponseBody
	public Map<String, Object> queryOnePage(HttpServletRequest request) {
		try {
			String startDate=request.getParameter("startDate");
			String endDate=request.getParameter("endDate");
			String roomId=request.getParameter("roomId");
			
			InitDataTablesParams(request);
			Page<HotelPlan> result =hotelPlanService.queryOnePage(roomId,startDate,endDate,this.getPageIndex(),this.getPageRow(),this.getSort());
			Map<String, Object> mapresult=getResultMap(result);
			
			return mapresult;
			
		}catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	
	

	@RequestMapping("/one")
	@ResponseBody
	public Result findById(String id) {
		Result result=new Result();
		try {
			ServiceResult<HotelPlan> data= hotelPlanService.findById(id);
			if(data.isSuccess()) {
				Map<String, Object> mapResult=new HashMap<String, Object>();
				HotelPlan plan=data.getData();
				
				mapResult.put("id", plan.getId());
				mapResult.put("date", plan.getDate());
				mapResult.put("planCount", plan.getPlanCount());
				mapResult.put("price", plan.getPrice());
				mapResult.put("memberPrice", plan.getMemberPrice());
				mapResult.put("hotelRoomId", plan.getHotelRoom().getId());
				mapResult.put("hotelId", plan.getHotel().getId());
				
		
				result.setRightResult(mapResult);
				
				
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
	
	
	@RequestMapping("/save")
	@ResponseBody
	public Result updatePlan(@RequestParam Map<String, Object> param) {
		
		Result result=new Result();
		try {
			
			ServiceResult<HotelPlan> data= hotelPlanService.update(param);
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
			
			ServiceResult<String> data= hotelPlanService.delete(id);
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
            ServiceResult<String> data= hotelPlanService.batchDelete(ids);

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
			
			ServiceResult<String> data= hotelPlanService.cancel(id,canceled);
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
