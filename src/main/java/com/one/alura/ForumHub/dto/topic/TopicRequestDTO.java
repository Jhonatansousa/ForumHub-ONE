package com.one.alura.ForumHub.dto.topic;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;


public record TopicRequestDTO(
        @NotBlank String title,
        @NotBlank String message,
        @NotNull UUID authorId,
        @NotBlank String courseName
        ) {
}
