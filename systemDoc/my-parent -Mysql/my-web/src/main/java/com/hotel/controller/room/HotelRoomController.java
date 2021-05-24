package com.hotel.controller.room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.hotel.entity.ServiceResult;
import com.hotel.entity.room.HotelRoom;
import com.hotel.entity.room.HotelRoomPhoto;
import com.hotel.entity.sys.Result;
import com.hotel.service.room.HotelRoomPhotoService;
import com.hotel.service.room.HotelRoomService;



@RequestMapping("room")
@Controller
public class HotelRoomController extends BasicController {
	
	private static Logger logger = LogManager.getLogger(HotelRoomController.class);
	
	@Autowired
	HotelRoomService hotelRoomService;
	
	@Autowired
	HotelRoomPhotoService hotelRoomPhotoService;
	
	@RequestMapping("")
	public String Index(HttpServletRequest request) {
		return "prod/room";
	}
	
	
	
	/**
	 * 分页方式查询所有房型
	 * @param request
	 * @return
	 */
	@RequestMapping("/page")
	@ResponseBody
	public Map<String, Object> queryOnePage(HttpServletRequest request) {
		String hotelId=request.getParameter("hotelId");
		InitDataTablesParams(request);
		Page<HotelRoom> result =hotelRoomService.queryOnePage(hotelId,this.getPageIndex(),this.getPageRow(),this.getSort());
		Map<String, Object> mapresult=getResultMap(result);
		
		return mapresult;
	}
	
//	/**
//	 * 查询所有
//	 * @return
//	 */
//	@RequestMapping("/all")
//	@ResponseBody
//	public Result query() {
//		Page<HotelRoom> resultPage =hotelRoomService.queryOnePage(this.getSort());
//		List<HotelRoom> list=resultPage.getContent();
//		
//		Result result=new Result();
//		result.setRightResult(list);
//		return result;
//	}

	@RequestMapping("/one")
	@ResponseBody
	public Result findById(String id) {
		Result result=new Result();
		try {
			ServiceResult<HotelRoom> data= hotelRoomService.findById(id);
			if(data.isSuccess()) {
				Map<String, Object> mapResult=new HashMap<String, Object>();
				HotelRoom room=data.getData();
				
				mapResult.put("id", room.getId());
				mapResult.put("area", room.getArea());
				mapResult.put("bathroomDevice", room.getBathroomDevice());
				mapResult.put("bedResume", room.getBedResume());
				mapResult.put("coverUrl", room.getCoverUrl());
				mapResult.put("foodDevice", room.getFoodDevice());
				mapResult.put("personMax", room.getPersonMax());
				mapResult.put("roomDevice", room.getRoomDevice());
				mapResult.put("typeName", room.getTypeName());
				
				//照片集合
				ServiceResult<List<HotelRoomPhoto>> photos=hotelRoomPhotoService.findByRoomId(room.getId());
				ArrayList<String > pics=new ArrayList<>();
				if(photos.isSuccess())
					for (HotelRoomPhoto photo : photos.getData()) {
						pics.add(photo.getUrl());
					}
				
				mapResult.put("pics", pics);
				
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
	public Result update(@RequestParam Map<String, Object> param,
			@RequestParam(value = "pics[]") String[] pics) {
		
		Result result=new Result();
		try {
			
			ServiceResult<HotelRoom> data= hotelRoomService.update(param,pics);
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
			
			ServiceResult<String> data= hotelRoomService.delete(id);
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
            ServiceResult<String> data= hotelRoomService.batchDelete(ids);

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
			
			ServiceResult<String> data= hotelRoomService.cancel(id,canceled);
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
