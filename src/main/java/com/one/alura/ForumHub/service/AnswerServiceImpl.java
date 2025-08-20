package com.one.alura.ForumHub.service;


import com.one.alura.ForumHub.dto.answer.AnswerCreateRequestDTO;
import com.one.alura.ForumHub.dto.answer.AnswerEditRequestDTO;
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
import com.one.alura.ForumHub.service.util.EntityValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements IAnswerService {

    private final AnswerRepository answerRepo;
    private final UserRepository userRepo;
    private final TopicRepository topicRepo;
    private final AnswerMapper answerMapper;

    private final EntityValidationService entityValidationService;

    @Override
    public void createAnswer(AnswerCreateRequestDTO request){
        if (answerRepo.existsAnswersByMessageAndSolution(request.message(), request.solution())){
            throw new DuplicatedContentException("Answer message and solution is duplicated");
        }
        User user = entityValidationService.validateAndGetUser(request.userId());
        Topic topic =  entityValidationService.validateAndGetTopic(request.topicId());

        if (topic.getAnswer().isEmpty()) {
            topic.setStatus(TopicStatus.NAO_SOLUCIONADO);
        }

        Answer answer =  answerMapper.toEntity(request, user, topic);
        topic.getAnswer().add(answer);
        answerRepo.save(answer);
    }

    @Override
    public void editAnswer(AnswerEditRequestDTO request) {
        //refatorar depois (duplicado com o metodo create)
        if (answerRepo.existsAnswersByMessageAndSolution(request.message(), request.solution())) {
            throw new DuplicatedContentException("Answer message and solution is equal to its previous answer");
        }

        Answer answer = answerRepo.findById(request.answerId()).orElseThrow(
                () -> new ResourceNotFoundException("Answer not found with id: " + request.answerId())
        );

        answer.setSolution(request.solution());
        answerRepo.save(answer);
    }


}
