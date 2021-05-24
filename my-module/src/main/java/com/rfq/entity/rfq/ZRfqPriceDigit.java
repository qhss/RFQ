package com.rfq.entity.rfq;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * POS价格小数位控制实体类
 *
 */
@Entity//实体标识
@Table(name="ZRFQPriceDigit")
@DynamicUpdate//表示update对象的时候，生成动态的update语句，如果这个字段的值是null就不会被加入到update语句中
@EntityListeners(AuditingEntityListener.class)//引入自定义监听类 ,可在保存前、后，更新前、后调用监听类，执行方法
public class ZRfqPriceDigit {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1L;

    /**
     * 唯一标示
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ZID")
    protected int id;

    /**
     * 报价金额小数位控制描述信息
     */
    @Column(columnDefinition="nvarchar(50) ",name = "ZExplain")
    private String zExplain;

    /**
     * 报价保留小数位
     */
    @Column(columnDefinition="nvarchar(50) ",name = "Dight")
    private String digit;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getzExplain() {
        return zExplain;
    }

    public void setzExplain(String zExplain) {
        this.zExplain = zExplain;
    }

    public String getDigit() {
        return digit;
    }

    public void setDigit(String digit) {
        this.digit = digit;
    }
}
