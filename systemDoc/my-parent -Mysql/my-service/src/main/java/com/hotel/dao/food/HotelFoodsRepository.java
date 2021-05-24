package com.hotel.dao.food;

import java.util.Optional;

import com.hotel.dao.BaseRepository;
import com.hotel.entity.foods.HotelFoods;

public interface HotelFoodsRepository extends BaseRepository<HotelFoods, String> {
	Optional<HotelFoods> findByHotelIdAndDeleted(String hotelId,int delete);
}
