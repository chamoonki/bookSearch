package com.kakao.bookSearch.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kakao.bookSearch.dao.user.User;
import com.kakao.bookSearch.dao.user.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Transactional
	public Optional<User> getUser(String account) {	
		return userRepository.findById(account);
	}

	@Transactional
	public void save(User user) {
		userRepository.save(user);
	}
}
