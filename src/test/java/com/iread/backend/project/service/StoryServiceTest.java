package com.iread.backend.project.service;

import com.iread.backend.project.entity.Activity;
import com.iread.backend.project.entity.Story;
import com.iread.backend.project.entity.Teacher;
import com.iread.backend.project.exception.ResourceNotFoundException;
import com.iread.backend.project.repository.ActivityRepository;
import com.iread.backend.project.repository.StoryRepository;
import com.iread.backend.project.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StoryServiceTest {
    @Mock
    private StoryRepository storyRepository;
    @Mock
    private TeacherRepository teacherRepository;
    @Mock
    private ActivityRepository activityRepository;

    @InjectMocks
    private StoryService storyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createStoryForTeacher_Successful() {
        // Arrange
        Long teacherId = 1L;
        Story story = new Story();

        Teacher teacher = new Teacher();
        teacher.setId(teacherId);

        when(teacherRepository.findTeacherById(teacherId)).thenReturn(teacher);
        when(storyRepository.save(story)).thenReturn(story);

        // Act
        Story result = assertDoesNotThrow(() -> storyService.createStoryForTeacher(teacherId, story));

        // Assert
        assertNotNull(result);
        assertEquals(teacher, result.getTeacher());

        verify(teacherRepository, times(1)).findTeacherById(teacherId);
        verify(storyRepository, times(1)).save(story);
    }


    @Test
    void createStoryForTeacher_TeacherNotFound_ShouldThrowException() {
        // Arrange
        Long teacherId = 1L;
        Story story = new Story();

        when(teacherRepository.findTeacherById(teacherId)).thenReturn(null);

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> storyService.createStoryForTeacher(teacherId, story));

        assertEquals("No se encontró al profesor con el ID: " + teacherId, exception.getMessage());

        verify(teacherRepository, times(1)).findTeacherById(teacherId);
        verify(storyRepository, never()).save(any(Story.class));
    }


    @Test
    void assignActivityToStory_Successful() {
        // Arrange
        Long storyId = 1L;
        Activity activityDetails = new Activity();

        Story existingStory = new Story();
        existingStory.setId(storyId);

        when(storyRepository.findById(storyId)).thenReturn(Optional.of(existingStory));
        when(activityRepository.save(any(Activity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(storyRepository.save(any(Story.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Story result = assertDoesNotThrow(() -> storyService.assignActivityToStory(storyId, activityDetails));

        // Assert
        assertNotNull(result);
        assertEquals(existingStory, result);

        verify(storyRepository, times(1)).findById(storyId);
        verify(activityRepository, times(1)).save(any(Activity.class));
        verify(storyRepository, times(1)).save(existingStory);
    }


    @Test
    void assignActivityToStory_StoryNotFound_ShouldThrowException() {
        // Arrange
        Long storyId = 1L;
        Activity activityDetails = new Activity();

        when(storyRepository.findById(storyId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> storyService.assignActivityToStory(storyId, activityDetails));

        assertEquals("Historia no encontrada: " + storyId, exception.getMessage());

        verify(storyRepository, times(1)).findById(storyId);
        verify(activityRepository, never()).save(any(Activity.class));
        verify(storyRepository, never()).save(any(Story.class));
    }

}