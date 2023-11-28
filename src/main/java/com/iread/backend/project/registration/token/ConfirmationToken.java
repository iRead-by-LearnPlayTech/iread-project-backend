package com.iread.backend.project.registration.token;

import com.iread.backend.project.entity.Teacher;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@Entity
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "confirmation_token_id")
    private Long id;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(nullable = false, name = "teacher_id")
    private Teacher teacher;

    public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiresAt, Teacher teacher) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.teacher = teacher;
    }
}
