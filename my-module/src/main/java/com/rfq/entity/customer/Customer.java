package com.rfq.entity.customer;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * 客户实体类
 *
 */
@Entity//实体标识
@Table(name="customer")
@DynamicUpdate//表示update对象的时候，生成动态的update语句，如果这个字段的值是null就不会被加入到update语句中
@EntityListeners(AuditingEntityListener.class)//引入自定义监听类 ,可在保存前、后，更新前、后调用监听类，执行方法
public class Customer {

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
     * 客户代码
     */
    @Column(columnDefinition="varchar(50) ", name="cardcode")
    private String cardCode;

    /**
     * 客户中文名称
     */
    @Column(columnDefinition="nvarchar(500)", name="cardname")
    private String cardName;

    /**
     * 客户英文名称
     */
    @Column(columnDefinition="nvarchar(500) ")
    private String cardEname;

    /**
     * 电话
     */
    @Column(columnDefinition="nvarchar(500)")
    private String phone;

    /**
     * 联系人
     */
    @Column(columnDefinition="nvarchar(500) ")
    private String contact;

    /**
     * 公司ID,销售组织 2-HS/3-SPC/4-MSHK/5-MSSZ/6-SPC
     */
    @Column(columnDefinition="nvarchar(500) ")
    private String dataId;

    /**
     * 币别
     */
    @Column(columnDefinition="nvarchar(500) ")
    private String currency;

    /**
     * 客户地址
     */
    @Column(columnDefinition="nvarchar(500) ")
    private String address;

    /**
     * 传真
     */
    @Column(columnDefinition="nvarchar(500) ")
    private String fax;

    /**
     *付款条款说明
     */
    @Column(columnDefinition="nvarchar(500) ")
    private String pymnt;

    /**
     *付款条款说明
     */
    @Column(columnDefinition="nvarchar(500) ",name="isspecial")
    private String isSpecial;


    @Column(columnDefinition="nvarchar(500) ")
    private String slpcode;

    /**
     *旧客户代码
     */
    @Column(columnDefinition="nvarchar(50) ", name="OldCard")
    private String oldCard;

    /**
     *	是否从SAP 获取的主数据
     */
    @Column(columnDefinition="nvarchar(50) ",name="IsNew")
    private String isNew;

    /**
     *客户对应的Sales
     */
    @Column(columnDefinition="nvarchar(50) ",name="Sales")
    private String sales;

    /**
     *客户网址
     */
    @Column(columnDefinition="nvarchar(500) ")
    private String website;

    /**
     *付款条款
     */
    @Column(columnDefinition="nvarchar(500) ",name="PayT ")
    private String payT;

    /**
     *付款条款
     */
    @Column(columnDefinition="nvarchar(500) ",name="EnPymnt ")
    private String enPymnt;

    /**
     *	企业型生产分类
     */
    @Column(columnDefinition="nvarchar(500) ",name="CusClass ")
    private String cusClass;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getPymnt() {
        return pymnt;
    }

    public void setPymnt(String pymnt) {
        this.pymnt = pymnt;
    }

    public String getIsSpecial() {
        return isSpecial;
    }

    public void setIsSpecial(String isSpecial) {
        this.isSpecial = isSpecial;
    }

    public String getSlpcode() {
        return slpcode;
    }

    public void setSlpcode(String slpcode) {
        this.slpcode = slpcode;
    }

    public String getOldCard() {
        return oldCard;
    }

    public void setOldCard(String oldCard) {
        this.oldCard = oldCard;
    }

    public String getIsNew() {
        return isNew;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPayT() {
        return payT;
    }

    public void setPayT(String payT) {
        this.payT = payT;
    }

    public String getEnPymnt() {
        return enPymnt;
    }

    public void setEnPymnt(String enPymnt) {
        enPymnt = enPymnt;
    }

    public String getCusClass() {
        return cusClass;
    }

    public void setCusClass(String cusClass) {
        this.cusClass = cusClass;
    }
}
