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
import com.hotel.dao.peopleInfo.HotelWorkerRepository;
import com.hotel.entity.ServiceResult;
import com.hotel.entity.foods.HotelFoods;
import com.hotel.entity.peopleInfo.HotelWorker;
import com.hotel.entity.room.Hotel;
import com.hotel.service.BasicServiceImpl;
import com.hotel.service.where.ExcludeDeleted;


@Service
public class HotelWorkerService  extends BasicServiceImpl {
	
	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(HotelWorkerService.class);
	@Resource
	HotelWorkerRepository hotelWorkerRepository;
	/**
	 * 根据主键查询周边
	 */
	public ServiceResult<HotelWorker> findById(String id) {
		if(StringUtils.isBlank(id))
			return new ServiceResult<>(true,"餐饮标示有误");
		Optional<HotelWorker> result=hotelWorkerRepository.findById(id);
		if(result.isPresent())
			return new ServiceResult<HotelWorker>(result.get());
		else
			return null;
	}

	
	/**
	 * 根据酒店ID获取酒店周边
	 * @param hotelId
	 * @return
	 */
	public ServiceResult<HotelWorker> findByHotelId(String hotelId) {
		Optional<HotelWorker> result=hotelWorkerRepository.findByHotelIdAndDeleted(hotelId,0);
		if(result.isPresent())
			return new ServiceResult<HotelWorker>(result.get());
		else
			return null;
	}

	public Page<HotelWorker> queryOnePage( Integer page, Integer row, Sort sort) {
		if(!sort.isSorted())
		sort=Sort.by("workerType");
		Page<HotelWorker> list= hotelWorkerRepository.findAll(new ExcludeDeleted<HotelWorker>(),PageRequest.of(page,row,sort));
		return list;
		
	}

	

//	public ServiceResult<List<HotelWorker>> findAll() {
//		List<HotelWorker> list=hotelWorkerRepository.findAll(new ExcludeDeleted<HotelWorker>());
//		return new ServiceResult<>(list);
//	}

	/**
	 * 修改单条数据
	 * @param param
	 * @return
	 */
	public ServiceResult<HotelWorker> update(Map<String, Object> param) {
		
		String id=param.get("id")==null?"":param.get("id").toString();
		HotelWorker hotelWorker=new HotelWorker();
		Optional<HotelWorker> result=hotelWorkerRepository.findById(id);
		if(result.isPresent()) {
			hotelWorker=result.get();
		}else {
			//先用登录用户的hotelid,找不到用参数中的hotelid
			String hotelId=getLoginUser().getData().getHotelId();
			if(StringUtils.isBlank(hotelId))
				hotelId=MapUtils.getString(param, "hotelId","");
			
			if(StringUtils.isBlank(hotelId))
				return new ServiceResult<>(true,"请用酒店账号登录");
			hotelWorker.setHotelId(hotelId);			
			hotelWorker.setDeleted(HotelWorker.DELETE_STATE_UNDELETE);
			hotelWorker.setCanceled(HotelWorker.CANCEL_STATE_UNCENCEL);
			
		}

		int workerType= MapUtils.getInteger(param, "workerType");
		if(workerType==0) {
			hotelWorker.setWorkerType(HotelWorker.WORKER_MANAGER);
		}
		else if(workerType==1){
			hotelWorker.setWorkerType(HotelWorker.WORKER_GROUP_LEADER);
		}
		else
			hotelWorker.setWorkerType(HotelWorker.WORKER_GENERAL_STAFF);
		//员工姓名
		hotelWorker.setWorkerName(MapUtils.getString(param, "workerName",""));
		//员工年龄
		hotelWorker.setWorkerAge(MapUtils.getInteger(param, "workerAge",0));
		//员工工资
		hotelWorker.setWorkerPrice(MapUtils.getDouble(param, "workerPrice",0.0));
		//员工电话
		hotelWorker.setWorkerPhone(MapUtils.getString(param, "workerPhone",""));
		
	
		
		hotelWorker=hotelWorkerRepository.save(hotelWorker);
		
		return new ServiceResult<>(hotelWorker);
		
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
		
		Optional<HotelWorker> result=hotelWorkerRepository.findById(id);
		if(result.isPresent()) {
			HotelWorker hotelWorker=result.get();
			if(cancel==hotelWorker.getCanceled())
				return new ServiceResult<>(true,"和要修改的状态相同,无需修改");
			else 
				hotelWorker.setCanceled(cancel);
			
			hotelWorker=hotelWorkerRepository.save(hotelWorker);
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
		
		Optional<HotelWorker> result=hotelWorkerRepository.findById(id);
		if(result.isPresent()) {
			HotelWorker hotelWoker=result.get();
			
			hotelWoker.setDeleted(Hotel.DELETE_STATE_DELETED);
			
			hotelWoker=hotelWorkerRepository.save(hotelWoker);
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
            	Optional<HotelWorker> objOption=hotelWorkerRepository.findById(id);
            	if(objOption.isPresent()) {
	                //TODO 增加删除校验
            		HotelWorker obj=objOption.get();
	            	obj.setDeleted(HotelWorker.DELETE_STATE_DELETED);
	                
	            	hotelWorkerRepository.save(obj);
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
