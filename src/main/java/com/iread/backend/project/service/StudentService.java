package com.iread.backend.project.service;

import com.iread.backend.project.dto.StoryDTORequest;
import com.iread.backend.project.dto.StoryResponse;
import com.iread.backend.project.entity.Activity;
import com.iread.backend.project.entity.Story;
import com.iread.backend.project.entity.Student;
import com.iread.backend.project.entity.StudentActivity;
import com.iread.backend.project.exception.ResourceNotFoundException;
import com.iread.backend.project.repository.ActivityRepository;
import com.iread.backend.project.repository.StoryRepository;
import com.iread.backend.project.repository.StudentActivityRepository;
import com.iread.backend.project.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentActivityRepository studentActivityRepository;
    private final StoryRepository storyRepository;
    private final ActivityRepository activityRepository;

    public Student enterName(Student student) {
        return  studentRepository.save(student);
    }

    public StoryResponse accessStory(StoryDTORequest storyDTORequest) {
        Story story = storyRepository.findStoryByAccessWord(storyDTORequest.getAccessWord());
        if (story != null) {
            return new StoryResponse(story.getId(), story.getAccessWord(), story.getActive());
        } else {
            throw new IllegalStateException("No se puede acceder a la historia.");
        }
    }

    public StudentActivity completeActivity(Long studentId, StudentActivity studentActivity, Long activityId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));

        Activity activity = activityRepository.findById(activityId)
                        .orElseThrow(() -> new ResourceNotFoundException("Activity not found with id: " + studentId));

        studentActivity.setStudent(student);
        studentActivity.setActivity(activity);
        return studentActivityRepository.save(studentActivity);
    }

}
