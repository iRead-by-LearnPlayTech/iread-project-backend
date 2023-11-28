package com.iread.backend.project.repository;

import com.iread.backend.project.entity.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoryRepository extends JpaRepository<Story,Long> {
    Story findStoryByAccessWord(String accessWord);
    List<Story> findAllStoriesByTeacherId(Long id);
}
