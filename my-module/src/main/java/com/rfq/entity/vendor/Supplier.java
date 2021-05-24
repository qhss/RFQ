package com.rfq.entity.vendor;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * 供应商列表
 */
@Entity//实体标识
@Table(name="SupplierList")
@DynamicUpdate//表示update对象的时候，生成动态的update语句，如果这个字段的值是null就不会被加入到update语句中
@EntityListeners(AuditingEntityListener.class)//引入自定义监听类 ,可在保存前、后，更新前、后调用监听类，执行方法
public class Supplier {
    /**
     * 唯一标示
     */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "SID")
    protected int id;


    /**
     * 供应商编号
     */
    @Column(columnDefinition="nvarchar(50) ",name = "VENDOR")
    private String vendor;


    /**
     *采购工厂
     */
    @Column(columnDefinition="nvarchar(50) ",name = "Purchase")
    private String purchase;

    /**
     * 供应商代码
     */
    @Column(columnDefinition="nvarchar(50) ",name = "Code")
    private String code;


    /**
     *供应商旧编码
     */
    @Column(columnDefinition="nvarchar(50) ")
    private String oldCode;


    /**
     * 供应商代码名称
     */
    @Column(columnDefinition="nvarchar(50) ",name = "CodeName")
    private String codeName;


    /**
     *新导入标识
     */
    @Column(columnDefinition="nvarchar(50) ",name = "IsNew")
    private String isNew;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOldCode() {
        return oldCode;
    }

    public void setOldCode(String oldCode) {
        this.oldCode = oldCode;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getIsNew() {
        return isNew;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }
}
