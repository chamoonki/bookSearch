package com.kakao.bookSearch.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kakao.bookSearch.dao.history.History;
import com.kakao.bookSearch.dao.history.HistoryRepository;
import com.kakao.bookSearch.dao.user.User;

/**
 * 내가 검색한 History를 저장 하는 Class
 * @author moonki
 *
 */
@Service
public class MyHistoryService {
	
	@Autowired
	HistoryRepository historyRepository;
	
	/**
	 * 검색 한 정보를 저장 한다.
	 * 
	 * @param entity
	 */
	@Transactional
	public void save(History entity) {
		historyRepository.save(entity);
	}
	
	/**
	 * 현재까지 검색한 정보를 가져온다.
	 * 
	 * @param member
	 * @param pageable
	 * @return
	 */
	//@Transactional
	public Page<History> findByHisory(User user, Pageable pageable) {
		//
		return historyRepository.findByHisory(user, pageable);
	}
	
	@Transactional
	public List<Object[]> findByPopular(){
		//return new ArrayList<Map<String, Object>>();
		return  historyRepository.findPopular();
	}
}
