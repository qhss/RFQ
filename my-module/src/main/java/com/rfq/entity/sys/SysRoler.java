package com.rfq.entity.sys;

import javax.persistence.*;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


/**
 * 用户实体类
 *
 */
@Entity//实体标识
@Table(name="sysroler")
@DynamicUpdate//表示update对象的时候，生成动态的update语句，如果这个字段的值是null就不会被加入到update语句中
@EntityListeners(AuditingEntityListener.class)//引入自定义监听类 ,可在保存前、后，更新前、后调用监听类，执行方法

public class SysRoler {

    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1L;

    /**未禁用**/
    public static String CANCEL_STATE_UNCENCEL="Y";
    /**已禁用**/
    public static String  CANCEL_STATE_CENCELED="N";
    /**
     * 唯一标示
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID")
    protected int id;

    /**
     * 登录账号
     */
    @Column(columnDefinition="varchar(50) ")
    private String loginId;

    /**
     * 登录密码
     */
    @Column(columnDefinition="varchar(50) ")
    private String  loginPwd;

    /**
     * 用户名称
     */
    @Column(columnDefinition="varchar(50) ")
    private String  chName;

    /**
     * 邮箱
     */
    @Column(columnDefinition="nvarchar(500) ")
    private String  email;

    /**
     * 账号启用、禁用状态控制
     */
    @Column(columnDefinition="char(50) ")
    private String  status;

    /**
     * 所属部门（关联部门ID,但类型不一致，后续需要转化）
     */
    @Column(columnDefinition="char(50) ")
    private String  whatteam;

    /**
     * 暂未发现用途，默认为N
     */
    @Column(columnDefinition="char(10) ")
    private String  online;


    /**
     * 存放菜单和数据权限
     */
    @Column(columnDefinition="nvarchar(500) ")
    private String  copy;

    /**
     * 员工工号
     */
    @Column(columnDefinition="varchar(50) ")
    private String  cardId;

    /**
     * 职位名称
     */
    @Column(columnDefinition="nvarchar(500) ")
    private String  position;

    /**
     * 入职日期
     */
    @Column(columnDefinition="datetime ")
    private String  partIndate;


    /**
     * 上司的用户ID,在出差申请时会需要根据ID找对应邮件发送通知
     */
    @Column(columnDefinition="varchar(50) ")
    private String  director;

    @Column(columnDefinition="varchar(50) ")
    private String  sapcode;

    /**
     * 业务员代码名称
     */
    @Column(columnDefinition="varchar(50) ")
    private String  memo;

    /**
     * 领导的用户id
     */
    @Column(columnDefinition="nvarchar(50) ")
    private String  BULeader;

    /*用户菜单*/
    @Transient//声明该字段不在数据库中生成对应字段
    private String hisMenus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getChName() {
        return chName;
    }

    public void setChName(String chName) {
        this.chName = chName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWhatteam() {
        return whatteam;
    }

    public void setWhatteam(String whatteam) {
        this.whatteam = whatteam;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public String getCopy() {
        return copy;
    }

    public void setCopy(String copy) {
        this.copy = copy;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPartIndate() {
        return partIndate;
    }

    public void setPartIndate(String partIndate) {
        this.partIndate = partIndate;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getSapcode() {
        return sapcode;
    }

    public void setSapcode(String sapcode) {
        this.sapcode = sapcode;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getBULeader() {
        return BULeader;
    }

    public void setBULeader(String BULeader) {
        this.BULeader = BULeader;
    }

    public String getHisMenus() {
        return hisMenus;
    }

    public void setHisMenus(String hisMenus) {
        this.hisMenus = hisMenus;
    }
}