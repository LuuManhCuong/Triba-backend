package com.backend.triba.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import lombok.Data;
@Data
public class SearchDTO {

	private UUID userId;
	private String keySearch;
	private String industries;
	private String positions;
	private String locations;
	private String workTypes;
	private LocalDate createAt;
}
