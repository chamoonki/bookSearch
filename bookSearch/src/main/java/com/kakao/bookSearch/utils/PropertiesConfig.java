package com.kakao.bookSearch.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * application properties를 사용하기 위한 
 * Config
 * @author moonki
 *
 */
@Component
public class PropertiesConfig {
	@Value("${kakao.api.key}")
    private String appKey;
	
	@Value("${kakao.api.uri}")
	private String appUri;

    public String getAppUri() {
		return appUri;
	}

	public void setAppUri(String appUri) {
		this.appUri = appUri;
	}

	public String getAppKey() {
        return appKey;
    }

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
}
