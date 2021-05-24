package com.rfq.service.rfq;

import com.rfq.common.impl.BaseServiceImpl;
import com.rfq.dao.rfq.RfqCheckListRepository;
import com.rfq.dao.rfq.RfqItemRepository;
import com.rfq.dao.rfq.RfqRepository;
import com.rfq.entity.rfq.RFQItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RfqCheckListService extends BaseServiceImpl {

    private final Logger logger = LoggerFactory.getLogger(RfqCheckListService.class);

    @Autowired
    RfqCheckListRepository rfqCheckListRepository;



    /**
     * 分页查询审批记录
     *
     */
    public Page<Map<String,Object>> queryOnePage(Integer kRfqId, String kCardCode, String kItemcode, Integer page, Integer row, Sort sort) {

        //加入排序条件
        if (!sort.isSorted()){
            //按照日期降序查找
            sort=Sort.by(Sort.Direction.DESC,"rfqApplyDate");
        }

        String chName = GetLoginUser().getLoginId();
        //手写sql,关联查找更方便
        Page<Map<String,Object>>  list = rfqCheckListRepository.findCheckRecord(kRfqId,kCardCode,kItemcode,chName,PageRequest.of(page,row,sort));

        return list;

    }
}
