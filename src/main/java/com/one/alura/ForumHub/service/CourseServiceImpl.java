package com.one.alura.ForumHub.service;

import com.one.alura.ForumHub.dto.CourseRequestDTO;
import com.one.alura.ForumHub.entity.Course;
import com.one.alura.ForumHub.exception.CourseServiceBusinessException;
import com.one.alura.ForumHub.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements ICourseService {

    private final CourseRepository courseRepo;

    @Override
    public void createCourse(CourseRequestDTO request) {
        if(courseRepo.existsByNameIgnoreCase(request.name())) {
            throw new CourseServiceBusinessException("Course already exists");
        }
        Course course = new Course();
        course.setName(request.name());
        course.setCategory(request.category());
        courseRepo.save(course);
    }

}
