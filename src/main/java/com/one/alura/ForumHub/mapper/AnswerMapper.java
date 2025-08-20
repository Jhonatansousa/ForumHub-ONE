package com.one.alura.ForumHub.mapper;

import com.one.alura.ForumHub.dto.answer.AnswerCreateRequestDTO;
import com.one.alura.ForumHub.entity.Answer;
import com.one.alura.ForumHub.entity.Topic;
import com.one.alura.ForumHub.entity.User;
import org.springframework.stereotype.Component;

@Component
public class AnswerMapper {

    public Answer toEntity(AnswerCreateRequestDTO request, User user, Topic topic) {
        Answer answer = new Answer();
        answer.setMessage(request.message());
        answer.setSolution(request.solution());
        answer.setAutor(user);
        answer.setTopic(topic);
        return answer;
    }

}
