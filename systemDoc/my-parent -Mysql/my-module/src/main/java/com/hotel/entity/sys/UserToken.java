package com.hotel.entity.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.hotel.entity.BasicEntity;

@Entity
@Table(name ="sys_user_token") 
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class UserToken extends BasicEntity {
	
	/**用户Token**/
	@Column(columnDefinition="varchar(36) comment '用户Token' ")
	@ColumnDefault(value="''")
	private String token;
	
	
	/**用户Id**/
	@Column(columnDefinition="varchar(36) comment '用户Id' ")
	@ColumnDefault(value="''")
	private String userId;
	

	
	/**用户Id**/
	@Column(columnDefinition="bigint(20) comment '过期时间' ")
	private long overTime;
	
	
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}


	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}


	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}


	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}


	/**
	 * @return the overTime
	 */
	public long getOverTime() {
		return overTime;
	}


	/**
	 * @param overTime the overTime to set
	 */
	public void setOverTime(long overTime) {
		this.overTime = overTime;
	}

	
	
	
}
