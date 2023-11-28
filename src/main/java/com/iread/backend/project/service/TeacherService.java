package com.iread.backend.project.service;

import com.iread.backend.project.dto.AuthDTO;
import com.iread.backend.project.dto.AuthenticationDTORequest;
import com.iread.backend.project.entity.Teacher;

public interface TeacherService {
    String singUpUser(Teacher user);
    AuthDTO authenticate(AuthenticationDTORequest request);
    int enableUser(String email);
}
