package com.one.alura.ForumHub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public record ErrorDTO (
        String field, String errorMessage
){
}
