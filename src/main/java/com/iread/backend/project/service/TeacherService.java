package com.iread.backend.project.service;

import com.iread.backend.project.entity.Teacher;

public interface TeacherService {
    String singUpUser(Teacher user);
    int enableUser(String email);
}
