package com.kakao.bookSearch.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kakao.bookSearch.dao.history.History;
import com.kakao.bookSearch.dao.history.HistoryRepository;

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
	 */
	@Transactional
	public void save(History entity) {
		System.out.println("@!@!@!@!@!@ = ");
		historyRepository.save(entity);
	}
}
