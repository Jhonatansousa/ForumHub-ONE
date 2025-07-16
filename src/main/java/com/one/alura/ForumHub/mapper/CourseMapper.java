package com.one.alura.ForumHub.mapper;

import com.one.alura.ForumHub.dto.CourseRequestDTO;
import com.one.alura.ForumHub.entity.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    public Course toEntity(CourseRequestDTO request) {
        Course course = new Course();
        course.setName(request.name());
        course.setCategory(request.category());
        return course;
    }

}
