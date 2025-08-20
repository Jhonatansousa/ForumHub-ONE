package com.one.alura.ForumHub.dto.answer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AnswerEditRequestDTO(
        @NotBlank String message,
        @NotBlank String solution,
        @NotNull UUID userId,
        @NotNull UUID topicId,
        @NotNull UUID answerId
) {
}
