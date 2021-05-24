package com.rfq.entity.rfq;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * RFQ详情实体类
 *
 */
@Entity//实体标识
@Table(name="rfqitems")
@DynamicUpdate//表示update对象的时候，生成动态的update语句，如果这个字段的值是null就不会被加入到update语句中
@EntityListeners(AuditingEntityListener.class)//引入自定义监听类 ,可在保存前、后，更新前、后调用监听类，执行方法
public class RFQItem {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1L;

    /**
     * 唯一标示
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID")
    protected int id;

    /**
     * 物料编号
     */
    @Column(columnDefinition="nvarchar(500) ",name = "itemcode")
    private String itemCode;

    /**
     * 	客户物料号
     */
    @Column(columnDefinition="nvarchar(500) ",name = "carditemcode")
    private String cardItemCode;


    /**
     * 品牌
     */
    @Column(columnDefinition="varchar(50) ")
    private String brand;

    /**
     * spq
     */
    @Column(columnDefinition="varchar(50) ")
    private String spq;


    /**
     * moq
     */
    @Column(columnDefinition="varchar(50) ")
    private String moq;

    /**
     * 年用量
     */
    @Column(columnDefinition="numeric(18, 0) ")
    private BigDecimal quantity;


    /**
     * 单位
     */
    @Column(columnDefinition="varchar(50) ")
    private String units;

    /**
     * Sales 含税价
     */
    @Column(columnDefinition="numeric(18, 7) ",name = "taxprice")
    private BigDecimal taxPrice;

    /**
     * Sales 未税价
     */
    @Column(columnDefinition="numeric(18, 6) ",name="notaxprice")
    private BigDecimal notaxPrice;

    /**
     *上一次提交的Pos 未税价格
     */
    @Column(columnDefinition="numeric(18, 6) ",name = "LTB_price")
    private BigDecimal ltbPrice;

    /**
     * lead time
     */
    @Column(columnDefinition="varchar(50) ")
    private String lt;

    /**
     * 价格有效期截止时间
     */
    @Column(columnDefinition="datetime ",name = "effectivedate")
    private LocalDateTime effectiveDate;

    /**
     *	项目行小计
     */
    @Column(columnDefinition="numeric(18, 6) ")
    private BigDecimal subtotal;

    /**
     * 项目行备注
     */
    @Column(columnDefinition="nvarchar(500) ")
    private String remark;


    /**
     * 系统DataID
     */
    @Column(columnDefinition="varchar(50) ",name = "data_sourceId")
    private String dataSourceId;

    /**
     *PM 审批含税价
     */
    @Column(columnDefinition="numeric(18, 7)",name = "taxcheckprice")
    private BigDecimal taxCheckPrice;


    /**
     * PM 审批未税价,此价格进SAP系统
     */
    @Column(columnDefinition="numeric(18, 6) ",name = "notaxcheckprice")
    private String notaxCheckPrice;

    /**
     * 审批日期
     */
    @Column(columnDefinition="nvarchar(500) ",name = "checkdate")
    private String checkDate;

    /**
     * 审批备注
     */
    @Column(columnDefinition="nvarchar(500)",name = "checkremark")
    private String checkRemark;

    /**
     * 审批PM
     */
    @Column(columnDefinition="varchar(50) ")
    private String checker;
    /**
     * 是否需要审批
     */
    @Column(columnDefinition="varchar(50) ",name="needcheck")
    private String needCheck;

    /**
     * 是否通过
     */
    @Column(columnDefinition="varchar(50) ",name = "ispass")
    private String isPass;
    /**
     * 是否审批完成
     */
    @Column(columnDefinition="varchar(50) ",name = "ischecked")
    private String isChecked;
    /**
     * 是否退回
     */
    @Column(columnDefinition="varchar(50) ",name = "isback")
    private String isBack;
    /**
     * 	RFQID,关联rfq表的主键值
     */
    @Column(columnDefinition="int")
    private Integer rfqId;
    /**
     * 物料品名
     */
    @Column(columnDefinition="nvarchar(500) ",name = "pname")
    private String pName;

    /**
     * ZF
     */
    @Column(columnDefinition="nvarchar(500) ")
    private String zf;


