package com.qpp.model;

// Generated Sep 24, 2014 2:42:53 PM by Hibernate Tools 3.4.0.CR1

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * TUser generated by hbm2java
 */
public class TUser extends Priciple implements java.io.Serializable {
    @Pattern(regexp = "^[\\w|\\-|_]{6,18}$",message = "err.user.register.username.verify")
	private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthDay;
    @Pattern(regexp ="(^(\\d{3}-){2}\\d{4})|((^\\+\\d{2})?[13|15|14|18|17]+\\d{9})" ,message = "err.user.register.phone.verify")
	private String phone;
    @Pattern(regexp = "^([\\w|-]+)@(\\w+)\\.(\\w+)$",message = "err.user.register.email.verify")
	private String email;
//	private long type;
	private int registerType;
//    @Pattern(regexp = "^[a-f0-9]{16,32}$",message = "err.user.register.password.verify")
	private long currentPassword;
    private long defaultAddr;
	private String nickname;
	private Double money;
	private long score;
	private String sex;
	private String comment;
	private String status;
	private String voucher;
	private long present;
	private Date ctime;
    @NotNull
	private Date utime;

	public TUser() {
//        super.authType= AuthType.USER;
    }

	public TUser(String name, String email, long type,
			long currentPassword, Date ctime, Date utime) {
//        super.authType= AuthType.USER;
		this.name = name;
		this.email = email;
//		this.type = type;
		this.currentPassword = currentPassword;
		this.ctime = ctime;
		this.utime = utime;
	}

	public TUser(String name, Date birthDay, String phone, String email,
			long type, long currentPassword, String nickname, Double money,
			long score, String sex, String comment, String status,
			String voucher, long present, Date ctime, Date utime) {
//        super.authType= AuthType.USER;
		this.name = name;
		this.birthDay = birthDay;
		this.phone = phone;
		this.email = email;
//		this.type = type;
		this.currentPassword = currentPassword;
		this.nickname = nickname;
		this.money = money;
		this.score = score;
		this.sex = sex;
		this.comment = comment;
		this.status = status;
		this.voucher = voucher;
		this.present = present;
		this.ctime = ctime;
		this.utime = utime;
	}
	public long getOid() {
		return this.oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

    public int getRegisterType() {
        return registerType;
    }

    public void setRegisterType(int registerType) {
        this.registerType = registerType;
    }

    public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthDay() {
		return this.birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public long getCurrentPassword() {
		return this.currentPassword;
	}

	public void setCurrentPassword(long currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Double getMoney() {
		return this.money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public long getScore() {
		return this.score;
	}

	public void setScore(long score) {
		this.score = score;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVoucher() {
		return this.voucher;
	}

	public void setVoucher(String voucher) {
		this.voucher = voucher;
	}

	public long getPresent() {
		return this.present;
	}

	public void setPresent(long present) {
		this.present = present;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Date getUtime() {
		return this.utime;
	}

	public void setUtime(Date utime) {
		this.utime = utime;
	}

    public long getDefaultAddr() {
        return defaultAddr;
    }

    public void setDefaultAddr(long defaultAddr) {
        this.defaultAddr = defaultAddr;
    }
}
