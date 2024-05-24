package com.backend.triba.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDTO {

    private String to;
    private String subject;
    private String text;

    // Getters và setters
}