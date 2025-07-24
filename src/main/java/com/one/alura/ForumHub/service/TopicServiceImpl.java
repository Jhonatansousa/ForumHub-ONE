package com.one.alura.ForumHub.service;

import com.one.alura.ForumHub.dto.topic.TopicRequestDTO;
import com.one.alura.ForumHub.dto.topic.TopicResponseDTO;
import com.one.alura.ForumHub.dto.topic.TopicWithAnswerResponseDTO;
import com.one.alura.ForumHub.entity.Course;
import com.one.alura.ForumHub.entity.Topic;
import com.one.alura.ForumHub.entity.User;
import com.one.alura.ForumHub.exception.*;
import com.one.alura.ForumHub.mapper.TopicMapper;
import com.one.alura.ForumHub.repository.CourseRepository;
import com.one.alura.ForumHub.repository.TopicRepository;
import com.one.alura.ForumHub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TopicServiceImpl implements ITopicService {

    private final TopicRepository topicRepo;
    private final UserRepository userRepo;
    private final CourseRepository courseRepo;
    private final TopicMapper topicMapper;

    @Override
    @Transactional
    public TopicWithAnswerResponseDTO createNewTopic(TopicRequestDTO request){
        if (topicRepo.existsByTitleAndMessage(request.title(), request.message())){
            throw new DuplicatedContentException("Topic title and message is duplicated");
        }
        User user = userRepo.findById(request.authorId()).orElseThrow(() -> new ResourceNotFoundException("User Not Found with id: " + request.authorId()));
        Course course = courseRepo.findByNameIgnoreCase(request.courseName());
        if (course == null) {
            throw new ResourceNotFoundException("Course Not Found");
        }
        Topic newTopic = topicRepo.save(topicMapper.toEntity(request, user, course));
        return topicMapper.toResponseWithAnswerDTO(newTopic);
    }

    @Override
    public Page<TopicWithAnswerResponseDTO> getAllTopicsWithAnswers(Pageable pageable) {
        Page<Topic> topicsPage = topicRepo.findAllWithAnswers(pageable);
        return topicsPage.map(topicMapper::toResponseWithAnswerDTO);
    }

    @Override
    public Page<TopicResponseDTO> getAllTopics(Pageable pageable) {
        Page<Topic> topicsPage = topicRepo.findAll(pageable);
        return topicsPage.map(topicMapper::toResponseDTO);
    }

}
