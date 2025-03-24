package com.kobi.memo.user.service;

import org.springframework.stereotype.Service;

import com.kobi.memo.common.MD5HashingEncoder;
import com.kobi.memo.user.domain.User;
import com.kobi.memo.user.repository.UserRepository;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	
	// 다른 생성자가 없이 autowired를 위한 생성자가 있는 경우는 @Autowired 생략가능
//	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public boolean addUser(
			String loginId
			, String password
			, String name
			, String email) {
		
		String encyptPassword = MD5HashingEncoder.encode(password);
		
		int count = userRepository.insertUser(loginId, encyptPassword, name, email);
		
		if(count == 1) {
			return true;
		} else {
			return false;
		}
	}
	
		public User getUser(String loginId, String password) {
		
			String encryptPassword = MD5HashingEncoder.encode(password);
			
			return userRepository.selectUser(loginId, encryptPassword);
		
	}
	
}
