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
 * 组织结构�?
 */
@Entity//实体标识
@Table(name="sys_organization")
@DynamicUpdate//表示update对象的时候，生成动态的update语句，如果这个字段的值是null就不会被加入到update语句中
@EntityListeners(AuditingEntityListener.class)//引入自定义监听类 ,可在保存前、后，更新前、后调用监听类，执行方法
public class Organization extends BaseInfoEntity  {
	
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	
	/**普通部门**/
	public static int ORG_TYPE_NORMAL_DEPT=0;
	/**公司**/
	public static int ORG_TYPE_COMPANY=1;
	/**仓库**/
	public static int ORG_TYPE_WAREHOUSE=2;
	
	@Column(columnDefinition="varchar(200) comment '负责人地址' ")
	@ColumnDefault(value="''")
	private String address;

	@Column(columnDefinition="varchar(10) comment '机构编码,erp库存地点编码' ")
	@ColumnDefault(value="''")
	private String code;

	@Column(columnDefinition="varchar(100) comment '备注' ")
	@ColumnDefault(value="''")
	private String memo;

	@Column(columnDefinition="varchar(50) comment '名称' ")
	@ColumnDefault(value="''")
	private String name;

	@Column(columnDefinition="int(5) comment '排序' ")
	@ColumnDefault(value="0")
	private Integer sort;
	
	@Column(columnDefinition="varchar(36) comment '父级id' ")
	@ColumnDefault(value="''")
	private String pid;

	@Column(columnDefinition="varchar(20) comment '负责人电话' ")
	@ColumnDefault(value="''")
	private String tel;

	@Column(columnDefinition="int(2) comment '组织机构类型 1-部门 2-仓库' ")
	@ColumnDefault(value="0")
	private Integer type;

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPid() {
		return this.pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}