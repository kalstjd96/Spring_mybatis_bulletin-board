package com.jang.bbs.model;

import org.hibernate.validator.constraints.NotEmpty;

public class UserVO {
	
	private int mno;
	
	@NotEmpty(message="id를 입력하세요.")
	private String userId;
	@NotEmpty(message="pass를 입력하세요.")
	private String passwd;
	@NotEmpty(message="name를 입력하세요.")
	private String name;
	@NotEmpty(message="jumin를 입력하세요.")
	private String jumin;
	private String regDate;
	@NotEmpty(message="email를 입력하세요.")
	private String email;
	private char del_yn ='n';
	public int getMno() {
		return mno;
	}
	public void setMno(int mno) {
		this.mno = mno;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getJumin() {
		return jumin;
	}
	public void setJumin(String jumin) {
		this.jumin = jumin;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public char getDel_yn() {
		return del_yn;
	}
	public void setDel_yn(char del_yn) {
		this.del_yn = del_yn;
	}
	

}
