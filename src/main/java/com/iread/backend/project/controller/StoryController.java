package com.iread.backend.project.controller;

import com.iread.backend.project.dto.StoryDTO;
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
import java.util.Map;

@RestController
@RequestMapping("/api/stories")
@CrossOrigin(origins = "https://iread4learnplaytech.netlify.app")
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

    @GetMapping("/byTeacher/{teacherId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<StoryDTO>> getStoriesByTeacherId(@PathVariable Long teacherId) {
        List<StoryDTO> storyDTOS = storyService.findAllStoriesByTeacherId(teacherId);
        return ResponseEntity.ok(storyDTOS);
    }

    @PutMapping("/activate/{storyId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public String activateStory(@PathVariable Long storyId) {
        return storyService.activateStory(storyId);
    }

    //trae la actividad al niño: publicEndpoint
    @GetMapping("/{storyId}/activity")
    public ResponseEntity<Activity> getActivityByStoryId(@PathVariable Long storyId) {
        Activity activity = storyService.getActivityByStoryId(storyId);
        return ResponseEntity.ok(activity);
    }

    @PutMapping("/deactivate/{storyId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Map<String, Object>> deactivateStory(@PathVariable Long storyId) {
        Map<String, Object> response = storyService.deactivateStory(storyId);
        return ResponseEntity.ok(response);
    }
}
