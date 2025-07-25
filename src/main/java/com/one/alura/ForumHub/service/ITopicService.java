package com.one.alura.ForumHub.service;

import com.one.alura.ForumHub.dto.topic.TopicRequestDTO;
import com.one.alura.ForumHub.dto.topic.TopicResponseDTO;
import com.one.alura.ForumHub.dto.topic.TopicWithAnswerResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITopicService {
    TopicWithAnswerResponseDTO createNewTopic(TopicRequestDTO request);
    Page<TopicWithAnswerResponseDTO> getAllTopicsWithAnswers(Pageable pageable);
    Page<TopicResponseDTO> getAllTopics(Pageable pageable);
}
