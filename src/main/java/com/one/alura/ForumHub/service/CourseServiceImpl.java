package com.one.alura.ForumHub.service;

import com.one.alura.ForumHub.dto.CourseRequestDTO;
import com.one.alura.ForumHub.exception.DuplicatedContentException;
import com.one.alura.ForumHub.mapper.CourseMapper;
import com.one.alura.ForumHub.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

}
