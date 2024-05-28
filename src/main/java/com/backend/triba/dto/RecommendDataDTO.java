package com.backend.triba.dto;

import java.util.List;

import com.backend.triba.entities.Job;
import com.backend.triba.entities.Search;

import lombok.Data;

@Data
public class RecommendDataDTO {
	private Search personalData;
	private List<Job> listJob;
}
