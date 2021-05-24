package com.hotel.service.where;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class OnlyOpenId<T> implements Specification<T> {

	private static final long serialVersionUID = 1L;
	
	private String openId;
	
	public OnlyOpenId(String openId) {
		super();
		this.openId = openId;
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		Predicate predicateDelted=cb.equal(root.get("deleted"),"0");
		//openid
		Predicate predicateOpenId=cb.equal(root.get("openId"),this.openId);
        return cb.and(predicateDelted,predicateOpenId);
	}

}
