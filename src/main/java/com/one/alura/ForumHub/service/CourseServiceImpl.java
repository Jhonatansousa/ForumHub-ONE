package com.one.alura.ForumHub.service;

import com.one.alura.ForumHub.dto.CourseRequestDTO;
import com.one.alura.ForumHub.entity.Course;
import com.one.alura.ForumHub.exception.DuplicatedContentException;
import com.one.alura.ForumHub.exception.ResourceNotFoundException;
import com.one.alura.ForumHub.mapper.CourseMapper;
import com.one.alura.ForumHub.repository.CourseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements ICourseService {

    private final CourseRepository courseRepo;
    private final CourseMapper courseMapper;


    @Override
    public void createCourse(CourseRequestDTO request) {
        if(courseRepo.existsByNameIgnoreCase(request.name())) {
            throw new DuplicatedContentException("Course already exists");
        }
        courseRepo.save(courseMapper.toEntity(request));
    }


    @Override
    public void updateCourse(CourseRequestDTO request, UUID courseId) {
        Course course = courseRepo.findById(courseId).orElseThrow(
                () -> new ResourceNotFoundException("Course with id: " + courseId + " not found")
        );

        if (course.getName().equals(request.name()) && course.getCategory().equals(request.category())) {
            throw new DuplicatedContentException("A course with the same name and category already exists. Please choose a different name or category.");
        }
        courseRepo.save(courseMapper.toEntity(request));
    }

}
