package com.one.alura.ForumHub.service;

import com.one.alura.ForumHub.dto.topic.TopicRequestDTO;
import com.one.alura.ForumHub.dto.topic.TopicResponseDTO;

public interface ITopicService {
    TopicResponseDTO createNewTopic(TopicRequestDTO request);
}
