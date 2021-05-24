package com.hotel.service.room;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.hotel.dao.room.HotelRepository;
import com.hotel.dao.room.HotelPlanRepository;
import com.hotel.dao.room.HotelRoomRepository;
import com.hotel.entity.ServiceResult;
import com.hotel.entity.room.HotelPlan;
import com.hotel.entity.room.HotelRoom;
import com.hotel.service.BasicServiceImpl;
import com.hotel.service.where.ExcludeDeleted;
import com.hotel.utils.DateTime;

@Service
public class HotelPlanService extends BasicServiceImpl  {
	
	private final Logger logger = LoggerFactory.getLogger(HotelPlanService.class);

	@Resource
	HotelRepository hotelRepository;
	
	@Autowired
	HotelPlanRepository hotelPlanRepository;
	
	@Autowired
	HotelRoomRepository hotelRoomRepository;

	public ServiceResult<HotelPlan> findById(String id) {
		if(StringUtils.isBlank(id))
			id=getLoginUser().getData().getHotelId();
		if(StringUtils.isBlank(id))
			return new ServiceResult<>(true,"标示有误");
		
		Optional<HotelPlan> result=hotelPlanRepository.findById(id);
		if(result.isPresent())
			return new ServiceResult<HotelPlan>(result.get());
		else
			return null;
	}
	
	/**
	 *    按时间段分页查询计划
	 * @param roomId
	 * @param startDate
	 * @param endDate
	 * @param page
	 * @param row
	 * @param sort
	 * @return
	 */
	public Page<HotelPlan> queryOnePage(String roomId, String startDate,String endDate, Integer page, Integer row, Sort sort) {
		
		if(StringUtils.isBlank(startDate) && StringUtils.isBlank(endDate)) {
			//没有日期条件
			Page<HotelPlan> list= hotelPlanRepository.findByHotelRoomIdAndDeleted(roomId,0,PageRequest.of(page,row,sort));
			return list;
		}
		else {
			//有日期条件
			if(StringUtils.isBlank(startDate))
				startDate=endDate;
			if(StringUtils.isBlank(endDate))
				endDate=startDate;
			Integer start= DateTime.StrToTimestamp(startDate);
			Integer end= DateTime.StrToTimestamp(endDate);
			
			Page<HotelPlan> list= hotelPlanRepository.findByHotelRoomIdAndDeletedAndDateBetween(roomId,0,start,end,PageRequest.of(page,row,sort));
			return list;
		}
	}

	/**
	 *    查询指定日期段的所有房型
	 * @param roomId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public ServiceResult<List<HotelPlan>> findByDateRange(String roomId, String startDate,String endDate) {
		if(StringUtils.isBlank(startDate))
			startDate=endDate;
		if(StringUtils.isBlank(endDate))
			endDate=startDate;
		Integer start= DateTime.StrToTimestamp(startDate);
		Integer end= DateTime.StrToTimestamp(endDate);
		
		List<HotelPlan> list=hotelPlanRepository.findByHotelRoomIdAndDeletedAndDateBetween(roomId, 0, start, end);
		
		return new ServiceResult<>(list);
	}
	
	
	public List<HotelPlan> findAll() {
		List<HotelPlan> list=hotelPlanRepository.findAll(new ExcludeDeleted<HotelPlan>());
		return list;
	}

	
	/**
	 * 修改单条数据
	 * @param param
	 * @return
	 */
	public ServiceResult<HotelPlan> update(Map<String, Object> param) {
		String id=param.get("id")==null?"":param.get("id").toString();
		HotelPlan hotelPlan=new HotelPlan();
		Optional<HotelPlan> result=hotelPlanRepository.findById(id);
		if(result.isPresent()) {
			hotelPlan=result.get();
		}else {
			hotelPlan.setDeleted(HotelPlan.DELETE_STATE_UNDELETE);
			hotelPlan.setCanceled(HotelPlan.CANCEL_STATE_UNCENCEL);
			hotelPlan.setSalesCount(0);
			
			//查找房型和酒店
			String roomId=MapUtils.getString(param, "hotelRoomId","");
			if(StringUtils.isBlank(roomId)) {
				return new ServiceResult<>(true,"房型标示错误");
			}
			Optional<HotelRoom> roomOptional=hotelRoomRepository.findById(roomId);
			if(roomOptional.isPresent()) {
				hotelPlan.setHotelRoom(roomOptional.get());
				hotelPlan.setHotel(roomOptional.get().getHotel());
			}else {
				
				return new ServiceResult<>(true,"房型标示错误");
			}
			
		}
		
		Integer planCount=MapUtils.getInteger(param, "planCount",0);
		Double price=MapUtils.getDoubleValue(param, "price",0);
		Double memberPrice=MapUtils.getDoubleValue(param, "memberPrice",0);
		
		hotelPlan.setDate(MapUtils.getInteger(param, "date",0));
		hotelPlan.setMemberPrice(new BigDecimal(memberPrice));
		hotelPlan.setPlanCount(planCount);
		hotelPlan.setPrice(new BigDecimal(price));
		

		hotelPlan=hotelPlanRepository.save(hotelPlan);
		
		return new ServiceResult<>(hotelPlan);
	}

