package com.rfq.service.pop;

import com.rfq.common.impl.BaseServiceImpl;
import com.rfq.dao.pop.PopApplyZFRepository;
import com.rfq.entity.pop.PopApplyZF;
import com.rfq.service.rfq.RfqItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PopApplyZFService extends BaseServiceImpl {

    private final Logger logger = LoggerFactory.getLogger(PopApplyZFService.class);

    @Autowired
    PopApplyZFRepository popApplyZFRepository;

    public List<PopApplyZF> findAll(){
        List<PopApplyZF> applyList = popApplyZFRepository.findAll();
        return applyList;
    }

}