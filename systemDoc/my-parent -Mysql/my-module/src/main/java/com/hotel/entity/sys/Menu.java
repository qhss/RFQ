package com.hotel.entity.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


/**
 * 菜单表
 */
@Entity//实体标识
@Table(name="sys_menu")
@DynamicUpdate//表示update对象的时候，生成动态的update语句，如果这个字段的值是null就不会被加入到update语句中
@EntityListeners(AuditingEntityListener.class)//引入自定义监听类 ,可在保存前、后，更新前、后调用监听类，执行方法
public class Menu {
	
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(columnDefinition="varchar(20) comment '编码' ")
	@ColumnDefault(value="''")
	private String code;

	@Column(columnDefinition="varchar(100) comment '备注' ")
	@ColumnDefault(value="''")
	private String remarks;

	@Column(columnDefinition="varchar(100)  comment '名称' ")
	@ColumnDefault(value="''")
	private String name;

	@Column(columnDefinition="int(7) comment '父级id' ")
	private Integer pid;

	@Column(columnDefinition="int(3) comment '排序' ")
	@ColumnDefault(value="0")
	private Integer sort;

	@Column(columnDefinition="int(2) comment '状态' ")
	@ColumnDefault(value="0")
	private Integer state;

	@Column(columnDefinition="varchar(20) comment '样式' ")
	@ColumnDefault(value="''")
	private String style;

	@Column(columnDefinition="int(3) comment '类型' ")
	@ColumnDefault(value="0")
	private Integer type;

	@Column(columnDefinition="varchar(200) comment '访问地址' ")
	@ColumnDefault(value="''")
	private String url;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	

}