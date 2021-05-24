package com.hotel.dao.room;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hotel.dao.BaseRepository;
import com.hotel.entity.room.HotelPlan;
import com.hotel.service.where.ExcludeDeleted;

public interface HotelPlanRepository extends BaseRepository<HotelPlan, String> {


	Page<HotelPlan> findByHotelRoomId(String roomId, ExcludeDeleted<HotelPlan> excludeDeleted, Pageable page);

	Page<HotelPlan> findByHotelRoomIdAndDeleted(String roomId, int deleteded, Pageable of);

	Page<HotelPlan> findByHotelRoomIdAndDeletedAndDateBetween(String roomId, int deleteded, Integer start, Integer end, Pageable page);

	List<HotelPlan> findByHotelRoomIdAndDeletedAndDateBetween(String roomId, int i, Integer start, Integer end);

	Optional<HotelPlan> findByHotelIdAndHotelRoomIdAndDateAndDeletedAndCanceled(String hotelId, String roomId, Integer theDayInt,int deleted,int canceled);

	
	
}
