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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    @Mock
    private StoryRepository storyRepository;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private ActivityRepository activityRepository;
    @Mock
    private StudentActivityRepository studentActivityRepository;

    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void accessStory_StoryFound() {
        // Arrange
        String accessWord = "testAccessWord";
        StoryDTORequest storyDTORequest = new StoryDTORequest();
        storyDTORequest.setAccessWord(accessWord);

        Story existingStory = new Story();
        existingStory.setId(1L);
        existingStory.setAccessWord(accessWord);
        existingStory.setActive(true);

        when(storyRepository.findStoryByAccessWord(accessWord)).thenReturn(existingStory);

        // Act
        StoryResponse result = studentService.accessStory(storyDTORequest);

        // Assert
        assertNotNull(result);
        assertEquals(existingStory.getId(), result.getStoryId());
        assertEquals(existingStory.getAccessWord(), result.getAccessWord());
        assertTrue(result.getActive());

        // Verify that the repository method was called
        verify(storyRepository, times(1)).findStoryByAccessWord(accessWord);
    }

    @Test
    void accessStory_StoryNotFound() {
        // Arrange
        String accessWord = "nonExistentAccessWord";
        StoryDTORequest storyDTORequest = new StoryDTORequest();
        storyDTORequest.setAccessWord(accessWord);

        when(storyRepository.findStoryByAccessWord(accessWord)).thenReturn(null);

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> studentService.accessStory(storyDTORequest));

        // Verify that the repository method was called
        verify(storyRepository, times(1)).findStoryByAccessWord(accessWord);
    }

    @Test
    void enterName_SaveStudent() {
        // Arrange
        Student inputStudent = new Student();

        Student savedStudent = new Student();
        savedStudent.setId(1L);

        when(studentRepository.save(inputStudent)).thenReturn(savedStudent);

        // Act
        Student result = studentService.enterName(inputStudent);

        // Assert
        assertNotNull(result);
        assertEquals(savedStudent.getId(), result.getId());
        verify(studentRepository, times(1)).save(inputStudent);
    }



    @Test
    void completeActivity_Successful() {
        // Arrange
        Long studentId = 1L;
        Long activityId = 2L;

        Student student = new Student();
        student.setId(studentId);

        Activity activity = new Activity();
        activity.setId(activityId);

        StudentActivity studentActivity = new StudentActivity();

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(activityRepository.findById(activityId)).thenReturn(Optional.of(activity));
        when(studentActivityRepository.save(studentActivity)).thenReturn(studentActivity);

        // Act
        StudentActivity result = studentService.completeActivity(studentId, studentActivity, activityId);

        // Assert
        assertNotNull(result);
        assertEquals(studentId, result.getStudent().getId());
        assertEquals(activityId, result.getActivity().getId());

        verify(studentRepository, times(1)).findById(studentId);
        verify(activityRepository, times(1)).findById(activityId);
        verify(studentActivityRepository, times(1)).save(studentActivity);
    }


    @Test
    void completeActivity_StudentNotFound() {
        // Arrange
        Long studentId = 1L;
        Long activityId = 2L;

        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class,
                () -> studentService.completeActivity(studentId, new StudentActivity(), activityId));

        verify(studentRepository, times(1)).findById(studentId);
        verify(activityRepository, never()).findById(activityId);
        verify(studentActivityRepository, never()).save(any());
    }


    @Test
    void completeActivity_ActivityNotFound() {
        // Arrange
        Long studentId = 1L;
        Long activityId = 2L;

        Student student = new Student();
        student.setId(studentId);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(activityRepository.findById(activityId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class,
                () -> studentService.completeActivity(studentId, new StudentActivity(), activityId));

        verify(studentRepository, times(1)).findById(studentId);
        verify(activityRepository, times(1)).findById(activityId);
        verify(studentActivityRepository, never()).save(any());
    }

}