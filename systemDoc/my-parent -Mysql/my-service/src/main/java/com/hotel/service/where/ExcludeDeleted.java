package com.hotel.service.where;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class ExcludeDeleted<T> implements Specification<T> {

	private static final long serialVersionUID = 1L;

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		Predicate predicateDelted=cb.equal(root.get("deleted"),"0");
        return cb.and(predicateDelted);
	}

}
