package com.one.alura.ForumHub.controller;

import com.one.alura.ForumHub.dto.APIResponse;
import com.one.alura.ForumHub.dto.answer.AnswerCreateRequestDTO;
import com.one.alura.ForumHub.dto.answer.AnswerEditRequestDTO;
import com.one.alura.ForumHub.service.IAnswerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answer")
@RequiredArgsConstructor
public class AnswerController {

    private final IAnswerService answerService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    public ResponseEntity<APIResponse<Void>> createAnswer(@Valid @RequestBody AnswerCreateRequestDTO request) {

        answerService.createAnswer(request);

        APIResponse<Void> response = APIResponse.<Void>builder()
                .status("SUCCESS")
                .results(null)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping
    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    public ResponseEntity<APIResponse<Void>> updateAnswer(@Valid @RequestBody AnswerEditRequestDTO request) {

        answerService.editAnswer(request);

        APIResponse<Void> response = APIResponse.<Void>builder()
                .status("SUCCESS")
                .results(null)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
