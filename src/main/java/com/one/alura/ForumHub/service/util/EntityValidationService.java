package com.one.alura.ForumHub.service.util;

import com.one.alura.ForumHub.entity.Topic;
import com.one.alura.ForumHub.entity.User;
import com.one.alura.ForumHub.exception.ResourceNotFoundException;
import com.one.alura.ForumHub.repository.TopicRepository;
import com.one.alura.ForumHub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EntityValidationService {

    private final UserRepository userRepository;
    private final TopicRepository topicRepository;

    public User validateAndGetUser(UUID userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found with id: " + userId)
        );
    }
    public Topic validateAndGetTopic(UUID topicId) {
        return topicRepository.findById(topicId).orElseThrow(
                () -> new ResourceNotFoundException("Topic not found with id: " + topicId)
        );
    }

}
