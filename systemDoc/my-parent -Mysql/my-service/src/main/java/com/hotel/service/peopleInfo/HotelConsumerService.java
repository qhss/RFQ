
package com.hotel.service.peopleInfo;

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
import com.hotel.dao.peopleInfo.HotelConsumerRepository;
import com.hotel.entity.ServiceResult;
import com.hotel.entity.foods.HotelFoods;
import com.hotel.entity.peopleInfo.HotelConsumer;
import com.hotel.entity.room.Hotel;
import com.hotel.service.BasicServiceImpl;
import com.hotel.service.where.ExcludeDeleted;


@Service
public class HotelConsumerService  extends BasicServiceImpl {
	
	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(HotelConsumerService.class);
	@Resource
	HotelConsumerRepository hotelConsumerRepository;
	/**
	 * 根据主键查询客人信息
	 */
	public ServiceResult<HotelConsumer> findById(String id) {
		if(StringUtils.isBlank(id))
			return new ServiceResult<>(true,"客人信息标示有误");
		Optional<HotelConsumer> result=hotelConsumerRepository.findById(id);
		if(result.isPresent())
			return new ServiceResult<HotelConsumer>(result.get());
		else
			return null;
	}

	
	/**
	 * 根据酒店ID获取酒店周边
	 * @param hotelId
	 * @return
	 */
	public ServiceResult<HotelConsumer> findByHotelId(String hotelId) {
		Optional<HotelConsumer> result=hotelConsumerRepository.findByHotelIdAndDeleted(hotelId,0);
		if(result.isPresent())
			return new ServiceResult<HotelConsumer>(result.get());
		else
			return null;
	}

	public Page<HotelConsumer> queryOnePage( Integer page, Integer row, Sort sort) {
		if(!sort.isSorted())
		sort=Sort.by("consumerPrice");
		Page<HotelConsumer> list= hotelConsumerRepository.findAll(new ExcludeDeleted<HotelConsumer>(),PageRequest.of(page,row,sort));
		return list;
		
	}

	

	public ServiceResult<List<HotelConsumer>> findAll() {
		List<HotelConsumer> list=hotelConsumerRepository.findAll(new ExcludeDeleted<HotelConsumer>());
		return new ServiceResult<>(list);
	}

	/**
	 * 修改单条数据
	 * @param param
	 * @return
	 */
	public ServiceResult<HotelConsumer> update(Map<String, Object> param) {
		
		String id=param.get("id")==null?"":param.get("id").toString();
		HotelConsumer hotelConsumer=new HotelConsumer();
		Optional<HotelConsumer> result=hotelConsumerRepository.findById(id);
		if(result.isPresent()) {
			hotelConsumer=result.get();
		}else {
			//先用登录用户的hotelid,找不到用参数中的hotelid
			String hotelId=getLoginUser().getData().getHotelId();
			if(StringUtils.isBlank(hotelId))
				hotelId=MapUtils.getString(param, "hotelId","");
			
			if(StringUtils.isBlank(hotelId))
				return new ServiceResult<>(true,"请用酒店账号登录");
			hotelConsumer.setHotelId(hotelId);			
			hotelConsumer.setDeleted(HotelConsumer.DELETE_STATE_UNDELETE);
			hotelConsumer.setCanceled(HotelConsumer.CANCEL_STATE_UNCENCEL);
			
		}	

		//客人建议
		hotelConsumer.setConsumerIdea(MapUtils.getString(param, "consumerIdea",""));
		//客人姓名
		hotelConsumer.setConsumerName(MapUtils.getString(param, "consumerName",""));
		//客人电话
		hotelConsumer.setConsumerPhone(MapUtils.getString(param, "consumerPhone",""));
		//客人消费价格
		hotelConsumer.setConsumerPrice(MapUtils.getString(param, "consumerPrice",""));
		//保存		
		hotelConsumer=hotelConsumerRepository.save(hotelConsumer);
		
		return new ServiceResult<>(hotelConsumer);
		
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
		
		Optional<HotelConsumer> result=hotelConsumerRepository.findById(id);
		if(result.isPresent()) {
			HotelConsumer hotelConsumer=result.get();
			if(cancel==hotelConsumer.getCanceled())
				return new ServiceResult<>(true,"和要修改的状态相同,无需修改");
			else 
				hotelConsumer.setCanceled(cancel);
			
			hotelConsumer=hotelConsumerRepository.save(hotelConsumer);
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
		
		Optional<HotelConsumer> result=hotelConsumerRepository.findById(id);
		if(result.isPresent()) {
			HotelConsumer hotelConsumer=result.get();
			
			hotelConsumer.setDeleted(Hotel.DELETE_STATE_DELETED);
			
			hotelConsumer=hotelConsumerRepository.save(hotelConsumer);
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
            	Optional<HotelConsumer> objOption=hotelConsumerRepository.findById(id);
            	if(objOption.isPresent()) {
	                //TODO 增加删除校验
            		HotelConsumer obj=objOption.get();
	            	obj.setDeleted(HotelFoods.DELETE_STATE_DELETED);
	                
	            	hotelConsumerRepository.save(obj);
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
