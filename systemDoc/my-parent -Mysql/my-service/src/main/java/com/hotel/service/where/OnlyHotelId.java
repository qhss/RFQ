package com.hotel.service.where;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class OnlyHotelId<T> implements Specification<T> {

	private static final long serialVersionUID = 1L;
	
	private String hotelId;
	
	public OnlyHotelId(String hotelId) {
		super();
		this.hotelId = hotelId;
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		Predicate predicateDelted=cb.equal(root.get("deleted"),"0");
		//hotelId
		Predicate predicateHotelId=cb.equal(root.get("hotelId"),this.hotelId);
        return cb.and(predicateDelted,predicateHotelId);
	}

}
