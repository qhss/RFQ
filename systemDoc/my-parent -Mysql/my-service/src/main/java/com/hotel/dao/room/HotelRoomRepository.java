package com.hotel.dao.room;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hotel.dao.BaseRepository;
import com.hotel.entity.room.HotelRoom;

public interface HotelRoomRepository extends BaseRepository<HotelRoom, String> {

	Page<HotelRoom> findByHotelIdAndDeleted(String hotelId, int deleted, Pageable page);

	List<HotelRoom> findByHotelIdAndDeleted(String hotelId, int deleted);

	
}
