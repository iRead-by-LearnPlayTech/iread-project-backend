package com.iread.backend.project.controller;

import com.iread.backend.project.dto.StoryDTORequest;
import com.iread.backend.project.dto.StoryResponse;
import com.iread.backend.project.entity.Student;
import com.iread.backend.project.entity.StudentActivity;
import com.iread.backend.project.service.StudentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "http://localhost:4200/")
@Tag(name = "Student", description = "Student management APIs")
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/register")
    public ResponseEntity<Student> enterName(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.enterName(student));
    }

    @PostMapping("/access-story")
    public ResponseEntity<StoryResponse> accessStory(@RequestBody StoryDTORequest storyDTORequest) {
        StoryResponse storyResponse = studentService.accessStory(storyDTORequest);
        return ResponseEntity.ok(storyResponse);
    }

    @PostMapping("/{studentId}/studentActivities/{activityId}")
    public ResponseEntity<StudentActivity> completeActivity(@PathVariable Long studentId, @RequestBody StudentActivity studentActivity, @PathVariable Long activityId) {
        StudentActivity completedActivity = studentService.completeActivity(studentId, studentActivity, activityId);
        return ResponseEntity.ok(completedActivity);
    }

}
