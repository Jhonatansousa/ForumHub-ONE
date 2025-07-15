package com.one.alura.ForumHub.dto.topic;

import com.one.alura.ForumHub.entity.TopicStatus;

import java.time.LocalDateTime;

public record TopicResponseDTO(
        String title,
        String message,
        TopicStatus status,
        LocalDateTime createdAt
) {
}
