package com.rfq.service.where;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class OnlyMember<T> implements Specification<T> {

	private static final long serialVersionUID = 1L;
	
	private String memberId;
	
	public OnlyMember(String memberId) {
		super();
		this.memberId = memberId;
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		Predicate predicateDelted=cb.equal(root.get("deleted"),"0");
		//会员id
		Predicate predicateMemberId=cb.equal(root.get("memberId"),this.memberId);
        return cb.and(predicateDelted,predicateMemberId);
	}

}
