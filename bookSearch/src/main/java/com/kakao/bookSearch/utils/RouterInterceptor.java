package com.kakao.bookSearch.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kakao.bookSearch.controller.BooksController;

/**
 * login 여부를 확인 하기 위한 router interceptor
 * @author moonki
 *
 */
@Component
public class RouterInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LoggerFactory.getLogger(RouterInterceptor.class);
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		logger.info("Interceptor > preHandle");
		
		// 현재 로그인 한 사용자에 대한 session을 가져온다. 
		// 만약 세션이 없다면 처음 들어왔다.
		HttpSession session = request.getSession(false);

		// session 의 값이 있을 경우
		//if(request.getSession(false) != null && session.getAttribute("account") != null) {			
		if(request.getSession(false) != null && session.getAttribute("account") != null) {
			System.out.println("@@@@@@@@@@@ = session 확인 + " + session.getAttribute("account"));				
			// Account를 확인 한다.
			//return true;
			
		}else {
			System.out.println("@@@@@@@@@@@ = session 없음 ");			
			// login Page로 돌려 준다.
			response.sendRedirect("/login");
			
			return false;
		}
		
		return true;
    }

	
	  @Override 
	  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		  logger.info("Interceptor > postHandle");
	  }
	  
	  @Override public void afterCompletion(HttpServletRequest request,
	  HttpServletResponse response, Object object, Exception arg3) throws Exception { 
		  logger.info("Interceptor > afterCompletion");
	  }
	 
}
