package com.kakao.bookSearch.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kakao.bookSearch.service.LoginService;

/**
 * 로그인을 위한 Action에 대한 Controller
 * 
 * @author moonki
 *
 */
@Controller
public class LoginController {
	private static Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	LoginService loginService;
	
	/**
	 * 로그인을 시도 하는 Action Method
	 * 
	 * @param req
	 * @param res
	 * @param model
	 * @param account
	 * @param pwd
	 * @return
	 */
	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(name = "account", defaultValue = "") String account,
			@RequestParam(name = "passwd", defaultValue = "") String passwd) {
		
		ModelAndView result = new ModelAndView();
		
		// account & password에 값이 모두 들어 있다면 if문을 들어간다.
		if(!account.isEmpty() && !passwd.isEmpty()) {
			logger.info("### [ID], [PASSWORD] => [" + account + "], [" + passwd + "]");
			
			// Account 를 이용하여 로그인을 시도 한다.	
			if(loginService.login(response, account, passwd)) {
				logger.info("#### [Account Result Success]");
				HttpSession session = request.getSession(true);
				// session에 account를 저장 시켜 준다.
				session.setAttribute("account", account);
				
				result.setViewName("redirect:/");
				
				return result;
			}else {	// 데이터가 없을 경우
				result.addObject("alertShow","show");
				result.addObject("alertType","danger");
				result.addObject("resultMessage","Account information is incorrect.<strong>ID </strong>, please check <strong>PASSWD</strong> again.");
			}
		}
		
		result.setViewName("login/login");
		
		return result;
	}
	
	@RequestMapping("/login/membership")
	public ModelAndView enterMembership(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam(name = "account", defaultValue = "") String account,
			@RequestParam(name = "passwd", defaultValue = "") String passwd) {
		
		ModelAndView result = new ModelAndView();
		
		// 가져온 데이터가 비어 있는지 확인 한다.
		if(!account.isEmpty() && !passwd.isEmpty()) {
			logger.info("### [ID], [PASSWORD] => [" + account + "], [" + passwd + "]");
			
			if(loginService.membership(response, account, passwd)) {
				result.addObject("alertShow","show");
				result.addObject("alertType","success");
				result.addObject("resultMessage","Sign up for membership, please try Login.");
			}else {
				result.addObject("alertShow","show");
				result.addObject("alertType","danger");
				result.addObject("resultMessage","Failed to join membership. Please try again.");
			}
		}
		
		result.addObject("eee", "eeeee");
		result.setViewName("/login/login");
		
		return result;
	}
	
	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView result = new ModelAndView();
		
		result.setViewName(loginService.logout(request));
		
		return result;
	}
}
