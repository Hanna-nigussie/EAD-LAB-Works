package com.EAD.project.Service.scheduling.system.services.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.EAD.project.Service.scheduling.system.dto.SignupRequestDTO;
import com.EAD.project.Service.scheduling.system.dto.UserDto;
import com.EAD.project.Service.scheduling.system.entity.User;
import com.EAD.project.Service.scheduling.system.enums.UserRole;
import com.EAD.project.Service.scheduling.system.repository.UserRepository;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private UserRepository userRepository;
	
	public UserDto signupClient(SignupRequestDTO signupRequestDTO) {
		
		User user = new User();
		
		user.setName(signupRequestDTO.getName());
		user.setLastname(signupRequestDTO.getLastname());
		user.setEmail(signupRequestDTO.getEmail());
		user.setPhone(signupRequestDTO.getPhone());
		user.setPassword(new BCryptPasswordEncoder().encode(signupRequestDTO.getPassword()));
		
		user.setRole(UserRole.CLIENT);
		
		return userRepository.save(user).getDto();
	}
	
	public Boolean presentByEmail(String email) {
		return userRepository.findFirstByEmail(email) != null;
	}
	
	public UserDto signupCompany(SignupRequestDTO signupRequestDTO) {
		
		User user = new User();
		
		user.setName(signupRequestDTO.getName());
		user.setLastname(signupRequestDTO.getLastname());
		user.setEmail(signupRequestDTO.getEmail());
		user.setPhone(signupRequestDTO.getPhone());
		user.setPassword(new BCryptPasswordEncoder().encode(signupRequestDTO.getPassword()));
		
		user.setRole(UserRole.COMPANY);
		
		return userRepository.save(user).getDto();
	}
}
