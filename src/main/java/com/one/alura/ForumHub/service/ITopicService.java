package com.one.alura.ForumHub.service;

import com.one.alura.ForumHub.dto.topic.TopicRequestDTO;
import com.one.alura.ForumHub.dto.topic.TopicResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITopicService {
    TopicResponseDTO createNewTopic(TopicRequestDTO request);
    Page<TopicResponseDTO> getAllTopics(Pageable pageable);
}
