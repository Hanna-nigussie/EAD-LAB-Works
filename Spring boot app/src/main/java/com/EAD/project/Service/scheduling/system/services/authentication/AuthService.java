package com.EAD.project.Service.scheduling.system.services.authentication;

import com.EAD.project.Service.scheduling.system.dto.SignupRequestDTO;
import com.EAD.project.Service.scheduling.system.dto.UserDto;

public interface AuthService {
	
	UserDto signupClient(SignupRequestDTO signupRequestDTO);
	
	Boolean presentByEmail(String email);
	
	UserDto signupCompany(SignupRequestDTO signupRequestDTO);

}
