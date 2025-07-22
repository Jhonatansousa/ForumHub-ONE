package com.one.alura.ForumHub.dto.topic;

import com.one.alura.ForumHub.dto.AnswerResponseDTO;
import com.one.alura.ForumHub.entity.TopicStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record TopicResponseDTO(
        UUID id,
        String title,
        String message,
        TopicStatus status,
        LocalDateTime createdAt,
        List<AnswerResponseDTO> answers
) {
}
