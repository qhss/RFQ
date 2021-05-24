package com.hotel.entity.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.hotel.entity.BasicEntity;


/**
 * The persistent class for the sys_user_role database table.
 * 用户角色表
 * 
 */
@Entity//实体标识
@Table(name="sys_user_role")
@DynamicUpdate//表示update对象的时候，生成动态的update语句，如果这个字段的值是null就不会被加入到update语句中
@EntityListeners(AuditingEntityListener.class)//引入自定义监听类 ,可在保存前、后，更新前、后调用监听类，执行方法

public class UserRole extends BasicEntity  {
	
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	@Column(columnDefinition="varchar(36) comment '用户id' ")
	@ColumnDefault(value="''")
    private String userId;
    
	@Column(columnDefinition="varchar(36) comment '角色id' ")
	@ColumnDefault(value="''")
    private String roleId;
    
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}




}