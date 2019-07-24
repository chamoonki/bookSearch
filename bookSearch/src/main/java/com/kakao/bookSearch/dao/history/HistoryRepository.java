package com.kakao.bookSearch.dao.history;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kakao.bookSearch.dao.bookmark.Bookmark;
import com.kakao.bookSearch.dao.user.User;

public interface HistoryRepository extends JpaRepository<History, Long>{

	// history Data에 대한 List를 가져온다.
	// Page 형식으로
	@Query("SELECT history FROM History history WHERE history.user=?1")
	Page<History> findByHisory(User user, Pageable pageable);
	
	// Popular Keyword List를 가져온다 총 10개만
	@Query(value = "SELECT keyword, COUNT(keyword) AS COUNT FROM History  GROUP BY keyword ORDER BY COUNT(keyword) DESC, keyword LIMIT 10", nativeQuery = true)  
	List<Object[]> findPopular();	
}
