package com.one.alura.ForumHub.controller;

import com.one.alura.ForumHub.dto.APIResponse;
import com.one.alura.ForumHub.dto.CourseRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/course")
public class CourseController {

    @PostMapping
    public ResponseEntity<APIResponse<Void>> createCourse(@Valid @RequestBody CourseRequestDTO request) {

        //fazer o service ->

        APIResponse<Void> apiResponse = APIResponse.<Void>builder()
                .status("SUCCESS")
                .results(null)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
}
