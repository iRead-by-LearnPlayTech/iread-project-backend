package com.iread.backend.project.service;

import com.iread.backend.project.dto.StoryDTO;
import com.iread.backend.project.entity.Activity;
import com.iread.backend.project.entity.Story;
import com.iread.backend.project.entity.StudentActivity;
import com.iread.backend.project.entity.Teacher;
import com.iread.backend.project.exception.ResourceNotFoundException;
import com.iread.backend.project.mapper.StoryMapper;
import com.iread.backend.project.repository.ActivityRepository;
import com.iread.backend.project.repository.StoryRepository;
import com.iread.backend.project.repository.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StoryService {

    private final StoryRepository storyRepository;
    private final TeacherRepository teacherRepository;
    private final ActivityRepository activityRepository;
    private final StoryMapper storyMapper;

    public Story createStoryForTeacher(Long teacherId, Story story) throws ResourceNotFoundException {
        Teacher teacher = teacherRepository.findTeacherById(teacherId);

        if (teacher != null) {
            story.setTeacher(teacher);
            story.prePersist();
            return storyRepository.save(story);

        } else {
            throw new ResourceNotFoundException("No se encontró al profesor con el ID: " + teacherId);
        }
    }

    public Story assignActivityToStory(Long storyId, Activity activityDetails) throws ResourceNotFoundException {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new ResourceNotFoundException("Historia no encontrada: " + storyId));

        Activity newActivity = new Activity();
        newActivity.setJsonConverted(activityDetails.getJsonConverted());
        newActivity.setImgPreview(activityDetails.getImgPreview());

        newActivity.setStory(story);
        activityRepository.save(newActivity);

        story.setActivity(newActivity);
        storyRepository.save(story);

        return story;
    }

    public List<StoryDTO> findAllStoriesByTeacherId(Long teacherId) {
        List<Story> stories = storyRepository.findAllStoriesByTeacherId(teacherId);

        return stories.stream()
                .map(storyMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    public Activity getActivityByStoryId(Long storyId) throws ResourceNotFoundException {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new ResourceNotFoundException("Story not found with id: " + storyId));

        return story.getActivity();
    }

    public String activateStory(Long storyId) throws ResourceNotFoundException {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new ResourceNotFoundException("Story not found with id: " + storyId));

        story.setActive(true);
        storyRepository.save(story);

        return story.getTitle();
    }

}
