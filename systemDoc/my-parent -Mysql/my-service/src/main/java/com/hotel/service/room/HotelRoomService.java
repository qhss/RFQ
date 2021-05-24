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
import com.hotel.dao.room.HotelRoomRepository;
import com.hotel.entity.ServiceResult;
import com.hotel.entity.room.Hotel;
import com.hotel.entity.room.HotelRoom;
import com.hotel.service.BasicServiceImpl;
import com.hotel.service.where.ExcludeDeleted;

@Service
public class HotelRoomService extends BasicServiceImpl  {
	
	private final Logger logger = LoggerFactory.getLogger(HotelRoomService.class);

	@Resource
	HotelRepository hotelRepository;
	
	@Resource
	HotelRoomRepository hotelRoomRepository;
	
	@Autowired
	HotelRoomPhotoService hotelRoomPhotoService;
	
	
	/**
	 * 分页方式查询所有数据,为了使用封装的方法
	 * @param sort
	 * @return
	 */
	public Page<HotelRoom> queryOnePage(String hotelId,Integer page, Integer row,Sort sort) {
		if(StringUtils.isBlank(hotelId))
			hotelId=getLoginUser().getData().getHotelId();
		Page<HotelRoom> list= hotelRoomRepository.findByHotelIdAndDeleted(hotelId,0,PageRequest.of(page,row,sort));
		return list;
	}
	
	/**
	 * 查询所有房型
	 * @return
	 */
	public ServiceResult<List<HotelRoom>> findByHotelId(String hotelId) {
		if(StringUtils.isBlank(hotelId))
			hotelId=getLoginUser().getData().getHotelId();
		
		List<HotelRoom> list=hotelRoomRepository.findByHotelIdAndDeleted(hotelId,0);
		
		return new ServiceResult<>(list);
	}
	
	
	/**
	 * 根据主键查询房型
	 */
	public ServiceResult<HotelRoom> findById(String id) {
		if(StringUtils.isBlank(id))
			return new ServiceResult<>(true,"房型标示有误");
		Optional<HotelRoom> result=hotelRoomRepository.findById(id);
		if(result.isPresent())
			return new ServiceResult<HotelRoom>(result.get());
		else
			return null;
	}


	/**
	 * 查询所有房型
	 * @return
	 */
	public List<HotelRoom> findAll() {
		List<HotelRoom> list=hotelRoomRepository.findAll(new ExcludeDeleted<HotelRoom>());
		return list;
	}

	
	/**
	 * 修改单条数据
	 * @param param
	 * @return
	 */
	public ServiceResult<HotelRoom> update(Map<String, Object> param,String[] pics) {
		if(pics==null) {
			return new ServiceResult<>(true,"图册不能为null");
		}
//		1.查找酒店数据
		String hotelId=param.get("hotelId")==null?"":param.get("hotelId").toString();
		if(StringUtils.isBlank(hotelId))
			hotelId=getLoginUser().getData().getHotelId();
		Hotel hotel=new Hotel();
		Optional<Hotel> hotelOption=hotelRepository.findById(hotelId);
		if(hotelOption.isPresent())
			hotel=hotelOption.get();
		else {
			return new ServiceResult<>(true,"未获取所属酒店数据");
		}
		
		//2.修改房间
		String id=param.get("id")==null?"":param.get("id").toString();
		HotelRoom hotelRoom=new HotelRoom();
		Optional<HotelRoom> result=hotelRoomRepository.findById(id);
		if(result.isPresent()) {
			hotelRoom=result.get();
		}else {
			//新增
			hotelRoom.setDeleted(HotelRoom.DELETE_STATE_UNDELETE);
			hotelRoom.setCanceled(HotelRoom.CANCEL_STATE_UNCENCEL);
			hotelRoom.setHotel(hotel);
		}
		hotelRoom.setArea(MapUtils.getString(param,"area",""));
		hotelRoom.setBedResume(MapUtils.getString(param,"bedResume",""));
		hotelRoom.setPersonMax(MapUtils.getString(param,"personMax",""));
		hotelRoom.setTypeName(MapUtils.getString(param,"typeName",""));
		//主图
		hotelRoom.setCoverUrl(MapUtils.getString(param,"coverUrl",""));
		//设施
		hotelRoom.setRoomDevice(MapUtils.getString(param,"roomDevice",""));
		hotelRoom.setBathroomDevice(MapUtils.getString(param,"bathroomDevice",""));
		hotelRoom.setFoodDevice(MapUtils.getString(param,"foodDevice",""));
		
		//保存房型
		hotelRoom=hotelRoomRepository.save(hotelRoom);
	
		//3.保存房间图册
		String roomId=hotelRoom.getId();
		hotelRoomPhotoService.save(hotelId, roomId, pics);
		
		return new ServiceResult<>(hotelRoom);
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
		
		Optional<HotelRoom> result=hotelRoomRepository.findById(id);
		if(result.isPresent()) {
			HotelRoom hotelRoom=result.get();
			if(cancel==hotelRoom.getCanceled())
				return new ServiceResult<>(true,"和要修改的状态相同,无需修改");
			else 
				hotelRoom.setCanceled(cancel);
			
			hotelRoom=hotelRoomRepository.save(hotelRoom);
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
		
		Optional<HotelRoom> result=hotelRoomRepository.findById(id);
		if(result.isPresent()) {
			HotelRoom hotelRoom=result.get();
			
			hotelRoom.setDeleted(HotelRoom.DELETE_STATE_DELETED);
			
			hotelRoom=hotelRoomRepository.save(hotelRoom);
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
            	Optional<HotelRoom> objOption=hotelRoomRepository.findById(id);
            	if(objOption.isPresent()) {
	                //TODO 增加删除校验
            		HotelRoom obj=objOption.get();
	            	obj.setDeleted(HotelRoom.DELETE_STATE_DELETED);
	                
	            	hotelRoomRepository.save(obj);
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
	 * 修改房间默认价格
	 * @param param
	 * @return
	 */
	public ServiceResult<String> savePrice(Map<String, Object> param) {

		String roomId=MapUtils.getString(param, "id","");
		Optional<HotelRoom> objOption=hotelRoomRepository.findById(roomId);
    	if(objOption.isPresent()) {
    		HotelRoom hotelRoom=objOption.get();
    		
    		Integer planCount=MapUtils.getInteger(param, "planCount",0);
			Double price=MapUtils.getDoubleValue(param, "price",0);
			Double memberPrice=MapUtils.getDoubleValue(param, "memberPrice",0);
			
			hotelRoom.setPlanCount(planCount);
			hotelRoom.setPrice(new BigDecimal(price));
			hotelRoom.setMemberPrice(new BigDecimal(memberPrice));
			
			hotelRoomRepository.save(hotelRoom);
			
			return new ServiceResult<String>("success");
    	}
    	
    	return new ServiceResult<String>(true,"房型标示错误");
	}

	


	
	
	
}
