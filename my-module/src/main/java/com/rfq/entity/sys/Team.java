package com.rfq.entity.sys;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * 部门实体类
 *
 */
@Entity//实体标识
@Table(name="team")
@DynamicUpdate//表示update对象的时候，生成动态的update语句，如果这个字段的值是null就不会被加入到update语句中
@EntityListeners(AuditingEntityListener.class)//引入自定义监听类 ,可在保存前、后，更新前、后调用监听类，执行方法
public class Team {


    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1L;

    /**
     * 唯一标示
     */
    @Id
    //GenerationType.AUTO提示主键重复，GenerationType.IDENTITY提示没有主键内容。
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID")
    protected int id;

    @Column(columnDefinition="varchar(50) ")
    private String tname;

    @Column(columnDefinition="varchar(50) ")
    private String manager;

    @Column(columnDefinition="varchar(50) ",name = "isclose")
    private String isClose;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

}
