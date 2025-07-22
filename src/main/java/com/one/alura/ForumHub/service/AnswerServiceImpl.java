package com.one.alura.ForumHub.service;


import com.one.alura.ForumHub.dto.AnswerRequestDTO;
import com.one.alura.ForumHub.entity.Answer;
import com.one.alura.ForumHub.entity.Topic;
import com.one.alura.ForumHub.entity.TopicStatus;
import com.one.alura.ForumHub.entity.User;
import com.one.alura.ForumHub.exception.DuplicatedContentException;
import com.one.alura.ForumHub.exception.ResourceNotFoundException;
import com.one.alura.ForumHub.mapper.AnswerMapper;
import com.one.alura.ForumHub.repository.AnswerRepository;
import com.one.alura.ForumHub.repository.TopicRepository;
import com.one.alura.ForumHub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements IAnswerService {

    private final AnswerRepository answerRepo;
    private final UserRepository userRepo;
    private final TopicRepository topicRepo;
    private final AnswerMapper answerMapper;

    @Override
    public void createAnswer(AnswerRequestDTO request){
        if (answerRepo.existsAnswersByMessageAndSolution(request.message(), request.solution())){
            throw new DuplicatedContentException("Answer message and solution is duplicated");
        }
        User user = userRepo.findById(request.userId()).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.userId()));
        Topic topic = topicRepo.findById(request.topicId()).orElseThrow(() -> new ResourceNotFoundException("Topic not found with id: " + request.topicId()));

        if (topic.getAnswer().isEmpty()) {
            topic.setStatus(TopicStatus.NAO_SOLUCIONADO);
        }

        Answer answer =  answerMapper.toEntity(request, user, topic);
        topic.getAnswer().add(answer);
        answerRepo.save(answer);
    }
}
