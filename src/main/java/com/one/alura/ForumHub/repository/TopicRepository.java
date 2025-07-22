package com.one.alura.ForumHub.repository;

import com.one.alura.ForumHub.entity.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TopicRepository extends JpaRepository<Topic, UUID> {
    boolean existsByTitleAndMessage(String title, String message);
    Page<Topic> findAll(Pageable pageable);
}
