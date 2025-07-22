package com.one.alura.ForumHub.mapper;

import com.one.alura.ForumHub.dto.AnswerResponseDTO;
import com.one.alura.ForumHub.dto.topic.TopicRequestDTO;
import com.one.alura.ForumHub.dto.topic.TopicResponseDTO;
import com.one.alura.ForumHub.entity.*;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TopicMapper {

    public Topic toEntity(TopicRequestDTO request, User user, Course course){
        Topic topic = new Topic();
        topic.setTitle(request.title());
        topic.setMessage(request.message());
        topic.setAutor(user);
        topic.setCourse(course);
        topic.setStatus(TopicStatus.NAO_RESPONDIDO);
        return topic;
    }

    public TopicResponseDTO toResponseDTO(Topic topic){
        List<AnswerResponseDTO> answersDTOs = (topic.getAnswer() != null)
                ? topic.getAnswer().stream()
                            .map(this::answerToResponseDTO)
                            .toList()
                : Collections.emptyList();

        return new TopicResponseDTO(
                topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getStatus(),
                topic.getCreatedAt(),
                answersDTOs
        );
    }

    private AnswerResponseDTO answerToResponseDTO(Answer answer){
        return new AnswerResponseDTO(
                answer.getId(),
                answer.getMessage(),
                answer.getSolution(),
                answer.getAutor().getName(),
                answer.getCreatedAt()
        );
    }
}
