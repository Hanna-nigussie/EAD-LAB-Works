package com.EAD.project.Service.scheduling.system.entity;

import com.EAD.project.Service.scheduling.system.dto.UserDto;
import com.EAD.project.Service.scheduling.system.enums.UserRole;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String email;
	
	private String password;
	
	private String name;
	
	private String lastname;
	
	private String phone;
	
	private UserRole role;
	
	public UserDto getDto() {
		UserDto userDto = new UserDto();
		
		userDto.setId(id);
		userDto.setName(name);
		userDto.setEmail(email);
		userDto.setRole(role);
		
		return userDto;
	}

}
