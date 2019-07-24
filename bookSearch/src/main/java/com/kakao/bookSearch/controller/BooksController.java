package com.kakao.bookSearch.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.kakao.bookSearch.dao.history.History;
import com.kakao.bookSearch.dao.user.User;
import com.kakao.bookSearch.enums.BookTarget;
import com.kakao.bookSearch.service.ApiService;
import com.kakao.bookSearch.service.MyHistoryService;
import com.kakao.bookSearch.service.UserService;

@RestController
@RequestMapping("/")
public class BooksController {
	private static final Logger logger = LoggerFactory.getLogger(BooksController.class);
	
	// user Service
	@Autowired
	UserService userService;
	
	// api를 위한 Service
	@Autowired
	ApiService apiService;
	
	@Autowired
	MyHistoryService myHistoryService;
	
	
	/**
	 * main 페이지로 들어가기 위한 API
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/")
	public ModelAndView main(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView result = new ModelAndView();
		try {
			
			// 
			result.addObject("BookTarget", BookTarget.values());
			result.setViewName("book/list");
			
		}catch(Exception e) {
			logger.error(e.getMessage());
			result.setViewName("book/list");
		}
		
		return result;
	}
	
	/**
	 * 필터 & keyword를 입력 받아 책을 검색해 주는 API
	 * 
	 * @param request
	 * @param response
	 * @param keyword
	 * @param target
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/search")
	public Map<String, Object> search(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("keyword") String keyword,
			@RequestParam(name = "target", defaultValue = "all") String target,
			@RequestParam(name = "page", defaultValue = "1") int page) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		HttpSession session = request.getSession();
		Optional<User> user = userService.getUser(session.getAttribute("account").toString());
		
		result = apiService.bookSearch(keyword, target, page);
		History history = new History(keyword, target, Timestamp.valueOf(LocalDateTime.now()), user.get());
		myHistoryService.save(history);
		
		return result;
	}
	
	@RequestMapping(value = "/detailView")
	public Map<String, Object> search(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam("isbn") String isbn) {
		 
		Map<String, Object> result = new HashMap<String, Object>();
		
		result = apiService.bookSearch(isbn, BookTarget.ISBN.getCode(), 1);
		System.out.println("@@@@@@@@@ isbn = " + result);
			//HttpSession session = request.getSession();
			//Optional<User> user = userService.getUser(session.getAttribute("account").toString());
			
			//result = apiService.bookSearch(keyword, target, page);
			//History history = new History(keyword, target, Timestamp.valueOf(LocalDateTime.now()), user.get());
			//myHistoryService.save(history);
			
		return result;
	}
}
