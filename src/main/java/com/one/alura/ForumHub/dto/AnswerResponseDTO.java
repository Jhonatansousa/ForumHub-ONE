package com.one.alura.ForumHub.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record AnswerResponseDTO(
        UUID id,
        String message,
        String autorName,
        String solution,
        LocalDateTime createdAt
) {
}
