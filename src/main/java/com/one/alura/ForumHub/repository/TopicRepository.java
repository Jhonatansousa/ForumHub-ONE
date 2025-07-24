package com.one.alura.ForumHub.repository;

import com.one.alura.ForumHub.entity.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.Optional;
import java.util.UUID;

public interface TopicRepository extends JpaRepository<Topic, UUID> {
    boolean existsByTitleAndMessage(String title, String message);

    @Query("SELECT t FROM Topic t LEFT JOIN FETCH t.answer WHERE t.id = :id")
    Optional<Topic> findByIdWithAnswers(UUID id);

    @Query(value = "SELECT DISTINCT t FROM Topic t LEFT JOIN FETCH t.answer",
    countQuery = "SELECT COUNT(t) FROM Topic t")
    Page<Topic> findAllWithAnswers(Pageable pageable);

    @Override
    @NonNull
    Page<Topic> findAll(@NonNull Pageable pageable);
}