	/**
	 * 取消或反取消
	 * @param id
	 * @param cancel
	 * @return
	 */
	public ServiceResult<String> cancel(String id, Integer cancel) {
		if(StringUtils.isBlank(id) || cancel==null) {
			return  new ServiceResult<>(true,"参数错误");
		}
		
		Optional<HotelPlan> result=hotelPlanRepository.findById(id);
		if(result.isPresent()) {
			HotelPlan hotel=result.get();
			if(cancel==hotel.getCanceled())
				return new ServiceResult<>(true,"和要修改的状态相同,无需修改");
			else 
				hotel.setCanceled(cancel);
			
			hotel=hotelPlanRepository.save(hotel);
		}else {
			return new ServiceResult<>(true,"未找到要修改的数据"); 
		}
		
		return new ServiceResult<>("success"); 
	}

	/**
	 * 逻辑删除数据
	 * @param id
	 * @return
	 */
	public ServiceResult<String> delete(String id) {
		if(StringUtils.isBlank(id)) {
			return  new ServiceResult<>(true,"参数错误");
		}
		
		Optional<HotelPlan> result=hotelPlanRepository.findById(id);
		if(result.isPresent()) {
			HotelPlan hotel=result.get();
			
			hotel.setDeleted(HotelPlan.DELETE_STATE_DELETED);
			
			hotel=hotelPlanRepository.save(hotel);
		}else {
			return new ServiceResult<>(true,"未找到要删除的数据"); 
		}
		
		return new ServiceResult<>("success"); 
	}

	/**
	 * 批量 逻辑删除
	 * @param ids
	 * @return
	 */
	public ServiceResult<String> batchDelete(String[] ids) {
		try {
            String result="";
            for (String id : ids) {
                if(id!=null&&!StringUtils.isBlank(id)){
                    result+=(Delete(id).getErrorMessage());
                }
            }
            if(!StringUtils.isBlank(result))
                return new ServiceResult<String>(true,"未能完全删除"+result);
            return new ServiceResult<String>("");
        }catch (Exception e) {
            return new ServiceResult<String>(true,e.getMessage());
        }
	}
	
	public ServiceResult<String> Delete(String id) {
        try {
            if(StringUtils.isNotBlank(id)) {
            	Optional<HotelPlan> objOption=hotelPlanRepository.findById(id);
            	if(objOption.isPresent()) {
	                //TODO 增加删除校验
            		HotelPlan obj=objOption.get();
	            	obj.setDeleted(HotelPlan.DELETE_STATE_DELETED);
	                
	            	hotelPlanRepository.save(obj);
	            	return new ServiceResult<>("");
            	}
            	else
            	{
            		return new ServiceResult<>(true,"未查到要删除的数据");
            	}
            }

            return new ServiceResult<>(true,"参数不能不能为空");

        } catch (Exception e) {
            logger.error("删除储位单条数据出错",e);
            return new ServiceResult<>(true,e.getMessage());
        }
    }

	/**
	 * 查询指定房间某个日期的计划
	 * @param hotelId
	 * @param roomId
	 * @param theDayInt
	 * @return
	 */
	public ServiceResult<HotelPlan> findByHotelIdRoomIdAndDate(String hotelId, String roomId, Integer theDayInt) {

		Optional<HotelPlan> objOption=hotelPlanRepository.findByHotelIdAndHotelRoomIdAndDateAndDeletedAndCanceled(hotelId,roomId,theDayInt,0,0);
		if(objOption.isPresent())
			return new ServiceResult<>(objOption.get());
		else {
			return new ServiceResult<>(true,"null");
		}
	}

	
	
}
