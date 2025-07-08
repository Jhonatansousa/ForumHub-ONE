package com.one.alura.ForumHub.dto;

import com.one.alura.ForumHub.entity.UserRole;

public record TokenDataDTO(String email, UserRole role) {
}
