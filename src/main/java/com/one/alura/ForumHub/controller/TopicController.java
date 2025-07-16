package com.one.alura.ForumHub.controller;

import com.one.alura.ForumHub.dto.APIResponse;
import com.one.alura.ForumHub.dto.topic.TopicRequestDTO;
import com.one.alura.ForumHub.dto.topic.TopicResponseDTO;
import com.one.alura.ForumHub.service.ITopicService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TopicController {


    private final ITopicService topicService;

    @PostMapping("/topicos")
    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    public ResponseEntity<APIResponse<TopicResponseDTO>> createTopic(@Valid @RequestBody TopicRequestDTO request){
        TopicResponseDTO response = topicService.createNewTopic(request);
        APIResponse<TopicResponseDTO> apiResponse = APIResponse.<TopicResponseDTO>builder()
                .status("SUCCESS")
                .results(response)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
}
