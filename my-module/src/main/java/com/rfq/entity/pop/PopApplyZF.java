package com.rfq.entity.pop;


import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * RFQ项目实体类
 *
 */
@Entity//实体标识
@Table(name="PopapplyZF")
@DynamicUpdate//表示update对象的时候，生成动态的update语句，如果这个字段的值是null就不会被加入到update语句中
@EntityListeners(AuditingEntityListener.class)//引入自定义监听类 ,可在保存前、后，更新前、后调用监听类，执行方法
public class PopApplyZF {

    private static final long serialVersionUID = 1L;


    /**
     * 唯一标示
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID")
    protected int id;

    /**
     *编码
     */
    @Column(columnDefinition="nvarchar(50) ")
    private String spazf;

    /**
     * 名称
     */
    @Column(columnDefinition="nvarchar(50) ",name="spazfname")
    private String spazfName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpazf() {
        return spazf;
    }

    public void setSpazf(String spazf) {
        this.spazf = spazf;
    }

    public String getSpazfName() {
        return spazfName;
    }

    public void setSpazfName(String spazfName) {
        this.spazfName = spazfName;
    }
}
