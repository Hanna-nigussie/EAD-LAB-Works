package com.EAD.project.Service.scheduling.system.dto;

import java.sql.Date;

import com.EAD.project.Service.scheduling.system.enums.ReservationStatus;
import com.EAD.project.Service.scheduling.system.enums.ReviewStatus;

import lombok.Data;

@Data
public class ReservationDTO {
	
	private Long id;
	
	private Date bookDate;
	
	private String serviceName;
	
	private ReservationStatus reservationStatus;
	
	private ReviewStatus reviewStatus;
	
	private Long userId;
	
	private String userName;
	
	private Long companyId;
	
	private Long adId;

}
