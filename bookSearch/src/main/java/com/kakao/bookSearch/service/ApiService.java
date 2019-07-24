package com.kakao.bookSearch.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kakao.bookSearch.utils.HttpUtils;
import com.kakao.bookSearch.utils.PropertiesConfig;

@Service
public class ApiService {
	private static final Logger logger = LoggerFactory.getLogger(ApiService.class);
	
	// application properties에서 값을 가져온다.
	@Autowired 
	private PropertiesConfig properties;  
	
	
	
	public Map<String, Object> bookSearch(String keyword, String target, int page){
		// Header 관련 Map
		Map<String, String> headers = new HashMap<String, String>();
		// Body 관련 Map
		Map<String, Object> params = new HashMap<String, Object>();
		// Result Map을 만들어 준다.
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String jsonString = null;
		String url = properties.getAppUri() + "?target=" + target + "&target=" + target + "&page=" + page + "&size=" + 12;
		
		// Header에 값을 세팅 해 준다.
		headers.put("Authorization", "KakaoAK " + properties.getAppKey());
		// Body에 값을 세팅 해 준다.
		params.put("query", keyword);
		
		
		// API를 통해 정보를 가져온다.
		try {
			// API를 호출 하여 데이터를 가져온다.
			jsonString = HttpUtils.getHttpPostString(url, headers, params);
			resultMap.put("bookData", jsonString);
		}catch(Exception e) {
			logger.error("[ " + url + "] API Error" + jsonString);
			e.printStackTrace();
		}
		
		return resultMap;
	}
}
