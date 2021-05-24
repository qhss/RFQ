package com.rfq.service.rfq;

import com.rfq.common.impl.BaseServiceImpl;
import com.rfq.dao.rfq.RfqRepository;
import com.rfq.entity.rfq.RFQ;
import com.rfq.entity.rfq.RFQItem;
import com.rfq.service.where.ExcludeDeleted;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class RfqService extends BaseServiceImpl {

    private final Logger logger = LoggerFactory.getLogger(RfqService.class);

    @Autowired
    RfqRepository rfqRepository;


    @Autowired
    RfqItemService rfqItemService;

    /**
     * 分页查询我的RFQ记录
     * kId为单号
     * kCardCode为客户代码
     * kItemcode为物料编号
     *
     */
    public Page<RFQ> queryOnePage(Integer kId,String kCardCode,String kItemcode,Integer page, Integer row, Sort sort) {

        //加入排序条件
        if (!sort.isSorted()){
            //按照日期降序查找
            sort=Sort.by(Sort.Direction.DESC,"docdate");
        }

        String chName = GetLoginUser().getLoginId();
        Page<RFQ>  list= rfqRepository.findAll(new Specification<RFQ>() {
                @Override
                public Predicate toPredicate(Root<RFQ> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicateList = new ArrayList<>();

                    //根据登录用户查询自己对应RFQ报价记录
                    predicateList.add(criteriaBuilder.equal(root.get("writer").as(String.class),chName));

                    if (!StringUtils.isBlank(kCardCode)){
                        predicateList.add(criteriaBuilder.equal(root.get("cardCode").as(String.class),kCardCode));
                    }
                    if (kId!=null&&kId!=0){
                        predicateList.add(criteriaBuilder.equal(root.get("id").as(Integer.class),kId));
                    }
                    Predicate[] p = new Predicate[predicateList.size()];
                    return criteriaBuilder.and(predicateList.toArray(p));
                }
            }, PageRequest.of(page,row,sort));

        //判断一个单号下所有物料是否完成审批，如果有一个未完成审批，记录状态为未审批
        if (!CollectionUtils.isEmpty(list.getContent())) {
            for (RFQ rfq : list.getContent()) {
                int rfqId = rfq.getId();
                List<RFQItem> result = rfqItemService.findByRfqId(rfqId);
                if (CollectionUtils.isEmpty(result)) {
                    rfq.setCheckStatus("未审核");
                } else{
                    rfq.setCheckStatus("已审核");
                }

            }
        }


        return list;
    }
}
