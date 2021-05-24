package com.rfq.entity.sys;

import javax.persistence.*;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


/**
 * 菜单实体类
 *
 */
@Entity//实体标识
@Table(name="sysmenu")
@DynamicUpdate//表示update对象的时候，生成动态的update语句，如果这个字段的值是null就不会被加入到update语句中
@EntityListeners(AuditingEntityListener.class)//引入自定义监听类 ,可在保存前、后，更新前、后调用监听类，执行方法

public class SysMenu {

    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1L;

    /**
     * 唯一标示
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    protected Integer id;

    /**
     * 菜单名称
     */
    @Column(columnDefinition="varchar(50) ")
    private String display;

    /**
     * 图标
     */
    @Column(columnDefinition="varchar(50) ")
    private String  icon;

    @Column(columnDefinition="int ")
    private Integer  parentId;

    /**
     * 父菜单ID
     */
    @Column(columnDefinition="int ")
    private Integer  orderId;

    /**
     * 窗体菜单权限标识
     */
    @Column(columnDefinition="int ")
    private Integer  copyId;

    /**
     * 类型（旧系统控制菜单列在第几行，暂本系统时弃用）
     */
    @Column(columnDefinition="int ")
    private Integer  type;

    @Column(columnDefinition="varchar(50) ")
    private String url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }



    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getCopyId() {
        return copyId;
    }

    public void setCopyId(Integer copyId) {
        this.copyId = copyId;
    }
}