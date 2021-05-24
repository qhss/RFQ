package com.hotel.entity.sys;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.hotel.entity.BaseInfoEntity;


/**
 * The persistent class for the sys_user database table.
 * 用户表
 * 
 */
@Entity//实体标识
@Table(name="sys_user")
@DynamicUpdate//表示update对象的时候，生成动态的update语句，如果这个字段的值是null就不会被加入到update语句中
@EntityListeners(AuditingEntityListener.class)//引入自定义监听类 ,可在保存前、后，更新前、后调用监听类，执行方法
public class User extends BaseInfoEntity  {
	
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	@Column(columnDefinition="int(3) comment '年龄'")
	@ColumnDefault(value="0")
	private Integer age =0;

	@Column(columnDefinition="varchar(20) comment '出生年月日' ")
	@ColumnDefault(value="''")
	private String birthDay;

	@Column(columnDefinition="varchar(50) comment '登录账号' ")
	@ColumnDefault(value="''")
	private String loginName;

	@Column(columnDefinition="varchar(100) comment '姓名' ")
	@ColumnDefault(value="''")
	private String userName;
	

	@Column(columnDefinition="varchar(50) comment '密码' ")
	@ColumnDefault(value="''")
	private String password;

	@Column(columnDefinition="varchar(20) comment '电话' ")
	@ColumnDefault(value="''")
	private String phone;

	@Column(columnDefinition="int(1) comment '性别' ")
	@ColumnDefault(value="0")
	private Integer sex;

	@Column(columnDefinition="int(1) comment '状态' ")
	@ColumnDefault(value="0")
	private Integer state =0;
	
	@Column(columnDefinition="varchar(10) comment '随机字符串' ")
	@ColumnDefault(value="''")
	private String randomStr;
	
	@Column(columnDefinition="int(2) comment '状态' ")
	@ColumnDefault(value="0")
	private Integer isUpdatePassword;
	
	/**
	 * 酒店自己维护
	 */
	@Column(columnDefinition="varchar(36) comment '酒店ID(用于酒店用户)' ")
	@ColumnDefault(value="''")
	private String hotelId;

	
	/*用户菜单*/
	@Transient//声明该字段不在数据库中生成对应字段
	private String hisMenus;
	

	@Transient
	private List<Role> roles;

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}
	
	

	public String getCredentialsSalt() {
		return this.password + this.randomStr;
	}


	public String getHisMenus() {
		return hisMenus;
	}

	public void setHisMenus(String hisMenus) {
		this.hisMenus = hisMenus;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getRandomStr() {
		return randomStr;
	}

	public void setRandomStr(String randomStr) {
		this.randomStr = randomStr;
	}

	public Integer getIsUpdatePassword() {
		return isUpdatePassword;
	}

	public void setIsUpdatePassword(Integer isUpdatePassword) {
		this.isUpdatePassword = isUpdatePassword;
	}

	/**
	 * @return the state
	 */
	public Integer getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */

	public void setState(Integer state) {
		this.state = state;
	}

	/**
	 * @return the hotelId
	 */
	public String getHotelId() {
		return hotelId;
	}

	/**
	 * @param hotelId the hotelId to set
	 */
	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}
	
	
	
}