package com.kakao.bookSearch.dao.bookmark;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.kakao.bookSearch.dao.user.User;

@Entity
@Table(name = "bookmark")
public class Bookmark {
	
	// bookmark의 고유 ID 설정
	@Id
	@GeneratedValue
	private Long id;
	
	// Book의 고유 키 isbn 설정
	@Column(nullable = false)
	private String isbn;
	
	// Book Title 설정
	@Column(nullable = false)
	private String title;
	
	// Bookmark 등록 일자.
	@Column(nullable = false)
	private Timestamp regdate;
	
	// 사용자의 
	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id")
	private User user;
	
	public Bookmark() {
		super();
	}

	public Bookmark(String title, String isbn, Timestamp regdate, User user) {
		super();
		this.title = title;
		this.isbn = isbn;
		this.regdate = regdate;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
