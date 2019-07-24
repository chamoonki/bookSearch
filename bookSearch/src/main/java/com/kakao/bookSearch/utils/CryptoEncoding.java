package com.kakao.bookSearch.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CryptoEncoding implements PasswordEncoder{
	private PasswordEncoder passwordEncoder;
	
	public CryptoEncoding() {
		this.passwordEncoder = new BCryptPasswordEncoder();
	}

	public CryptoEncoding(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public String encode(CharSequence rawPassword) {
		//
		return passwordEncoder.encode(rawPassword);
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}
	
}
