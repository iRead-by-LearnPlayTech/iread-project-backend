package com.iread.backend.project.controller;

import com.iread.backend.project.dto.StoryDTORequest;
import com.iread.backend.project.dto.StoryResponse;
import com.iread.backend.project.entity.Student;
import com.iread.backend.project.service.StudentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/students")
@CrossOrigin(origins = "http://localhost:4200/")
@Tag(name = "Student", description = "Student management APIs")
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/access-story")
    public ResponseEntity<StoryResponse> accessStory(@RequestBody StoryDTORequest storyDTORequest) {
        StoryResponse storyResponse = studentService.accessStory(storyDTORequest);
        return ResponseEntity.ok(storyResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<Student> enterName(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.enterName(student));
    }
}
