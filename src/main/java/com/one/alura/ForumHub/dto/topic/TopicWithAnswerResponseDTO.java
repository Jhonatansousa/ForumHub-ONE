package com.one.alura.ForumHub.dto.topic;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.one.alura.ForumHub.dto.AnswerResponseDTO;
import com.one.alura.ForumHub.entity.TopicStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TopicWithAnswerResponseDTO(
        UUID id,
        String title,
        String message,
        TopicStatus status,
        LocalDateTime createdAt,
        List<AnswerResponseDTO> answers
) {
}
