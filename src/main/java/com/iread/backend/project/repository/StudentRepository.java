package com.iread.backend.project.repository;

import com.iread.backend.project.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByNameStudent(String nameStudent);
}
