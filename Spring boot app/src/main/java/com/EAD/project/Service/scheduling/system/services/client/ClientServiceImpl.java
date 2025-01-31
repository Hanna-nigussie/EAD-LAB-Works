package com.EAD.project.Service.scheduling.system.services.client;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EAD.project.Service.scheduling.system.dto.AdDTO;
import com.EAD.project.Service.scheduling.system.dto.AdDetailsForClientDTO;
import com.EAD.project.Service.scheduling.system.dto.ReservationDTO;
import com.EAD.project.Service.scheduling.system.dto.ReviewDTO;
import com.EAD.project.Service.scheduling.system.entity.Ad;
import com.EAD.project.Service.scheduling.system.entity.Reservation;
import com.EAD.project.Service.scheduling.system.entity.Review;
import com.EAD.project.Service.scheduling.system.entity.User;
import com.EAD.project.Service.scheduling.system.enums.ReservationStatus;
import com.EAD.project.Service.scheduling.system.enums.ReviewStatus;
import com.EAD.project.Service.scheduling.system.repository.AdRepository;
import com.EAD.project.Service.scheduling.system.repository.ReservationRepository;
import com.EAD.project.Service.scheduling.system.repository.ReviewRepository;
import com.EAD.project.Service.scheduling.system.repository.UserRepository;

@Service
public class ClientServiceImpl implements ClientService {
	
	Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);
	
	@Autowired
	private AdRepository adRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	
	
	@Override
	public List<AdDTO> getAllAds() {
		return adRepository.findAll().stream().map(Ad::getAdDto).collect(Collectors.toList());
	}
	
	@Override
	public List<AdDTO> searchAdByName(String name) {
		return adRepository.findAllByServiceNameContaining(name).stream().map(Ad::getAdDto).collect(Collectors.toList());
	}
	
	@Override
	public boolean bookService(ReservationDTO reservationDTO) {
		Optional<Ad> optionalAd = adRepository.findById(reservationDTO.getAdId());
		Optional<User> optionalUser = userRepository.findById(reservationDTO.getUserId());
		
		if(optionalAd.isPresent() && optionalUser.isPresent()) {
			Reservation reservation = new Reservation();
			
			reservation.setBookDate(reservationDTO.getBookDate());
			reservation.setReservationStatus(ReservationStatus.PENDING);
			reservation.setUser(optionalUser.get());
			
			reservation.setAd(optionalAd.get());
			reservation.setCompany(optionalAd.get().getUser());
			reservation.setReviewStatus(ReviewStatus.FALSE);
			
			reservationRepository.save(reservation);
			return true;
		}
		
		return false;
 	}
	
	@Override
	public AdDetailsForClientDTO getAdDetailsById(Long adId) {
		Optional<Ad> optionalAd = adRepository.findById(adId);
		AdDetailsForClientDTO adDetailsForClientDTO = new AdDetailsForClientDTO();
		if(optionalAd.isPresent()) {
			adDetailsForClientDTO.setAdDTO(optionalAd.get().getAdDto());
			
			List<Review> reviewList = reviewRepository.findAllByAdId(adId);
			adDetailsForClientDTO.setReviewDTOList(reviewList.stream().map(Review::getDto).collect(Collectors.toList()));
		}
		return adDetailsForClientDTO;
	}
	
	@Override
	public List<ReservationDTO> getAllBookingsByUserId(Long userId) {
		return reservationRepository.findAllByUserId(userId)
				.stream().map(Reservation::getReservationDto).collect(Collectors.toList());
	}
	
	@Override
	public boolean giveReview(ReviewDTO reviewDTO) {
		Optional<User> optionalUser = userRepository.findById(reviewDTO.getUserId());
		Optional<Reservation> optionalBooking = reservationRepository.findById(reviewDTO.getBookId());
		
		if(optionalUser.isPresent() && optionalBooking.isPresent()) {
			Review review = new Review();
			
			review.setReviewDate(new Date());
			review.setReview(reviewDTO.getReview());
			review.setRating(reviewDTO.getRating());
			
			review.setUser(optionalUser.get());
			review.setAd(optionalBooking.get().getAd());
			
			reviewRepository.save(review);
			
			Reservation booking = optionalBooking.get();
			booking.setReviewStatus(ReviewStatus.TRUE);
			
			reservationRepository.save(booking);
			
 			return true;
		}
		return false;
	}

}
