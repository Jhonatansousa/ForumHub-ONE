package com.one.alura.ForumHub.service;

import com.one.alura.ForumHub.dto.topic.TopicRequestDTO;
import com.one.alura.ForumHub.dto.topic.TopicResponseDTO;
import com.one.alura.ForumHub.entity.Course;
import com.one.alura.ForumHub.entity.Topic;
import com.one.alura.ForumHub.entity.User;
import com.one.alura.ForumHub.exception.*;
import com.one.alura.ForumHub.mapper.TopicMapper;
import com.one.alura.ForumHub.repository.CourseRepository;
import com.one.alura.ForumHub.repository.TopicRepository;
import com.one.alura.ForumHub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TopicServiceImpl implements ITopicService {

    private final TopicRepository topicRepo;
    private final UserRepository userRepo;
    private final CourseRepository courseRepo;

    private final TopicMapper topicMapper;


    @Override
    public TopicResponseDTO createNewTopic(TopicRequestDTO request){
        if (topicRepo.existsByTitleAndMessage(request.title(), request.message())){
            throw new DuplicatedContentException("Topic title and message is duplicated");
        }
        User user = userRepo.findById(request.authorId()).orElseThrow(() -> new ResourceNotFoundException("User Not Found with id: " + request.authorId()));
        Course course = courseRepo.findByNameIgnoreCase(request.courseName());
        if (course == null) {
            throw new ResourceNotFoundException("Course Not Found");
        }

        Topic newTopic = topicRepo.save(topicMapper.toEntity(request, user, course));


        return topicMapper.toResponseDTO(newTopic);
    }

}
