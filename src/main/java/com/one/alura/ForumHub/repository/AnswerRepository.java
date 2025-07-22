package com.one.alura.ForumHub.repository;

import com.one.alura.ForumHub.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AnswerRepository extends JpaRepository<Answer, UUID> {
    boolean existsAnswersByMessageAndSolution(String message, String solution);
}
