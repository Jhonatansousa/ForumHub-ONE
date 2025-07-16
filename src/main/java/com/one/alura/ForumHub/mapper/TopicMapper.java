package com.one.alura.ForumHub.mapper;

import com.one.alura.ForumHub.dto.topic.TopicRequestDTO;
import com.one.alura.ForumHub.dto.topic.TopicResponseDTO;
import com.one.alura.ForumHub.entity.Course;
import com.one.alura.ForumHub.entity.Topic;
import com.one.alura.ForumHub.entity.TopicStatus;
import com.one.alura.ForumHub.entity.User;
import org.springframework.stereotype.Component;

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
        return new TopicResponseDTO(
                topic.getTitle(),
                topic.getMessage(),
                topic.getStatus(),
                topic.getCreatedAt()
        );
    }
}
