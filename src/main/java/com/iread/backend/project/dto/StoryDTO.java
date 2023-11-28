package com.iread.backend.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoryDTO {
    private Long id;
    private String title;
    private LocalDateTime dateCreation;
    private String accessWord;
    private String imgPreview;
}
