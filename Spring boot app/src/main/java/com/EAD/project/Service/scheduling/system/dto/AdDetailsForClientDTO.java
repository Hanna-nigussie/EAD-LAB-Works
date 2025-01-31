package com.EAD.project.Service.scheduling.system.dto;

import java.util.List;

import lombok.Data;

@Data
public class AdDetailsForClientDTO {
	
	private AdDTO adDTO;
	
	private List<ReviewDTO> reviewDTOList;

}
