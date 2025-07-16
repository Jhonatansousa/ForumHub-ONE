package com.one.alura.ForumHub.controller;

import com.one.alura.ForumHub.dto.APIResponse;
import com.one.alura.ForumHub.dto.CourseRequestDTO;
import com.one.alura.ForumHub.service.ICourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {

    private final ICourseService courseService;

    @PostMapping
    @PreAuthorize("hasAnyRole('MODERATOR','ADMIN')")
    public ResponseEntity<APIResponse<Void>> createCourse(@Valid @RequestBody CourseRequestDTO request) {

        courseService.createCourse(request);

        APIResponse<Void> apiResponse = APIResponse.<Void>builder()
                .status("SUCCESS")
                .results(null)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
}
