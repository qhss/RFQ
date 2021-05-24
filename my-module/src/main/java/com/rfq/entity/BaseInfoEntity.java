package com.rfq.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.ColumnDefault;

@MappedSuperclass
public class BaseInfoEntity extends BasicEntity {

	/**未禁用**/
	public static int CANCEL_STATE_UNCENCEL=0;
	/**已禁用**/
	public static int CANCEL_STATE_CENCELED=1;
	
	/**
	 * 数据是否被禁用
	 */
	@Column(columnDefinition="int ")
//	@Column(columnDefinition="int(2) comment '是否已禁用 0-未禁用 1-已禁用' ")
	@ColumnDefault(value="0")
	private Integer canceled;

	/**
	 * @return the canceled
	 */
	public Integer getCanceled() {
		return canceled;
	}

	/**
	 * @param canceled the canceled to set
	 */
	public void setCanceled(Integer canceled) {
		this.canceled = canceled;
	}
	
}
