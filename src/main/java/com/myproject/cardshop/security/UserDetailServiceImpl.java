package com.myproject.cardshop.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.myproject.cardshop.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
	
	private final UserRepository userRepository;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
		return userRepository.findByEmail(userEmail)
				.orElseThrow(() -> new UsernameNotFoundException("查無此帳號" + userEmail));
	}

}
