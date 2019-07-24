package com.kakao.bookSearch.dao.history;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kakao.bookSearch.dao.user.User;

public interface HistoryRepository extends JpaRepository<History, Long>{

	/*
	 * @Query("SELECT history FROM History history WHERE history.user=?1")
	 * Page<History> findByMember(User user, Pageable pageable);
	 */
}
