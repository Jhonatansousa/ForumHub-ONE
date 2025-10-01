package com.one.alura.ForumHub.service;

import com.one.alura.ForumHub.dto.CourseRequestDTO;

import java.util.UUID;

public interface ICourseService {
    void createCourse(CourseRequestDTO request);
    void updateCourse(CourseRequestDTO request, UUID courseId);
}
