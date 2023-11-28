package com.iread.backend.project.service;

import com.iread.backend.project.entity.Activity;
import com.iread.backend.project.entity.Story;
import com.iread.backend.project.entity.Teacher;
import com.iread.backend.project.exception.ResourceNotFoundException;
import com.iread.backend.project.repository.ActivityRepository;
import com.iread.backend.project.repository.StoryRepository;
import com.iread.backend.project.repository.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StoryService {

    private final StoryRepository storyRepository;
    private final TeacherRepository teacherRepository;
    private final ActivityRepository activityRepository;

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

        story.setActivities(newActivity);
        storyRepository.save(story);

        return story;
    }

}
