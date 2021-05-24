package com.rfq.dao.rfq;

import com.rfq.dao.BaseRepository;
import com.rfq.entity.rfq.RFQ;
import com.rfq.entity.rfq.RFQItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public interface RfqItemRepository extends BaseRepository<RFQItem,Integer> {

    List<RFQItem> findByIsCheckedAndRfqId(String isChecked,int rfqId);

    @Query(value = "select b.cardName as cardName, b.cardCode as cardCode,b.writer as sales,b.submitClass as cubClass,b.docdate as docdate,b.id as rfqId, " +
            "a.rfqId as RFQId,a.itemCode as itemCode,a.notaxCheckPrice as notaxCheckPrice,a.notaxPrice as notaxPrice," +
            "a.taxCheckPrice as taxCheckPrice,a.taxPrice as taxPrice,a.dataSourceId as dataSourceId   " +
            "from RFQItem as a " +
            "left  join RFQ as b on a.rfqId=b.id " +
            "where a.checker=:chName " +
            "and (:kRfqId=0 or :kRfqId=null or a.rfqId=:kRfqId) " +
            "and (:kCardCode='' or:kCardCode=null  or b.cardCode=:kCardCode) " +
            "and (:kItemCode='' or :kItemCode=null or a.itemCode=:kItemCode) " +
            "and a.isChecked=:isChecked " )
    Page<Map<String,Object>> findByCheckerAndIsChecked( Integer kRfqId, String kCardCode,String kItemCode,String chName, String isChecked, Pageable page);

}
