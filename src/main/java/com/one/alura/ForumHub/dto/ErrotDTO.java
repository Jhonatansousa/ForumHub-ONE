package com.one.alura.ForumHub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrotDTO {
    private String field;
    private String errorMessage;
}
