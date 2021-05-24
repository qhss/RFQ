package com.rfq.dao.rfq;

import com.rfq.dao.BaseRepository;
import com.rfq.entity.rfq.RfqCheckList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;


public interface RfqCheckListRepository extends BaseRepository<RfqCheckList, Integer> {

    @Query(value = "select b.cardName as cardName, b.cardCode as cardCode,b.writer as sales,b.submitClass as cubClass,b.docdate as docdate,b.id as rfqId, " +
            "a.rfqId as RFQId,a.itemCode as itemCode,a.notaxCheckPrice as notaxCheckPrice,a.notaxPrice as notaxPrice," +
            "a.taxCheckPrice as taxCheckPrice,a.taxPrice as taxPrice,a.dataSourceId as dataSourceId ,c.rfqApplyDate as rfqApplyDate  " +
            "from RfqCheckList as c " +
            "left join RFQItem as a  on c.zItemId= a.id " +
            "left  join RFQ as b on a.rfqId=b.id " +
            "where a.checker=:chName " +
            "and (:kRfqId=0 or :kRfqId=null or a.rfqId=:kRfqId) " +
            "and (:kCardCode='' or:kCardCode=null  or b.cardCode=:kCardCode) " +
            "and (:kItemCode='' or :kItemCode=null or a.itemCode=:kItemCode) "
             )
    Page<Map<String,Object>> findCheckRecord(Integer kRfqId, String kCardCode, String kItemCode, String chName, Pageable page);

}