    /**
     * soldto
     */
    @Column(columnDefinition="nvarchar(500)",name = "soldto")
    private String soldTo;

    /**
     *信息价格,此值进SAP系统,小数点位数多时
     */
    @Column(columnDefinition="varchar(50) ",name = "InfoPrice")
    private String infoPrice;

    /**
     *BOM 用量
     */
    @Column(columnDefinition="int")
    private Integer EAU;


    /**
     * 	报价类型
     */
    @Column(columnDefinition="nvarchar(50)")
    private String priceType;

    /**
     * 物料产地
     */
    @Column(columnDefinition="nvarchar(50) ",name = "ZZCOUNTRY")
    private String zzCountry;

    /**
     * 	产品系列
     */
    @Column(columnDefinition="nvarchar(50) ",name = "Series")
    private String series;

    /**
     * 原始审批的PM,因为提交下一审批人会更改
     */
    @Column(columnDefinition="nvarchar(50)",name = "OriginalPM")
    private String originalPM;

    /**
     *提交下一审批人给到的备注
     */
    @Column(columnDefinition="nvarchar(500) ")
    private String itemOpRemark;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getCardItemCode() {
        return cardItemCode;
    }

    public void setCardItemCode(String cardItemCode) {
        this.cardItemCode = cardItemCode;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSpq() {
        return spq;
    }

    public void setSpq(String spq) {
        this.spq = spq;
    }

    public String getMoq() {
        return moq;
    }

    public void setMoq(String moq) {
        this.moq = moq;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public BigDecimal getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(BigDecimal taxPrice) {
        this.taxPrice = taxPrice;
    }

    public BigDecimal getNotaxPrice() {
        return notaxPrice;
    }

    public void setNotaxPrice(BigDecimal notaxPrice) {
        this.notaxPrice = notaxPrice;
    }

    public BigDecimal getLtbPrice() {
        return ltbPrice;
    }

    public void setLtbPrice(BigDecimal ltbPrice) {
        this.ltbPrice = ltbPrice;
    }

    public String getLt() {
        return lt;
    }

    public void setLt(String lt) {
        this.lt = lt;
    }

    public LocalDateTime getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDateTime effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public BigDecimal getTaxCheckPrice() {
        return taxCheckPrice;
    }

    public void setTaxCheckPrice(BigDecimal taxCheckPrice) {
        this.taxCheckPrice = taxCheckPrice;
    }

    public String getNotaxCheckPrice() {
        return notaxCheckPrice;
    }

    public void setNotaxCheckPrice(String notaxCheckPrice) {
        this.notaxCheckPrice = notaxCheckPrice;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public String getCheckRemark() {
        return checkRemark;
    }

    public void setCheckRemark(String checkRemark) {
        this.checkRemark = checkRemark;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public String getNeedCheck() {
        return needCheck;
    }

    public void setNeedCheck(String needCheck) {
        this.needCheck = needCheck;
    }

    public String getIsPass() {
        return isPass;
    }

    public void setIsPass(String isPass) {
        this.isPass = isPass;
    }

    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked;
    }

    public String getIsBack() {
        return isBack;
    }

    public void setIsBack(String isBack) {
        this.isBack = isBack;
    }

    public Integer getRfqId() {
        return rfqId;
    }

    public void setRfqId(Integer rfqId) {
        this.rfqId = rfqId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
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

    public String getInfoPrice() {
        return infoPrice;
    }

    public void setInfoPrice(String infoPrice) {
        this.infoPrice = infoPrice;
    }

    public Integer getEAU() {
        return EAU;
    }

    public void setEAU(Integer EAU) {
        this.EAU = EAU;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public String getZzCountry() {
        return zzCountry;
    }

    public void setZzCountry(String zzCountry) {
        this.zzCountry = zzCountry;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getOriginalPM() {
        return originalPM;
    }

    public void setOriginalPM(String originalPM) {
        this.originalPM = originalPM;
    }

    public String getItemOpRemark() {
        return itemOpRemark;
    }

    public void setItemOpRemark(String itemOpRemark) {
        this.itemOpRemark = itemOpRemark;
    }

}
