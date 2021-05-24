package com.hotel.dao.room;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hotel.dao.BaseRepository;
import com.hotel.entity.room.Hotel;

public interface HotelRepository extends BaseRepository<Hotel, String> {

	Page<Hotel> findByCodeContainingOrNameContaining(String codename, String codename2, Pageable page);

	Optional<Hotel> findByIdAndDeleted(String id, int deleted);

	
}
