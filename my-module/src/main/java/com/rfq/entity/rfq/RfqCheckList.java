package com.rfq.entity.rfq;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 审批记录表
 *
 */
@Entity//实体标识
@Table(name="rfqcheckList")
@DynamicUpdate//表示update对象的时候，生成动态的update语句，如果这个字段的值是null就不会被加入到update语句中
@EntityListeners(AuditingEntityListener.class)//引入自定义监听类 ,可在保存前、后，更新前、后调用监听类，执行方法
public class RfqCheckList {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1L;

    /**
     * 主键Id
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ZID")
    private int id;
    /**
     * 客户编码
     */
    @Column(columnDefinition="nvarchar(50) ",name = "CUSTOMER")
    private String customer;

    /**
     * 料号
     */
    @Column(columnDefinition="nvarchar(50) ",name = "PN")
    private String pn;
    /**
     * pos价格
     */
    @Column(columnDefinition="nvarchar(50) ")
    private String salesPos;

    /**
     * pop价格
     */
    @Column(columnDefinition="nvarchar(50) ")
    private String posCur;

    /**
     * pop币别
     */
    @Column(columnDefinition="nvarchar(50) ",name = "checkpop")
    private String checkPop;


    /**
     * pop币别
     */
    @Column(columnDefinition="nvarchar(50) ",name = "checkcur")
    private String checkCur;
    /**
     * 利润
     */
    @Column(columnDefinition="nvarchar(50) ")
    private String profit;

    /**
     * 编号
     */
    @Column(columnDefinition="nvarchar(50) ",name = "ZGQV")
    private String zgqv;

    /**
     */
    @Column(columnDefinition="nvarchar(50) ",name = "ZF")
    private String zf;

    /**
     */
    @Column(columnDefinition="nvarchar(50) ",name = "SOLD_TO")
    private String soldTo;

    /**
     * 供应商编码
     */
    @Column(columnDefinition="nvarchar(50) ",name = "VENDOR")
    private String vendor;


    /**
     * 工厂id
     */
    @Column(columnDefinition="nvarchar(50) ",name = "Purchase")
    private String purchase;

    /**
     * 有效期开始日期
     */
    @Column(columnDefinition="datetime ",name = "valid_from")
    private LocalDateTime validFrom;


    /**
     * 有效期截至日期
     */
    @Column(columnDefinition="datetime ",name = "valid_to")
    private LocalDateTime validTo;

    /**
     *
     */
    @Column(columnDefinition="nvarchar(50) ",name = "ZEXPLAIN")
    private String zExplain;

    /**
     */
    @Column(columnDefinition="nvarchar(50) ",name = "ZINPUT_PRICE")
    private String zInputPrice;

    /**
     *
     */
    @Column(columnDefinition="nvarchar(50) ",name = "ZINFO_UNIT")
    private String zInfoUnit;

    /**
     */
    @Column(columnDefinition="nvarchar(50) ",name = "ZINPUT_POS")
    private String zInputPos;

    /**
     *
     */
    @Column(columnDefinition="nvarchar(50) ",name = "ZPOS_UNIT")
    private String zPosUnit;

    /**
     * rfqId
     */
    @Column(columnDefinition="int ",name = "ZRFQID")
    private Integer zRfqId;

    /**
     *报价申请日期
     */
    @Column(columnDefinition="datetime ")
    private LocalDateTime rfqApplyDate;
    /**
     *PM审批日期
     */
    @Column(columnDefinition="datetime ")
    private LocalDateTime creationDate;

    /**
     *具体料号对应的详细id
     */
    @Column(columnDefinition="int ",name = "Zitemid")
    private Integer zItemId;

    /**
     * 报价类型
     */
    @Column(columnDefinition="nvarchar(50) ",name = "PriceType")
    private String priceType;

    /**
     *
     */
    @Column(columnDefinition="nvarchar(50) ",name = "ZPOPID")
    private String zPopId;

    /**
     * 备注
     */
    @Column(columnDefinition="nvarchar(50) ",name = "Remarks")
    private String remark;

    /**
     *审批人
     */
    @Column(columnDefinition="nvarchar(50) ",name = "Checker")
    private String checker;

    /**
     * 工厂id
     */
    @Column(columnDefinition="nvarchar(50) ",name = "DataID")
    private String dataId;

    /**
     *
     */
    @Column(columnDefinition="nvarchar(500) ")
    private String relationCus;

    /**
     *
     */
    @Column(columnDefinition="nvarchar(50) ",name = "Spaname")
    private String spaName;

    /**
     *
     */
    @Column(columnDefinition="nvarchar(500) ")
    private String op;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getPn() {
        return pn;
    }

    public void setPn(String pn) {
        this.pn = pn;
    }

    public String getSalesPos() {
        return salesPos;
    }

    public void setSalesPos(String salesPos) {
        this.salesPos = salesPos;
    }

    public String getPosCur() {
        return posCur;
    }

    public void setPosCur(String posCur) {
        this.posCur = posCur;
    }

    public String getCheckPop() {
        return checkPop;
    }

    public void setCheckPop(String checkPop) {
        this.checkPop = checkPop;
    }

    public String getCheckCur() {
        return checkCur;
    }

    public void setCheckCur(String checkCur) {
        this.checkCur = checkCur;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public String getZgqv() {
        return zgqv;
    }

    public void setZgqv(String zgqv) {
        this.zgqv = zgqv;
    }

    public String getZf() {
        return zf;
    }

    public void setZf(String zf) {
        this.zf = zf;
    }

    public String getSoldTo() {
        return soldTo;
    }

    public void setSoldTo(String soldTo) {
        this.soldTo = soldTo;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getPurchase() {
        return purchase;
    }

    public void setPurchase(String purchase) {
        this.purchase = purchase;
    }

    public LocalDateTime getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDateTime validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDateTime getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDateTime validTo) {
        this.validTo = validTo;
    }

    public String getzExplain() {
        return zExplain;
    }

    public void setzExplain(String zExplain) {
        this.zExplain = zExplain;
    }

    public String getzInputPrice() {
        return zInputPrice;
    }

    public void setzInputPrice(String zInputPrice) {
        this.zInputPrice = zInputPrice;
    }

    public String getzInfoUnit() {
        return zInfoUnit;
    }

    public void setzInfoUnit(String zInfoUnit) {
        this.zInfoUnit = zInfoUnit;
    }

    public String getzInputPos() {
        return zInputPos;
    }

    public void setzInputPos(String zInputPos) {
        this.zInputPos = zInputPos;
    }

    public String getzPosUnit() {
        return zPosUnit;
    }

    public void setzPosUnit(String zPosUnit) {
        this.zPosUnit = zPosUnit;
    }

    public Integer getzRfqId() {
        return zRfqId;
    }

    public void setzRfqId(Integer zRfqId) {
        this.zRfqId = zRfqId;
    }

    public LocalDateTime getRfqApplyDate() {
        return rfqApplyDate;
    }

    public void setRfqApplyDate(LocalDateTime rfqApplyDate) {
        this.rfqApplyDate = rfqApplyDate;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getzItemId() {
        return zItemId;
    }

    public void setzItemId(Integer zItemId) {
        this.zItemId = zItemId;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public String getzPopId() {
        return zPopId;
    }

    public void setzPopId(String zPopId) {
        this.zPopId = zPopId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getRelationCus() {
        return relationCus;
    }

    public void setRelationCus(String relationCus) {
        this.relationCus = relationCus;
    }

    public String getSpaName() {
        return spaName;
    }

    public void setSpaName(String spaName) {
        this.spaName = spaName;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }
}
