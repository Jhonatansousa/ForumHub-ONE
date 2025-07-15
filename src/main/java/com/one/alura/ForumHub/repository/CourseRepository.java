package com.one.alura.ForumHub.repository;

import com.one.alura.ForumHub.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {
    Course findByName(String name);

    boolean existsByNameIgnoreCase(String name);


    Course findByNameIgnoreCase(String name);
}
