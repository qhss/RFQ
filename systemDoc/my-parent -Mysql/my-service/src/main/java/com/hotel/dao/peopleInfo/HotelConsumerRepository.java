package com.hotel.dao.peopleInfo;

import java.util.Optional;

import com.hotel.dao.BaseRepository;
import com.hotel.entity.foods.HotelFoods;
import com.hotel.entity.peopleInfo.HotelConsumer;

public interface HotelConsumerRepository extends BaseRepository<HotelConsumer, String> {
	Optional<HotelConsumer> findByHotelIdAndDeleted(String hotelId,int delete);
	
}
