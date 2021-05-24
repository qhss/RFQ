package com.rfq.entity.vendor;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * soldTo ,供应商，工厂对应参照表
 *
 */
@Entity//实体标识
@Table(name="ZVendorRely")
@DynamicUpdate//表示update对象的时候，生成动态的update语句，如果这个字段的值是null就不会被加入到update语句中
@EntityListeners(AuditingEntityListener.class)//引入自定义监听类 ,可在保存前、后，更新前、后调用监听类，执行方法
public class ZVendorRely {
    /**
     * 唯一标示
     */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "ID")
    protected int id;


    /**
     *供应商售达地
     */
    @Column(columnDefinition="nvarchar(50) ",name = "sold_to")
    private String soldTo;
    /**
     * 供应商编号
     */
    @Column(columnDefinition="nvarchar(50) ",name = "Vendor")
    private String vendor;

    /**
     * 工厂id
     */
    @Column(columnDefinition="nvarchar(50) ",name = "EKORG")
    private String ekorg;

    /**
     * SAP采购单号前三位
     */
    @Column(columnDefinition="nvarchar(50) ",name = "PO")
    private String po;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getEkorg() {
        return ekorg;
    }

    public void setEkorg(String ekorg) {
        this.ekorg = ekorg;
    }

    public String getPo() {
        return po;
    }

    public void setPo(String po) {
        this.po = po;
    }
}
