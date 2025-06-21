package com.gabrielfigueiredol.seaApi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ErrorResponseDTO {
    private String message;
    private String description;
    private LocalDateTime timestamp;
}
