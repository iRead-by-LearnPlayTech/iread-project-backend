package com.iread.backend.project.repository;

import com.iread.backend.project.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Optional<Teacher> findUserByEmail(String email);

    @Modifying
    @Query("UPDATE Teacher t SET t.enabled = true WHERE t.email = :email")
    int enableUser(String email);

}
