package com.iread.backend.project.repository;

import com.iread.backend.project.entity.StudentActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentActivityRepository extends JpaRepository<StudentActivity, Long> {
}
