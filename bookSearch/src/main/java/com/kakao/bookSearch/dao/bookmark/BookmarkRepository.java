package com.kakao.bookSearch.dao.bookmark;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kakao.bookSearch.dao.user.User;

public interface BookmarkRepository extends PagingAndSortingRepository<Bookmark, Long> {
	
	@Query("SELECT bm FROM Bookmark bm WHERE bm.user=?1 and bm.isbn=?2")
	List<Bookmark> findByBookmark(User user, String isbn, Pageable pageable);
	
	// 사용자와 isbn을 이용 하여 설정 한 bookmark를 찾아 준다. 
	// 데이터가 없을 경우 null을 반환 한다.
	default Bookmark findOneBookmark(User user, String isbn) {
		List<Bookmark> boomarks = findByBookmark(user, isbn, PageRequest.of(0, 1));
		
		// 찾는 데이터가 있을 경우.
		if (boomarks != null && boomarks.size() > 0) {
			return boomarks.get(0);
		}
		return null;
	}
}
