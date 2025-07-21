package com.one.alura.ForumHub.repository;

import com.one.alura.ForumHub.entity.Profile;
import com.one.alura.ForumHub.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {
    Optional<Profile> findByName(RoleName name);
}
