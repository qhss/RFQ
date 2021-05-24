package com.hotel.entity.room;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.hotel.entity.BaseInfoEntity;



/**
 * 酒店档案
 */
@Entity//实体标识
@Table(name="bs_hotel")//表名
@DynamicUpdate//表示update对象的时候，生成动态的update语句，如果这个字段的值是null就不会被加入到update语句中
@EntityListeners(AuditingEntityListener.class)//引入自定义监听类 ,可在保存前、后，更新前、后调用监听类，执行方法
public class Hotel extends BaseInfoEntity  {
	
	
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	
	@Column(columnDefinition="varchar(50) comment '编码' ")
	@ColumnDefault(value="''")
	private String code;
	
	@Column(columnDefinition="varchar(100) comment '酒店名称' ")
	@ColumnDefault(value="''")
	private String name;
	
	@Column(columnDefinition="varchar(100) comment '酒店介绍词' ")
	@ColumnDefault(value="''")
	private String recommendWord;
	
	@Column(columnDefinition="varchar(500) comment '标签,逗号分隔' ")
	@ColumnDefault(value="''")
	private String labels;
	
	@Column(columnDefinition="varchar(500) comment '封面图片地址' ")
	@ColumnDefault(value="''")
	private String coverUrl;
	
	@Column(columnDefinition="varchar(200) comment '所在城市' ")
	@ColumnDefault(value="''")
	private String city;
	
	@Column(columnDefinition="varchar(500) comment '详细地址' ")
	@ColumnDefault(value="''")
	private String address;
	
	@Column(columnDefinition="varchar(50) comment '联系电话' ")
	@ColumnDefault(value="''")
	private String tel;
	
	@Column(columnDefinition="text comment '酒店介绍' ")
	private String resume;
	
	
	/**备注**/
	@Column(columnDefinition="varchar(100) comment '备注' ")
	private String remark;
	


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	 * @return the labels
	 */
	public String getLabels() {
		return labels;
	}

	/**
	 * @param labels the labels to set
	 */
	public void setLabels(String labels) {
		this.labels = labels;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * @return the resume
	 */
	public String getResume() {
		return resume;
	}

	/**
	 * @param resume the resume to set
	 */
	public void setResume(String resume) {
		this.resume = resume;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the coverUrl
	 */
	public String getCoverUrl() {
		return coverUrl;
	}

	/**
	 * @param coverUrl the coverUrl to set
	 */
	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}

	/**
	 * @return the recommendWord
	 */
	public String getRecommendWord() {
		return recommendWord;
	}

	/**
	 * @param recommendWord the recommendWord to set
	 */
	public void setRecommendWord(String recommendWord) {
		this.recommendWord = recommendWord;
	}




}