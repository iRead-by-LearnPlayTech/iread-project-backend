package com.iread.backend.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "story")
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "story_id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    @Size(min = 10, max = 25)
    private String title;

    @Column(name = "date_creation", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime dateCreation;

    @Column(name = "access_word", nullable = false, unique = true)
    @Size(min = 5, max = 15)
    private String accessWord;

    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    @JsonIgnore
    private Teacher teacher;

    @OneToOne(mappedBy = "story", cascade = CascadeType.ALL, orphanRemoval = true)
    private Activity activity;

    @PrePersist
    public void prePersist() {
        dateCreation = LocalDateTime.now();
        active = false;
    }
}