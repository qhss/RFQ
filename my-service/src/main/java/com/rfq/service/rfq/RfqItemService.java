package com.rfq.service.rfq;

import com.rfq.common.impl.BaseServiceImpl;
import com.rfq.dao.rfq.RfqItemRepository;
import com.rfq.dao.rfq.RfqRepository;
import com.rfq.entity.ServiceResult;
import com.rfq.entity.rfq.RFQ;
import com.rfq.entity.rfq.RFQItem;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RfqItemService extends BaseServiceImpl {

    private final Logger logger = LoggerFactory.getLogger(RfqItemService.class);

    @Autowired
    RfqItemRepository rfqItemRepository;

    @Autowired
    RfqRepository rfqRepository;

    /**
     * 判断一个单号下所有物料是否完成审批
     * 如果有一个未完成审批，记录状态为未审批
     */
    public List<RFQItem> findByRfqId(int rfqId) {
        List<RFQItem> n = rfqItemRepository.findByIsCheckedAndRfqId( "N",rfqId);
        return n;
    }



    /**
     * 分页查询审批记录
     *
     */
    public Page<Map<String,Object>> queryOnePage(Integer kRfqId, String kCardCode, String kItemcode, Integer page, Integer row, Sort sort) {

        //加入排序条件
        if (!sort.isSorted()){
            //按照日期降序查找
            sort=Sort.by(Sort.Direction.DESC,"checkDate");
        }

        String chName = GetLoginUser().getLoginId();
        //手写sql,关联查找更方便
        Page<Map<String,Object>>  list = rfqItemRepository.findByCheckerAndIsChecked(kRfqId,kCardCode,kItemcode,chName,"Y",PageRequest.of(page,row,sort));

        return list;

    }
}
