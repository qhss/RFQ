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
 * 酒店房型图片
 */
@Entity//实体标识
@Table(name="bs_room_photo")//表名
@DynamicUpdate//表示update对象的时候，生成动�?�的update语句，如果这个字段的值是null就不会被加入到update语句�?
@EntityListeners(AuditingEntityListener.class)//引入自定义监听类 ,可在保存前�?�后，更新前、后调用监听类，执行方法
public class HotelRoomPhoto extends BaseInfoEntity  {
	
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	
	@Column(columnDefinition="varchar(500) comment '图片网址' ")
	@ColumnDefault(value="''")
	private String url;
	
	@Column(columnDefinition="int(4) comment '排序�?' ")
	@ColumnDefault(value="99")
	private Integer sort;

	@ManyToOne
	@JoinColumn(name="hotelRoomId",foreignKey=@ForeignKey(name="none",value = ConstraintMode.NO_CONSTRAINT),columnDefinition="varchar(36) comment '酒店房型id'")
	private HotelRoom hotelRoom;
	
	/**备注**/
	@Column(columnDefinition="varchar(100) comment '备注' ")
	@ColumnDefault(value="''")
	private String remark;


	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}


	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}




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
	 * @return the sort
	 */
	public Integer getSort() {
		return sort;
	}


	/**
	 * @param sort the sort to set
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
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