package com.iread.backend.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student_activity")
public class StudentActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_interaction_id", nullable = false)
    private Long id;

    @Column(name = "correct_answer", nullable = false)
    private int correctAnswer;

    @Column(name = "consulted_word", nullable = false)
    private int consultedWord;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "student_id")
    private Student student;

}
