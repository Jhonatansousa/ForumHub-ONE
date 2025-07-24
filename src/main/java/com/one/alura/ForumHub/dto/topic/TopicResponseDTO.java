package com.one.alura.ForumHub.dto.topic;

import com.one.alura.ForumHub.entity.TopicStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TopicResponseDTO(
        UUID id,
        String title,
        String message,
        TopicStatus status,
        LocalDateTime createdAt
) {
}
