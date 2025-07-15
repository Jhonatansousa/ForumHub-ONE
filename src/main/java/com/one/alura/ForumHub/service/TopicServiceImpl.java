package com.one.alura.ForumHub.service;

import com.one.alura.ForumHub.dto.topic.TopicRequestDTO;
import com.one.alura.ForumHub.dto.topic.TopicResponseDTO;
import com.one.alura.ForumHub.entity.Course;
import com.one.alura.ForumHub.entity.Topic;
import com.one.alura.ForumHub.entity.TopicStatus;
import com.one.alura.ForumHub.entity.User;
import com.one.alura.ForumHub.exception.CourseNotFoundException;
import com.one.alura.ForumHub.exception.TopicContentAlreadyExistsException;
import com.one.alura.ForumHub.exception.UserNotFoundException;
import com.one.alura.ForumHub.repository.CourseRepository;
import com.one.alura.ForumHub.repository.TopicRepository;
import com.one.alura.ForumHub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TopicServiceImpl implements ITopicService {

    private final TopicRepository topicRepo;
    private final UserRepository userRepo;
    private final CourseRepository courseRepo;


    @Override
    public TopicResponseDTO createNewTopic(TopicRequestDTO request){
        if (topicRepo.existsByTitleAndMessage(request.title(), request.message())){
            throw new TopicContentAlreadyExistsException("Topic title and message is duplicated");
        }
        User user = userRepo.findById(request.authorId()).orElseThrow(() -> new UserNotFoundException("User Not Found with id: " + request.authorId()));
        Course course = courseRepo.findByNameIgnoreCase(request.courseName());
        if (course == null) {
            throw new CourseNotFoundException("Course Not Found");
        }

        Topic topic = new Topic();
        topic.setTitle(request.title());
        topic.setMessage(request.message());
        topic.setAutor(user);
        topic.setCourse(course);
        topic.setStatus(TopicStatus.NAO_RESPONDIDO);
        Topic newTopic = topicRepo.save(topic);


        return new TopicResponseDTO(
                newTopic.getTitle(),
                newTopic.getMessage(),
                newTopic.getStatus(),
                newTopic.getCreatedAt()
        );
    }

}
