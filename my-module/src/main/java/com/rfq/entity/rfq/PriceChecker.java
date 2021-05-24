package com.rfq.entity.rfq;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * 品牌审批人参照表
 *
 */
@Entity//实体标识
@Table(name="pricechecker")
@DynamicUpdate//表示update对象的时候，生成动态的update语句，如果这个字段的值是null就不会被加入到update语句中
@EntityListeners(AuditingEntityListener.class)//引入自定义监听类 ,可在保存前、后，更新前、后调用监听类，执行方法
public class PriceChecker {

    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1L;

    /**
     * 主键Id
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    /**
     * 公司Id
     */
    @Column(columnDefinition="varchar(50) ")
    private String dataId;

    /**
     * 品牌Id
     */
    @Column(columnDefinition="varchar(50) ")
    private String sapId;

    /**
     *审批人1
     */
    @Column(columnDefinition="varchar(50) ")
    private String checker1;

    /**
     * 审批人2
     */
    @Column(columnDefinition="varchar(50) ")
    private String checker2;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getSapId() {
        return sapId;
    }

    public void setSapId(String sapId) {
        this.sapId = sapId;
    }

    public String getChecker1() {
        return checker1;
    }

    public void setChecker1(String checker1) {
        this.checker1 = checker1;
    }

    public String getChecker2() {
        return checker2;
    }

    public void setChecker2(String checker2) {
        this.checker2 = checker2;
    }
}
