package com.hotel.service.food;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.hotel.dao.food.HotelFoodsRepository;
import com.hotel.entity.ServiceResult;
import com.hotel.entity.foods.HotelFoods;
import com.hotel.entity.room.Hotel;
import com.hotel.service.BasicServiceImpl;
import com.hotel.service.where.ExcludeDeleted;

@Service
public class HotelFoodsService  extends BasicServiceImpl  {
	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(HotelFoodsService.class);

	@Resource
	HotelFoodsRepository hotelFoodsRepository;
	/**
	 * 根据主键查询周边
	 */
	public ServiceResult<HotelFoods> findById(String id) {
		if(StringUtils.isBlank(id))
			return new ServiceResult<>(true,"餐饮标示有误");
		Optional<HotelFoods> result=hotelFoodsRepository.findById(id);
		if(result.isPresent())
			return new ServiceResult<HotelFoods>(result.get());
		else
			return null;
	}

	
	/**
	 * 根据酒店ID获取酒店周边
	 * @param hotelId
	 * @return
	 */
	public ServiceResult<HotelFoods> findByHotelId(String hotelId) {
		Optional<HotelFoods> result=hotelFoodsRepository.findByHotelIdAndDeleted(hotelId,0);
		if(result.isPresent())
			return new ServiceResult<HotelFoods>(result.get());
		else
			return null;
	}

	public Page<HotelFoods> queryOnePage( Integer page, Integer row, Sort sort) {
		if(!sort.isSorted())
		sort=Sort.by("foodType");
		Page<HotelFoods> list= hotelFoodsRepository.findAll(new ExcludeDeleted<HotelFoods>(),PageRequest.of(page,row,sort));
		return list;
		
	}

	

	public ServiceResult<List<HotelFoods>> findAll() {
		List<HotelFoods> list=hotelFoodsRepository.findAll(new ExcludeDeleted<HotelFoods>());
		return new ServiceResult<>(list);
	}

	/**
	 * 修改单条数据
	 * @param param
	 * @return
	 */
	public ServiceResult<HotelFoods> update(Map<String, Object> param) {
		
		String id=param.get("id")==null?"":param.get("id").toString();
		HotelFoods hotelFoods=new HotelFoods();
		Optional<HotelFoods> result=hotelFoodsRepository.findById(id);
		if(result.isPresent()) {
			hotelFoods=result.get();
		}else {
			//先用登录用户的hotelid,找不到用参数中的hotelid
			String hotelId=getLoginUser().getData().getHotelId();
			if(StringUtils.isBlank(hotelId))
				hotelId=MapUtils.getString(param, "hotelId","");
			
			if(StringUtils.isBlank(hotelId))
				return new ServiceResult<>(true,"请用酒店账号登录");
			hotelFoods.setHotelId(hotelId);			
			hotelFoods.setDeleted(HotelFoods.DELETE_STATE_UNDELETE);
			hotelFoods.setCanceled(HotelFoods.CANCEL_STATE_UNCENCEL);
			
		}
		//周边类别
//		hotelArround.setArroundtype(HotelArround.ARROUND_FOOD);
//		hotelArround.setArroundtype(HotelArround.ARROUND_TRAFFIC);
//		hotelArround.setArroundtype(HotelArround.ARROUND_VIEW);	
		int foodType= MapUtils.getInteger(param, "foodType");
		if(foodType==0) {
			hotelFoods.setFoodType(HotelFoods.FOOD_DISH);
		}
		else if(foodType==1){
			hotelFoods.setFoodType(HotelFoods.FOOD_PACKAGE);
		}
		else
			hotelFoods.setFoodType(HotelFoods.FOOD_DRINK);
		//名称
		hotelFoods.setFoodName(MapUtils.getString(param, "foodName",""));
		//价钱
		hotelFoods.setFoodPrice(MapUtils.getString(param, "foodPrice",""));
		
		
		
		hotelFoods=hotelFoodsRepository.save(hotelFoods);
		
		return new ServiceResult<>(hotelFoods);
		
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
		
		Optional<HotelFoods> result=hotelFoodsRepository.findById(id);
		if(result.isPresent()) {
			HotelFoods hotelFoods=result.get();
			if(cancel==hotelFoods.getCanceled())
				return new ServiceResult<>(true,"和要修改的状态相同,无需修改");
			else 
				hotelFoods.setCanceled(cancel);
			
			hotelFoods=hotelFoodsRepository.save(hotelFoods);
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
		
		Optional<HotelFoods> result=hotelFoodsRepository.findById(id);
		if(result.isPresent()) {
			HotelFoods hotelFoods=result.get();
			
			hotelFoods.setDeleted(Hotel.DELETE_STATE_DELETED);
			
			hotelFoods=hotelFoodsRepository.save(hotelFoods);
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
            	Optional<HotelFoods> objOption=hotelFoodsRepository.findById(id);
            	if(objOption.isPresent()) {
	                //TODO 增加删除校验
            		HotelFoods obj=objOption.get();
	            	obj.setDeleted(HotelFoods.DELETE_STATE_DELETED);
	                
	            	hotelFoodsRepository.save(obj);
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

		
}