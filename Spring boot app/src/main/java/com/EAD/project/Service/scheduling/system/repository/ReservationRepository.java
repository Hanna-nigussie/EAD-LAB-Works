package com.EAD.project.Service.scheduling.system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EAD.project.Service.scheduling.system.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	
	List<Reservation> findAllByCompanyId(Long companyId);
	
	List<Reservation> findAllByUserId(Long userId);

}
