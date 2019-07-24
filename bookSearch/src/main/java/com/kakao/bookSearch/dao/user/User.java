package com.kakao.bookSearch.dao.user;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user")
public class User {
	
	// Account를 ID로 잡아 준다.
	@Id
	private String account;
	
	// password 컬럼 설정
	@JsonIgnore
	@Column(nullable = false)
	private String passwd;
	
	// 등록 날짜 컬럼 설정
	@Column(nullable = false)
	private Timestamp regdate;

	public User() {
		super();
	}

	public User(String account, String passwd, Timestamp regdate) {
		super();
		this.account = account;
		this.passwd = passwd;
		this.regdate = regdate;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPwd(String passwd) {
		this.passwd = passwd;
	}

	public Timestamp getRegdate() {
		return regdate;
	}

	public void setRegdate(Timestamp regdate) {
		this.regdate = regdate;
	}
}
