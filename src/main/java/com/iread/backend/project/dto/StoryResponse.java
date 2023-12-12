package com.iread.backend.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoryResponse {
    private Long storyId;
    private String accessWord;
    private Boolean active;
}