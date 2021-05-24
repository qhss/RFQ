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
import com.hotel.entity.room.Hotel;
import com.hotel.entity.room.HotelRoom;



/**
 * 酒店计划表
 */
@Entity//实体标识
@Table(name="tp_hotel_plan")//表名
@DynamicUpdate//表示update对象的时候，生成动态的update语句，如果这个字段的值是null就不会被加入到update语句中
@EntityListeners(AuditingEntityListener.class)//引入自定义监听类 ,可在保存前、后，更新前、后调用监听类，执行方法
public class HotelPlan extends BaseInfoEntity  {
	
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name="hotelId",foreignKey=@ForeignKey(name="none",value = ConstraintMode.NO_CONSTRAINT),columnDefinition="varchar(36) comment '酒店id'")
	private Hotel hotel;
	
	@ManyToOne
	@JoinColumn(name="hotelRoomId",foreignKey=@ForeignKey(name="none",value = ConstraintMode.NO_CONSTRAINT),columnDefinition="varchar(36) comment '酒店房型id'")
	private HotelRoom hotelRoom;
	
	@Column(columnDefinition="int(10) comment '日期' ")
	private Integer date;
	
	@Column(columnDefinition="int(4) comment '可销售数量' ")
	@ColumnDefault(value="0")
	private Integer planCount;

	
	@Column(columnDefinition="decimal(10,2) comment '门市价格' ")
	@ColumnDefault(value="0")
	private BigDecimal price;
	
	@Column(columnDefinition="decimal(10,2) comment '会员价格' ")
	@ColumnDefault(value="0")
	private BigDecimal memberPrice;
	
	
	@Column(columnDefinition="int(4) comment '已销售数量' ")
	@ColumnDefault(value="0")
	private Integer salesCount;
	
	/**备注**/
	@Column(columnDefinition="varchar(100) comment '备注' ")
	@ColumnDefault(value="''")
	private String remark;





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
	 * @return the hotelRoom
	 */
	public HotelRoom getHotelRoom() {
		return hotelRoom;
	}


	/**
	 * @param hotelRoom the hotelRoom to set
	 */
	public void setHotelRoom(HotelRoom hotelRoom) {
		this.hotelRoom = hotelRoom;
	}


	/**
	 * @return the date
	 */
	public Integer getDate() {
		return date;
	}


	/**
	 * @param date the date to set
	 */
	public void setDate(Integer date) {
		this.date = date;
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


	/**
	 * @return the salesCount
	 */
	public Integer getSalesCount() {
		return salesCount;
	}


	/**
	 * @param salesCount the salesCount to set
	 */
	public void setSalesCount(Integer salesCount) {
		this.salesCount = salesCount;
	}




}