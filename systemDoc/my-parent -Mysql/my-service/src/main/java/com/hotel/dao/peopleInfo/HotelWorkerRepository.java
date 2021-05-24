package com.hotel.dao.peopleInfo;

import java.util.Optional;

import com.hotel.dao.BaseRepository;
import com.hotel.entity.foods.HotelFoods;
import com.hotel.entity.peopleInfo.HotelWorker;

public interface HotelWorkerRepository extends BaseRepository<HotelWorker, String> {
	Optional<HotelWorker> findByHotelIdAndDeleted(String hotelId,int delete);
	
}
