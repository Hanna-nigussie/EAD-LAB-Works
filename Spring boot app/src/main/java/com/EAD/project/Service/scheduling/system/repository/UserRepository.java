package com.EAD.project.Service.scheduling.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.EAD.project.Service.scheduling.system.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	User findFirstByEmail(String email);

}
