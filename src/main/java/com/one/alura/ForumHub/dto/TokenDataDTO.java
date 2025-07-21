package com.one.alura.ForumHub.dto;

import java.util.Set;

public record TokenDataDTO(String email, Set<String> roles) {
}
