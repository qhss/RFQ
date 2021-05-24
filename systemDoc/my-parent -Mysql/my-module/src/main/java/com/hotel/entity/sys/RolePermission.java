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
 * The persistent class for the sys_role_permissions database table.
 * 
 */
@Entity
@Table(name="sys_role_permissions")
@DynamicUpdate//表示update对象的时候，生成动态的update语句，如果这个字段的值是null就不会被加入到update语句中
@EntityListeners(AuditingEntityListener.class)//引入自定义监听类 ,可在保存前、后，更新前、后调用监听类，执行方法

public class RolePermission extends BasicEntity  {
	
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	@Column(columnDefinition="varchar(36) comment '�˵�id' ")
	@ColumnDefault(value="''")
	private String menuId;

	@Column(columnDefinition="varchar(36) comment '��ɫid' ")
	@ColumnDefault(value="''")
	private String roleId;

	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}