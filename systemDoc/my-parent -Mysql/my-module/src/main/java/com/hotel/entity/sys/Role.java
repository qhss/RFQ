package com.hotel.entity.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.hotel.entity.BaseInfoEntity;


/**
 * The persistent class for the sys_role database table.
 * 角色表
 * 
 */
@Entity//实体标识
@Table(name="sys_role")
@DynamicUpdate//表示update对象的时候，生成动态的update语句，如果这个字段的值是null就不会被加入到update语句中
@EntityListeners(AuditingEntityListener.class)//引入自定义监听类 ,可在保存前、后，更新前、后调用监听类，执行方法

public class Role extends BaseInfoEntity  {
	
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	/**普通角色**/
	public  static int ROLE_TYPE_NOTMAL=0;
	/**系统内置角色**/
	public static int ROLE_TYPE_SYSTEM=1;
	
	/**是否是管理员**/
	public  static int ROLE_IS_ADMIN=0;
	/**是否是管理员**/
	public static int ROLE_NOT_ADMIN=1;

	@Column(columnDefinition="varchar(100) comment '描述' ")
	@ColumnDefault(value="''")
	private String descripe;

	@Column(columnDefinition="int(2) comment '是否管理员' ")
	@ColumnDefault(value="0")
	private Integer isAdmin;


	@Column(columnDefinition="varchar(50) comment '名称' ")
	@ColumnDefault(value="''")
	private String name;

	@Column(columnDefinition="int(3) comment '类型' ")
	@ColumnDefault(value="0")
	private Integer type;

	public String getDescripe() {
		return descripe;
	}

	public void setDescripe(String descripe) {
		this.descripe = descripe;
	}

	public Integer getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}


	

}