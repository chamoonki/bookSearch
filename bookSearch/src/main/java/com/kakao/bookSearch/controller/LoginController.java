package com.kakao.bookSearch.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 로그인을 위한 Action에 대한 Controller
 * 
 * @author moonki
 *
 */
@Controller
public class LoginController {
	private static Logger logger = LoggerFactory.getLogger(LoginController.class);
	
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
	public String loginForm(HttpServletRequest req, HttpServletResponse res, Model model,
			@RequestParam(name = "account", defaultValue = "") String account,
			@RequestParam(name = "pwd", defaultValue = "") String pwd) {
		
		// account & password에 값이 모두 들어 있다면 if문을 들어간다.
		if(!account.isEmpty() && !pwd.isEmpty()) {
			logger.info("### [ID], [PASSWORD] => [" + account + "], [" + pwd + "]");
			
			model.addAttribute("alertShow", "show");
			model.addAttribute("resultMessage", "일치하지 않습니다.");
		}
		
		return "login/login";
	}

}
