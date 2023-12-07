package com.iread.backend.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "activity")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_id", nullable = false)
    private Long id;

    @Column(name = "json_converted", nullable = false, columnDefinition = "LONGTEXT")
    private String jsonConverted;

    @Column(name = "img_preview", nullable = false, columnDefinition = "LONGTEXT")
    private String imgPreview;

    @OneToOne
    @JoinColumn(name = "story_id")
    @JsonIgnore
    private Story story;

    @OneToMany(mappedBy = "activity", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<StudentActivity> studentActivities;

}