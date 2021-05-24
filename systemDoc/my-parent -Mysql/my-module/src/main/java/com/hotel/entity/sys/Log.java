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
 * The persistent class for the sys_log database table.
 * 日志�?
 * 
 */
@Entity//实体标识
@Table(name="sys_log")
@DynamicUpdate//表示update对象的时候，生成动�?�的update语句，如果这个字段的值是null就不会被加入到update语句�?
@EntityListeners(AuditingEntityListener.class)//引入自定义监听类 ,可在保存前�?�后，更新前、后调用监听类，执行方法

public class Log extends BasicEntity  {
	
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	
	@Column(columnDefinition="varchar(100) comment '模块' ")
	@ColumnDefault(value="''")
	private String modular;
	
	@Column(columnDefinition="varchar(300) comment '内容' ")
	@ColumnDefault(value="''")
	private String content;
	
	@Column(columnDefinition="varchar(500) comment '详情' ")
	@ColumnDefault(value="''")
	private String details;

	@Column(columnDefinition="varchar(50) comment '类型' ")
	@ColumnDefault(value="''")
	private String type;
	
	@Column(columnDefinition="varchar(20) comment '操作ip' ")
	@ColumnDefault(value="''")
	private String ip;
	
	@Column(columnDefinition="int(2) comment '操作状�??' ")
	@ColumnDefault(value="0")
	private Integer state;

	public String getModular() {
		return modular;
	}

	public void setModular(String modular) {
		this.modular = modular;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


}