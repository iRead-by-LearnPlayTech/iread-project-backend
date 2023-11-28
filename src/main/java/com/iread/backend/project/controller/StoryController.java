package com.iread.backend.project.controller;

import com.iread.backend.project.entity.Activity;
import com.iread.backend.project.entity.Story;
import com.iread.backend.project.service.StoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stories")
@CrossOrigin(origins = "http://localhost:4200/")
@Tag(name = "Story", description = "Story management APIs")
@AllArgsConstructor
public class StoryController {

    private final StoryService storyService;

    @PostMapping("/createStory/{teacherId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Story> createStory(@PathVariable Long teacherId, @RequestBody Story story) {
        Story createdStory = storyService.createStoryForTeacher(teacherId, story);
        return new ResponseEntity<>(createdStory, HttpStatus.CREATED);
    }

    @PutMapping("/{storyId}/activity")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Story> assignActivityToStory(@PathVariable Long storyId, @RequestBody Activity activityDetails) {
        Story updatedStory = storyService.assignActivityToStory(storyId, activityDetails);
        return ResponseEntity.ok(updatedStory);
    }


}
