package com.hotel.entity.room;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.hotel.entity.BaseInfoEntity;



/**
 * 酒店房型档案
 */
@Entity//实体标识
@Table(name="bs_hotel_room")//表名
@DynamicUpdate//表示update对象的时候，生成动态的update语句，如果这个字段的值是null就不会被加入到update语句中
@EntityListeners(AuditingEntityListener.class)//引入自定义监听类 ,可在保存前�?�后，更新前、后调用监听类，执行方法
public class HotelRoom extends BaseInfoEntity  {
	
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	
	@Column(columnDefinition="varchar(500) comment '房型名称' ")
	@ColumnDefault(value="''")
	private String typeName;
	
	@Column(columnDefinition="varchar(4) comment '面积(平方)' ")
	@ColumnDefault(value="''")
	private String area;
	
	@Column(columnDefinition="varchar(50) comment '床位情况' ")
	@ColumnDefault(value="''")
	private String bedResume;

	@Column(columnDefinition="varchar(50) comment '最大人数' ")
	@ColumnDefault(value="''")
	private String personMax;
	
	@Column(columnDefinition="varchar(500) comment '封面图片地址' ")
	@ColumnDefault(value="''")
	private String coverUrl;
	
	
	//====房间设施====
	@Column(columnDefinition="text comment '房间设施(逗号分隔)' ")
	private String roomDevice;
	
	@Column(columnDefinition="text comment '浴室设施(逗号分隔)' ")
	private String bathroomDevice;
	
	@Column(columnDefinition="text comment '食品饮料' ")
	private String foodDevice;
	
	
	@ManyToOne
	@JoinColumn(name="hotelId",foreignKey=@ForeignKey(name="none",value = ConstraintMode.NO_CONSTRAINT),columnDefinition="varchar(36) comment '酒店id'")
	private Hotel hotel;
	
	/**备注**/
	@Column(columnDefinition="varchar(100) comment '备注' ")
	@ColumnDefault(value="''")
	private String remark;

	
	//====默认计划数据=======
	@Column(columnDefinition="int(4) comment '可销售数�?' ")
	@ColumnDefault(value="0")
	private Integer planCount;

	
	@Column(columnDefinition="decimal(10,2) comment '门市价格' ")
	@ColumnDefault(value="0")
	private BigDecimal price;
	
	@Column(columnDefinition="decimal(10,2) comment '会员价格' ")
	@ColumnDefault(value="0")
	private BigDecimal memberPrice;

	
	
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}


	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}


	/**
	 * @return the hotel
	 */
	public Hotel getHotel() {
		return hotel;
	}


	/**
	 * @param hotel the hotel to set
	 */
	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}


	/**
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}


	/**
	 * @param typeName the typeName to set
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}


	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}


	/**
	 * @param area the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}


	/**
	 * @return the bedResume
	 */
	public String getBedResume() {
		return bedResume;
	}


	/**
	 * @param bedResume the bedResume to set
	 */
	public void setBedResume(String bedResume) {
		this.bedResume = bedResume;
	}


	/**
	 * @return the personMax
	 */
	public String getPersonMax() {
		return personMax;
	}


	/**
	 * @param personMax the personMax to set
	 */
	public void setPersonMax(String personMax) {
		this.personMax = personMax;
	}


	/**
	 * @return the coverUrl
	 */
	public String getCoverUrl() {
		return coverUrl;
	}


	/**
	 * @param coverUrl the coverUrl to set
	 */
	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}


	/**
	 * @return the roomDevice
	 */
	public String getRoomDevice() {
		return roomDevice;
	}


	/**
	 * @param roomDevice the roomDevice to set
	 */
	public void setRoomDevice(String roomDevice) {
		this.roomDevice = roomDevice;
	}


	/**
	 * @return the bathroomDevice
	 */
	public String getBathroomDevice() {
		return bathroomDevice;
	}


	/**
	 * @param bathroomDevice the bathroomDevice to set
	 */
	public void setBathroomDevice(String bathroomDevice) {
		this.bathroomDevice = bathroomDevice;
	}




	/**
	 * @return the foodDevice
	 */
	public String getFoodDevice() {
		return foodDevice;
	}


	/**
	 * @param foodDevice the foodDevice to set
	 */
	public void setFoodDevice(String foodDevice) {
		this.foodDevice = foodDevice;
	}


	/**
	 * @return the planCount
	 */
	public Integer getPlanCount() {
		return planCount;
	}


	/**
	 * @param planCount the planCount to set
	 */
	public void setPlanCount(Integer planCount) {
		this.planCount = planCount;
	}


	/**
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}


	/**
	 * @param price the price to set
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}


	/**
	 * @return the memberPrice
	 */
	public BigDecimal getMemberPrice() {
		return memberPrice;
	}


	/**
	 * @param memberPrice the memberPrice to set
	 */
	public void setMemberPrice(BigDecimal memberPrice) {
		this.memberPrice = memberPrice;
	}
	


}