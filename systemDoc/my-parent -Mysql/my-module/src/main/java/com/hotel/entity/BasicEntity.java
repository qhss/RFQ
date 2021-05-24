package com.hotel.entity;


import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * 所有实体的基础类,包含删除标志,创建者,更新者及创建更新时间记录
 * @author duanqs
 *
 */
@MappedSuperclass
public class BasicEntity {
	
	/**未删除**/
	public static int DELETE_STATE_UNDELETE=0;
	/**已删除**/
	public static int DELETE_STATE_DELETED=1;
	
	/**
	 * 唯一标示
	 */
	@Id
	@GenericGenerator(name="idGenerator",strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	@Column(length=36)
	protected String id;
	
	/**
	 * 数据是否被删除
	 */
	@Column(columnDefinition="int(2) comment '是否已删除' ")
	@ColumnDefault(value="0")
	private Integer deleted;
	
	/**
	 * 创建者ID
	 */
	@Column(columnDefinition="varchar(36) comment '创建者ID'")
	@CreatedBy
	private String createUserId;
	
	/**
	 * 更新者ID
	 */
	@Column(columnDefinition="varchar(36) comment '更新者ID'")
	@LastModifiedBy
	private String updateUserId;
	
	/**
	 * 创建时间
	 */
	@Column(columnDefinition="bigint(20) comment '创建时间' ")
	@CreatedDate
	private Long createTime;
	
	/**
	 * 更新时间
	 */
	@Column(columnDefinition="bigint(20) comment '更新时间' ")
	@LastModifiedDate
	private Long updateTime;
	
	
	/**
	 * 公司id
	 */
	@Column(columnDefinition="varchar(36) comment '组织机构(公司Id)'")
	private String companyId;

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the createUserId
	 */
	public String getCreateUserId() {
		return createUserId;
	}

	/**
	 * @param createUserId the createUserId to set
	 */
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	/**
	 * @return the updateUserId
	 */
	public String getUpdateUserId() {
		return updateUserId;
	}

	/**
	 * @param updateUserId the updateUserId to set
	 */
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	/**
	 * @return the deleted
	 */
	public Integer getDeleted() {
		return deleted;
	}

	/**
	 * @param deleted the deleted to set
	 */
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	
	
}
