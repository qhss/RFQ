package com.rfq.entity.rfq;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 *PM审批价格利润计算表
 *
 */
@Entity//实体标识
@Table(name="rfqProfitRule")
@DynamicUpdate//表示update对象的时候，生成动态的update语句，如果这个字段的值是null就不会被加入到update语句中
@EntityListeners(AuditingEntityListener.class)//引入自定义监听类 ,可在保存前、后，更新前、后调用监听类，执行方法
public class RfqProfitRule {

    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1L;

    /**
     * 唯一标示
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "RID")
    protected int id;

    /**
     * 系统名称
     */
    @Column(columnDefinition="nvarchar(50) ",name = "VKORG_NAME")
    private String vkorgName;

    /**
     * 销售组织
     */
    @Column(columnDefinition="nvarchar(50) ",name = "VKORG")
    private String vkorg;

    /**
     * 工厂
     */
    @Column(columnDefinition="nvarchar(50) ",name = "WERKS")
    private String werks;

    /**
     * 品牌
     */
    @Column(columnDefinition="nvarchar(50) ",name = "Brand")
    private String brand;

    /**
     * 系统名称
     */
    @Column(columnDefinition="nvarchar(50) ",name = "ZZPRDSERIES")
    private String zzPrdseries;

    /**
     * 内联扣减
     */
    @Column(columnDefinition="nvarchar(50) ")
    private String inline;

    /**
     * 	胶壳商检费
     */
    @Column(columnDefinition="nvarchar(50) ")
    private String tariff;

    /**
     * 增值税
     */
    @Column(columnDefinition="nvarchar(50) ",name = "vat_invoice")
    private String vatInvoice;

    /**
     *报关手续费
     */
    @Column(columnDefinition="nvarchar(50) ",name = "cus_fees")
    private String cusFees;


    /**
     * 	共计
     */
    @Column(columnDefinition="nvarchar(50) ")
    private String total;


    /**
     * 	备注
     */
    @Column(columnDefinition="nvarchar(50) ")
    private String remarks;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVkorgName() {
        return vkorgName;
    }

    public void setVkorgName(String vkorgName) {
        this.vkorgName = vkorgName;
    }

    public String getVkorg() {
        return vkorg;
    }

    public void setVkorg(String vkorg) {
        this.vkorg = vkorg;
    }

    public String getWerks() {
        return werks;
    }

    public void setWerks(String werks) {
        this.werks = werks;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getZzPrdseries() {
        return zzPrdseries;
    }

    public void setZzPrdseries(String zzPrdseries) {
        this.zzPrdseries = zzPrdseries;
    }

    public String getInline() {
        return inline;
    }

    public void setInline(String inline) {
        this.inline = inline;
    }

    public String getTariff() {
        return tariff;
    }

    public void setTariff(String tariff) {
        this.tariff = tariff;
    }

    public String getVatInvoice() {
        return vatInvoice;
    }

    public void setVatInvoice(String vatInvoice) {
        this.vatInvoice = vatInvoice;
    }

    public String getCusFees() {
        return cusFees;
    }

    public void setCusFees(String cusFees) {
        this.cusFees = cusFees;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
