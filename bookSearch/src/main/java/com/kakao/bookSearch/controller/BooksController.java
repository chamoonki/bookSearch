package com.kakao.bookSearch.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.kakao.bookSearch.dao.bookmark.Bookmark;
import com.kakao.bookSearch.dao.history.History;
import com.kakao.bookSearch.dao.user.User;
import com.kakao.bookSearch.enums.BookTarget;
import com.kakao.bookSearch.service.ApiService;
import com.kakao.bookSearch.service.BookmarkService;
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
	
	// Search History를 위한 Service
	@Autowired
	MyHistoryService myHistoryService;
	
	@Autowired
	BookmarkService bookmarkService;
	
	
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
			
			result.addObject("BookTarget", BookTarget.values());
			result.setViewName("book/list");
			
		}catch(Exception e) {
			logger.error(e.getMessage());
			result.setViewName("book/list");
		}
		
		return result;
	}
	
	/**
	 * 로그인 한 사용자의 검색에 대한 기록을 모두 가져온다.
	 * 
	 * @param request
	 * @param response
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/history")
	public ModelAndView history(HttpServletRequest request, HttpServletResponse response, 
			@PageableDefault(size = 10, page = 0, sort = "regdate", direction = Direction.DESC) Pageable pageable) {
		ModelAndView result = new ModelAndView();
		
		HttpSession session = request.getSession();
		Optional<User> user = userService.getUser(session.getAttribute("account").toString());
		
		Page<History> historyPage = myHistoryService.findByHisory(user.get(), pageable);
		
		result.addObject("BookTarget", BookTarget.values());
		result.addObject("historyData", historyPage);
		result.setViewName("book/history");
		
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
		try {
			HttpSession session = request.getSession();
			Optional<User> user = userService.getUser(session.getAttribute("account").toString());
			
			result = apiService.bookSearch(keyword, target, page);
			History history = new History(keyword, target, Timestamp.valueOf(LocalDateTime.now()), user.get());
			myHistoryService.save(history);
		} catch(Exception e) {
			
		}
		
		return result;
	}
	
	/** 
	 * 책 한개를 선택 하여 Detail View를 보기 위한 데이터를 가져온다.
	 * parameter로 isbn을 선택한 이유는 유일한 키가 isbn 같아보이기 때문이다.
	 * 
	 * @param request
	 * @param response
	 * @param isbn
	 * @return
	 */
	@RequestMapping(value = "/detailView")
	public ModelAndView search(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam("isbn") String isbn) {
		ModelAndView result = new ModelAndView();
		
		HttpSession session = request.getSession();
		Optional<User> user = userService.getUser(session.getAttribute("account").toString());
		
		// Detail 에서 보여 줄 Book 정보를 가져온다.
		Map<String, Object> bookInfo = apiService.bookSearch(isbn, BookTarget.ISBN.getCode(), 1);

		// Bookmark 표시를 보여 준다.
		Bookmark bookmark = bookmarkService.getBookmark(user.get(), isbn);
		
		result.addObject("book", bookInfo.get("bookData"));
		result.addObject("bookmark", bookmark);
		result.setViewName("jsonView");
		
		return result;
	}
	
	/**
	 * 인기 검색어에 대한 정보를 가져온다.
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/popular")
	public ModelAndView popularKeyword(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView result = new ModelAndView();
		
		List<Object[]> popularData = myHistoryService.findByPopular();
		
		result.addObject("popular", popularData);
		result.setViewName("jsonView");
		
		return result;
	}
}
