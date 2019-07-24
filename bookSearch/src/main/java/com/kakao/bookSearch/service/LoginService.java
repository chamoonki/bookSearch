package com.kakao.bookSearch.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kakao.bookSearch.dao.user.User;
import com.kakao.bookSearch.utils.CryptoEncoding;

/**
 * 로그인 관련 작업을 하기 위한 service
 * @author moonki
 *
 */
@Service
public class LoginService {
	private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
	
	@Autowired
	UserService userService;
	
	public boolean login(HttpServletResponse response, String account, String passwd) {
		PasswordEncoder passwordEncoder = new CryptoEncoding();
		
		// account & password가 있다면 들어 간다.
		if(!account.isEmpty() && !passwd.isEmpty()) {
			// user DB에서 해당 계정을 가져온다.
			Optional<User> user = userService.getUser(account);
			
			// account 데이터가 있을 경우.
			if(user.isPresent()) {
				System.out.println("@@@@@@@@@@@@ = " + user.toString());
				
				// DB에서 가져온 Password 와 입력한 Password가 같을 때 들어와 준다.
				if (passwordEncoder.matches(passwd, user.get().getPasswd())) {					
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean membership(HttpServletResponse response, String account, String passwd) {
		PasswordEncoder passwordEncoder = new CryptoEncoding();
		
		User user = new User(account, passwordEncoder.encode(passwd), Timestamp.valueOf(LocalDateTime.now()));
		
		userService.save(user);
		
		return true;
	}
	
	public String logout(HttpServletRequest request) {
		request.getSession().removeAttribute("account");
		
		return "redirect:/login";
	}
}
