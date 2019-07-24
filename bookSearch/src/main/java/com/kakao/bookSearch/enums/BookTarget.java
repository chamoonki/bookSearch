package com.kakao.bookSearch.enums;

/**
 * Book Search 를 위한 Target Enum을 만들어 준다.
 * 
 * @author moonki
 *
 */
public enum BookTarget {
	전체("전체", "all"), 제목("제목", "title"), ISBN("ISBN", "isbn"), 주제어("주제어", "keyword"), 목차("목차", "contents"), 책소개("책소개",
			"overview"), 출판사("출판사", "publisher");
	
	private String title;
	private String code;
	
	private BookTarget(String title, String code) {
		this.title = title;
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public String getCode() {
		return code;
	}

	public static BookTarget getByCode(String code) {
		BookTarget result = null;

		for(BookTarget temp : BookTarget.values()) {
			if (temp.getCode().equals(code)) {
				result = temp;
				break;
			}
		}

		return result;
	}
}
