package com.iread.backend.project.mapper;

import com.iread.backend.project.dto.StoryDTO;
import com.iread.backend.project.entity.Story;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class StoryMapper {

    private final ModelMapper modelMapper;

    public StoryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public StoryDTO mapToDTO(Story story) {
        return StoryDTO.builder()
                .id(story.getId())
                .title(story.getTitle())
                .dateCreation(story.getDateCreation())
                .accessWord(story.getAccessWord())
                .imgPreview(story.getActivity().getImgPreview())
                .build();
    }

    public Story mapToModel(StoryDTO storyDTO) {
        return modelMapper.map(storyDTO, Story.class);
    }

}
