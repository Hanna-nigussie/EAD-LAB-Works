package com.EAD.project.Service.scheduling.system.services.client;

import java.util.List;

import com.EAD.project.Service.scheduling.system.dto.AdDTO;
import com.EAD.project.Service.scheduling.system.dto.AdDetailsForClientDTO;
import com.EAD.project.Service.scheduling.system.dto.ReservationDTO;
import com.EAD.project.Service.scheduling.system.dto.ReviewDTO;

public interface ClientService {

	List<AdDTO> getAllAds();

	List<AdDTO> searchAdByName(String name);

	boolean bookService(ReservationDTO reservationDTO);

	AdDetailsForClientDTO getAdDetailsById(Long adId);

	List<ReservationDTO> getAllBookingsByUserId(Long userId);

	boolean giveReview(ReviewDTO reviewDTO);
	
}
