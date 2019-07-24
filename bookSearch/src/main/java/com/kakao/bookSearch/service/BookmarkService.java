package com.kakao.bookSearch.service;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kakao.bookSearch.dao.bookmark.Bookmark;
import com.kakao.bookSearch.dao.bookmark.BookmarkRepository;
import com.kakao.bookSearch.dao.user.User;

@Service
public class BookmarkService {
	private static final Logger logger = LoggerFactory.getLogger(BookmarkService.class);
	
	@Autowired
	BookmarkRepository bookmarkRepository;
	
	@Transactional
	public Bookmark getBookmark(User user, String isbn) {
		return bookmarkRepository.findOneBookmark(user, isbn);
	}
}
