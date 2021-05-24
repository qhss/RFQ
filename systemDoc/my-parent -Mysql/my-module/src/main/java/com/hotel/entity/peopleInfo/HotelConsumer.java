package com.hotel.entity.peopleInfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.hotel.entity.BaseInfoEntity;

/**
 *
 */
@Entity//实体类
@Table(name="bs_hotel_consumer")
@DynamicUpdate//表示update对象的时候，生成动态的update语句，如果这个字段的值是null就不会被加入到update语句中
@EntityListeners(AuditingEntityListener.class)//引入自定义监听类 ,可在保存前、后，更新前、后调用监听类，执行方法
public class HotelConsumer extends BaseInfoEntity {
	@Column(columnDefinition="varchar(32) comment '酒店id' ")
	@ColumnDefault(value="''")
	private String hotelId;
	
	@Column(columnDefinition="varchar(100) comment '客户名称' ")
	@ColumnDefault(value="''")
	private String consumerName;
	
	@Column(columnDefinition="varchar(100) comment '客户电话' ")
	@ColumnDefault(value="''")
	
	private String consumerPhone;
		
	@Column(columnDefinition="varchar(100) comment '客户消费' ")
	@ColumnDefault(value="''")
	private String consumerPrice;
	
	@Column(name="consumerIdea",columnDefinition="varchar(1000) comment '客户建议' ")
	@ColumnDefault(value="''")
	private String consumerIdea;
	
	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

	public String getConsumerName() {
		return consumerName;
	}

	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

	public String getConsumerPhone() {
		return consumerPhone;
	}

	public void setConsumerPhone(String consumerPhone) {
		this.consumerPhone = consumerPhone;
	}

	public String getConsumerPrice() {
		return consumerPrice;
	}

	public void setConsumerPrice(String consumerPrice) {
		this.consumerPrice = consumerPrice;
	}

	public String getConsumerIdea() {
		return consumerIdea;
	}

	public void setConsumerIdea(String consumerIdea) {
		this.consumerIdea = consumerIdea;
	}

}
