package com.one.alura.ForumHub.service;

import com.one.alura.ForumHub.dto.answer.AnswerCreateRequestDTO;
import com.one.alura.ForumHub.dto.answer.AnswerEditRequestDTO;

public interface IAnswerService {
    void createAnswer(AnswerCreateRequestDTO request);
    void editAnswer(AnswerEditRequestDTO request);
}
