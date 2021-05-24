package com.rfq.entity.material;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * 物料实体类
 *
 */
@Entity//实体标识
@Table(name="material")
@DynamicUpdate//表示update对象的时候，生成动态的update语句，如果这个字段的值是null就不会被加入到update语句中
@EntityListeners(AuditingEntityListener.class)//引入自定义监听类 ,可在保存前、后，更新前、后调用监听类，执行方法
public class Material {

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
     *物料编码
     */
    @Column(columnDefinition="nvarchar(500) ",name = "itemcode")
    private String itemCode;

    /**
     *描述
     */
    @Column(columnDefinition="nvarchar(500) ")
    private String descrip;

    /**
     *
     */
    @Column(columnDefinition="varchar(50) ")
    private String spq;

    /**
     *
     */
    @Column(columnDefinition="varchar(50) ")
    private String moq;



    /**
     *
     */
    @Column(columnDefinition="varchar(50) ")
    private String unitmsr;

    /**
     *
     */
    @Column(columnDefinition="varchar(50) ")
    private String lt;

    /**
     *品牌
     */
    @Column(columnDefinition="varchar(50) ")
    private String brand;

    /**
     *
     */
    @Column(columnDefinition="varchar(50) ",name = "isspecail")
    private String isSpecial;

    /**
     *公司Id
     */
    @Column(columnDefinition="varchar(50) ")
    private String dataId;
    /**
     *
     */
    @Column(columnDefinition="nvarchar(500) ")
    private String isNew;

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

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
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

    public String getUnitmsr() {
        return unitmsr;
    }

    public void setUnitmsr(String unitmsr) {
        this.unitmsr = unitmsr;
    }

    public String getLt() {
        return lt;
    }

    public void setLt(String lt) {
        this.lt = lt;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getIsSpecial() {
        return isSpecial;
    }

    public void setIsSpecial(String isSpecial) {
        this.isSpecial = isSpecial;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getIsNew() {
        return isNew;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }
}




