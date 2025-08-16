package com.one.alura.ForumHub.controller;

import com.one.alura.ForumHub.dto.APIResponse;
import com.one.alura.ForumHub.dto.topic.TopicRequestDTO;
import com.one.alura.ForumHub.dto.topic.TopicResponseDTO;
import com.one.alura.ForumHub.dto.topic.TopicWithAnswerResponseDTO;
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

@RestController
@AllArgsConstructor
@RequestMapping("/topicos")
public class TopicController {


    private final ITopicService topicService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    public ResponseEntity<APIResponse<TopicWitithubbhAnswerResponseDTO>> createTopic(@Valid @RequestBody TopicRequestDTO request){
        TopicWithAnswerResponseDTO response = topicService.createNewTopic(request);
        APIResponse<TopicWithAnswerResponseDTO> apiResponse = APIResponse.<TopicWithAnswerResponseDTO>builder()
                .status("SUCCESS")
                .results(response)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }


    @GetMapping("/extended")
    @PreAuthorize("hasAnyRole('USER', 'MODERATOR','ADMIN')")
    public ResponseEntity<APIResponse<Page<TopicWithAnswerResponseDTO>>> getAllTopicsWithAnswers(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ){
        Page<TopicWithAnswerResponseDTO> topicPage = topicService.getAllTopicsWithAnswers(pageable);

        APIResponse<Page<TopicWithAnswerResponseDTO>> apiResponse = APIResponse.<Page<TopicWithAnswerResponseDTO>>builder()
                .status("SUCCESS")
                .results(topicPage)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/basic")
    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    public ResponseEntity<APIResponse<Page<TopicResponseDTO>>> getAllTopics(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC)  Pageable pageable
    ) {
        Page<TopicResponseDTO> topicPage = topicService.getAllTopics(pageable);
        APIResponse<Page<TopicResponseDTO>> apiResponse = APIResponse.<Page<TopicResponseDTO>>builder()
                .status("SUCCESS")
                .results(topicPage)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

//    @PatchMapping("/")
//    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
//    public ResponseEntity<APIResponse<>>
}
