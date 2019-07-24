package com.kakao.bookSearch.enums;

/**
 * Book Search 를 위한 Target Enum을 만들어 준다.
 * 
 * @author moonki
 *
 */
public enum BookTarget {
	All("All", "all"), 
	Title("Title", "title"), 
	ISBN("ISBN", "isbn"), 
	Keyword("Keyword", "keyword"), 
	Contents("Contents", "contents"), 
	Overview("Overview", "overview"), 
	Publisher("Publisher", "publisher");
	
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
