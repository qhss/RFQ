package com.hotel.entity.room;

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
 * 酒店房间设施
 */
@Entity//实体标识
@Table(name="bs_room_device")//表名
@DynamicUpdate//表示update对象的时候，生成动�?�的update语句，如果这个字段的值是null就不会被加入到update语句�?
@EntityListeners(AuditingEntityListener.class)//引入自定义监听类 ,可在保存前�?�后，更新前、后调用监听类，执行方法
public class HotelRoomDevice extends BaseInfoEntity  {
	
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	
	@Column(columnDefinition="text comment '房间设施(逗号分隔)' ")
	private String roomDevice;
	
	@Column(columnDefinition="text comment '浴室设施(逗号分隔)' ")
	private String bathroomDevice;
	
	@Column(columnDefinition="text comment '食品饮料' ")
	private String food;
	
	@ManyToOne
	@JoinColumn(name="hotelRoomId",foreignKey=@ForeignKey(name="none",value = ConstraintMode.NO_CONSTRAINT),columnDefinition="varchar(36) comment '酒店房型id'")
	private HotelRoom hotelRoom;
	
	
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
	 * @return the food
	 */
	public String getFood() {
		return food;
	}


	/**
	 * @param food the food to set
	 */
	public void setFood(String food) {
		this.food = food;
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


	


}