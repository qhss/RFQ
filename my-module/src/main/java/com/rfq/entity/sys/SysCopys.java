package com.rfq.entity.sys;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * The persistent class for the sys_role database table.
 * 权限表
 *
 */
@Entity//实体标识
@Table(name="syscopys")
@DynamicUpdate//表示update对象的时候，生成动态的update语句，如果这个字段的值是null就不会被加入到update语句中
@EntityListeners(AuditingEntityListener.class)//引入自定义监听类 ,可在保存前、后，更新前、后调用监听类，执行方法
public class SysCopys {

    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1L;

    /**
     * 唯一标示
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID")
    protected int id;

    @Column(columnDefinition="varchar(50) ")
    private String text;

    /**
     * 父节点id,暂未发现用途
     */
    @Column(columnDefinition="int ")
    private int  parentId;

    /**
     * 排序id
     */
    @Column(columnDefinition="int  ",name = "orderid")
    private int  orderId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
