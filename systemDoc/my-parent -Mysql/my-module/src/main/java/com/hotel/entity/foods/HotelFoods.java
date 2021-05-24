package com.hotel.entity.foods;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.hotel.entity.BaseInfoEntity;

/**
 * 餐饮美食实体类
 */
@Entity//实体标识
@Table(name="bs_hotel_foods")
@DynamicUpdate////表示update对象的时候，生成动态的update语句，如果这个字段的值是null就不会被加入到update语句中
@EntityListeners(AuditingEntityListener.class)//引入自定义监听类 ,可在保存前、后，更新前、后调用监听类，执行方法
public class HotelFoods extends BaseInfoEntity{
	 public static int FOOD_DISH=0;
     public static int FOOD_PACKAGE=1;
     public static int FOOD_DRINK=2;
	  
		@Column(columnDefinition="varchar(32) comment '酒店id' ")
		@ColumnDefault(value="''")
		private String hotelId;
		
		@Column(columnDefinition="varchar(32) comment '餐饮类型 1-特色小吃 2- 3-饮品' ")
		@ColumnDefault(value="''")
		private Integer foodType;
		
		@Column(columnDefinition="varchar(1000) comment '食物名称' ")
		@ColumnDefault(value="''")
		private String foodName;
			
		@Column(columnDefinition="varchar(1000) comment '价钱' ")
		@ColumnDefault(value="''")
		private String foodPrice;

		public String getHotelId() {
			return hotelId;
		}

		public void setHotelId(String hotelId) {
			this.hotelId = hotelId;
		}

		public Integer getFoodType() {
			return foodType;
		}

		public void setFoodType(Integer foodType) {
			this.foodType = foodType;
		}

		public String getFoodName() {
			return foodName;
		}

		public void setFoodName(String foodName) {
			this.foodName = foodName;
		}

		public String getFoodPrice() {
			return foodPrice;
		}

		public void setFoodPrice(String foodPrice) {
			this.foodPrice = foodPrice;
		}


}

