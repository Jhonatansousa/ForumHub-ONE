package com.one.alura.ForumHub.controller;

import com.one.alura.ForumHub.dto.APIResponse;
import com.one.alura.ForumHub.dto.topic.TopicRequestDTO;
import com.one.alura.ForumHub.dto.topic.TopicResponseDTO;
import com.one.alura.ForumHub.entity.Topic;
import com.one.alura.ForumHub.service.ITopicService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/topicos")
public class TopicController {


    private final ITopicService topicService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    public ResponseEntity<APIResponse<TopicResponseDTO>> createTopic(@Valid @RequestBody TopicRequestDTO request){
        TopicResponseDTO response = topicService.createNewTopic(request);
        APIResponse<TopicResponseDTO> apiResponse = APIResponse.<TopicResponseDTO>builder()
                .status("SUCCESS")
                .results(response)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }


    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'MODERATOR','ADMIN')")
    public ResponseEntity<APIResponse<Page<TopicResponseDTO>>> getAllTopics(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ){
        Page<TopicResponseDTO> topicPage = topicService.getAllTopics(pageable);

        APIResponse<Page<TopicResponseDTO>> apiResponse = APIResponse.<Page<TopicResponseDTO>>builder()
                .status("SUCCESS")
                .results(topicPage)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
