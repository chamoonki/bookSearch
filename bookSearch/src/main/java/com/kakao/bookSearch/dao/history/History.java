package com.kakao.bookSearch.dao.history;

import java.sql.Timestamp;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.kakao.bookSearch.dao.user.User;
import com.kakao.bookSearch.enums.BookTarget;

@Entity
@Table(name = "history")
public class History {
	
	// history id 변수 선언
	@Id
	@GeneratedValue
	private Long id;
	
	// 검색 Keyword를 설정
	@Column(nullable = false)
	private String keyword;
	
	// 검색 target을 설정
	@Column
	private String target;
	
	// 등록 날짜를 설정
	@Column(nullable = false)
	private Timestamp regdate;

	// 
	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id")
	private User user;
	
	public History() {
		super();

	}
	
	public History(String keyword, String target, Timestamp regdate, User user) {
		super();
		this.keyword = keyword;
		this.target = target;
		this.regdate = regdate;
		this.user = user;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getTarget() {
		return target;
	}
	
	public String getTragetName() {
		return BookTarget.getByCode(this.target).getTitle();
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Timestamp getRegdate() {
		return regdate;
	}

	public void setRegdate(Timestamp regdate) {
		this.regdate = regdate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
