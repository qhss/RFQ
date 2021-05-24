package com.rfq.entity.rfq;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * RFQ项目实体类
 *
 */
@Entity//实体标识
@Table(name="rfq")
@DynamicUpdate//表示update对象的时候，生成动态的update语句，如果这个字段的值是null就不会被加入到update语句中
@EntityListeners(AuditingEntityListener.class)//引入自定义监听类 ,可在保存前、后，更新前、后调用监听类，执行方法
public class RFQ {

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
     * 报价申请日期
     */
    @Column(columnDefinition="varchar(50) ")
    private String docdate;

    /**
     * 申请人
     */
    @Column(columnDefinition="varchar(50) ")
    private String writer;

    /**
     * 客户币别
     */
    @Column(columnDefinition="varchar(50) ")
    private String currency;

    /**
     * 申请备注
     */
    @Column(columnDefinition="text ")
    private String remark;

    /**
     * 客户代码
     */
    @Column(columnDefinition="varchar(50) ",name = "cardcode")
    private String cardCode;

    /**
     * 客户中文名称
     */
    @Column(columnDefinition="nvarchar(500) ",name = "cardname")
    private String cardName;

    /**
     * 客户英文名称
     */
    @Column(columnDefinition="nvarchar(500) ")
    private String cardEname;


    /**
     * 联系人
     */
    @Column(columnDefinition="nvarchar(50) ")
    private String connect;

    /**
     * 联系电话
     */
    @Column(columnDefinition="nvarchar(500) ",name = "phone_fax")
    private String phoneFax;

    /**
     * 报价总金额
     */
    @Column(columnDefinition="numeric(18, 6) ")
    private BigDecimal total;

    /**
     * 类型,0 已提交审批 1未提交审批
     */
    @Column(columnDefinition="nvarchar(50) ")
    private String type;

    /**
     * 	旧的RFQID号
     */
    @Column(columnDefinition="nvarchar(500) ",name = "OldRfqID")
    private String oldRfqID;

    /**
     * 从哪个RFQID点击的重新申请报价
     */
    @Column(columnDefinition="nvarchar(500) ",name = "AnewRfqID")
    private String aNewRfqID;

    /**
     *报价类型,用户区分期货/ 现货
     */
    @Column(columnDefinition="nvarchar(50) ",name = "stock")
    private String stock;

    /**
     * SpaName
     */
    @Column(columnDefinition="nvarchar(500) ",name = "SpaName")
    private String spaName;

    /**
     * 	项目名称
     */
    @Column(columnDefinition="nvarchar(500) ")
    private String projectName;

    /**
     * application
     */
    @Column(columnDefinition="nvarchar(500) ")
    private String application;

    /**
     * endProduct
     */
    @Column(columnDefinition="nvarchar(500) ")
    private String endProduct;

    /**
     * 客户网址
     */
    @Column(columnDefinition="nvarchar(500) ")
    private String cuswebsite;


    /**
     * lifetime
     */
    @Column(columnDefinition="nvarchar(50) ")
    private String lifetime;

    /**
     * business
     */
    @Column(columnDefinition="nvarchar(50) ",name = "Business")
    private String business;

    /**
     * 地址1
     */
    @Column(columnDefinition="nvarchar(50) ")
    private String location1;
    /**
     * 地址2
     */
    @Column(columnDefinition="nvarchar(50) ")
    private String location2;


    /**
     * mqDate
     */
    @Column(columnDefinition="datetime ")
    private LocalDateTime mpDate;
    /**
     * 生产型企业分类
     */
    @Column(columnDefinition="nvarchar(500) ",name = "SubmitClass")
    private String submitClass;


    @Transient
    private String checkStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDocdate() {
        return docdate;
    }

    public void setDocdate(String docdate) {
        this.docdate = docdate;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardEname() {
        return cardEname;
    }

    public void setCardEname(String cardEname) {
        this.cardEname = cardEname;
    }

    public String getConnect() {
        return connect;
    }

    public void setConnect(String connect) {
        this.connect = connect;
    }

    public String getPhoneFax() {
        return phoneFax;
    }

    public void setPhoneFax(String phoneFax) {
        this.phoneFax = phoneFax;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOldRfqID() {
        return oldRfqID;
    }

    public void setOldRfqID(String oldRfqID) {
        this.oldRfqID = oldRfqID;
    }

    public String getaNewRfqID() {
        return aNewRfqID;
    }

    public void setaNewRfqID(String aNewRfqID) {
        this.aNewRfqID = aNewRfqID;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getSpaName() {
        return spaName;
    }

    public void setSpaName(String spaName) {
        this.spaName = spaName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getEndProduct() {
        return endProduct;
    }

    public void setEndProduct(String endProduct) {
        this.endProduct = endProduct;
    }

    public String getCuswebsite() {
        return cuswebsite;
    }

    public void setCuswebsite(String cuswebsite) {
        this.cuswebsite = cuswebsite;
    }

    public String getLifetime() {
        return lifetime;
    }

    public void setLifetime(String lifetime) {
        this.lifetime = lifetime;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getLocation1() {
        return location1;
    }

    public void setLocation1(String location1) {
        this.location1 = location1;
    }

    public String getLocation2() {
        return location2;
    }

    public void setLocation2(String location2) {
        this.location2 = location2;
    }

    public LocalDateTime getMpDate() {
        return mpDate;
    }

    public void setMpDate(LocalDateTime mpDate) {
        this.mpDate = mpDate;
    }

    public String getSubmitClass() {
        return submitClass;
    }

    public void setSubmitClass(String submitClass) {
        this.submitClass = submitClass;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }
}
