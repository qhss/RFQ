package com.hotel.dao.room;

import java.util.List;
import java.util.Optional;

import com.hotel.dao.BaseRepository;
import com.hotel.entity.room.HotelRoomPhoto;

public interface HotelRoomPhotoRepository extends BaseRepository<HotelRoomPhoto, String> {

	void deleteByHotelRoomId( String roomId);

	Optional<List<HotelRoomPhoto>> findByHotelRoomId(String roomId);

	
}
