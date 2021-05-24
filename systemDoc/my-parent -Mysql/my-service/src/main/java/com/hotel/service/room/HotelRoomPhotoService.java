package com.hotel.service.room;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.dao.room.HotelRoomPhotoRepository;
import com.hotel.dao.room.HotelRoomRepository;
import com.hotel.entity.ServiceResult;
import com.hotel.entity.room.HotelPhoto;
import com.hotel.entity.room.HotelRoom;
import com.hotel.entity.room.HotelRoomPhoto;
import com.hotel.service.BasicServiceImpl;

@Service
public class HotelRoomPhotoService extends BasicServiceImpl  {
	
	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(HotelRoomPhotoService.class);

	@Autowired
	HotelRoomRepository hotelRoomRepository;
	
	
	@Resource
	HotelRoomPhotoRepository hotelRoomPhotoRepository;




	/**
	 * 保存相册
	 * @param param
	 * @param pics
	 * @return
	 */
	@Transactional
	public ServiceResult<String> save(String hotelId,String roomId, String[] pics) {

		if(StringUtils.isBlank(hotelId) || StringUtils.isBlank(roomId))
			return new ServiceResult<>(true,"酒店及房型标示有误");
		
		Optional<HotelRoom> roomOption=hotelRoomRepository.findById(roomId);
		if(!roomOption.isPresent())
			return new ServiceResult<>(true,"酒店及房型标示有误");
		
		HotelRoom hotelRoom=roomOption.get();
		if(!hotelRoom.getHotel().getId().equals(hotelId))
			return new ServiceResult<>(true,"房型对应酒店错误");
		//保存酒店图片
		// 先删除
		hotelRoomPhotoRepository.deleteByHotelRoomId(roomId);
		
		//如果pics中没值,删除现在所有的图
		//TODO 有风险
		if(pics.length>0) {
			ArrayList<HotelRoomPhoto> photos=new ArrayList<>();
			int i=0;
			for (String url : pics) {
				if(!StringUtils.isBlank(url)) {
					HotelRoomPhoto photo=new HotelRoomPhoto();
					photo.setCanceled(HotelPhoto.CANCEL_STATE_UNCENCEL);
					photo.setDeleted(HotelPhoto.DELETE_STATE_UNDELETE);
					photo.setHotelRoom(hotelRoom);
					
					photo.setRemark("");
					photo.setSort(i);
					i++;
					photo.setUrl(url);
					
					photos.add(photo);
				}
			}
			//批量保存
			hotelRoomPhotoRepository.saveAll(photos);
		}
		
		return new ServiceResult<>("success");
		
	}




	public ServiceResult<List<HotelRoomPhoto>> findByRoomId(String roomId) {
		Optional<List<HotelRoomPhoto>> result=hotelRoomPhotoRepository.findByHotelRoomId(roomId);
		if(result.isPresent())
			return new ServiceResult<>(result.get());
		else
			return new ServiceResult<>(true,"无数据");
	}

	

	

}
