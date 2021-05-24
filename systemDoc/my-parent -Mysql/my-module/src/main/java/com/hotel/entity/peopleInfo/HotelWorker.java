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
@Entity
@Table(name="bs_hotel_worker")
@DynamicUpdate//表示update对象的时候，生成动态的update语句，如果这个字段的值是null就不会被加入到update语句中
@EntityListeners(AuditingEntityListener.class)//引入自定义监听类 ,可在保存前、后，更新前、后调用监听类，执行方法
public class HotelWorker extends BaseInfoEntity {
	 public static int WORKER_MANAGER=0;
     public static int WORKER_GROUP_LEADER=1;
     public static int WORKER_GENERAL_STAFF=2;
	  
		@Column(columnDefinition="varchar(32) comment '酒店id' ")
		@ColumnDefault(value="''")
		private String hotelId;
		
		@Column(columnDefinition="varchar(32) comment 'ְ工作类型' ")
		@ColumnDefault(value="''")
		private Integer workerType;
		
		@Column(columnDefinition="varchar(100) comment '工人姓名' ")
		@ColumnDefault(value="''")
		private String workerName;
		
		@Column(columnDefinition="varchar(10) comment '年龄' ")
		@ColumnDefault(value="''")
		private Integer workerAge;
		
		@Column(columnDefinition="varchar(100) comment '工资' ")
		@ColumnDefault(value="''")
	    private Double workerPrice;
	    
		@Column(columnDefinition="varchar(100) comment '工人电话 ' ")
		@ColumnDefault(value="''")
		private String workerPhone;
		
		
		public String getWorkerPhone() {
			return workerPhone;
		}

		public void setWorkerPhone(String workerPhone) {
			this.workerPhone = workerPhone;
		}

	
		public String getHotelId() {
			return hotelId;
		}
		public void setHotelId(String hotelId) {
			this.hotelId = hotelId;
		}

		public Integer getWorkerType() {
			return workerType;
		}

		public void setWorkerType(Integer workerType) {
			this.workerType = workerType;
		}

		public String getWorkerName() {
			return workerName;
		}

		public void setWorkerName(String workerName) {
			this.workerName = workerName;
		}



		public Integer getWorkerAge() {
			return workerAge;
		}

		public void setWorkerAge(Integer workerAge) {
			this.workerAge = workerAge;
		}

		public Double getWorkerPrice() {
			return workerPrice;
		}

		public void setWorkerPrice(Double workerPrice) {
			this.workerPrice = workerPrice;
		}

		

}
