package com.backend.triba.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class CommentDTO {
	private UUID jobId;
	private UUID userId;
	private String content;
	public LocalDateTime createdAt;
	private String username;
	private String avatar;
}
