package com.iread.backend.project.mapper;

import com.iread.backend.project.dto.TeacherDTO;
import com.iread.backend.project.dto.TeacherDTORequest;
import com.iread.backend.project.entity.Teacher;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TeacherMapper {

    private final ModelMapper modelMapper;

    public TeacherMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Teacher mapToModel(TeacherDTO teacherDTO) {
        return modelMapper.map(teacherDTO, Teacher.class);
    }

    public TeacherDTO mapToDTO(Teacher teacher) {
        return modelMapper.map(teacher, TeacherDTO.class);
    }

    public void updateModel(TeacherDTORequest teacherDTORequest, Teacher teacher) {
        modelMapper.map(teacherDTORequest, teacher);
    }

}