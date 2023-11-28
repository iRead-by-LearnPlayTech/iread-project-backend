package com.iread.backend.project.service;

import com.iread.backend.project.dto.StoryDTO;
import com.iread.backend.project.entity.Activity;
import com.iread.backend.project.entity.Story;
import com.iread.backend.project.entity.Teacher;
import com.iread.backend.project.exception.ResourceNotFoundException;
import com.iread.backend.project.mapper.StoryMapper;
import com.iread.backend.project.repository.ActivityRepository;
import com.iread.backend.project.repository.StoryRepository;
import com.iread.backend.project.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
    @Mock
    private StoryMapper storyMapper;

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


    @Test
    void findAllStoriesByTeacherId_Successful() {
        // Arrange
        Long teacherId = 1L;

        Story story1 = new Story();
        story1.setId(1L);
        story1.setTitle("Story 1");
        story1.setDateCreation(LocalDateTime.now());
        story1.setAccessWord("Access1");

        Story story2 = new Story();
        story2.setId(2L);
        story2.setTitle("Story 2");
        story2.setDateCreation(LocalDateTime.now());
        story2.setAccessWord("Access2");

        List<Story> stories = Arrays.asList(story1, story2);

        when(storyRepository.findAllStoriesByTeacherId(teacherId)).thenReturn(stories);

        StoryDTO storyDto1 = new StoryDTO(1L, "Story 1", LocalDateTime.now(), "Access1", "ImgPreview1");
        StoryDTO storyDto2 = new StoryDTO(2L, "Story 2", LocalDateTime.now(), "Access2", "ImgPreview2");

        when(storyMapper.mapToDTO(story1)).thenReturn(storyDto1);
        when(storyMapper.mapToDTO(story2)).thenReturn(storyDto2);

        // Act
        List<StoryDTO> result = storyService.findAllStoriesByTeacherId(teacherId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(storyDto1, result.get(0));
        assertEquals(storyDto2, result.get(1));

        verify(storyRepository, times(1)).findAllStoriesByTeacherId(teacherId);
        verify(storyMapper, times(2)).mapToDTO(any(Story.class));
    }


    @Test
    void findAllStoriesByTeacherId_NoStoriesFound() {
        // Arrange
        Long teacherId = 1L;

        when(storyRepository.findAllStoriesByTeacherId(teacherId)).thenReturn(Collections.emptyList());

        // Act
        List<StoryDTO> result = storyService.findAllStoriesByTeacherId(teacherId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(storyRepository, times(1)).findAllStoriesByTeacherId(teacherId);
        verify(storyMapper, never()).mapToDTO(any(Story.class));
    }

    @Test
    void activateStory_Successful() {
        // Arrange
        Long storyId = 1L;

        Story existingStory = new Story();
        existingStory.setId(storyId);
        existingStory.setTitle("Test Story");
        existingStory.setActive(false);

        when(storyRepository.findById(storyId)).thenReturn(Optional.of(existingStory));

        // Act
        String result = storyService.activateStory(storyId);

        // Assert
        assertNotNull(result);
        assertEquals(existingStory.getTitle(), result);
        assertTrue(existingStory.getActive());

        // Verify that the save method was called
        verify(storyRepository, times(1)).save(existingStory);
    }


    @Test
    void activateStory_StoryNotFound() {
        // Arrange
        Long storyId = 1L;

        when(storyRepository.findById(storyId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> storyService.activateStory(storyId));

        // Verify that the save method was not called
        verify(storyRepository, never()).save(any(Story.class));
    }

}