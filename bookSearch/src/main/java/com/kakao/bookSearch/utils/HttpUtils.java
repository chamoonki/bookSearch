package com.kakao.bookSearch.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpUtils {
	
	static HttpClient httpclient = HttpClientBuilder.create().build();
	
	/**
	 * Restfull API 호출
	 * method : GET
	 * 
	 * @param url
	 * @param headers
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String getHttpGetString(String url, Map<String, String> headers, Map<String, Object> params) throws Exception{
		
		String result = null;
		
		try {
			// POST 로 나갈 변수를 설정 해 준다.
			HttpGet httpGet = new HttpGet(url);
			
			// params에 데이터가 있을 경우.
			if(params != null && !params.isEmpty()) {
				// get parameter 값을 생성 해 줍니다.
				String paramString = params.keySet().stream()
							      .map(key -> key + "=" + params.get(key))
							      .collect(Collectors.joining("&", "", ""));
				url += "?" + paramString; 
			}
			System.out.println("@@@@@@@@@@@@ = " + url);
			// Header에 데이터가 있을 경우.
			if (headers != null && !headers.isEmpty()) {
				for (String key : headers.keySet()) {
					httpGet.addHeader(key, headers.get(key));
				}
			}
			
			HttpResponse response = httpclient.execute(httpGet);
			
			result = EntityUtils.toString(response.getEntity(), "UTF-8");
			System.out.println("@@@@@@@@@@@@ = " + result);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Restfull API 호출
	 * method : POST
	 * 
	 * @param url
	 * @param headers
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String getHttpPostString(String url, Map<String, String> headers, Map<String, Object> params) throws Exception{
		String result = null;
		
		try {
			// POST 로 나갈 변수를 설정 해 준다.
			HttpPost httpPost = new HttpPost(url);
			
			// params에 데이터가 있을 경우.
			if(params != null && !params.isEmpty()) {
				List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
				for (String key : params.keySet()) {
					parameters.add(new BasicNameValuePair(key, (String) params.get(key)));
				}

				UrlEncodedFormEntity reqEntity = new UrlEncodedFormEntity(parameters, "UTF-8");
				httpPost.setEntity(reqEntity);
			}
			
			// Header에 데이터가 있을 경우.
			if (headers != null && !headers.isEmpty()) {
				for (String key : headers.keySet()) {
					httpPost.addHeader(key, headers.get(key));
				}
			}
			
			HttpResponse response = httpclient.execute(httpPost);
			
			result = EntityUtils.toString(response.getEntity(), "UTF-8");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
